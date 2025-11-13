package com.cgpr.visitApp.controllers;

import static com.cgpr.visitApp.utils.Constants.APP_ROOT;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

 
import com.cgpr.visitApp.model.RaisonSanction;
import com.cgpr.visitApp.model.TypeSanction;
import com.cgpr.visitApp.model.gestionChombre.Chambre;
import com.cgpr.visitApp.repository.gestionChombre.ChambreRepository;
import com.cgpr.visitApp.repository.repositorySanction.RaisonSanctionRepository;
import com.cgpr.visitApp.repository.repositorySanction.TypeSanctionRepository;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequiredArgsConstructor
public class SanctionReferenceController {

    private final TypeSanctionRepository typeSanctionRepo;
    private final RaisonSanctionRepository raisonSanctionRepo;
    private final ChambreRepository chambreRepo;
   

    // ===== Type Sanction =====
    @GetMapping(path = APP_ROOT + "ref/type-sanctions", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TypeSanction> getAllTypeSanctions() {
        return typeSanctionRepo.findAll();
    }

    @PostMapping(path = APP_ROOT + "ref/type-sanctions", produces = MediaType.APPLICATION_JSON_VALUE)
    public TypeSanction addTypeSanction(@RequestBody TypeSanction ts) {
        return typeSanctionRepo.save(ts);
    }

    // ===== Raison Sanction =====
    @GetMapping(path = APP_ROOT + "ref/raison-sanctions", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RaisonSanction> getAllRaisonSanctions() {
        return raisonSanctionRepo.findAll();
    }

    @PostMapping(path = APP_ROOT + "ref/raison-sanctions", produces = MediaType.APPLICATION_JSON_VALUE)
    public RaisonSanction addRaisonSanction(@RequestBody RaisonSanction rs) {
        return raisonSanctionRepo.save(rs);
    }

    // ===== Chambre Isolement =====
 
    @GetMapping(path = APP_ROOT + "ref/chambre/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Chambre> searchChambres(
            @RequestParam String type,
            @RequestParam String codeGouv,
            @RequestParam String codePrison
    ) {
        return chambreRepo.findByTypeAndCodeGouvernoratAndCodePrison(type, codeGouv, codePrison);
    } 

    @PostMapping(path = APP_ROOT + "ref/chambres", produces = MediaType.APPLICATION_JSON_VALUE)
    public Chambre addChambreIsolement(@RequestBody Chambre ci) {
        return chambreRepo.save(ci);
    }

  
}
