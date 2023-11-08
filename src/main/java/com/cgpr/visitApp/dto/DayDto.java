package com.cgpr.visitApp.dto;

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
public class DayDto {
	 
	    public String name;
	    public Long total;
	    
	    public List<String> timeSlots;
	    public List<Integer> visitCounts;
	    
	 
}
