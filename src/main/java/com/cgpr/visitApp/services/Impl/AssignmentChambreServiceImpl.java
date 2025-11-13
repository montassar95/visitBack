package com.cgpr.visitApp.services.Impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cgpr.visitApp.dto.AssignmentChambreDto;
import com.cgpr.visitApp.dto.CentreWithTotalDto;
import com.cgpr.visitApp.dto.ChambreWithTotalDto;
import com.cgpr.visitApp.dto.ComplexeWithTotalDto;
import com.cgpr.visitApp.dto.PavillonWithTotalDto;
import com.cgpr.visitApp.dto.PrisonWithTotalDto;
import com.cgpr.visitApp.model.AssignmentChambre;
import com.cgpr.visitApp.model.Prisoner;
import com.cgpr.visitApp.model.gestionChombre.Chambre;
import com.cgpr.visitApp.model.gestionChombre.PrisonerCategory;
import com.cgpr.visitApp.model.gestionChombre.RaisonAffectationChambre;
import com.cgpr.visitApp.repository.AssignmentChambreService;
import com.cgpr.visitApp.repository.PrisonerRepository;
import com.cgpr.visitApp.repository.gestionChombre.AssignmentChambreRepository;
import com.cgpr.visitApp.repository.gestionChombre.ChambreRepository;
import com.cgpr.visitApp.repository.gestionChombre.PrisonerCategoryRepository;
import com.cgpr.visitApp.repository.gestionChombre.RaisonAffectationChambreRepository;

@Service
@Transactional
public class AssignmentChambreServiceImpl implements AssignmentChambreService {

    @Autowired
    private AssignmentChambreRepository repository;

    @Autowired
    private RaisonAffectationChambreRepository raisonAffectationChambreRepository;

    @Autowired
    private PrisonerCategoryRepository prisonerCategoryRepository;

    @Autowired
    private ChambreRepository chambreRepository;

    @Autowired
    private PrisonerRepository prisonerRepository;
    
     
    
  
    @Override
    public AssignmentChambreDto save(AssignmentChambreDto dto) {
        if (dto == null) throw new IllegalArgumentException("L'affectation est nulle");

        // --- 1. Charger ou créer le prisonnier ---
        Prisoner prisoner = prisonerRepository.findById(dto.getPrisonerId().toString())
                .orElseGet(() -> {
                    Prisoner p = new Prisoner();
                    p.setIdPrisoner(dto.getPrisonerId().toString());
                    p.setFirstName(dto.getFirstName());
                    p.setLastName(dto.getLastName());
                    p.setFatherName(dto.getFatherName());
                    p.setGrandFatherName(dto.getGrandFatherName());
                    p.setCodeNationalite(dto.getCodeNationalite());
                    return prisonerRepository.save(p);
                });

        // --- 2. Charger la raison ---
        RaisonAffectationChambre raison = raisonAffectationChambreRepository.findById(dto.getRaisonAffectationId())
                .orElseThrow(() -> new RuntimeException("Raison d'affectation non trouvée"));

        // --- 3. Si type = 'F' → fermer la dernière affectation uniquement ---
        if ("F".equalsIgnoreCase(raison.getType())
        		&& (raison.getTypeMouvement() ==  null)	) {
            List<AssignmentChambre> currentAssignments = repository.findOngoingByPrisoner(prisoner.getIdPrisoner());
            if (!currentAssignments.isEmpty()) {
                AssignmentChambre current = currentAssignments.get(0);
                current.setEndDate(dto.getStartDate()); // On ferme à la date du nouveau événement
                current.setRaisonFermerChambre(raison); 
                repository.save(current);
            }
            // Pas de nouvelle affectation créée
            return null;
        }

        // --- 4. Fermer la précédente affectation si elle existe ---
        List<AssignmentChambre> currentAssignments = repository.findOngoingByPrisoner(prisoner.getIdPrisoner());
        if (!currentAssignments.isEmpty()) {
            AssignmentChambre current = currentAssignments.get(0);
            current.setEndDate(dto.getStartDate());
            current.setRaisonFermerChambre(raison); 
            repository.save(current);
        }

        // --- 5. Vérifier chevauchement ---
        checkOverlap(prisoner, dto.getStartDate(), dto.getEndDate());

        
        Chambre chambre = null;
        if ((raison.getTypeMouvement() !=  null) && ("sanction".equalsIgnoreCase(raison.getTypeMouvement()) ||
        		"hospital".equalsIgnoreCase(raison.getTypeMouvement()) ||
        		"seance".equalsIgnoreCase(raison.getTypeMouvement()))
        		) {
             
            if (!currentAssignments.isEmpty()) {
                AssignmentChambre current = currentAssignments.get(0);
                chambre= current.getChambre();
            }
             
        }
        else {
                chambre = chambreRepository.findById(dto.getChambreId())
                    .orElseThrow(() -> new RuntimeException("Chambre non trouvée"));
        }
        
       
        
        
        
        // --- 6. Charger les entités nécessaires ---
   
//        PrisonerCategory category = prisonerCategoryRepository.findById(dto.getPrisonerCategoryId())
//                .orElseThrow(() -> new RuntimeException("Catégorie non trouvée"));

        // --- 7. Créer la nouvelle affectation ---
        AssignmentChambre entity = AssignmentChambre.builder()
                .prisoner(prisoner)
                .chambre(chambre)
                .prisonerCategory(null)
                .raisonAffectationChambre(raison)
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .codeGouvernorat(chambre.getPavillon().getComplexe().getCentre().getPrison().getCodeGou())
                .codePrison(chambre.getPavillon().getComplexe().getCentre().getPrison().getCodePr())
                .namePrison(dto.getNamePrison())
                .codeResidance(dto.getCodeResidance())
                .anneeResidance(dto.getAnneeResidance())
                .build();

        AssignmentChambre saved = repository.save(entity);
        return AssignmentChambreDto.fromEntity(saved);
    }


    @Override
    public AssignmentChambreDto update(AssignmentChambreDto dto) {
        if (dto == null || dto.getId() == null) {
            throw new IllegalArgumentException("ID de l'affectation manquant");
        }

        AssignmentChambre existing = repository.findById(dto.getId())
                .orElseThrow(() -> new IllegalStateException("Affectation inexistante"));

        // Vérifier chevauchement pour l'affectation mise à jour
        checkOverlap(existing.getPrisoner(), dto.getStartDate(), dto.getEndDate(), existing.getId());

        // Mettre à jour les champs
        existing.setStartDate(dto.getStartDate());
        existing.setEndDate(dto.getEndDate());
        existing.setCodeGouvernorat(dto.getCodeGouvernorat());
        existing.setCodeGouvernorat(existing.getChambre().getPavillon().getComplexe().getCentre().getPrison().getCodeGou());
        existing.setCodePrison(existing.getChambre().getPavillon().getComplexe().getCentre().getPrison().getCodePr());
        existing.setCodeResidance(dto.getCodeResidance());
        existing.setAnneeResidance(dto.getAnneeResidance());

        // Mettre à jour les entités liées
        existing.setChambre(chambreRepository.findById(dto.getChambreId())
                .orElseThrow(() -> new RuntimeException("Chambre non trouvée")));

        existing.setPrisonerCategory(prisonerCategoryRepository.findById(dto.getPrisonerCategoryId())
                .orElseThrow(() -> new RuntimeException("Catégorie non trouvée")));

        existing.setRaisonAffectationChambre(raisonAffectationChambreRepository.findById(dto.getRaisonAffectationId())
                .orElseThrow(() -> new RuntimeException("Raison d'affectation non trouvée")));

        AssignmentChambre updated = repository.save(existing);
        return AssignmentChambreDto.fromEntity(updated);
    }

    @Override
    public List<AssignmentChambreDto> findByPrisoner(String prisonerId) {
        return repository.findByPrisoner_IdPrisoner(prisonerId)
                         .stream()
                         .map(AssignmentChambreDto::fromEntity)
                         .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void delete(Long id) {
        AssignmentChambre toDelete = repository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Affectation introuvable"));

        String prisonerId = toDelete.getPrisoner().getIdPrisoner();

        // Supprimer l’affectation
        repository.delete(toDelete);

        // --- Rouvrir la précédente affectation ---
        // On cherche la dernière affectation avant celle supprimée (par date de début)
     // --- Rouvrir la précédente affectation ---
        List<AssignmentChambre> previousList = repository.findPreviousAffectations(
                prisonerId, toDelete.getStartDate()
        );

        if (!previousList.isEmpty()) {
            AssignmentChambre previous = previousList.get(0); // la plus récente avant celle supprimée
            previous.setEndDate(null); // Rouvrir
            repository.save(previous);
        }

    }

    /**
     * Vérifie chevauchement et affectations en cours
     * @param prisoner le prisonnier
     * @param start date de début
     * @param end date de fin (nullable)
     */
    private void checkOverlap(Prisoner prisoner, LocalDate start, LocalDate end) {
        checkOverlap(prisoner, start, end, null);
    }

    /**
     * Vérifie chevauchement et affectations en cours, ignore une affectation spécifique si excludeId != null
     */
    private void checkOverlap(Prisoner prisoner, LocalDate start, LocalDate end, Long excludeId) {
        if (start == null) throw new IllegalArgumentException("La date de début est obligatoire !");
        if (end != null && start.isAfter(end)) {
            throw new IllegalStateException(
                "La date de début (" + start + ") ne peut pas être après la date de fin (" + end + ") !");
        }


        // Affectations en cours
//        if (end == null) {
//            List<AssignmentChambre> ongoing = repository.findOngoingByPrisoner(prisoner.getIdPrisoner());
//            if (!ongoing.isEmpty() && (excludeId == null || ongoing.stream().anyMatch(a -> !a.getId().equals(excludeId)))) {
//                throw new IllegalStateException("Le prisonnier est actuellement déjà affecté à une chambre !");
//            }
//        }

        // Vérification chevauchement
//        List<AssignmentChambre> overlapping = repository.findOverlapping(prisoner.getIdPrisoner(), start, end, excludeId);
//        if (!overlapping.isEmpty()) {
//            throw new IllegalStateException("La période chevauche une autre affectation existante !");
//        }
    }
    @Override
	 public List<PrisonWithTotalDto> getPrisonsWithTotals() {
	        return repository.getPrisonsWithActiveAffectations();
	    }

	@Override
	public List<CentreWithTotalDto> getCentresWithActiveAffectations(Long prisonId) {
		// TODO Auto-generated method stub
		return repository.getCentresWithActiveAffectations(prisonId);
	}
	
	
	@Override
	public List<ComplexeWithTotalDto> getComplexesWithActiveAffectations(Long prisonId) {
		// TODO Auto-generated method stub
		return repository.getComplexesWithActiveAffectations(prisonId);
	}

	@Override
	public List<PavillonWithTotalDto> getPavillonsWithActiveAffectations(Long complexeId) {
		// TODO Auto-generated method stub
		return  repository.getPavillonsWithActiveAffectations(complexeId);
	}

	@Override
	public List<ChambreWithTotalDto> getChambresWithActiveAffectations(Long pavillonId) {
		// TODO Auto-generated method stub
		return  repository.getChambresWithActiveAffectations(pavillonId);
	}

	@Override
	public AssignmentChambreDto findByPrisonerAndDate(String prisonerId, LocalDate date) {
		// TODO Auto-generated method stub
		
	    AssignmentChambre exist = repository.findByPrisonerAndDate(prisonerId, date);
        return AssignmentChambreDto.fromEntity(exist);
	 
	}
	
}
