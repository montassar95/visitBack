//package com.cgpr.visitApp.services.Impl;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
//import org.springframework.stereotype.Service;
//import org.springframework.web.reactive.function.client.WebClient;
//
//import com.cgpr.visitApp.services.EnvoiSmsService;
//
//import reactor.core.publisher.Mono;
//
//@Service
//public class EnvoiSmsServiceImpl implements EnvoiSmsService {
//
// 
//
//	private final WebClient webClient;
//
//    @Autowired
//    public EnvoiSmsServiceImpl(WebClient.Builder webClientBuilder) {
//        this.webClient = webClientBuilder
//                .baseUrl("https://backct.easybulk.intra")
//                .build();
//    }
//
//    @Override
//    public Mono<Boolean> envoyerSms(String phone, String message, String msgId) {
//        System.out.println("👉 Envoi SMS en cours ...");
//
//        Map<String, Object> variables = new HashMap<>();
//        variables.put("message", message);
//
//        Map<String, Object> contact = new HashMap<>();
//        contact.put("firstName", "");
//        contact.put("lastName", "");
//        contact.put("email", "");
//        contact.put("phone", phone);
//        contact.put("codeUri", "P6B1C");
//        contact.put("msgId", msgId);
//        contact.put("tags", new ArrayList<>());
//
//        Map<String, Object> entry = new HashMap<>();
//        entry.put("variables", variables);
//        entry.put("contact", contact);
//
//        Map<String, Object> body = new HashMap<>();
//        body.put("campaignKey", "121");
//        body.put("contacts", Collections.singletonList(entry));
//
//        return webClient.post()
//                .uri("/services/send-sms-easybulk")
//                .header("admin-key-3", "3252d343282d4c6c98b2e7c294cc8f80")
//                .contentType(MediaType.APPLICATION_JSON)
//                .bodyValue(body)
//                .retrieve()
//                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
//                        clientResponse -> clientResponse.bodyToMono(String.class)
//                                .flatMap(errorBody -> {
//                                    System.err.println("❌ Erreur EasyBulk : " + errorBody);
//                                    return Mono.error(new RuntimeException(errorBody));
//                                })
//                )
//                .bodyToMono(String.class)
//                .map(response -> {
//                    System.out.println("✅ Réponse brute EasyBulk : " + response);
//
//                    if (response.trim().startsWith("[")) {
//                        // ✅ log plus clair du succès
//                        System.out.println("📩 SMS envoyé avec succès !");
//                        System.out.println("📱 Téléphone  : " + phone);
//                        System.out.println("🆔 msgId coté EasyBulk : " + response);
//
//                        return true;
//                    }
//                    return false;
//                })
//                .onErrorResume(ex -> {
//                    System.err.println("❗ Exception technique : " + ex.getMessage());
//                    return Mono.just(false);
//                });
//    }
//
//}

package com.cgpr.visitApp.services.Impl;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.cgpr.visitApp.services.EnvoiSmsService;

import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

@Service
public class EnvoiSmsServiceImpl implements EnvoiSmsService {

    private final WebClient webClient;

    @Autowired
    public EnvoiSmsServiceImpl(WebClient.Builder webClientBuilder) throws Exception {

        // ---- Ignorer la vérification SSL (équivalent curl -k) ----
        SslContextBuilder sslContext = SslContextBuilder
                .forClient()
                .trustManager(InsecureTrustManagerFactory.INSTANCE);

        HttpClient httpClient = HttpClient.create()
                .secure(ssl -> ssl.sslContext(sslContext));

        this.webClient = webClientBuilder
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .baseUrl("https://backct.easybulk.intra")
                .build();
    }

    @Override
    public Mono<Boolean> envoyerSms(String phone, String message, String msgId) {

        // Construction du JSON pour l'API EasyBulk
        Map<String, Object> variables = new HashMap<>();
        variables.put("message", message);

        Map<String, Object> contact = new HashMap<>();
        contact.put("firstName", "");
        contact.put("lastName", "");
        contact.put("email", "");
        contact.put("phone", phone);
        contact.put("codeUri", "PE849");
        contact.put("msgId", msgId);
        contact.put("tags", new ArrayList<>());

        Map<String, Object> entry = new HashMap<>();
        entry.put("variables", variables);
        entry.put("contact", contact);

        Map<String, Object> body = new HashMap<>();
        body.put("campaignKey", "1");
        body.put("contacts", Collections.singletonList(entry));

        // Envoi via WebClient avec logs
        return webClient.post()
                .uri("/services/send-sms-easybulk")
                .header("API-KEY", "31357173fcbc428eae15a97a3859c77d")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(body)
                .retrieve()
                .bodyToMono(String.class)
                .map(response -> {
                    System.out.println("📩 SMS envoyé au numéro : " + phone);
                    System.out.println("🆔 msgId : " + msgId);
                    System.out.println("💬 Réponse brute EasyBulk : " + response);
                    return response.trim().startsWith("["); // true si succès
                })
                .onErrorResume(ex -> {
                    System.err.println("❌ Erreur lors de l'envoi du SMS : " + ex.getMessage());
                    return Mono.just(false);
                });
    }
}
