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
@Table(name = "type_audience")
@SequenceGenerator(name = "SEQUENCE_TYPE_AUDIENCE", sequenceName = "SEQUENCE_TYPE_AUDIENCE", allocationSize = 1)
public class TypeAudience {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCE_TYPE_AUDIENCE")
    private Long id;

    private String label; // exemple : "جلسة استماع", "جلسة نظر"
}
