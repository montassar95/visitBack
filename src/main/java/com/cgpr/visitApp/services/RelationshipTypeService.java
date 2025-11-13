package com.cgpr.visitApp.services;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;

import com.cgpr.visitApp.dto.DayDto;
import com.cgpr.visitApp.dto.EventSMSDto;
import com.cgpr.visitApp.dto.PrisonerDto;
import com.cgpr.visitApp.dto.RelationshipTypeAmenDto;
import com.cgpr.visitApp.dto.RelationshipTypeDto;
import com.cgpr.visitApp.model.RelationshipType;

public interface  RelationshipTypeService {
	
	List<RelationshipType>  save (RelationshipTypeDto relationshipTypeDto);
	
	RelationshipTypeDto findByPrisonerId(String idPrisoner);
	RelationshipTypeDto findByPrisonerIdFromPenal(String idPrisoner);
	
	
	List<PrisonerDto> findPrisonersEnteringByPeriodandLocation( String startDate,String endDate, String gouvernorat, String prison);
	
	List<PrisonerDto> findPrisonersMutatingByPeriodandLocation( String startDate,String endDate, String gouvernorat, String prison);
	
	
	
	List<PrisonerDto> findPrisonersMutatingSortantByPeriodandLocation( String startDate,String endDate, String gouvernorat, String prison);
	
	
	
	
	
	List<RelationshipTypeDto> findPrisonersLeavingByPeriodandLocation( String startDate,String endDate, String gouvernorat, String prison);
	
	
//	List<RelationshipTypeDto> findPrisonersExistingByLocationWithOutVisit(  String gouvernorat, String prison);
	
	
	List<PrisonerDto> findPrisonersExistingByLocationWithOutVisit(  String gouvernorat, String prison,String nom,String prenom, String nomPere );
	
	List<RelationshipTypeDto> findAllPrisonersExisting (  String gouvernorat, String prison,String nom,String prenom, String nomPere ,String idPrisoner , String numeroEcrou);
	
	
	
	List<RelationshipTypeDto> findAllPrisonersExistingFromPenal (  String gouvernorat, String prison,String nom,String prenom, String nomPere ,String idPrisoner , String numeroEcrou);
	
	
	List<RelationshipTypeDto> findByTimeAndDayAndPrisonAndStatutO( String time,String day, String gouvernorat, String prison, String nom, String prenom, String nomPere,String idPrisoner, String numeroEcrou );
	
	List<RelationshipTypeDto> findByPrisonAndStatutOAndStatutSMSReady(   String gouvernorat, String prison);
	
	List<RelationshipTypeDto> findByPrisonAndStatutNotO(  String gouvernorat, String prison); 
	
	void updateRelationshipTypeStatutResidance();
	
	 List<List<String>> getCountMatrix(  String codeGouvernorat,  String codePrison,  String centre,  String salle) ;
	 List<DayDto>  getCountMatrixForDashboarding(  String codeGouvernorat, String codePrison);
	 
	 void updateStatutSMSToReady(String statut,List<RelationshipTypeDto> relationshipTypeDtoList );
	 
	 List<RelationshipType> findRelationshipTypesByStatutSMSAndLibelleStatutResidance(  String parametre);
	 
	   List<RelationshipTypeDto > findByStatuses(  
			   String codeGouvernorat,
               String codePrison,
               String statutResidance,
               String statutSMS,
               String dateStart, String dateEnd);
	   
	   
	   
	   List<RelationshipTypeAmenDto> findVisitByPrisonerId (String idPrisoner);
//	 List<DayDto> getCountByDay(String codeGouvernorat, String codePrison);
	   
	   
	   List<EventSMSDto> getEntrantEvents(String prisonerId);
	   
	   public List<PrisonerDto> findResidantPrisonersByLocation( String gouvernorat, String prison) ;
	 
}
