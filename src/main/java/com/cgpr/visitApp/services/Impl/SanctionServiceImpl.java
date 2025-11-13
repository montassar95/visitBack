package com.cgpr.visitApp.services.Impl;

 

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cgpr.visitApp.dto.SanctionDto;
import com.cgpr.visitApp.model.DetenuParticipant;
import com.cgpr.visitApp.model.Prisoner;
import com.cgpr.visitApp.model.RaisonSanction;
import com.cgpr.visitApp.model.Rapporteur;
import com.cgpr.visitApp.model.Sanction;
import com.cgpr.visitApp.model.SanctionParticipant;
import com.cgpr.visitApp.model.SanctionRaisonSanction;
import com.cgpr.visitApp.model.SanctionRapporteur;
import com.cgpr.visitApp.model.SanctionTypeSanctionDuree;
import com.cgpr.visitApp.model.TypeSanction;
import com.cgpr.visitApp.model.TypeSanctionDuree;
import com.cgpr.visitApp.repository.PrisonerRepository;
import com.cgpr.visitApp.repository.SanctionRepository;
import com.cgpr.visitApp.repository.gestionChombre.CentreRepository;
import com.cgpr.visitApp.repository.gestionChombre.ChambreRepository;
import com.cgpr.visitApp.repository.repositorySanction.DetenuParticipantRepository;
import com.cgpr.visitApp.repository.repositorySanction.RaisonSanctionRepository;
import com.cgpr.visitApp.repository.repositorySanction.RapporteurRepository;
import com.cgpr.visitApp.repository.repositorySanction.TypeSanctionDureeRepository;
import com.cgpr.visitApp.repository.repositorySanction.TypeSanctionRepository;
import com.cgpr.visitApp.services.SanctionService;

@Service
public class SanctionServiceImpl implements SanctionService {

    @Autowired
    private SanctionRepository sanctionRepository;
    
    
    @Autowired
    private ChambreRepository chambreRepository;
    
    
    @Autowired
    private RapporteurRepository rapporteurRepository;
    
    
    @Autowired
    private DetenuParticipantRepository detenuParticipantRepository;
    
//    @Autowired
//    private RelationshipTypeRepository relationshipTypeRepository;

    @Autowired
    private PrisonerRepository prisonerRepository;

    
    @Autowired
    private TypeSanctionDureeRepository typeSanctionDureeRepository;
    
    
    @Autowired
    private TypeSanctionRepository typeSanctionRepository;
    
    @Autowired
    private RaisonSanctionRepository raisonSanctionRepository;
    
    @Transactional
    public SanctionDto save(SanctionDto sanctionDto) {
        Prisoner prisoner;

        // Vérifier si le prisonnier existe
        Optional<Prisoner> prisonerOpt = prisonerRepository.findById(sanctionDto.getPrisonerId().toString());
        if (prisonerOpt.isPresent()) {
            prisoner = prisonerOpt.get();
        } else {
            // Créer un nouveau prisonnier si n'existe pas
            prisoner = new Prisoner();
            prisoner.setIdPrisoner(sanctionDto.getPrisonerId().toString());
            prisoner.setFirstName(sanctionDto.getFirstName());
            prisoner.setLastName(sanctionDto.getLastName());
            prisoner.setFatherName(sanctionDto.getFatherName());
            prisoner.setGrandFatherName(sanctionDto.getGrandFatherName());
            prisoner.setCodeNationalite(sanctionDto.getCodeNationalite());
            prisoner.setDateDebutPunition(null);
            prisoner.setDateFinPunition(null);

            prisoner = prisonerRepository.save(prisoner);
        }

        // Créer et sauvegarder la sanction
        Sanction sanction = new Sanction();
        sanction.setPrisoner(prisoner);
        sanction.setCodeGouvernorat(sanctionDto.getCodeGouvernorat());
        sanction.setCodePrison(sanctionDto.getCodePrison());
        sanction.setNamePrison(sanctionDto.getNamePrison());
        sanction.setCodeResidance(sanctionDto.getCodeResidance());
        sanction.setAnneeResidance(sanctionDto.getAnneeResidance());
        sanction.setDateConseil(sanctionDto.getDateConseil());
        sanction.setNumeroReference(sanctionDto.getNumeroReference());
        sanction.setJourIncident(sanctionDto.getJourIncident());
        sanction.setSanctionText(sanctionDto.getSanctionText());
        sanction.setInfractionText(sanctionDto.getInfractionText());

        // Chambres
        if (sanctionDto.getChambreIsolement() != null) {
            sanction.setChambreIsolement(chambreRepository.findById(sanctionDto.getChambreIsolement().getId()).orElse(null));
        }
        if (sanctionDto.getChambreTransfert() != null) {
            sanction.setChambreTransfert(chambreRepository.findById(sanctionDto.getChambreTransfert().getId()).orElse(null));
        }

        // Participants
        if (sanctionDto.getSanctionParticipants() != null) {
            List<SanctionParticipant> participants = sanctionDto.getSanctionParticipants()
                .stream()
                .map(pDto -> {
                    // Recherche existante
                    List<DetenuParticipant> result = detenuParticipantRepository
                        .searchByMultipleFields(
                            pDto.getIdPrisoner(),
                            pDto.getCodeGouvernorat(),
                            pDto.getCodePrison(),
                            pDto.getCodeResidance(),
                            pDto.getAnneeResidance()
                        );

                    DetenuParticipant dp;
                    if (result != null && !result.isEmpty()) {
                        dp = result.get(0);
                    } else {
                        dp = DetenuParticipant.builder()
                                .idPrisoner(pDto.getIdPrisoner())
                                .nom(pDto.getNom())
                                .prenom(pDto.getPrenom())
                                .codeGouvernorat(pDto.getCodeGouvernorat())
                                .codePrison(pDto.getCodePrison())
                                .namePrison(pDto.getNamePrison())
                                .codeResidance(pDto.getCodeResidance())
                                .anneeResidance(pDto.getAnneeResidance())
                                .build();
                        dp = detenuParticipantRepository.save(dp);
                    }

                    // Création de la liaison SanctionParticipant
                    SanctionParticipant sp = SanctionParticipant.builder()
                            .sanction(sanction)
                            .participant(dp)
                            .build();

                    return sp;
                })
                .collect(Collectors.toList()); // Java 8 compatible

            sanction.setSanctionParticipants(participants);
        }

        // Rapporteurs
    
        if (sanctionDto.getSanctionRapporteurs() != null) {
            List<SanctionRapporteur> rapporteurs = sanctionDto.getSanctionRapporteurs()
                .stream()
                .map(rDto -> {
                    // Recherche par plusieurs champs
                    List<Rapporteur> result = rapporteurRepository.searchByMultipleFields(
                            rDto.getNumeroAdministratif(),
                                  
                            rDto.getNom(),
                            rDto.getPrenom(),
                            rDto.getCodeGouvernorat(),
                            rDto.getCodePrison() 
                    );

                    Rapporteur r;
                    if (result != null && !result.isEmpty()) {
                        r = result.get(0); // premier trouvé
                    } else {
                        // Création si pas trouvé
                        r = Rapporteur.builder()
                                .numeroAdministartif(rDto.getNumeroAdministratif())
                                .nom(rDto.getNom())
                                .prenom(rDto.getPrenom())
                                .codeGouvernorat(rDto.getCodeGouvernorat())
                                .codePrison(rDto.getCodePrison())
                                .namePrison(rDto.getNamePrison())
                                .build();
                        r = rapporteurRepository.save(r);
                    }

                    // Créer le lien avec la sanction
                    return SanctionRapporteur.builder()
                            .sanction(sanction)
                            .rapporteur(r)
                            .build();
                })
                .collect(Collectors.toList());

            sanction.setSanctionRapporteurs(rapporteurs);
        }

        // Types de sanction avec durée
     // Types de sanction avec durée
        if (sanctionDto.getSanctionTypeSanctionDuree() != null) {
            List<SanctionTypeSanctionDuree> typeSanctionDurees = sanctionDto.getSanctionTypeSanctionDuree()
                .stream()
                .map(tsDto -> {
                    // Recherche existante par typeSanction.id et duree
                    List<TypeSanctionDuree> result = typeSanctionDureeRepository
                            .findByTypeSanctionIdAndDuree(tsDto.getTypeSanction().getId(), tsDto.getDuree());

                    TypeSanctionDuree tsdEntity;
                    if (result != null && !result.isEmpty()) {
                        tsdEntity = result.get(0);
                    } else {
                        // Si pas trouvé, créer TypeSanctionDuree
                        TypeSanction typeSanction = typeSanctionRepository.findById(tsDto.getTypeSanction().getId())
                                .orElseThrow(() -> new RuntimeException("TypeSanction introuvable"));

                        tsdEntity = TypeSanctionDuree.builder()
                                .typeSanction(typeSanction)
                                .duree(tsDto.getDuree())
                                .build();
                        tsdEntity = typeSanctionDureeRepository.save(tsdEntity);
                    }

                    // Créer le lien avec la sanction
                    return SanctionTypeSanctionDuree.builder()
                            .sanction(sanction)
                            .typeSanctionDuree(tsdEntity) // ici c'est TypeSanctionDuree
                            .build();
                })
                .collect(Collectors.toList());

            sanction.setSanctionTypeSanctionDurees(typeSanctionDurees);
        }

     // Raisons de sanction
        if (sanctionDto.getSanctionRaisonsSanction() != null) {
            List<SanctionRaisonSanction> raisonsSanction = sanctionDto.getSanctionRaisonsSanction()
                .stream()
                .map(rDto -> {
                    // Recherche obligatoire, exception si non trouvée
                    RaisonSanction rs = raisonSanctionRepository.findById(rDto.getId())
                            .orElseThrow(() -> new RuntimeException(
                                "RaisonSanction introuvable pour l'ID : " + rDto.getId()
                            ));

                    // Créer le lien avec la sanction
                    return SanctionRaisonSanction.builder()
                            .sanction(sanction)
                            .raisonSanction(rs)
                            .build();
                })
                .collect(Collectors.toList());

            sanction.setSanctionRaisonsSanction(raisonsSanction);
        }


        // Sauvegarde
        Sanction saved = sanctionRepository.save(sanction);


       return SanctionDto.convertToDto (sanctionRepository.save(sanction));
       
    }

 
    @Override
    public List<SanctionDto> findByPrisoner(String prisonerId) {
    	System.out.println("  prisonerId = " + prisonerId);
        List<Sanction> sanctions = sanctionRepository.findByPrisonerId(prisonerId);// avec find all samarche mais findByPrisonerId non
    //	List<Sanction> sanctions = sanctionRepository.findAll( );
        System.out.println("from repository = " + sanctions.size());
        return sanctions.stream()
                        .map(SanctionDto::convertToDto) 
                     .collect(Collectors.toList());
         
    }

 
  

//    @Override
//    @Transactional
//    public void notifyFamily(Sanction sanction) {
//        List<RelationshipType> relations = relationshipTypeRepository.findByPrisonerId(
//                Long.parseLong(sanction.getPrisoner().getIdPrisoner())
//        );
//
//        for (RelationshipType rel : relations) {
//            String msg = "Bonjour " + rel.getRelationShip() +
//                         ", nous vous informons que " + rel.getPrisoner().getLastName() +
//                         " a reçu une sanction : " + sanction.getTypeSanction() +
//                         " (" + sanction.getRaisonSanction() + ") du " +
//                         sanction.getDateDebutSanction() + " au " + sanction.getDateFinSanction();
//
//            // Ici, tu peux appeler ton service SMS ou Email
//            System.out.println("Envoi SMS à " + rel.getVisitor().getPhone() + " : " + msg);
//        }
//
//        sanction.setNotified(true);
//        sanction.setNotificationDate(new Date());
//        sanctionRepository.save(sanction);
//    }
}
