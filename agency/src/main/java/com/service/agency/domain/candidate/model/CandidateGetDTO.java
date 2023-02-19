package com.service.agency.domain.candidate.model;

import com.service.agency.domain.city.model.CityGetDTO;
import com.service.agency.domain.profesionalDegree.ProfessionalDegree;

public record CandidateGetDTO(
    String firstName,
    String lastName,
    ProfessionalDegree professionalDegree,
    CityGetDTO city
) {

}
