package com.cgpr.visitApp.model;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.cgpr.visitApp.model.gestionChombre.Chambre;

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
@Table(name = "sanction")
@SequenceGenerator(name = "SEQUENCE_SANCTION", sequenceName = "SEQUENCE_SANCTION", allocationSize = 1)
public class Sanction{
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQUENCE_SANCTION")
    private Long id;

    // Prisonnier concerné
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prisoner_id", referencedColumnName = "idPrisoner")
    private Prisoner prisoner;

    // Informations sur la prison et la résidence
    private String codeGouvernorat;
    private String codePrison;
    private String namePrison;
    private String codeResidance;
    private String anneeResidance;

    // Informations sur la sanction
    private LocalDate dateConseil;      // تاريخ المجلس
    private String numeroReference;     // رقم الإحالة
    private String jourIncident;        // يوم الواقعة
    private String sanctionText;        // نص العقوبة
    private String infractionText;      // نص المخالفة

    // Sélections de chambre
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chambre_isolement_id")
    private Chambre chambreIsolement;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chambre_transfert_id")
    private Chambre chambreTransfert;

    
    @OneToMany(mappedBy = "sanction", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SanctionParticipant> sanctionParticipants;

    @OneToMany(mappedBy = "sanction", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SanctionRapporteur> sanctionRapporteurs;

    @OneToMany(mappedBy = "sanction", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SanctionTypeSanctionDuree> sanctionTypeSanctionDurees;

    @OneToMany(mappedBy = "sanction", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SanctionRaisonSanction> sanctionRaisonsSanction;
    
    
 

}