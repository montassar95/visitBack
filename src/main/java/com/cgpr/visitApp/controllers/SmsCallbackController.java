package com.cgpr.visitApp.controllers;
 
 
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cgpr.visitApp.model.RelationshipType;
import com.cgpr.visitApp.repository.RelationshipTypeRepository;

@RestController
@RequestMapping("/api/sms")
public class SmsCallbackController {

	
	
	  @Autowired
	    private RelationshipTypeRepository relationshipTypeRepository;
	  
	  
	  
// @PostMapping("/callback")
// public ResponseEntity<String> receiveSmsResponse(@RequestBody SmsResponse smsResponse) {
//     System.out.println("🆔 ID du message : " + smsResponse.getMsgId());
//     System.out.println("📦 Statut reçu : " + smsResponse.getStatus());
//
//     // Tu peux ici sauvegarder le statut dans ta base de données
//     // smsService.saveStatus(smsResponse);
//
//     return ResponseEntity.ok("Réponse reçue avec succès ✅");
// }
 
	  @PostMapping("/callback")
 public ResponseEntity<String> receiveSmsResponse(@RequestBody SmsResponse smsResponse) {

     System.out.println("🆔 ID du message reçu : " + smsResponse.getMsgId());
     System.out.println("📦 Statut DLR : " + smsResponse.getStatus());

     try {
         Long id = Long.parseLong(smsResponse.getMsgId()); // convertir en Long

         Optional<RelationshipType> optionalRT = relationshipTypeRepository.findById(id);
         if (optionalRT.isPresent()) {
             RelationshipType rt = optionalRT.get();
             rt.setStatutDLR(smsResponse.getStatus());
             rt.setDlrDate(new Date());
             relationshipTypeRepository.save(rt);
             System.out.println("✅ RelationshipType mis à jour pour id=" + id);
         } else {
             System.err.println("❌ Aucun RelationshipType trouvé pour id=" + id);
         }

     } catch (NumberFormatException e) {
         System.err.println("❌ msgId n'est pas un Long valide : " + smsResponse.getMsgId());
     }

     return ResponseEntity.ok("DLR reçu et traité ✅");
 }
}
