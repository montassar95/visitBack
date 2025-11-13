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
//	private String id;
//	 private String tpgpera;
//     private String tnoma;
//     private String tpnoma;
//     private String tppera;
//     private String tcodgou;
//     private String tcodpr;
//     private String tannee_pecule;
//     private String tnumpecule;
//     private String tcode_chambre;
//     private String tlibelle_pavillon;
//     private String tcode_pavillon;
//     private String tcodres;
//     private String tnumide;
     
	
	   private String tpgpera;                 // "الطاهر"
	    private String tnoma;                    // "بلقايد"
	    private String tpnoma;                   // "اميمة"
	    private String tppera;                   // "جلال الدين"
	    private String tcodgou;                  // "24"
	    private String tcodpr;                   // "01"
	    private String tannee_pecule;            // "2025"
	    private String tnumpecule;               // "1207"
	    private String tcode_chambre;            // "2"
	    private String tlibelle_pavillon;        // "الجناح أ"
	    private String tcode_pavillon;           // "01"
	    private String tcodres;                  // "34225"
	    private String tnumide;                  // "3000019589"
	    private String tcode_type_prisonier;     // "1"
	    private String tlibelle_type_prisonier;  // "عادي"
	    private String tcodecentre;              // "01"
	    private String libcentre;                // "مركز1"
	    private String tsolde_existant;          // "45560"
	    private String identifiantRibPoste;      // "GG250120784853"
	    private String identifianttaxiphone;     // "2501207"
	    private String id;                       // ""
}
