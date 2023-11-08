package com.cgpr.visitApp.services.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cgpr.visitApp.dto.MessageTimingDto;
import com.cgpr.visitApp.model.MessageTiming;
import com.cgpr.visitApp.model.RelationshipType;
import com.cgpr.visitApp.repository.MessageTimingRepository;
import com.cgpr.visitApp.services.MessageTimingService;

@Service
public class MessageTimingServiceImpl implements MessageTimingService {
	
	
    private final MessageTimingRepository messageTimingRepository;

    @Autowired
    public MessageTimingServiceImpl(MessageTimingRepository messageTimingRepository) {
        this.messageTimingRepository = messageTimingRepository;
    }

    @Transactional
    @Override
    public MessageTiming saveMessageTiming(MessageTimingDto messageTimingDto) {
        MessageTiming messageTiming = new MessageTiming();
        messageTiming.setTypeOfMsg(messageTimingDto.getTypeOfMsg());
        messageTiming.setSunday(messageTimingDto.isSunday());
        messageTiming.setMonday(messageTimingDto.isMonday());
        messageTiming.setTuesday(messageTimingDto.isTuesday());
        messageTiming.setWednesday(messageTimingDto.isWednesday());
        messageTiming.setThursday(messageTimingDto.isThursday());
        messageTiming.setFriday(messageTimingDto.isFriday());
        messageTiming.setSaturday(messageTimingDto.isSaturday());
        messageTiming.setStartTime(messageTimingDto.getStartTime());
        messageTiming.setEndTime(messageTimingDto.getEndTime());
        messageTiming.setContent(messageTimingDto.getContent());
        messageTiming.setActivated(messageTimingDto.isActivated());
        // Récupérer la liste des éléments à mettre à jour
        List<MessageTiming> listToUpdate = messageTimingRepository.findByTypeOfMsg(messageTimingDto.getTypeOfMsg());

        // Parcourir la liste et mettre à jour chaque élément
        for (MessageTiming timing : listToUpdate) {
            timing.setActivated(false);
        }

        // Enregistrer les éléments mis à jour dans le référentiel
        messageTimingRepository.saveAll(listToUpdate);

        // Enregistrer le nouveau messageTiming
      
        return messageTimingRepository.save(messageTiming);
    }

	@Override
	public MessageTiming findByTypeOfMsgAndActivatedIsTrue(String typeOfMsg) {
		// TODO Auto-generated method stub
		return messageTimingRepository.findByTypeOfMsgAndActivatedIsTrue(typeOfMsg);
	}

	@Override
	public Optional<MessageTiming> findMaxIdByTypeOfMsg(String typeOfMsg) {
		// TODO Auto-generated method stub
		return  messageTimingRepository.findMaxIdByTypeOfMsg(typeOfMsg);
	}

 }

	 


 
