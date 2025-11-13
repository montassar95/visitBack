package com.cgpr.visitApp.controllers;

import static com.cgpr.visitApp.utils.Constants.APP_ROOT;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cgpr.visitApp.dto.CentreWithTotalDto;
import com.cgpr.visitApp.dto.ChambreWithTotalDto;
import com.cgpr.visitApp.dto.ComplexeWithTotalDto;
import com.cgpr.visitApp.dto.PavillonWithTotalDto;
import com.cgpr.visitApp.dto.PrisonWithTotalDto;
import com.cgpr.visitApp.model.gestionChombre.Centre;
import com.cgpr.visitApp.model.gestionChombre.Chambre;
import com.cgpr.visitApp.model.gestionChombre.Complexe;
import com.cgpr.visitApp.model.gestionChombre.Pavillon;
import com.cgpr.visitApp.model.gestionChombre.Prison;
import com.cgpr.visitApp.model.gestionChombre.PrisonerCategory;
import com.cgpr.visitApp.model.gestionChombre.RaisonAffectationChambre;
import com.cgpr.visitApp.repository.AssignmentChambreService;
import com.cgpr.visitApp.repository.gestionChombre.CentreRepository;
import com.cgpr.visitApp.repository.gestionChombre.ChambreRepository;
import com.cgpr.visitApp.repository.gestionChombre.ComplexeRepository;
import com.cgpr.visitApp.repository.gestionChombre.PavillonRepository;
import com.cgpr.visitApp.repository.gestionChombre.PrisonRepository;
import com.cgpr.visitApp.repository.gestionChombre.PrisonerCategoryRepository;
import com.cgpr.visitApp.repository.gestionChombre.RaisonAffectationChambreRepository;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class GestionChambreController {

    @Autowired
    private PrisonRepository prisonRepository;

    @Autowired
    private CentreRepository centreRepository;

    @Autowired
    private ComplexeRepository complexeRepository;

    @Autowired
    private PavillonRepository pavillonRepository;

    @Autowired
    private ChambreRepository chambreRepository;
    
    
    @Autowired
    private PrisonerCategoryRepository prisonerCategoryRepository;
    
    @Autowired
    private RaisonAffectationChambreRepository raisonAffectationChambreRepository;
    
    @Autowired
    private AssignmentChambreService assignmentChambreService;

    // ================= PRISON =================
    @GetMapping(path = APP_ROOT + "gestion/prisons", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Prison> getAllPrisons() {
        return prisonRepository.findAll();
    }
    
    
    @GetMapping(path = APP_ROOT + "gestion/prisons/with-total/{date}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PrisonWithTotalDto> getPrisonsWithActiveTotals(@PathVariable String date) {
        LocalDate localDate = LocalDate.parse(date); // ⚠️ format ISO "yyyy-MM-dd"
       
        return assignmentChambreService.getPrisonsWithTotals( );
    }


    @PostMapping(path = APP_ROOT + "gestion/prisons", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Prison addPrison(@RequestBody Prison prison) {
        return prisonRepository.save(prison);
    }

    @DeleteMapping(path = APP_ROOT + "gestion/prisons/{id}")
    public void deletePrison(@PathVariable Long id) {
        prisonRepository.deleteById(id);
    }
    @GetMapping(path = APP_ROOT + "gestion/prisons/{prisonId}/prisons", produces = MediaType.APPLICATION_JSON_VALUE)
    public Prison getPrisonsById(@PathVariable Long prisonId) {
        return prisonRepository.findById(prisonId).get();
    }
    @GetMapping(path = APP_ROOT + "gestion/prisons/{gouvernorat}/{prison}/prisons", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Prison> getPrisonsById(@PathVariable("gouvernorat") String gouvernorat, @PathVariable("prison") String prison) {
        return prisonRepository.findByCodeGouAndCodePr(gouvernorat,prison); 
    }
    
    // ================= CENTRE =================
    @GetMapping(path = APP_ROOT + "gestion/prisons/{prisonId}/centres", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Centre> getCentresByPrison(@PathVariable Long prisonId) {
        return centreRepository.findByPrisonIdOrderByNameAsc(prisonId);
    }

    
    // Endpoint pour récupérer les centres d'une prison avec total
    @GetMapping(path =  APP_ROOT + "gestion/prisons/{prisonId}/centres/with-total/{date}" , produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CentreWithTotalDto> getCentresWithTotal(
    		@PathVariable("prisonId") Long prisonId,@PathVariable String date) {
    	 LocalDate localDate = LocalDate.parse(date); // ⚠️ format ISO "yyyy-MM-dd"
        return assignmentChambreService.getCentresWithActiveAffectations(prisonId);
    }
    
    @PostMapping(path = APP_ROOT + "gestion/centres", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Centre addCentre(@RequestBody Centre centre) {
        return centreRepository.save(centre);
    }

    @DeleteMapping(path = APP_ROOT + "gestion/centres/{id}")
    public void deleteCentre(@PathVariable Long id) {
        centreRepository.deleteById(id);
    }

    // ================= COMPLEXE =================
    @GetMapping(path = APP_ROOT + "gestion/centres/{centreId}/complexes", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Complexe> getComplexesByCentre(@PathVariable Long centreId) {
        return complexeRepository.findByCentreIdOrderByNameAsc(centreId);
    }
    @GetMapping(path = APP_ROOT + "gestion/centres/{centreId}/complexes/with-total/{date}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ComplexeWithTotalDto> getComplexesByCentreWithTotal(@PathVariable Long centreId,	@PathVariable("prisonId") Long prisonId,@PathVariable String date) {
    	 LocalDate localDate = LocalDate.parse(date); // ⚠️ format ISO "yyyy-MM-dd"
    	return assignmentChambreService.getComplexesWithActiveAffectations(centreId);
    }
    
    @PostMapping(path = APP_ROOT + "gestion/complexes", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Complexe addComplexe(@RequestBody Complexe complexe) {
        return complexeRepository.save(complexe);
    }

    @DeleteMapping(path = APP_ROOT + "gestion/complexes/{id}")
    public void deleteComplexe(@PathVariable Long id) {
        complexeRepository.deleteById(id);
    }

    // ================= PAVILLON =================
    @GetMapping(path = APP_ROOT + "gestion/complexes/{complexeId}/pavillons", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Pavillon> getPavillonsByComplexe(@PathVariable Long complexeId) {
        return pavillonRepository.findByComplexeIdOrderByNameAsc(complexeId);
    }

    @GetMapping(path = APP_ROOT + "gestion/complexes/{complexeId}/pavillons/with-total/{date}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PavillonWithTotalDto> getPavillonsByComplexeWithTotal(@PathVariable Long complexeId,	@PathVariable("prisonId") Long prisonId,@PathVariable String date) {
    	 LocalDate localDate = LocalDate.parse(date); // ⚠️ format ISO "yyyy-MM-dd"
    	return assignmentChambreService.getPavillonsWithActiveAffectations(complexeId);
    }

    @PostMapping(path = APP_ROOT + "gestion/pavillons", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Pavillon addPavillon(@RequestBody Pavillon pavillon) {
        return pavillonRepository.save(pavillon);
    }

    @DeleteMapping(path = APP_ROOT + "gestion/pavillons/{id}")
    public void deletePavillon(@PathVariable Long id) {
        pavillonRepository.deleteById(id);
    }

    // ================= CHAMBRE =================
    @GetMapping(path = APP_ROOT + "gestion/pavillons/{pavillonId}/chambres", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Chambre> getChambresByPavillon(@PathVariable Long pavillonId) {
        return chambreRepository.findByPavillonIdOrderByNameAsc(pavillonId);
    }
    @GetMapping(path = APP_ROOT + "gestion/pavillons/{pavillonId}/chambres/with-total/{date}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ChambreWithTotalDto> getChambresByPavillonWithTotal(@PathVariable Long pavillonId,	@PathVariable("prisonId") Long prisonId,@PathVariable String date) {
    	 LocalDate localDate = LocalDate.parse(date); // ⚠️ format ISO "yyyy-MM-dd"
    	return assignmentChambreService.getChambresWithActiveAffectations(pavillonId);
    }
    @PostMapping(path = APP_ROOT + "gestion/chambres", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Chambre addChambre(@RequestBody Chambre chambre) {
        return chambreRepository.save(chambre);
    }

    @DeleteMapping(path = APP_ROOT + "gestion/chambres/{id}")
    public void deleteChambre(@PathVariable Long id) {
        chambreRepository.deleteById(id);
    }
    
 // ================= RAISON AFFECTATION CHAMBRE =================
    
    @GetMapping(path = APP_ROOT + "gestion/raisons-affectation", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RaisonAffectationChambre> getAllRaisonsAffectation() {
        return raisonAffectationChambreRepository.findAll();
    }
    
    
 // ================= CATEGORIE PRISONNIER =================
     
    @GetMapping(path = APP_ROOT + "gestion/categories-prisonniers", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PrisonerCategory> getAllPrisonerCategories() {
        return prisonerCategoryRepository.findAll();
    }
}
