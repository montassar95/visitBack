package com.cgpr.visitApp.services.Impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cgpr.visitApp.config.Simplification;
import com.cgpr.visitApp.dto.DayDto;
import com.cgpr.visitApp.dto.EventSMSDto;
import com.cgpr.visitApp.dto.PenalSyntheseDto;
import com.cgpr.visitApp.dto.PrisonerDto;
import com.cgpr.visitApp.dto.RelationshipSalleAndTimeDTO;
import com.cgpr.visitApp.dto.RelationshipTypeAmenDto;
import com.cgpr.visitApp.dto.RelationshipTypeDto;
import com.cgpr.visitApp.dto.VisitorDto;
import com.cgpr.visitApp.dto.amenDto.ApiResponseAmenRoomDto;
import com.cgpr.visitApp.dto.amenDto.ApiResponseAmenVisitorDto;
import com.cgpr.visitApp.model.Prisoner;
import com.cgpr.visitApp.model.RelationshipType;
import com.cgpr.visitApp.model.Visitor;
import com.cgpr.visitApp.repository.PrisonerPenalRepository;
import com.cgpr.visitApp.repository.PrisonerRepository;
import com.cgpr.visitApp.repository.RechercherAffairesRepositoryCustom;
import com.cgpr.visitApp.repository.RelationshipTypeRepository;
import com.cgpr.visitApp.repository.VisitorRepository;
import com.cgpr.visitApp.services.RelationshipTypeService;
import com.cgpr.visitApp.services.VisitorService;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@EnableScheduling
@Service
@Slf4j
public class RelationshipTypeImpl implements RelationshipTypeService {
	Simplification simplifier = new Simplification();
	
	 private String simplifyOrNull(String value) {
		    String simplified =  value != null ? value.trim() : "" ;
		    return simplified.isEmpty() ? null : simplified;
		}
	 
	private VisitorService visitorService;

	private RelationshipTypeRepository relationshipTypeRepository;
	private PrisonerRepository prisonerRepository;
	private VisitorRepository visitorRepository;
	private PrisonerPenalRepository prisonerPenalRepository;
	private RechercherAffairesRepositoryCustom rechercherAffairesRepositoryCustom ;
	@Autowired
	public RelationshipTypeImpl(RelationshipTypeRepository relationshipTypeRepository,
			PrisonerRepository prisonerRepository, VisitorRepository visitorRepository, VisitorService visitorService,
			PrisonerPenalRepository prisonerPenalRepository,RechercherAffairesRepositoryCustom rechercherAffairesRepositoryCustom) {

		this.relationshipTypeRepository = relationshipTypeRepository;
		this.prisonerRepository = prisonerRepository;
		this.visitorRepository = visitorRepository;
		this.prisonerPenalRepository = prisonerPenalRepository;
		this.visitorService = visitorService;
		this.rechercherAffairesRepositoryCustom=rechercherAffairesRepositoryCustom;
	}

	@Override
	@Transactional
	public List<RelationshipType> save(RelationshipTypeDto relationshipTypeDto) {
		log.error(relationshipTypeDto.toString());
		List<RelationshipType> relationshipTypes = RelationshipTypeDto.toListEntity(relationshipTypeDto);

		// Utilisez une map pour stocker les prisonniers existants (ID -> Prisoner)
		Map<String, Prisoner> existingPrisonersMap = new HashMap<>();

		// Utilisez une map pour stocker les visiteurs existants (ID -> Visitor)
		Map<Long, Visitor> existingVisitorsMap = new HashMap<>();

		for (RelationshipType relationshipType : relationshipTypes) {
			Prisoner prisoner = relationshipType.getPrisoner();
			Visitor visitor = relationshipType.getVisitor();

			// Récupérez ou enregistrez le Prisoner existant
			existingPrisonersMap.putIfAbsent(prisoner.getIdPrisoner(), getExistingPrisoner(prisoner));

		}

		// Supprimez tous les anciens RelationshipType associés aux prisonniers affectés
		// Affichez les clés de la map

		relationshipTypeRepository.updateStatutResidanceToF(existingPrisonersMap.keySet());

		// Mettez à jour les RelationshipType avec les Prisoners et Visitors existants
		List<RelationshipType> savedRelationshipTypes = relationshipTypes.stream().map(relationshipType -> {
			relationshipType.setPrisoner(existingPrisonersMap.get(relationshipType.getPrisoner().getIdPrisoner()));
   //  relationshipType.setVisitor(existingVisitorsMap.get(relationshipType.getVisitor().getIdVisitor()));

			relationshipType.setVisitor(visitorRepository.save(relationshipType.getVisitor()));
			String statutResidance = relationshipType.getStatutResidance();
			statutResidance = statutResidance.toUpperCase().trim();
			if ((statutResidance.toString().equals("M".toString()) || statutResidance.toString().equals("E".toString()))
					&& relationshipType.getDay() != null && relationshipType.getTime() != null) {
				statutResidance = "O";
				log.error("mise  a jou de statut de O a M");
			}
			relationshipType.setStatutResidance(statutResidance);

			switch (relationshipType.getStatutResidance().toString()) {
			case "E":
				relationshipType.setLibelleStatutResidance("Entrant");
				//System.out.println(relationshipType.getLibelleStatutResidance());
				break;
			case "O":
				relationshipType.setLibelleStatutResidance("Visit");
				//System.out.println(relationshipType.getLibelleStatutResidance());
				break;
			case "M":
				relationshipType.setLibelleStatutResidance("Mutation");
				//System.out.println(relationshipType.getLibelleStatutResidance());
				break;
			case "L":
				relationshipType.setLibelleStatutResidance("Liberation");
				//System.out.println(relationshipType.getLibelleStatutResidance());
				break;
			default:
				//System.out.println("Option non valide");
				break;
			}
			return relationshipType;
		}).collect(Collectors.toList());

		// Enregistrez les nouveaux RelationshipType
		return relationshipTypeRepository.saveAll(savedRelationshipTypes);
	}

	private Prisoner getExistingPrisoner(Prisoner prisoner) {
		Optional<Prisoner> existingPrisoner = prisonerRepository.findById(prisoner.getIdPrisoner());

		return existingPrisoner.orElseGet(() -> prisonerRepository.save(prisoner));
	}

	@Override
	public RelationshipTypeDto findByPrisonerId(String idPrisoner) {
		if (idPrisoner == null || idPrisoner.toString() == "null") {
			log.error("Prisoner id is null");
			return null;
		} else {

			List<RelationshipType> listRelationship = relationshipTypeRepository
					.findByPrisonerIdAndStatutOorMorE(idPrisoner);

			if (!listRelationship.isEmpty()) {
				log.error(idPrisoner + " exist   dans RelationshipType");
				RelationshipTypeDto r = RelationshipTypeDto.fromListEntity(listRelationship);
				if (r != null) {
					Map<String, String> resultat = prisonerPenalRepository.findEtatAndLibelleById(r.getIdPrisoner());

					if (resultat != null) {
						String etat = resultat.get("etat");
						String libelleNature = resultat.get("libelleNature");
						r.setEtat(etat);
						r.setAffaires(libelleNature);
						// Maintenant, vous pouvez utiliser les valeurs comme vous le souhaitez

					} else {
						// Gérez le cas où aucun résultat n'est trouvé
						//System.out.println("Aucun résultat trouvé.");
					}

					// Appeler la méthode Mono et attendre la réponse (bloquer l'exécution)
					String parameter = r.getCodeGouvernorat() + r.getCodePrison() + r.getCodeResidance();
					Mono<ApiResponseAmenRoomDto> monoApiResponse = visitorService.callAmenRoomAPI(parameter.trim());
					ApiResponseAmenRoomDto apiResponse = monoApiResponse.block();

					if (apiResponse != null && apiResponse.getResult() != null && apiResponse.getResult().size() > 0) {
						r.setRoom(apiResponse.getResult().get(0).getLibcentre() + " " + apiResponse.getResult().get(0).getTlibelle_pavillon() + " غرفة "
								+ apiResponse.getResult().get(0).getTcode_chambre());
						
						 r .setNumeroPecule(apiResponse.getResult().get(0).getTnumpecule());;
				     	  r .setAnneePecule(apiResponse.getResult().get(0).getTannee_pecule());;
				     	  r .setSoldeExistant(apiResponse.getResult().get(0).getTsolde_existant());;          // "45560"
				  	      r .setIdentifiantRibPoste(apiResponse.getResult().get(0).getIdentifiantRibPoste());      // "GG250120784853"
				  	      r .setIdentifianttaxiphone(apiResponse.getResult().get(0).getIdentifianttaxiphone());    // "2501207"
					} else {
						r.setRoom("لا يوجد غرفة ");
						// Gérer le cas où la réponse de l'API est nulle ou la liste de résultats est
						// nulle
						Collections.emptyList(); // Ou renvoyez une liste vide, ou une autre action appropriée
					}
				}
				 
				return r;
			} else {
				log.error(idPrisoner + " n'exist pas  dans RelationshipType");
				Optional<PrisonerDto> prisonerDto = prisonerPenalRepository.findById(idPrisoner);
				if (prisonerDto.isPresent()) {
					log.error(idPrisoner + "  exist   dans penal" + prisonerDto.get().toString());
					RelationshipTypeDto r = RelationshipTypeDto
							.convertPrisonerDtoToRelationshipTypeDto(prisonerDto.get(), new ArrayList<VisitorDto>());
					// Appeler la méthode Mono et attendre la réponse (bloquer l'exécution)
					String parameter = r.getCodeGouvernorat() + r.getCodePrison() + r.getCodeResidance();
					Mono<ApiResponseAmenRoomDto> monoApiResponse = visitorService.callAmenRoomAPI(parameter.trim());
					ApiResponseAmenRoomDto apiResponse = monoApiResponse.block();

					if (apiResponse != null && apiResponse.getResult() != null && apiResponse.getResult().size() > 0) {
						r.setRoom(apiResponse.getResult().get(0).getLibcentre() + " " + apiResponse.getResult().get(0).getTlibelle_pavillon() + " غرفة "
								+ apiResponse.getResult().get(0).getTcode_chambre());
						r .setNumeroPecule(apiResponse.getResult().get(0).getTnumpecule());;
				     	  r .setAnneePecule(apiResponse.getResult().get(0).getTannee_pecule());;
				     	  r .setSoldeExistant(apiResponse.getResult().get(0).getTsolde_existant());;          // "45560"
				  	      r .setIdentifiantRibPoste(apiResponse.getResult().get(0).getIdentifiantRibPoste());      // "GG250120784853"
				  	      r .setIdentifianttaxiphone(apiResponse.getResult().get(0).getIdentifianttaxiphone());    // "2501207"
					} else {
						// Gérer le cas où la réponse de l'API est nulle ou la liste de résultats est
						// nulle
						r.setRoom("لا يوجد غرفة ");
						Collections.emptyList(); // Ou renvoyez une liste vide, ou une autre action appropriée
					}
					Map<String, String> resultat = prisonerPenalRepository.findEtatAndLibelleById(r.getIdPrisoner());

					if (resultat != null) {
						String etat = resultat.get("etat");
						String libelleNature = resultat.get("libelleNature");
						r.setEtat(etat);
						r.setAffaires(libelleNature);
						// Maintenant, vous pouvez utiliser les valeurs comme vous le souhaitez

					} else {
						// Gérez le cas où aucun résultat n'est trouvé
						//System.out.println("Aucun résultat trouvé.");
					}
					//System.err.println("r.toString()");
					//System.err.println(r.toString());
					return r;
					// }

				}

			}
		}
		log.error(idPrisoner + " n'exist pas ");
		return null;

	}

	
	@Override
	public RelationshipTypeDto findByPrisonerIdFromPenal(String idPrisoner) {
		
		if (idPrisoner == null || idPrisoner.toString() == "null") {
			log.error("Prisoner id is null");
			return null;
		} else {

			 
 
				log.error(idPrisoner + " n'exist pas  dans RelationshipType");
				Optional<PrisonerDto> prisonerDto = prisonerPenalRepository.findById(idPrisoner);
				if (prisonerDto.isPresent()) {
					log.error(idPrisoner + "  exist   dans penal" + prisonerDto.get().toString());
					RelationshipTypeDto r = RelationshipTypeDto
							.convertPrisonerDtoToRelationshipTypeDto(prisonerDto.get(), new ArrayList<VisitorDto>());
					// Appeler la méthode Mono et attendre la réponse (bloquer l'exécution)
					String parameter = r.getCodeGouvernorat() + r.getCodePrison() + r.getCodeResidance();
					Mono<ApiResponseAmenRoomDto> monoApiResponse = visitorService.callAmenRoomAPI(parameter.trim());
					ApiResponseAmenRoomDto apiResponse = monoApiResponse.block();

					if (apiResponse != null && apiResponse.getResult() != null && apiResponse.getResult().size() > 0) {
						r.setRoom(apiResponse.getResult().get(0).getLibcentre() + " " + apiResponse.getResult().get(0).getTlibelle_pavillon() + " غرفة " 
								+ apiResponse.getResult().get(0).getTcode_chambre());
						r .setNumeroPecule(apiResponse.getResult().get(0).getTnumpecule());;
				     	  r .setAnneePecule(apiResponse.getResult().get(0).getTannee_pecule());;
				     	  r .setSoldeExistant(apiResponse.getResult().get(0).getTsolde_existant());;          // "45560"
				  	      r .setIdentifiantRibPoste(apiResponse.getResult().get(0).getIdentifiantRibPoste());      // "GG250120784853"
				  	      r .setIdentifianttaxiphone(apiResponse.getResult().get(0).getIdentifianttaxiphone());    // "2501207"
					} else {
						// Gérer le cas où la réponse de l'API est nulle ou la liste de résultats est
						// nulle
						r.setRoom("لا يوجد غرفة ");
						Collections.emptyList(); // Ou renvoyez une liste vide, ou une autre action appropriée
					}
					RelationshipSalleAndTimeDTO lastResidence = relationshipTypeRepository
					        .findLastActiveResidenceByPrisonerId(r.getIdPrisoner());
 
					if (lastResidence != null) {
					    r.setDay(lastResidence.getDay());
					    r.setTime(lastResidence.getTime());
					    r.setSalle(lastResidence.getSalle());
					    r.setCentre(lastResidence.getCentre());
					}

					
					PenalSyntheseDto penalSyntheseDto =	rechercherAffairesRepositoryCustom .rechercherPenalSyntheseDetenu(r.getIdPrisoner(), r.getNumDetention());
//					Map<String, String> resultat = prisonerPenalRepository.findEtatAndLibelleById(r.getIdPrisoner());

					if (penalSyntheseDto != null) {
//						String etat = resultat.get("etat");
						 
//						r.setEtat(etat);
						r.setAffaires(penalSyntheseDto.getTypeAffaire());
						
						r.setTnumseqaff(penalSyntheseDto.getTnumseqaff());
						  	
						  	r.setNumAffaire(penalSyntheseDto.getNumAffaire());
						  	r.setTribunal(penalSyntheseDto.getTribunal());
						  	r.setDateJugement(penalSyntheseDto.getDateJugement());
						  	
						  	r.setSituationPenal(penalSyntheseDto.getSituationPenal());
						  	r.setContestation(penalSyntheseDto.getContestation()); 
						  	
						  	r.setTypeAffaire(penalSyntheseDto.getTypeAffaire());
						  	r.setAccusation(penalSyntheseDto.getAccusation());
						  	
						  	
						  	r.setTotaleJugement(penalSyntheseDto.getTotaleJugement());
						  	r.setTypeJugement(penalSyntheseDto.getTypeJugement());
						  	r.setDateDebutPunition(penalSyntheseDto.getDateDebutPunition());
						  	r.setDateFinPunition(penalSyntheseDto.getDateFinPunition());
						
						
						
						// Maintenant, vous pouvez utiliser les valeurs comme vous le souhaitez

					} else {
						// Gérez le cas où aucun résultat n'est trouvé
						//System.out.println("Aucun résultat trouvé.");
					}
					
					//System.err.println("r.toString()");
					//System.err.println(r.toString());
					return r;
					// }

				}

			 
		}
		log.error(idPrisoner + " n'exist pas ");
		return null;

	}
	
	
	
	
	@Override
	public List<PrisonerDto> findResidantPrisonersByLocation( String gouvernorat, String prison) {
//		if (  gouvernorat == null || prison == null) {
//			log.error("startDate or endDate is null");
//			return null;
//		} else {
//			List<RelationshipTypeDto> listRelationship = new ArrayList();
//
//			List<PrisonerDto> prisonersEntering = prisonerPenalRepository
//					.findResidantPrisonersByLocation(  gouvernorat, prison);
//			if (!prisonersEntering.isEmpty()) {
//
//				for (PrisonerDto p : prisonersEntering) {
//					listRelationship.add(RelationshipTypeDto.convertPrisonerDtoToRelationshipTypeDto(p,
// 						new ArrayList<VisitorDto>()));
// 
//				}
//				return listRelationship;
//			} else {
//				log.error("il n'a pas des entarnt pour cette periode et ce prison ");
//			}
//		}

		return  prisonerPenalRepository
				.findResidantPrisonersByLocation(  gouvernorat, prison);

	}

	@Override
	public List<PrisonerDto> findPrisonersEnteringByPeriodandLocation(String startDate, String endDate,
			String gouvernorat, String prison) {
		List<PrisonerDto> prisonersEntering = prisonerPenalRepository
				.findPrisonersEnteringByPeriodAndLocation(startDate, endDate, gouvernorat, prison);
//		if (startDate == null || endDate == null || gouvernorat == null || prison == null) {
//			log.error("startDate or endDate is null");
//			return null;
//		} else {
//			List<PrisonerDto> listRelationship = new ArrayList();
//
//			List<PrisonerDto> prisonersEntering = prisonerPenalRepository
//					.findPrisonersEnteringByPeriodAndLocation(startDate, endDate, gouvernorat, prison);
//	 
//			if (!prisonersEntering.isEmpty()) {
//
//				for (PrisonerDto p : prisonersEntering) {
//					listRelationship.add(RelationshipTypeDto.convertPrisonerDtoToRelationshipTypeDto(p,
// 							new ArrayList<VisitorDto>()));
////					List<RelationshipType> listRelationshipOfOnePresoner = relationshipTypeRepository
////							.findByPrisonerId(p.getIdPrisoner());
////
////					if (!listRelationshipOfOnePresoner.isEmpty()) {
////						listRelationship.add(RelationshipTypeDto.fromListEntity(listRelationshipOfOnePresoner));
////					} else {
////						listRelationship.add(RelationshipTypeDto.convertPrisonerDtoToRelationshipTypeDto(p,
////								new ArrayList<VisitorDto>()));
////
////					}
//				}
//				return listRelationship;
//			} else {
//				log.error("il n'a pas des entarnt pour cette periode et ce prison ");
//			}
//		}

		return prisonersEntering;

	}

	@Override
	public List<PrisonerDto> findPrisonersMutatingByPeriodandLocation(String startDate, String endDate,
			String gouvernorat, String prison) {
//		if (startDate == null || endDate == null || gouvernorat == null || prison == null) {
//			log.error("startDate or endDate is null");
//			return null;
//		} else {
//			List<RelationshipTypeDto> listRelationship = new ArrayList();
//
//			List<PrisonerDto> prisonersEntering = prisonerPenalRepository
//					.findPrisonersMutatingByPeriodandLocation(startDate, endDate, gouvernorat, prison);
//			if (!prisonersEntering.isEmpty()) {
//
//				for (PrisonerDto p : prisonersEntering) {
//					listRelationship.add(RelationshipTypeDto.convertPrisonerDtoToRelationshipTypeDto(p,
// 						new ArrayList<VisitorDto>()));
// 
//				}
//				return listRelationship;
//			} else {
//				log.error("il n'a pas des entarnt pour cette periode et ce prison ");
//			}
//		}

		return prisonerPenalRepository
				.findPrisonersMutatingByPeriodandLocation(startDate, endDate, gouvernorat, prison);

	}

	@Override
	public List<RelationshipTypeDto> findPrisonersLeavingByPeriodandLocation(String startDate, String endDate,
			String gouvernorat, String prison) {
		if (startDate == null || endDate == null || gouvernorat == null || prison == null) {
			log.error("startDate or endDate is null");
			return null;
		} else {
			List<RelationshipTypeDto> listRelationship = new ArrayList();

			List<PrisonerDto> prisonersEntering = prisonerPenalRepository
					.findPrisonersLeavingByPeriodandLocation(startDate, endDate, gouvernorat, prison);
			if (!prisonersEntering.isEmpty()) {

				for (PrisonerDto p : prisonersEntering) {
					
					listRelationship.add(RelationshipTypeDto.convertPrisonerDtoToRelationshipTypeDto(p,
							new ArrayList<VisitorDto>()));

					
//					List<RelationshipType> listRelationshipOfOnePresoner = relationshipTypeRepository
//							.findByPrisonerId(p.getIdPrisoner());
//
//					if (!listRelationshipOfOnePresoner.isEmpty()) {
//						listRelationship.add(RelationshipTypeDto.fromListEntity(listRelationshipOfOnePresoner));
//					} else {
//						listRelationship.add(RelationshipTypeDto.convertPrisonerDtoToRelationshipTypeDto(p,
//								new ArrayList<VisitorDto>()));
//
//					}
				}
				return listRelationship;
			} else {
				log.error("il n'a pas des entarnt pour cette periode et ce prison ");
			}
		}

		return null;

	}

	@Scheduled(cron = "0 0 */1 * * *") // heure
	// @Scheduled(cron = "0 * * * * *") //minute
	@Transactional
	@Override
	public void updateRelationshipTypeStatutResidance() {
		// Exécution de la requête de mise à jour
		log.error("La planification de la mise à jour des entrées a commencé.");

		// Récupération de la liste des mutations
		List<RelationshipType> listMutation = relationshipTypeRepository.findMutation();
		List<RelationshipType> listLiberation = relationshipTypeRepository.findLiberation();

		// Nouvelle liste pour stocker les nouvelles instances
		List<RelationshipType> newListLiberation = new ArrayList<>();

		// Parcourir la liste des mutations
		for (RelationshipType liberation : listLiberation) {
			// Clonez l'instance existante pour créer une nouvelle instance
			RelationshipType newLiberation = new RelationshipType();

			// Réinitialisation des valeurs sur la nouvelle instance
			newLiberation.setDay(null);
			newLiberation.setTime(null);
			newLiberation.setRelationShip(liberation.getRelationShip());
			newLiberation.setPrisoner(liberation.getPrisoner());
			newLiberation.setVisitor(liberation.getVisitor());
			newLiberation.setLibelleStatutResidance("Liberation");
			// newLiberation.setLibelleStatutResidance("Liberation");
			newLiberation.setCodeGouvernorat(liberation.getCodeGouvernorat());
			newLiberation.setCodePrison(liberation.getCodePrison());
			newLiberation.setNamePrison(liberation.getNamePrison());
			newLiberation.setStatutSMS(null);;
			newLiberation.setNumDetention(liberation.getNumDetention());
			newLiberation.setCodeResidance(liberation.getCodeResidance());
			newLiberation.setAnneeResidance(liberation.getAnneeResidance());

			// Recherche du PrisonerDto correspondant
			Optional<PrisonerDto> prisonerDtoOptional = prisonerPenalRepository
					.findById(liberation.getPrisoner().getIdPrisoner());

			if (prisonerDtoOptional.isPresent()) {
				PrisonerDto prisonerDto = prisonerDtoOptional.get();

				// Mise à jour des autres propriétés de la nouvelle instance
				newLiberation.setCodeGouvernorat(prisonerDto.getCodeGouvernorat());
				newLiberation.setCodePrison(prisonerDto.getCodePrison());
				newLiberation.setCodeResidance(prisonerDto.getCodeResidance());
				newLiberation.setNamePrison(prisonerDto.getNamePrison());
				newLiberation.setEventDate(prisonerDto.getEventDate());

			}
			newLiberation.setStatutResidance("L");
			// Ajoutez la nouvelle instance à la nouvelle liste
			newListLiberation.add(newLiberation);
		}
//
////	     Enregistrement des nouvelles instances dans la base de données
		relationshipTypeRepository.saveAll(newListLiberation);
//	     mise a j

		prisonerPenalRepository.updateRelationshipTypeStatutResidance();
	}

	// Définissez les tableaux de jours et de plages horaires ici
	private String[] days = { "الإثنين", "الثلاثاء", "الأربعاء", "الخميس", "الجمعة" };
	private String[] daysInEnglish = { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday" };

	
//	private String[] timeSlots = { "08:40", "09:00", "09:20", "09:40", "10:00", "10:20", "10:40", "11:00", "11:20",
//			"11:40", "12:00", "12:20", "12:40", "13:40", "14:00", "14:20", "14:40", "15:00", "15:20", "15:40",
//			"16:00" };
	private String[] timeSlots = { "08:40", "09:00", "09:20", "09:40", "10:00", "10:20", "10:40", "11:00", "11:20",
			"11:40", "12:00", "12:20", "12:40", "13:00", "13:20", "13:40", "14:00", "14:20", "14:40", "15:00", "15:20"  };

	public List<List<String>> getCountMatrix(String codeGouvernorat, String codePrison, String centre, String salle) {
		List<Object[]> results = relationshipTypeRepository.countRelationshipsByDayAndTime(codeGouvernorat, codePrison ,centre , salle);
		List<List<String>> countMatrix = new ArrayList<>();

		// Initialisation de la matrice avec des zéros
		for (int i = 0; i < timeSlots.length; i++) {
			List<String> row = new ArrayList<>();
			for (int j = 0; j < days.length; j++) {
				row.add("0");
			}
			countMatrix.add(row);
		}

		// Remplissage de la matrice avec les résultats du comptage
		for (Object[] result : results) {
			String day = (String) result[0];
			String time = (String) result[1];
			long count = (Long) result[2];

			int dayIndex = Arrays.asList(days).indexOf(day);
			int timeIndex = Arrays.asList(timeSlots).indexOf(time);

			if (dayIndex >= 0 && timeIndex >= 0) {
				countMatrix.get(timeIndex).set(dayIndex, String.valueOf(count));
			}
		}

		return countMatrix;
	}

	public List<DayDto> getCountMatrixForDashboarding(String codeGouvernorat, String codePrison) {
		List<Object[]> results = relationshipTypeRepository.countRelationshipsByDayAndTimeForDashboarding(codeGouvernorat, codePrison);
		Map<String, DayDto> dayDtoMap = new LinkedHashMap<>(); // Utilisez LinkedHashMap pour préserver l'ordre des
																// jours

		// Initialisez le map avec des DayDto pour chaque jour
		for (String day : days) {
			DayDto dayDto = new DayDto();
			dayDto.setName(day);
			dayDto.setTotal(0L);
			dayDto.setTimeSlots(new ArrayList<>(Arrays.asList(timeSlots))); // Initialisez avec les plages horaires
			dayDto.setVisitCounts(new ArrayList<>(Collections.nCopies(timeSlots.length, 0))); // Initialisez avec des
																								// zéros
			dayDtoMap.put(day, dayDto);
		}

		// Remplissez le map avec les résultats du comptage
		for (Object[] result : results) {
			String day = (String) result[0];
			String time = (String) result[1];
			long count = (Long) result[2];

			DayDto dayDto = dayDtoMap.get(day);
			if (dayDto != null) {
				dayDto.setTotal(dayDto.getTotal() + count);
				int timeIndex = Arrays.asList(timeSlots).indexOf(time);
				if (timeIndex >= 0) {
					// Mettez à jour la valeur correspondante dans la liste visitCounts
					dayDto.getVisitCounts().set(timeIndex, (int) count);
				}
			}
		}

		return new ArrayList<>(dayDtoMap.values());
	}

	@Override
	public List<RelationshipTypeDto> findByTimeAndDayAndPrisonAndStatutO(
	        String time, String day, String gouvernorat,
	        String prison, String nom, String prenom, String nomPere,String idPrisoner, String numeroEcrou) {

	    // Normalisation des paramètres
	    String timeParam = (time == null || time.trim().isEmpty() || time.trim().equals("null")) ? null : time.trim();
	    String dayParam = (day == null || day.trim().isEmpty() || day.trim().equals("null")) ? null : day.trim();
	    // Simplification des valeurs d'entrée
	    String nomSimplifie = (nom != null && !nom.trim().isEmpty()) ? simplifier.simplify(nom) : null;
	    String prenomSimplifie = (prenom != null && !prenom.trim().isEmpty()) ? simplifier.simplify(prenom) : null;
	    String nomPereSimplifie = (nomPere != null && !nomPere.trim().isEmpty()) ? simplifier.simplify(nomPere) : null;

	   // String dateNaissance = dateNaissance;

	    // Conversion du sexe en valeur utilisable (1 pour "masculin", 0 pour "féminin")
	   
	      nomSimplifie = simplifyOrNull(nomSimplifie);
	      prenomSimplifie = simplifyOrNull(prenomSimplifie);
	      nomPereSimplifie = simplifyOrNull(nomPereSimplifie);
	    
	    
	  //  //System.out.println("firstname : " + nomSimplifie);
	 //   //System.out.println("father_name : " + nomPereSimplifie);
	    //System.out.println("lastname : " + prenomSimplifie);

	    // Requête repository avec les nouveaux filtres
	    List<RelationshipType> relationshipTypes = relationshipTypeRepository
	            .findByTimeDayPrisonWithOptionalNames(
	                timeParam, dayParam, gouvernorat, prison, nomSimplifie, prenomSimplifie, nomPereSimplifie,    idPrisoner,   numeroEcrou
	            );

	    // Grouper par détenu
	    List<List<RelationshipType>> groupedRelationshipTypes = getListRelationshipTypeByPrisoner(relationshipTypes);

	    List<RelationshipTypeDto> result = new ArrayList<>();
	    for (List<RelationshipType> group : groupedRelationshipTypes) {
	        RelationshipTypeDto relationshipTypeDto = RelationshipTypeDto.fromListEntity(group);
	        result.add(relationshipTypeDto);
	    }

	    return trierParJourEtHeure(result);
	}


	@Override
	public List<RelationshipTypeDto> findByPrisonAndStatutNotO(String gouvernorat, String prison) {

		List<RelationshipType> relationshipTypes = relationshipTypeRepository.findByPrisonAndStatutNotO(gouvernorat,
				prison);

		List<List<RelationshipType>> groupedRelationshipTypes = getListRelationshipTypeByPrisoner(relationshipTypes);

		List<RelationshipTypeDto> result = new ArrayList<>();

		for (List<RelationshipType> group : groupedRelationshipTypes) {
			RelationshipTypeDto relationshipTypeDto = RelationshipTypeDto.fromListEntity(group);
			result.add(relationshipTypeDto);
		}

		return result;
	}

	private List<List<RelationshipType>> getListRelationshipTypeByPrisoner(List<RelationshipType> relationshipTypes) {
		// Créez une map pour regrouper les RelationshipType par ID de prisonnier
		Map<String, List<RelationshipType>> relationshipTypeMap = new HashMap<>();

		for (RelationshipType relationshipType : relationshipTypes) {
			String prisonerId = relationshipType.getPrisoner().getIdPrisoner();

			// Vérifiez si la liste pour ce prisonnier existe déjà dans la map
			if (!relationshipTypeMap.containsKey(prisonerId)) {
				relationshipTypeMap.put(prisonerId, new ArrayList<>());
			}

			// Ajoutez RelationshipType à la liste correspondante
			relationshipTypeMap.get(prisonerId).add(relationshipType);
		}

		// Convertissez la map en une liste de listes
		List<List<RelationshipType>> result = new ArrayList<>(relationshipTypeMap.values());

		return result;
	}

	@Override
	public List<PrisonerDto> findPrisonersExistingByLocationWithOutVisit(String gouvernorat, String prison,String nom,String prenom, String nomPere ) {
	    if (gouvernorat == null || prison == null) {
	        log.error("gouv or prison is null");
	        return Collections.emptyList();
	    }
	    // Simplification des valeurs d'entrée
	    String nomSimplifie = (nom != null && !nom.trim().isEmpty()) ? simplifier.simplify(nom) : null;
	    String prenomSimplifie = (prenom != null && !prenom.trim().isEmpty()) ? simplifier.simplify(prenom) : null;
	    String nomPereSimplifie = (nomPere != null && !nomPere.trim().isEmpty()) ? simplifier.simplify(nomPere) : null;

	   // String dateNaissance = dateNaissance;

	    // Conversion du sexe en valeur utilisable (1 pour "masculin", 0 pour "féminin")
	   
	      nomSimplifie = simplifyOrNull(nomSimplifie);
	      prenomSimplifie = simplifyOrNull(prenomSimplifie);
	      nomPereSimplifie = simplifyOrNull(nomPereSimplifie);
	    
	    
	    //System.out.println("firstname : " + nomSimplifie);
	    //System.out.println("father_name : " + nomPereSimplifie);
	    //System.out.println("lastname : " + prenomSimplifie);
	 
	    
	    List<PrisonerDto> prisonersEntering = prisonerPenalRepository
	            .findPrisonersExistingByLocationWithOutVisit(gouvernorat, prison     ,   nomSimplifie,
	            		prenomSimplifie,
	                      nomPereSimplifie );
	      
//	      List<PrisonerDto> prisonersEntering =  prisonerPenalRepository
//			.findResidantPrisonersByLocation(  gouvernorat, prison);

	    if (prisonersEntering.isEmpty()) {
	        log.error("No prisoners found for location without visit");
	        return Collections.emptyList();
	    }

	    // Conversion avec stream pour plus de lisibilité
//	    return prisonersEntering.stream()
//	            .map(p -> RelationshipTypeDto.convertPrisonerDtoToRelationshipTypeDto(p, new ArrayList<VisitorDto>()))
//	            .collect(Collectors.toList());
	    return prisonersEntering; 
	}

//	public List<RelationshipTypeDto> findPrisonersExistingByLocationWithOutVisit(String gouvernorat, String prison) {
//		if (gouvernorat == null || prison == null) {
//			log.error("gouv  or prision  is null");
//			return null;
//		} else {
//			List<RelationshipTypeDto> listRelationship = new ArrayList();
//
//			List<PrisonerDto> prisonersEntering = prisonerPenalRepository
//					.findPrisonersExistingByLocationWithOutVisit(gouvernorat, prison);
//
//			if (!prisonersEntering.isEmpty()) {
//
//				for (PrisonerDto p : prisonersEntering) {
//
//					listRelationship.add(RelationshipTypeDto.convertPrisonerDtoToRelationshipTypeDto(p,
//							new ArrayList<VisitorDto>()));
//
//				}
//				return listRelationship;
//			} else {
//				log.error("il n'a pas des PrisonersExistingByLocationWithOutVisit ");
//				return null;
//			}
//		}
//
//	}

	@Override
	public void updateStatutSMSToReady(String statut, List<RelationshipTypeDto> relationshipTypeDtoList) {

		List<String> prisonerIds = relationshipTypeDtoList.stream().map(RelationshipTypeDto::getIdPrisoner)
				.collect(Collectors.toList());

		relationshipTypeRepository.updateStatutSMSByStatutResidanceAndPrisonerIds(statut, prisonerIds);

	}

	@Override
	public List<RelationshipType> findRelationshipTypesByStatutSMSAndLibelleStatutResidance(String parametre) {
		List<RelationshipType> listRelationship = new ArrayList();
		listRelationship = relationshipTypeRepository
				.findRelationshipTypesByStatutSMSAndLibelleStatutResidance(parametre);
		return listRelationship;
	}

	@Override
	public List<RelationshipTypeDto> findByStatuses(String codeGouvernorat, String codePrison, String statutResidance,
			String statutSMS ,String dateStartString,String dateEndString) {
		// TODO Auto-generated method stub
		List<RelationshipType> relationshipTypes = new ArrayList<RelationshipType>();
		 
		if (statutSMS == null) {
			//System.err.println("je suis dans WAIT ");
			relationshipTypes = relationshipTypeRepository.findByStatuses(codeGouvernorat, codePrison, statutResidance);
		} 
		else if(statutSMS.trim().equals("SENT")) {
			// Créez un objet SimpleDateFormat avec le modèle correspondant à vos chaînes de caractères
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

			// Convertissez vos chaînes en objets Date
			 
			try {
				Date dateStart = dateFormat.parse(dateStartString.trim());
				Date dateEnd = dateFormat.parse(dateEndString.trim());
				//System.err.println("je suis dans SENT"+dateStart+" "+dateEnd);
				relationshipTypes = relationshipTypeRepository.findByStatuses(codeGouvernorat, codePrison, statutResidance ,dateStart,dateEnd);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		else {
			//System.err.println("je suis dans FAILED OR READY");
			relationshipTypes = relationshipTypeRepository.findByStatuses(codeGouvernorat, codePrison, statutResidance, statutSMS);
		}

		List<List<RelationshipType>> groupedRelationshipTypes = getListRelationshipTypeByPrisoner(relationshipTypes);

		List<RelationshipTypeDto> result = new ArrayList<>();

		for (List<RelationshipType> group : groupedRelationshipTypes) {

			RelationshipTypeDto relationshipTypeDto = RelationshipTypeDto.fromListEntity(group);
			result.add(relationshipTypeDto);

		}

		return   trierParJourEtHeure(result) ;
	}

	@Override
	public List<RelationshipTypeDto> findByPrisonAndStatutOAndStatutSMSReady(String gouvernorat, String prison) {

		List<RelationshipType> relationshipTypes = relationshipTypeRepository
				.findByPrisonAndStatutOAndStatutSMSReady(gouvernorat, prison);

		List<List<RelationshipType>> groupedRelationshipTypes = getListRelationshipTypeByPrisoner(relationshipTypes);

		List<RelationshipTypeDto> result = new ArrayList<>();

		for (List<RelationshipType> group : groupedRelationshipTypes) {
			RelationshipTypeDto relationshipTypeDto = RelationshipTypeDto.fromListEntity(group);
			result.add(relationshipTypeDto);

		}

		return result;
	}

	
	//api balti DTO
	@Override
	public List<RelationshipTypeAmenDto> findVisitByPrisonerId(String idPrisoner) {
		
		return RelationshipTypeDto.fromListEntityAmen( relationshipTypeRepository.findVisitByPrisonerId(idPrisoner));
	}

	
	 
	    // Ordre prédéfini des heures
	// Ordre prédéfini des jours
	private static final String[] ordreJours = { "الإثنين", "الثلاثاء", "الأربعاء", "الخميس", "الجمعة" };

	// Créez une LinkedHashMap pour stocker les jours avec l'indice de priorité
	private static final Map<String, Integer> joursAvecPriorite = new LinkedHashMap<>();

	static {
	    for (int i = 0; i < ordreJours.length; i++) {
	        joursAvecPriorite.put(ordreJours[i], i);
	    }
	}

	// Ordre prédéfini des heures
//	private static final String[] ordreHeures = {
//	    "08:40", "09:00", "09:20", "09:40", "10:00", "10:20", "10:40", "11:00", "11:20",
//	    "11:40", "12:00", "12:20", "12:40", "13:40", "14:00", "14:20", "14:40", "15:00", "15:20", "15:40",
//	    "16:00"
//	};
	private static final String[] ordreHeures = {
			"08:40", "09:00", "09:20", "09:40", "10:00", "10:20", "10:40", "11:00", "11:20",
			"11:40", "12:00", "12:20", "12:40", "13:00", "13:20", "13:40", "14:00", "14:20", "14:40", "15:00", "15:20"
	};
	// Créez une LinkedHashMap pour stocker les heures avec l'indice de priorité
	private static final Map<String, Integer> heuresAvecPriorite = new LinkedHashMap<>();

	static {
	    for (int i = 0; i < ordreHeures.length; i++) {
	        heuresAvecPriorite.put(ordreHeures[i], i);
	    }
	}

	public static List<RelationshipTypeDto> trierParJourEtHeure(List<RelationshipTypeDto> listeNonTriee) {
	    listeNonTriee.sort((r1, r2) -> {
	        Integer prioriteJour1 = joursAvecPriorite.get(r1.getDay());
	        Integer prioriteJour2 = joursAvecPriorite.get(r2.getDay());
	        Integer prioriteHeure1 = heuresAvecPriorite.get(r1.getTime());
	        Integer prioriteHeure2 = heuresAvecPriorite.get(r2.getTime());

	        if (prioriteJour1 != null && prioriteJour2 != null) {
	            int comparaisonJour = Integer.compare(prioriteJour1, prioriteJour2);
	            if (comparaisonJour == 0) {
	                if (prioriteHeure1 != null && prioriteHeure2 != null) {
	                    return Integer.compare(prioriteHeure1, prioriteHeure2);
	                } else {
	                    return 0;
	                }
	            }
	            return comparaisonJour;
	        } else {
	            // Gérez les jours ou heures non trouvés dans les Maps ici
	            // Vous pouvez choisir de les placer au début ou à la fin de la liste, par exemple
	            return 0;
	        }
	    });

	    return listeNonTriee;
	}

	@Override
	public List<RelationshipTypeDto> findAllPrisonersExisting(String gouvernorat, String prison, String nom,
			String prenom, String nomPere  ,String idPrisoner , String numeroEcrou) {
		 if (gouvernorat == null || prison == null) {
		        log.error("gouv or prison is null");
		        return Collections.emptyList();
		    }
		    // Simplification des valeurs d'entrée
		    String nomSimplifie = (nom != null && !nom.trim().isEmpty()) ? simplifier.simplify(nom) : null;
		    String prenomSimplifie = (prenom != null && !prenom.trim().isEmpty()) ? simplifier.simplify(prenom) : null;
		    String nomPereSimplifie = (nomPere != null && !nomPere.trim().isEmpty()) ? simplifier.simplify(nomPere) : null;

		   // String dateNaissance = dateNaissance;

		    // Conversion du sexe en valeur utilisable (1 pour "masculin", 0 pour "féminin")
		   
		      nomSimplifie = simplifyOrNull(nomSimplifie);
		      prenomSimplifie = simplifyOrNull(prenomSimplifie);
		      nomPereSimplifie = simplifyOrNull(nomPereSimplifie);
		      
		      idPrisoner = simplifyOrNull(idPrisoner);
		      numeroEcrou = simplifyOrNull(numeroEcrou);
		     
		    
		    //System.out.println("firstname : " + nomSimplifie);
		    //System.out.println("father_name : " + nomPereSimplifie);
		    //System.out.println("lastname : " + prenomSimplifie);
		 
		    
		    //System.out.println("idPrisoner : " + idPrisoner);
		    //System.out.println("numeroEcrou : " + numeroEcrou);
		    
		    
		    List<PrisonerDto> prisonersEntering = prisonerPenalRepository
		            .findAllPrisonersExisting(gouvernorat, prison     ,   nomSimplifie,
		            		prenomSimplifie,
		                      nomPereSimplifie,idPrisoner,numeroEcrou );

		    if (prisonersEntering.isEmpty()) {
		        log.error("No prisoners found for location without visit");
		        return Collections.emptyList();
		    }

		    // Conversion avec stream pour plus de lisibilité
		    return prisonersEntering.stream()
		            .map(p -> RelationshipTypeDto.convertPrisonerDtoToRelationshipTypeDto(p, new ArrayList<VisitorDto>()))
		            .collect(Collectors.toList());
	}



	@Override
	public List<RelationshipTypeDto> findAllPrisonersExistingFromPenal(String gouvernorat, String prison, String nom,
			String prenom, String nomPere  ,String idPrisoner , String numeroEcrou) {
		 if (gouvernorat == null || prison == null) {
		        log.error("gouv or prison is null");
		        return Collections.emptyList();
		    }
		    // Simplification des valeurs d'entrée
		    String nomSimplifie = (nom != null && !nom.trim().isEmpty()) ? simplifier.simplify(nom) : null;
		    String prenomSimplifie = (prenom != null && !prenom.trim().isEmpty()) ? simplifier.simplify(prenom) : null;
		    String nomPereSimplifie = (nomPere != null && !nomPere.trim().isEmpty()) ? simplifier.simplify(nomPere) : null;

		   // String dateNaissance = dateNaissance;

		    // Conversion du sexe en valeur utilisable (1 pour "masculin", 0 pour "féminin")
		   
		      nomSimplifie = simplifyOrNull(nomSimplifie);
		      prenomSimplifie = simplifyOrNull(prenomSimplifie);
		      nomPereSimplifie = simplifyOrNull(nomPereSimplifie);
		      
		      idPrisoner = simplifyOrNull(idPrisoner);
		      numeroEcrou = simplifyOrNull(numeroEcrou);
		     
		    
		    //System.out.println("firstname : " + nomSimplifie);
		    //System.out.println("father_name : " + nomPereSimplifie);
		    //System.out.println("lastname : " + prenomSimplifie);
		 
		    
		    //System.out.println("idPrisoner : " + idPrisoner);
		    //System.out.println("numeroEcrou : " + numeroEcrou);
		    
		    
		    List<PrisonerDto> prisonersEntering = prisonerPenalRepository
		            .findAllPrisonersExistingFromPenal(gouvernorat, prison     ,   nomSimplifie,
		            		prenomSimplifie,
		                      nomPereSimplifie,idPrisoner,numeroEcrou );

		    if (prisonersEntering.isEmpty()) {
		        log.error("No prisoners found for location without visit");
		        return Collections.emptyList();
		    }

		    // Conversion avec stream pour plus de lisibilité
		    return prisonersEntering.stream()
		            .map(p -> RelationshipTypeDto.convertPrisonerDtoToRelationshipTypeDto(p, new ArrayList<VisitorDto>()))
		            .collect(Collectors.toList());
	}

	@Override
	public List<PrisonerDto> findPrisonersMutatingSortantByPeriodandLocation(String startDate, String endDate,
			String gouvernorat, String prison) {
//		if (startDate == null || endDate == null || gouvernorat == null || prison == null) {
//			log.error("startDate or endDate is null");
//			return null;
//		} else {
//			List<RelationshipTypeDto> listRelationship = new ArrayList();
//
//			List<PrisonerDto> prisonersEntering = prisonerPenalRepository
//					.findPrisonersMutatingSortantByPeriodandLocation(startDate, endDate, gouvernorat, prison);
//			if (!prisonersEntering.isEmpty()) {
//
//				for (PrisonerDto p : prisonersEntering) {
//					listRelationship.add(RelationshipTypeDto.convertPrisonerDtoToRelationshipTypeDto(p,
// 						new ArrayList<VisitorDto>()));
// 
//				}
//				return listRelationship;
//			} else {
//				log.error("il n'a pas des entarnt pour cette periode et ce prison ");
//			}
//		}
		return prisonerPenalRepository
				.findPrisonersMutatingSortantByPeriodandLocation(startDate, endDate, gouvernorat, prison);
	}
	   @Override
	    public List<EventSMSDto> getEntrantEvents(String prisonerId) {
		   //System.err.println("relationshipTypeRepository.findEntrantEventsByPrisonerId(prisonerId).size() "+relationshipTypeRepository.findEntrantEventsByPrisonerId(prisonerId).size());
	        return relationshipTypeRepository.findEntrantEventsByPrisonerId(prisonerId);
	    }
}
