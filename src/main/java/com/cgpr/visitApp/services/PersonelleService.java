package com.cgpr.visitApp.services;

import java.util.List;

import com.cgpr.visitApp.dto.PersonelleDto;

public interface PersonelleService {

 	
	

	PersonelleDto findByLoginAndPwd(String login , String pwd);


}
