package com.cgpr.visitApp.services;

import com.cgpr.visitApp.dto.amenDto.ApiResponseAmenRoomDto;
import com.cgpr.visitApp.dto.amenDto.ApiResponseAmenVisitorDto;

import reactor.core.publisher.Mono;
 

public interface VisitorService {

	Mono<ApiResponseAmenVisitorDto> callAmenVisitorAPI(String parameter);
	Mono<ApiResponseAmenRoomDto> callAmenRoomAPI(String parameter);

}
