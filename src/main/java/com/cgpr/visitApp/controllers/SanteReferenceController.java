package com.cgpr.visitApp.controllers;

 
import static com.cgpr.visitApp.utils.Constants.APP_ROOT;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import com.cgpr.visitApp.model.*;
import com.cgpr.visitApp.repository.*;
import com.cgpr.visitApp.repository.repositorySante.EtatUrgenceRepository;
import com.cgpr.visitApp.repository.repositorySante.HospitalRepository;
import com.cgpr.visitApp.repository.repositorySante.MedecinRepository;
import com.cgpr.visitApp.repository.repositorySante.SpecialiteRepository;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
public class SanteReferenceController {

    private final HospitalRepository hospitalRepo;
    private final MedecinRepository medecinRepo;
    private final SpecialiteRepository specialiteRepo;
    private final EtatUrgenceRepository etatUrgenceRepo;

    // ==================== HÔPITAUX ====================
    @GetMapping(path = APP_ROOT + "ref/sante/hospitals", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Hospital> getHospitals() {
        return hospitalRepo.findAll();
    }

    @PostMapping(path = APP_ROOT + "ref/sante/hospitals", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Hospital createHospital(@RequestBody Hospital hospital) {
        return hospitalRepo.save(hospital);
    }

    // ==================== MÉDECINS ====================
    @GetMapping(path = APP_ROOT + "ref/sante/medecins", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Medecin> getMedecins() {
        return medecinRepo.findAll();
    }

    @PostMapping(path = APP_ROOT + "ref/sante/medecins", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Medecin createMedecin(@RequestBody Medecin medecin) {
        return medecinRepo.save(medecin);
    }

    // ==================== SPÉCIALITÉS ====================
    @GetMapping(path = APP_ROOT + "ref/sante/specialites", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Specialite> getSpecialites() {
        return specialiteRepo.findAll();
    }

    @PostMapping(path = APP_ROOT + "ref/sante/specialites", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Specialite createSpecialite(@RequestBody Specialite specialite) {
        return specialiteRepo.save(specialite);
    }

    // ==================== ÉTAT URGENCE ====================
    @GetMapping(path = APP_ROOT + "ref/sante/etat-urgences", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<EtatUrgence> getEtatUrgences() {
        return etatUrgenceRepo.findAll();
    }

    @PostMapping(path = APP_ROOT + "ref/sante/etat-urgences", consumes = MediaType.APPLICATION_JSON_VALUE)
    public EtatUrgence createEtatUrgence(@RequestBody EtatUrgence etatUrgence) {
        return etatUrgenceRepo.save(etatUrgence);
    }
}
