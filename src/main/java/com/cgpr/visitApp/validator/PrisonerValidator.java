package com.cgpr.visitApp.validator;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.util.StringUtils;

import com.cgpr.visitApp.dto.PrisonerDto;

 

public class PrisonerValidator {

    public static List<String> validate(PrisonerDto prisonerDto) {

        List<String> errors = new ArrayList<>();

        if (prisonerDto == null) {
            errors.add("Veuillez renseigner le nom");
            errors.add("Veuillez renseigner le prénom");
          

            return errors;
        }

        if (!StringUtils.hasLength(prisonerDto.getFirstName())) {
            errors.add("Veuillez renseigner le nom");
        }
        if (!StringUtils.hasLength(prisonerDto.getLastName())) {
            errors.add("Veuillez renseigner le prénom");
        }
      

        return errors;
    }
}
