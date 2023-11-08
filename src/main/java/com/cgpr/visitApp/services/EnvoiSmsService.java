package com.cgpr.visitApp.services;

import java.util.List;

import com.cgpr.visitApp.dto.PrisonDto;
import com.cgpr.visitApp.dto.PrisonerDto;

import reactor.core.publisher.Mono;

public interface EnvoiSmsService {

	Mono<Boolean> envoyerSms(String from, String to, String text);

}
