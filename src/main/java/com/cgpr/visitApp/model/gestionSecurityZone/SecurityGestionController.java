package com.cgpr.visitApp.model.gestionSecurityZone;
 
import static com.cgpr.visitApp.utils.Constants.APP_ROOT;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class SecurityGestionController {

    @Autowired
    private SecurityZoneRepository zoneRepository;

    @Autowired
    private SecurityCenterRepository centerRepository;

    // ====================== ZONES ======================

    @GetMapping(path = APP_ROOT + "gestion-security/zones", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<SecurityZone> getAllZones() {
        return zoneRepository.findAll();
    }

    @PostMapping(path = APP_ROOT + "gestion-security/zones", 
                 produces = MediaType.APPLICATION_JSON_VALUE, 
                 consumes = MediaType.APPLICATION_JSON_VALUE)
    public SecurityZone addZone(@RequestBody SecurityZone zone) {
        return zoneRepository.save(zone);
    }

    @DeleteMapping(path = APP_ROOT + "gestion-security/zones/{id}")
    public void deleteZone(@PathVariable Long id) {
        zoneRepository.deleteById(id);
    }

    @GetMapping(path = APP_ROOT + "gestion-security/zones/{zoneId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public SecurityZone getZoneById(@PathVariable Long zoneId) {
        return zoneRepository.findById(zoneId).orElse(null);
    }

    // ====================== CENTRES ======================

    @GetMapping(path = APP_ROOT + "gestion-security/zones/{zoneId}/centres", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<SecurityCenter> getCentersByZone(@PathVariable Long zoneId) {
        return centerRepository.findCenterByZone(zoneId);
    }

    @PostMapping(path = APP_ROOT + "gestion-security/centres", 
                 produces = MediaType.APPLICATION_JSON_VALUE, 
                 consumes = MediaType.APPLICATION_JSON_VALUE)
    public SecurityCenter addCenter(@RequestBody SecurityCenter center) {
        // on rattache la zone à partir de l’objet reçu s’il contient juste l’ID
        if (center.getZone() != null && center.getZone().getId() != null) {
            SecurityZone zone = zoneRepository.findById(center.getZone().getId())
                    .orElseThrow(() -> new RuntimeException("Zone introuvable"));
            center.setZone(zone);
        }
        return centerRepository.save(center);
    }

    @DeleteMapping(path = APP_ROOT + "gestion-security/centres/{id}")
    public void deleteCenter(@PathVariable Long id) {
        centerRepository.deleteById(id);
    }
}

