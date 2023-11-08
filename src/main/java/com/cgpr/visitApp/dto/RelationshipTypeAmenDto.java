package com.cgpr.visitApp.dto;

import java.util.ArrayList;
import java.util.List;

import com.cgpr.visitApp.model.Prisoner;
import com.cgpr.visitApp.model.RelationshipType;
import com.cgpr.visitApp.model.Visitor;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RelationshipTypeAmenDto {

	
	   
       private String numide;
      
	   private String visiteur; 
	  private String relationship;
	  private String day;
	   private String time;
	   private String phone;
	   private String  statutSMS;
    
 
}
