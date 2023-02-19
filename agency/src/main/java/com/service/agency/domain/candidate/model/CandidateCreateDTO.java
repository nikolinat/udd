package com.service.agency.domain.candidate.model;

import com.service.agency.domain.profesionalDegree.ProfessionalDegree;

public record CandidateCreateDTO(
    String firstName,
    String lastName,
    ProfessionalDegree professionalDegree,
    Long city
) {

}
