package com.cgpr.visitApp.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MessageTimingDto {
    private String typeOfMsg;
    private boolean sunday;
    private boolean monday;
    private boolean tuesday;
    private boolean wednesday;
    private boolean thursday;
    private boolean friday;
    private boolean saturday;
    private String startTime;
    private String endTime;
    private boolean activated;
    
    private String content;
 
}
