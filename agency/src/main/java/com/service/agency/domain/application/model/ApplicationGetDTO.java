package com.service.agency.domain.application.model;

import com.service.agency.domain.candidate.model.CandidateGetDTO;

public record ApplicationGetDTO(
    Long id,
    CandidateGetDTO candidate
) {

}
