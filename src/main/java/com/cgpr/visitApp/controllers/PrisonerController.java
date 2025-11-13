package com.cgpr.visitApp.controllers;

import static com.cgpr.visitApp.utils.Constants.APP_ROOT;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cgpr.visitApp.dto.PrisonDto;
import com.cgpr.visitApp.dto.PrisonerDto;
import com.cgpr.visitApp.services.PrisonerService;
 
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
 
public class PrisonerController {

	private PrisonerService prisonerService;

	@Autowired
	public PrisonerController(PrisonerService prisonerService) {

		this.prisonerService = prisonerService;
	}

	
	@GetMapping(path =  "/test", produces = MediaType.APPLICATION_JSON_VALUE)
 	public String test() {
		 System.exit(0);
		return "";

	}
	
	
	
	@PostMapping(path = APP_ROOT
			+ "prisoners/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
 
	public ResponseEntity<PrisonerDto> save(@RequestBody PrisonerDto prisonerDto) {

		return ResponseEntity.ok(prisonerService.save(prisonerDto));

	}

	@GetMapping(path = APP_ROOT + "prisoners/id/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	 
	public ResponseEntity<PrisonerDto> findById(@PathVariable("id") String id) {

		return ResponseEntity.ok(prisonerService.findById(id));

	}

	@GetMapping(path = APP_ROOT + "prisoners/all", produces = MediaType.APPLICATION_JSON_VALUE)
 	public ResponseEntity<List<PrisonerDto>> findAll() {

		return ResponseEntity.ok(prisonerService.findAll());

	}

	
	@GetMapping(path = APP_ROOT + "prisons/all", produces = MediaType.APPLICATION_JSON_VALUE)
	 
	public ResponseEntity<List<PrisonDto>> findAllPrisons() {

		return ResponseEntity.ok(prisonerService.findAllPrisons());

	}
	
	
	
	@DeleteMapping(path = APP_ROOT + "prisoners/delete/{id}")
	 
	public ResponseEntity deletePrisoner(@PathVariable("id") String id) {

		prisonerService.delete(id);

		return ResponseEntity.ok().build();

	}

}
