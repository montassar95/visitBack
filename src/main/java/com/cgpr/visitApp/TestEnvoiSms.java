package com.cgpr.visitApp;

 

import com.cgpr.visitApp.services.Impl.EnvoiSmsServiceImpl;
import reactor.core.publisher.Mono;
import org.springframework.web.reactive.function.client.WebClient;

public class TestEnvoiSms {

    public static void main(String[] args) throws Exception {

        // Créer WebClient.Builder "propre" pour tester
        WebClient.Builder webClientBuilder = WebClient.builder();

        // Créer l'instance du service
        EnvoiSmsServiceImpl smsService = new EnvoiSmsServiceImpl(webClientBuilder);

        // Définir numéro, message et msgId
        String phone = "21695472986";
        String message = "Test SMS depuis Java";
        String msgId = "5465465";

        // Appeler le service et bloquer pour obtenir le résultat
        Mono<Boolean> resultMono = smsService.envoyerSms(phone, message, msgId);
        Boolean success = resultMono.block(); // Bloque jusqu'à la réponse

        // Affichage du résultat
        if (success) {
            System.out.println("✅ SMS envoyé avec succès !");
        } else {
            System.out.println("❌ Échec de l'envoi du SMS.");
        }
    }
}
