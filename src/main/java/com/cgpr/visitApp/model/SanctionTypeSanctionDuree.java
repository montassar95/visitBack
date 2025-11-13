package com.cgpr.visitApp.model;
 

import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "sanction_type_sanction")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SanctionTypeSanctionDuree {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SANCTION_TYPE")
    @SequenceGenerator(name = "SEQ_SANCTION_TYPE", sequenceName = "SEQ_SANCTION_TYPE", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sanction_id", nullable = false)
    private Sanction sanction;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_sanction_id", nullable = false)
    private TypeSanctionDuree typeSanctionDuree;
 
}