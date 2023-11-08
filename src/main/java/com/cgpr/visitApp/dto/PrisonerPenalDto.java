package com.cgpr.visitApp.dto;

import com.cgpr.visitApp.model.Prisoner;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PrisonerPenalDto {

	    private String TNUMIDE;
	    private String TPNOMA;
	    private String TPPERA;
	    private String TPGPERA;
	    private String TNOMA;
	    private String libellePrison; // Champ pour stocker GETLIBELLEPRISON@LINK_PENALE

 

	       
}
