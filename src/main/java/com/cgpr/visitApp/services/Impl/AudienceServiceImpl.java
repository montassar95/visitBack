package com.cgpr.visitApp.services.Impl;
 
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cgpr.visitApp.dto.AudienceDto;
import com.cgpr.visitApp.model.Audience;
import com.cgpr.visitApp.model.Prisoner;
import com.cgpr.visitApp.model.Tribunal;
import com.cgpr.visitApp.model.TypeAudience;
import com.cgpr.visitApp.repository.AudienceRepository;
import com.cgpr.visitApp.repository.PrisonerRepository;
import com.cgpr.visitApp.repository.repositoryAudience.TribunalRepository;
import com.cgpr.visitApp.repository.repositoryAudience.TypeAudienceRepository;
import com.cgpr.visitApp.services.AudienceService;

@Service
public class AudienceServiceImpl implements AudienceService {

    @Autowired
    private AudienceRepository audienceRepository;

    @Autowired
    private PrisonerRepository prisonerRepository;

    @Autowired
    private TribunalRepository tribunalRepository;

    @Autowired
    private TypeAudienceRepository typeAudienceRepository;

    @Transactional
    @Override
    public AudienceDto save(AudienceDto audienceDto) {
        Prisoner prisoner;

        // Vérifier si le prisonnier existe
        Optional<Prisoner> prisonerOpt = prisonerRepository.findById(audienceDto.getPrisonerId().toString());
        if (prisonerOpt.isPresent()) {
            prisoner = prisonerOpt.get();
        } else {
            // Créer un nouveau prisonnier si n'existe pas (même logique que Sanction)
            prisoner = new Prisoner();
            prisoner.setIdPrisoner(audienceDto.getPrisonerId().toString());
            prisoner.setFirstName(audienceDto.getFirstName());
            prisoner.setLastName(audienceDto.getLastName());
            prisoner.setFatherName(audienceDto.getFatherName());
            prisoner.setGrandFatherName(audienceDto.getGrandFatherName());
            prisoner.setCodeNationalite(audienceDto.getCodeNationalite());

            prisoner = prisonerRepository.save(prisoner);
        }

        // Charger Tribunal et TypeAudience à partir des IDs
        Tribunal tribunal = null;
        if (audienceDto.getTribunalId() != null) {
            tribunal = tribunalRepository.findById(audienceDto.getTribunalId())
                    .orElse(null);
        }

        TypeAudience typeAudience = null;
        if (audienceDto.getTypeAudienceId() != null) {
            typeAudience = typeAudienceRepository.findById(audienceDto.getTypeAudienceId())
                    .orElse(null);
        }

        // Créer et sauvegarder l’audience
        Audience audience = Audience.builder()
                .prisoner(prisoner)
                .dateAudience(audienceDto.getDateAudience())
                .tribunal(tribunal)
                .typeAudience(typeAudience)
                .codeGouvernorat(audienceDto.getCodeGouvernorat())
                .codePrison(audienceDto.getCodePrison())
                .namePrison(audienceDto.getNamePrison())
                .codeResidance(audienceDto.getCodeResidance())
                .anneeResidance(audienceDto.getAnneeResidance())
                .build();

        return AudienceDto.convertToDto(audienceRepository.save(audience));
    }

    @Override
    public List<AudienceDto> findByPrisoner(String prisonerId) {
        List<Audience> audiences = audienceRepository.findByPrisonerId(prisonerId);
        return audiences.stream()
                        .map(AudienceDto::convertToDto)
                        .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public AudienceDto update(Long id, AudienceDto audienceDto) {
        Audience audience = audienceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Audience not found with id: " + id));

        // Charger le prisoner
        Prisoner prisoner = prisonerRepository.findById(audienceDto.getPrisonerId().toString())
                .orElseThrow(() -> new RuntimeException("Prisoner not found with id: " + audienceDto.getPrisonerId()));

        // Charger Tribunal et TypeAudience
        Tribunal tribunal = null;
        if (audienceDto.getTribunalId() != null) {
            tribunal = tribunalRepository.findById(audienceDto.getTribunalId()).orElse(null);
        }

        TypeAudience typeAudience = null;
        if (audienceDto.getTypeAudienceId() != null) {
            typeAudience = typeAudienceRepository.findById(audienceDto.getTypeAudienceId()).orElse(null);
        }

        // Mettre à jour les champs
        audience.setPrisoner(prisoner);
        audience.setDateAudience(audienceDto.getDateAudience());
        audience.setTribunal(tribunal);
        audience.setTypeAudience(typeAudience);
        audience.setCodeGouvernorat(audienceDto.getCodeGouvernorat());
        audience.setCodePrison(audienceDto.getCodePrison());
        audience.setNamePrison(audienceDto.getNamePrison());
        audience.setCodeResidance(audienceDto.getCodeResidance());
        audience.setAnneeResidance(audienceDto.getAnneeResidance());

        return AudienceDto.convertToDto(audienceRepository.save(audience));
    }

    @Override
    public void delete(Long id) {
    	System.err.println(id);
    	audienceRepository.deleteById(id);
    }
}
