package com.cgpr.visitApp.services.Impl;

import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.http.HttpStatus;
//import org.springframework.security.crypto.password.NoOpPasswordEncoder; // Si vous utilisez NoOpPasswordEncoder
import org.springframework.http.MediaType;

import com.cgpr.visitApp.dto.ApiResponseAmenPhotoDto;
import com.cgpr.visitApp.dto.PrisonDto;
import com.cgpr.visitApp.dto.PrisonerDto;
import com.cgpr.visitApp.dto.amenDto.ApiResponseAmenRoomDto;
import com.cgpr.visitApp.dto.amenDto.ApiResponseAmenVisitorDto;
import com.cgpr.visitApp.exception.EntityNotFoundException;
import com.cgpr.visitApp.exception.ErrorCodes;
import com.cgpr.visitApp.exception.InvalidEntityException;
import com.cgpr.visitApp.model.Prisoner;
import com.cgpr.visitApp.repository.PrisonerPenalRepository;
import com.cgpr.visitApp.repository.PrisonerRepository;
import com.cgpr.visitApp.services.PrisonerService;
import com.cgpr.visitApp.services.VisitorService;
import com.cgpr.visitApp.validator.PrisonerValidator;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class VisitorServiceImpl implements VisitorService {

	 
	
 

	 
	@Override
	public Mono<ApiResponseAmenVisitorDto> callAmenVisitorAPI(String parameter) {
        String apiUrl = "http://192.168.100.2:8181/AppWebServiceCgprSiege/api/cgpr/visiteur/IdentiteAmenVisiteur/IdentiteAmenAllByGouvPrCodres/" + parameter;

        WebClient client = WebClient.builder()
                .baseUrl(apiUrl)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Basic " + getBasicAuthCredentialsµForVisiteur())
                .build();

        return client.get()
                .uri(apiUrl)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(ApiResponseAmenVisitorDto.class)
                .doOnError(error -> {
                    throw new RuntimeException("Erreur lors de l'appel à l'API ApiResponseAmenVisitorDto : " + error.getMessage());
                });
    } 

 

	@Override
	public Mono<ApiResponseAmenRoomDto> callAmenRoomAPI(String parameter) {
		  String apiUrl = "http://192.168.100.2:8181/AppWebServiceCgprSiege/api/cgpr/visiteur/IdentiteAmenVisiteur/IdentiteAmenAllByGouvPrCodresPavChambre/" + parameter;
//System.err.println(apiUrl);
	        WebClient client = WebClient.builder()
	                .baseUrl(apiUrl)
	                .defaultHeader(HttpHeaders.AUTHORIZATION, "Basic " + getBasicAuthCredentialsµForVisiteur())
	                .build();

	        return client.get()
	                .uri(apiUrl)
	                .accept(MediaType.APPLICATION_JSON)
	                .retrieve()
	                .bodyToMono(ApiResponseAmenRoomDto.class)
	                .doOnError(error -> {
	                    throw new RuntimeException("Erreur lors de l'appel à l'API ApiResponseAmenRoomDto : " + error.getMessage());
	                });
	}
	
	   private String getBasicAuthCredentialsµForVisiteur() {
	        String username = "visiteur";
	        String password = "Visireur#CgpR";
	        String credentials = username + ":" + password;
	        String base64Credentials = Base64.getEncoder().encodeToString(credentials.getBytes());
	        return base64Credentials;
	    }
	   
	   @Override
	   public Mono<ApiResponseAmenPhotoDto> callAmenPhotoAPI(String parameter) {
	        String apiUrl = "http://192.168.100.2:8181/AppWebServiceCgprSiege/api/cgpr/photo/IdentiteAmenPhoto/IdentiteAmenImgByGouvPrAnneeMoisJourCodres/" + parameter;

	        WebClient client = WebClient.builder()
	                .baseUrl(apiUrl)
	                .defaultHeader(HttpHeaders.AUTHORIZATION, "Basic " + getBasicAuthCredentialsForPhoto())
	                .build();

	        return client.get()
	        	    .accept(MediaType.APPLICATION_JSON)
	        	    .retrieve()
	        	    .bodyToMono(ApiResponseAmenPhotoDto.class)
	        	    .doOnError(error -> {
	        	        throw new RuntimeException("Erreur lors de l'appel à l'API ApiResponseAmenPhotoDto : " + error.getMessage());
	        	    });
	    } 

	 

	 
		
		   private String getBasicAuthCredentialsForPhoto() {
		        String username = "photo";
		        String password = "photoAxYPhoto2024";
		        String credentials = username + ":" + password;
		        String base64Credentials = Base64.getEncoder().encodeToString(credentials.getBytes());
		        return base64Credentials;
		    }
}
