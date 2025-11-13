package com.cgpr.visitApp.model.gestionSecurityZone;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "security_center")
@SequenceGenerator(name = "SEQUENCE_SEC_CENTER", sequenceName = "SEQUENCE_SEC_CENTER", allocationSize = 1)
public class SecurityCenter  {

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCE_SEC_CENTER")
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "zone_id")
    private SecurityZone zone;

    // Getters et setters
}