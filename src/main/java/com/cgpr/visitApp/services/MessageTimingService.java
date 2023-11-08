package com.cgpr.visitApp.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.query.Param;

import com.cgpr.visitApp.dto.MessageTimingDto;
import com.cgpr.visitApp.dto.PrisonerDto;
import com.cgpr.visitApp.model.MessageTiming;
import com.cgpr.visitApp.model.RelationshipType;

public interface  MessageTimingService {

	MessageTiming saveMessageTiming(MessageTimingDto messageTimingDto);
	MessageTiming   findByTypeOfMsgAndActivatedIsTrue(String typeOfMsg);
	  Optional<MessageTiming> findMaxIdByTypeOfMsg(  String typeOfMsg);
	

	 
}
