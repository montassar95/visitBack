package com.cgpr.visitApp.services.Impl;

 

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cgpr.visitApp.dto.HospitalisationDto;
import com.cgpr.visitApp.exception.ErrorCodes;
import com.cgpr.visitApp.handlers.HospitalisationException;
import com.cgpr.visitApp.model.EtatUrgence;
import com.cgpr.visitApp.model.Hospital;
import com.cgpr.visitApp.model.Hospitalisation;
import com.cgpr.visitApp.model.Medecin;
import com.cgpr.visitApp.model.Prisoner;
import com.cgpr.visitApp.model.Specialite;
import com.cgpr.visitApp.repository.HospitalisationRepository;
import com.cgpr.visitApp.repository.PrisonerRepository;
import com.cgpr.visitApp.repository.repositorySante.EtatUrgenceRepository;
import com.cgpr.visitApp.repository.repositorySante.HospitalRepository;
import com.cgpr.visitApp.repository.repositorySante.MedecinRepository;
import com.cgpr.visitApp.repository.repositorySante.SpecialiteRepository;
import com.cgpr.visitApp.services.HospitalisationService;

@Service
public class HospitalisationServiceImpl implements HospitalisationService {

    @Autowired
    private HospitalisationRepository hospitalisationRepository;

    @Autowired
    private PrisonerRepository prisonerRepository;

    @Autowired
    private EtatUrgenceRepository etatUrgenceRepository;
    @Autowired
    private HospitalRepository hospitalRepository;
    @Autowired
    private MedecinRepository medecinRepository;
    @Autowired
    private SpecialiteRepository specialiteRepository;
    
    
    @Transactional
    @Override
    public HospitalisationDto save(HospitalisationDto dto) {

        // --- 1. Prisonnier ---
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

        LocalDate admission = dto.getAdmissionDate();
        LocalDate discharge = dto.getDischargeDate();

        // --- 2. Vérifications ---
        if (discharge != null && admission.isAfter(discharge)) {
            throw new HospitalisationException(
                    ErrorCodes.Hospitalisation_Error,
                    Arrays.asList("La date d'entrée ne peut pas être après la date de sortie !")
            );
        }

        List<Hospitalisation> ongoing = hospitalisationRepository.findOngoingByPrisoner(prisoner.getIdPrisoner());
        if (!ongoing.isEmpty() && discharge == null) {
            throw new HospitalisationException(
                    ErrorCodes.Hospitalisation_Error,
                    Arrays.asList("Le prisonnier est déjà hospitalisé actuellement !")
            );
        }

        List<Hospitalisation> overlapping = hospitalisationRepository.findOverlapping(
                prisoner.getIdPrisoner(),
                admission,
                discharge
        );
        if (!overlapping.isEmpty()) {
            throw new HospitalisationException(
                    ErrorCodes.Hospitalisation_Error,
                    Arrays.asList("La période d'hospitalisation chevauche une hospitalisation existante !")
            );
        }

        // --- 3. Récupération des entités de référence ---
        Hospital hospital = hospitalRepository.findById(dto.getHospitalId())
                .orElseThrow(() -> new HospitalisationException(
                        ErrorCodes.Hospitalisation_Error,
                        Arrays.asList("Hôpital non trouvé !")
                ));

        Medecin medecin = medecinRepository.findById(dto.getMedecinId())
                .orElseThrow(() -> new HospitalisationException(
                        ErrorCodes.Hospitalisation_Error,
                        Arrays.asList("Médecin non trouvé !")
                ));

        Specialite specialite = specialiteRepository.findById(dto.getSpecialiteId())
                .orElseThrow(() -> new HospitalisationException(
                        ErrorCodes.Hospitalisation_Error,
                        Arrays.asList("Spécialité non trouvée !")
                ));

        EtatUrgence etatUrgence = etatUrgenceRepository.findById(dto.getEtatUrgenceId())
                .orElseThrow(() -> new HospitalisationException(
                        ErrorCodes.Hospitalisation_Error,
                        Arrays.asList("État d'urgence non trouvé !")
                ));

        // --- 4. Création de l'hospitalisation ---
        Hospitalisation hospitalisation = new Hospitalisation();
        hospitalisation.setPrisoner(prisoner);
        hospitalisation.setCodeGouvernorat(dto.getCodeGouvernorat());
        hospitalisation.setCodePrison(dto.getCodePrison());
        hospitalisation.setNamePrison(dto.getNamePrison());
        hospitalisation.setCodeResidance(dto.getCodeResidance());
        hospitalisation.setAnneeResidance(dto.getAnneeResidance());
        hospitalisation.setHospital(hospital);
        hospitalisation.setMedecin(medecin);
        hospitalisation.setSpecialite(specialite);
        hospitalisation.setEtatUrgence(etatUrgence);
        hospitalisation.setAdmissionDate(admission);
        hospitalisation.setDischargeDate(discharge);

        // --- 5. Sauvegarde et retour DTO ---
        return HospitalisationDto.convertToDto(hospitalisationRepository.save(hospitalisation));
    }



    @Transactional
    @Override
    public HospitalisationDto update(HospitalisationDto dto) {

        if (dto.getId() == null) {
            throw new IllegalArgumentException("ID obligatoire pour la mise à jour");
        }

        Hospitalisation entity = hospitalisationRepository.findById(dto.getId())
                .orElseThrow(() -> new HospitalisationException(
                        ErrorCodes.Hospitalisation_Error,
                        Arrays.asList("Hospitalisation non trouvée")
                ));

        LocalDate admission = dto.getAdmissionDate();
        LocalDate discharge = dto.getDischargeDate();

        // --- 1. Vérifications des dates ---
        if (discharge != null && admission.isAfter(discharge)) {
            throw new HospitalisationException(
                    ErrorCodes.Hospitalisation_Error,
                    Arrays.asList("La date d'entrée ne peut pas être après la date de sortie !")
            );
        }

        // --- 2. Vérifier hospitalisation en cours (hors la ligne actuelle) ---
        List<Hospitalisation> ongoing = hospitalisationRepository.findOngoingByPrisoner(entity.getPrisoner().getIdPrisoner());
        boolean hasOngoing = ongoing.stream().anyMatch(h -> !h.getId().equals(entity.getId()));
        if (!hasOngoing && discharge == null) {
            throw new HospitalisationException(
                    ErrorCodes.Hospitalisation_Error,
                    Arrays.asList("Le prisonnier est déjà hospitalisé actuellement !")
            );
        }

        // --- 3. Vérifier chevauchement de dates (hors la ligne actuelle) ---
        List<Hospitalisation> overlapping = hospitalisationRepository.findOverlapping(
                entity.getPrisoner().getIdPrisoner(),
                admission,
                discharge
        ).stream().filter(h -> !h.getId().equals(entity.getId()))
         .collect(Collectors.toList());

        if (!overlapping.isEmpty()) {
            throw new HospitalisationException(
                    ErrorCodes.Hospitalisation_Error,
                    Arrays.asList("La période d'hospitalisation chevauche une hospitalisation existante !")
            );
        }

        // --- 4. Récupérer les entités de référence ---
        Hospital hospital = hospitalRepository.findById(dto.getHospitalId())
                .orElseThrow(() -> new HospitalisationException(
                        ErrorCodes.Hospitalisation_Error,
                        Arrays.asList("Hôpital non trouvé !")
                ));

        Medecin medecin = medecinRepository.findById(dto.getMedecinId())
                .orElseThrow(() -> new HospitalisationException(
                        ErrorCodes.Hospitalisation_Error,
                        Arrays.asList("Médecin non trouvé !")
                ));

        Specialite specialite = specialiteRepository.findById(dto.getSpecialiteId())
                .orElseThrow(() -> new HospitalisationException(
                        ErrorCodes.Hospitalisation_Error,
                        Arrays.asList("Spécialité non trouvée !")
                ));

        EtatUrgence etatUrgence = etatUrgenceRepository.findById(dto.getEtatUrgenceId())
                .orElseThrow(() -> new HospitalisationException(
                        ErrorCodes.Hospitalisation_Error,
                        Arrays.asList("État d'urgence non trouvé !")
                ));

        // --- 5. Mettre à jour les champs ---
        entity.setHospital(hospital);
        entity.setMedecin(medecin);
        entity.setSpecialite(specialite);
        entity.setEtatUrgence(etatUrgence);
        entity.setAdmissionDate(admission);
        entity.setDischargeDate(discharge);
        entity.setCodeGouvernorat(dto.getCodeGouvernorat());
        entity.setCodePrison(dto.getCodePrison());
        entity.setNamePrison(dto.getNamePrison());
        entity.setCodeResidance(dto.getCodeResidance());
        entity.setAnneeResidance(dto.getAnneeResidance());

        // --- 6. Sauvegarde et retour DTO ---
        Hospitalisation updated = hospitalisationRepository.save(entity);
        return HospitalisationDto.convertToDto(updated);
    }




    @Override
    public List<HospitalisationDto> findByPrisoner(String prisonerId) {
        List<Hospitalisation> hospitalisations = hospitalisationRepository.findByPrisonerId(prisonerId);
        return hospitalisations.stream()
                .map(HospitalisationDto::convertToDto)
                .collect(Collectors.toList());
    }

   

    @Override
    public void delete(Long id) {
    	System.err.println(id);
        hospitalisationRepository.deleteById(id);
    }

}
