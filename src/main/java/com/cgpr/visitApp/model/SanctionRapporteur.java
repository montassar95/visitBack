package com.cgpr.visitApp.model;

 

import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "sanction_rapporteur")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SanctionRapporteur {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCE_SANCTION_RAPPORTEUR")
    @SequenceGenerator(name = "SEQUENCE_SANCTION_RAPPORTEUR", sequenceName = "SEQUENCE_SANCTION_RAPPORTEUR", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sanction_id", nullable = false)
    private Sanction sanction;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rapporteur_id", nullable = false)
    private Rapporteur rapporteur;
}
