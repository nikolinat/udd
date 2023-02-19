package com.service.agency.domain.candidate.service;

import com.service.agency.domain.candidate.entity.Candidate;
import com.service.agency.domain.candidate.model.CandidateCreateDTO;

public interface CandidateService {

    Candidate create(CandidateCreateDTO candidateCreateDTO);

}
