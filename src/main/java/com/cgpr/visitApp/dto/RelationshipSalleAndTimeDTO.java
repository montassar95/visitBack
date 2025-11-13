package com.cgpr.visitApp.dto;

 

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
public class RelationshipSalleAndTimeDTO {
    private String day;
    private String time;
    private String centre;
    private String salle;
}