package com.cgpr.visitApp.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ApiResponseAmenPhotoDto {
	 
	    private int status;
	    private String message;
	    private  ApiResultAmenPhotoDto  result;

	 
}
