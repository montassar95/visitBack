package com.cgpr.visitApp.model;

 

import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "sanction_participant")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SanctionParticipant {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCE_SANCTION_PARTICIPANT")
    @SequenceGenerator(name = "SEQUENCE_SANCTION_PARTICIPANT", sequenceName = "SEQUENCE_SANCTION_PARTICIPANT", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sanction_id", nullable = false)
    private Sanction sanction;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "participant_id", nullable = false)
    private DetenuParticipant participant;
}
