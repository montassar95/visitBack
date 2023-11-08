package com.cgpr.visitApp.model;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@SequenceGenerator(name = "SEQUENCE_MESSAGE_TIMING", sequenceName = "SEQUENCE_MESSAGE_TIMING", allocationSize = 1)
public class MessageTiming {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCE_MESSAGE_TIMING")
    private Long id;
    private String typeOfMsg;
    private boolean sunday;
    private boolean monday;
    private boolean tuesday;
    private boolean wednesday;
    private boolean thursday;
    private boolean friday;
    private boolean saturday;
    private String startTime;
    private String endTime;
    private boolean activated;
    private String content;

    // Getters et setters
}
