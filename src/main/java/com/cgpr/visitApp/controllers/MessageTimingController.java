package com.cgpr.visitApp.controllers;

import static com.cgpr.visitApp.utils.Constants.APP_ROOT;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cgpr.visitApp.dto.MessageTimingDto;
import com.cgpr.visitApp.model.MessageTiming;
import com.cgpr.visitApp.services.MessageTimingService;
 

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
 
public class MessageTimingController {
    private final MessageTimingService messageTimingService;

    @Autowired
    public MessageTimingController(MessageTimingService messageTimingService) {
        this.messageTimingService = messageTimingService;
    }
	@PostMapping(path = APP_ROOT
			+ "messageTimings/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
 
    public ResponseEntity<MessageTiming> createMessageTiming(@RequestBody MessageTimingDto messageTimingDto) {
        MessageTiming savedMessageTiming = messageTimingService.saveMessageTiming(messageTimingDto);
        return ResponseEntity.ok(savedMessageTiming);
    }
	 
	
	@GetMapping(path = APP_ROOT + "messageTimings/findByTypeOfMsgAndActivatedIsTrue/{typeOfMsg}", produces = MediaType.APPLICATION_JSON_VALUE)
 	public ResponseEntity<MessageTiming> findAll(@PathVariable("typeOfMsg") String typeOfMsg) {

		return ResponseEntity.ok(messageTimingService.findByTypeOfMsgAndActivatedIsTrue(typeOfMsg));

	}
	
	@GetMapping(path = APP_ROOT + "messageTimings/findMaxIdByTypeOfMsg/{typeOfMsg}", produces = MediaType.APPLICATION_JSON_VALUE)
 	public ResponseEntity<MessageTiming> findMaxIdByTypeOfMsg(@PathVariable("typeOfMsg") String typeOfMsg) {

	if(messageTimingService.findMaxIdByTypeOfMsg(typeOfMsg).isPresent()) {
		return ResponseEntity.ok(messageTimingService.findMaxIdByTypeOfMsg(typeOfMsg).get());
	}
	return null;
		

	}
	
	
}
