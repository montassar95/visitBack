package com.cgpr.visitApp.SMS;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cgpr.visitApp.model.MessageTiming;
import com.cgpr.visitApp.model.RelationshipType;
import com.cgpr.visitApp.repository.MessageTimingRepository;
import com.cgpr.visitApp.repository.RelationshipTypeRepository;
import com.cgpr.visitApp.services.EnvoiSmsService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.function.Tuple2;

@Component
public class SmsScheduler {
    private final EnvoiSmsService envoiSmsService;
    private final MessageTimingRepository messageTimingRepository;
    private final RelationshipTypeRepository relationshipTypeRepository;

    public SmsScheduler(EnvoiSmsService envoiSmsService, MessageTimingRepository messageTimingRepository, RelationshipTypeRepository relationshipTypeRepository) {
        this.envoiSmsService = envoiSmsService;
        this.messageTimingRepository = messageTimingRepository;
        this.relationshipTypeRepository = relationshipTypeRepository;
    }

      //@Scheduled(cron = "0 * * * * *") // Cette méthode sera exécutée chaque minute
     //  @Scheduled(cron = "*/5 * * * * *") // toutes les 1 secondes
       @Scheduled(cron = "0/30 * * * * *") //30 seconde

     public void envoyerSmsPlanifie() {
        LocalDate currentDate = LocalDate.now();
        DayOfWeek currentDayOfWeek = currentDate.getDayOfWeek();
        LocalTime currentTime = LocalTime.now();

        String from = "SOUJOUN";


        Flux<Tuple2<RelationshipType, MessageTiming>> fluxEntrant = getRelationshipTypeFlux("Entrant", currentDayOfWeek, currentTime);
        Flux<Tuple2<RelationshipType, MessageTiming>> fluxMutation = getRelationshipTypeFlux("Mutation", currentDayOfWeek, currentTime);
        Flux<Tuple2<RelationshipType, MessageTiming>> fluxVisit = getRelationshipTypeFlux("Visit", currentDayOfWeek, currentTime);
        Flux<Tuple2<RelationshipType, MessageTiming>> fluxLibiration = getRelationshipTypeFlux("Libiration", currentDayOfWeek, currentTime);

        // Utilisez merge pour exécuter les flux de manière parallèle
        Flux<Tuple2<RelationshipType, MessageTiming>> mergedFlux = Flux.merge(fluxEntrant, fluxMutation, fluxVisit, fluxLibiration);

        // Traitez les éléments du flux
        mergedFlux
            .parallel()
            .runOn(Schedulers.parallel()) // Exécution parallèle
            .doOnNext(tuple -> {
                RelationshipType relationshipType = tuple.getT1();                      // Récupérer RelationshipType
                MessageTiming messageTiming = tuple.getT2();                           // Récupérer MessageTiming
                envoyerSms(from, relationshipType, messageTiming.getContent());       // Appeler envoyerSms avec les deux objets
            })
            .subscribe(); // N'oubliez pas d'ajouter cette ligne pour déclencher l'exécution du flux

    }

     
     //je veux retourner   Flux<RelationshipType> + messageTiming
    private Flux<Tuple2<RelationshipType, MessageTiming>> getRelationshipTypeFlux(String typeOfMsg, DayOfWeek currentDayOfWeek, LocalTime currentTime) {
        MessageTiming messageTiming = messageTimingRepository.findByTypeOfMsgAndActivatedIsTrue(typeOfMsg);
        if (messageTiming != null && conditionsPourEnvoyerSms(messageTiming, currentDayOfWeek, currentTime)) {
//        	System.err.println("lancement de getRelationshipTypeFlux = "+typeOfMsg);
        	
        	Flux<RelationshipType> relationshipTypeFlux = Flux.fromIterable(relationshipTypeRepository.findRelationshipTypesByStatutSMSAndLibelleStatutResidance(typeOfMsg));

             // Utilisez Mono.just pour créer un flux contenant l'objet MessageTiming
             Mono<MessageTiming> messageTimingMono = Mono.just(messageTiming);

             // Utilisez Flux.zip pour combiner les deux flux en un seul
             return Flux.zip(relationshipTypeFlux, messageTimingMono);
//            return Flux.fromIterable(relationshipTypeRepository.findRelationshipTypesByStatutSMSAndLibelleStatutResidance(typeOfMsg));
        }
        return Flux.empty(); 
    }

    private void envoyerSms(String from, RelationshipType relationshipType,String content) {
        String to = relationshipType.getVisitor().getPhone();
        String text = "تعلمكم إدارة سجن " + relationshipType.getNamePrison() + " " ;
        
        if(relationshipType.getStatutResidance().toString().trim().equals("O")) {
        	
        	 text =  text + content+ " " + "كل يوم  "+relationshipType.getDay()+   " على الساعة "+relationshipType.getTime()+". "     ;
        	 text =  text +" "+"الرجاء القدوم قبل ربع ساعة للقيام بإجراءات التسجيل";
        }
        else {
        	text =text+  content;
        }
        
        
        envoiSmsService.envoyerSms( "216"+to, text,relationshipType.getId()+"")
        .subscribe(envoiReussi -> {
            if (envoiReussi) {
            	relationshipType.setStatutSMS("SENT");
            } else {
            	relationshipType.setStatutSMS("FAILED");
            }
            relationshipType.setSentDate(new Date());
            relationshipTypeRepository.save(relationshipType);
        });
//        relationshipTypeRepository.save(entrant);
    }

    private boolean conditionsPourEnvoyerSms(MessageTiming messageTiming, DayOfWeek currentDayOfWeek, LocalTime currentTime) {
//    	System.err.println("lancement de conditionsPourEnvoyerSms");
        if (messageTiming.isActivated()) {
            switch (currentDayOfWeek) {
                case SUNDAY:
                    if (messageTiming.isSunday()) {
                        break;
                    } else {
                        return false;
                    }
                case MONDAY:
                    if (messageTiming.isMonday()) {
                        break;
                    } else {
                        return false;
                    }
                case TUESDAY:
                    if (messageTiming.isTuesday()) {
                        break;
                    } else {
                        return false;
                    }
                case WEDNESDAY:
                    if (messageTiming.isWednesday()) {
                        break;
                    } else {
                        return false;
                    }
                case THURSDAY:
                    if (messageTiming.isThursday()) {
                        break;
                    } else {
                        return false;
                    }
                case FRIDAY:
                    if (messageTiming.isFriday()) {
                        break;
                    } else {
                        return false;
                    }
                case SATURDAY:
                    if (messageTiming.isSaturday()) {
                        break;
                    } else {
                        return false;
                    }
                default:
                    return false;
            }

            LocalTime startTime = LocalTime.parse(messageTiming.getStartTime());
            LocalTime endTime = LocalTime.parse(messageTiming.getEndTime());

            return !currentTime.isBefore(startTime) && !currentTime.isAfter(endTime);
        }
        return false;
    }
}
