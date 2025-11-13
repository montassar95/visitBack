package com.cgpr.visitApp.controllers;

import lombok.Data;

//Fichier : SmsResponse.java
 
@Data
public class SmsResponse {
 private String msgId;
 private String status;

 // Constructeurs
 public SmsResponse() {}
 public SmsResponse(String msgId, String status) {
     this.msgId = msgId;
     this.status = status;
 }

 
}
