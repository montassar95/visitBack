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

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tribunal")
@SequenceGenerator(name = "SEQUENCE_TRIBUNAL", sequenceName = "SEQUENCE_TRIBUNAL", allocationSize = 1)
public class Tribunal {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCE_TRIBUNAL")
    private Long id;

    private String name; // exemple : "المحكمة الابتدائية", "محكمة الاستئناف"
}

