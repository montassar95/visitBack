package com.cgpr.visitApp.dto.amenDto;

import java.util.List;

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
public class ApiResponseAmenRoomDto {
	 
	 private int status;
	    private String message;
	    private List<ApiResultAmenRoomDto> result;

	 
}
