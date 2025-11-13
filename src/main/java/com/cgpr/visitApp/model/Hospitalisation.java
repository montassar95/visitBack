package com.cgpr.visitApp.model;

import java.time.LocalDate;

import javax.persistence.*;

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
@Table(name = "hospitalisation")
@SequenceGenerator(name = "SEQUENCE_HOSPITALISATION", sequenceName = "SEQUENCE_HOSPITALISATION", allocationSize = 1)
public class Hospitalisation {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCE_HOSPITALISATION")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "prisoner_id", referencedColumnName = "idPrisoner")
    private Prisoner prisoner;

    private String codeGouvernorat;
    private String codePrison;
    private String namePrison;
    private String codeResidance;
    private String anneeResidance;

//    private String hospitalName; // Nom de l’hôpital

    // Relations avec les classes de référence
    @ManyToOne
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;

    @ManyToOne
    @JoinColumn(name = "specialite_id")
    private Specialite specialite;

    @ManyToOne
    @JoinColumn(name = "medecin_id")
    private Medecin medecin;

    @ManyToOne
    @JoinColumn(name = "etat_urgence_id")
    private EtatUrgence etatUrgence;
    
    
    
    private LocalDate admissionDate; // Date d’entrée
    private LocalDate dischargeDate; // Date de sortie
	public Hospitalisation(Prisoner prisoner, String codeGouvernorat, String codePrison, String namePrison,
			String codeResidance, String anneeResidance, Hospital hospital, Specialite specialite, Medecin medecin,
			EtatUrgence etatUrgence, LocalDate admissionDate, LocalDate dischargeDate) {
		super();
		this.prisoner = prisoner;
		this.codeGouvernorat = codeGouvernorat;
		this.codePrison = codePrison;
		this.namePrison = namePrison;
		this.codeResidance = codeResidance;
		this.anneeResidance = anneeResidance;
		this.hospital = hospital;
		this.specialite = specialite;
		this.medecin = medecin;
		this.etatUrgence = etatUrgence;
		this.admissionDate = admissionDate;
		this.dischargeDate = dischargeDate;
	}

   
}
