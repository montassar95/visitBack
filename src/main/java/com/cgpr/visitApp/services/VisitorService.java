package com.cgpr.visitApp.services;

import com.cgpr.visitApp.dto.ApiResponseAmenPhotoDto;
import com.cgpr.visitApp.dto.amenDto.ApiResponseAmenRoomDto;
import com.cgpr.visitApp.dto.amenDto.ApiResponseAmenVisitorDto;

import reactor.core.publisher.Mono;
 

public interface VisitorService {

 	Mono<ApiResponseAmenVisitorDto> callAmenVisitorAPI(String parameter);
 	Mono<ApiResponseAmenRoomDto> callAmenRoomAPI(String parameter);
	Mono<ApiResponseAmenPhotoDto> callAmenPhotoAPI(String parameter);

}
