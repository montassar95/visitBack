package com.cgpr.visitApp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

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
@Table(name = "etat_urgence")
@SequenceGenerator(name = "SEQUENCE_ETAT_URGENCE", sequenceName = "SEQUENCE_ETAT_URGENCE", allocationSize = 1)
public class EtatUrgence {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCE_ETAT_URGENCE")
    private Long id;
 

    private String label; // الحالة (موعد، استعجالي، استشارة)
}
