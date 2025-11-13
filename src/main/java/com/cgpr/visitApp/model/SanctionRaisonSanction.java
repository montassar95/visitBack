package com.cgpr.visitApp.model;
 

import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "sanction_raison_sanction")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SanctionRaisonSanction {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCE_SANCTION_RAISONS")
    @SequenceGenerator(name = "SEQUENCE_SANCTION_RAISONS", sequenceName = "SEQUENCE_SANCTION_RAISONS", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sanction_id", nullable = false)
    private Sanction sanction;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "raison_sanction_id", nullable = false)
    private RaisonSanction raisonSanction;
}

