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
public class ApiResultAmenVisitorDto {
	  private String id;
	    private String tclose;
	    private String tcode_VISITEUR_CIN;
	    private String tnom_PRENOM_VISITEUR;
	    private String tcode_TYPE_LIAISON;
	    private String tlibelle_TYPE_LIAISON;
	    private String tnumide_PENAL;
	    private String tcodres;
	    private String tpnoma;
	    private String tppera;
	    private String tpgpera;
	    private String tnoma;
	    private String gouvprpecule;
	    private String tcodgou;
	    private String tcodpr;
	    private String tannee_PECULE;
	    private String tnumpecule;
}
