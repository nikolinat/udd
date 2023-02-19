package com.service.agency.infrastructure.elasticsearch.model;

import com.service.agency.domain.candidate.model.CandidateGetDTO;

public record ResultDataDTO(
    CandidateGetDTO candidateGetDTO,
    String highlight
) {

}
