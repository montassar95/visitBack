package com.cgpr.visitApp.model.gestionSecurityZone;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.cgpr.visitApp.model.gestionChombre.Centre;
import com.cgpr.visitApp.model.gestionChombre.Prison;

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
@Table(name = "security_zone")
@SequenceGenerator(name = "SEQUENCE_SEC_ZONE", sequenceName = "SEQUENCE_SEC_ZONE", allocationSize = 1)
public class SecurityZone {

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCE_SEC_ZONE")
    private Long id;


    private String name;

 
 
}