package com.cgpr.visitApp.dto.amenDto;

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
public class ApiResultAmenRoomDto {
	private String id;
	 private String tpgpera;
     private String tnoma;
     private String tpnoma;
     private String tppera;
     private String tcodgou;
     private String tcodpr;
     private String tannee_pecule;
     private String tnumpecule;
     private String tcode_chambre;
     private String tlibelle_pavillon;
     private String tcode_pavillon;
     private String tcodres;
     private String tnumide;
     
}
