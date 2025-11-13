package com.cgpr.visitApp.dto;

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
public class PrisonWithTotalDto {

    private Long id;
    private String codeGou;
    private String codePr;
    private String name;
    private String type;
    private Long  compteHier;      // حساب الأمس
    private Long  nombreEntrants;  // الدخلون
    private Long  nombreSortants;  // الخارجون
    private Long  compteAujourdhui; // حساب اليوم

    private int compteHospitalises;//حساب المستشفيات
}
 
