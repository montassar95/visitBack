package com.cgpr.visitApp.model;

import javax.persistence.*;

import lombok.*;

@Entity
@Table(name = "sanction_type_duree")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TypeSanctionDuree {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TYPE_SANCTION_DUREE")
    @SequenceGenerator(name = "SEQ_TYPE_SANCTION_DUREE", sequenceName = "SEQ_TYPE_SANCTION_DUREE", allocationSize = 1)
    private Long id;

  

    // Relation avec TypeSanction
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_sanction_id", nullable = false)
    private TypeSanction typeSanction;

    // Durée de la sanction
    @Column(nullable = false)
    private Integer duree; // durée en jours ou mois
}
