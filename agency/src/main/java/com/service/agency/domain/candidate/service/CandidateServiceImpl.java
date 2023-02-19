package com.service.agency.domain.candidate.service;

import com.service.agency.domain.candidate.entity.Candidate;
import com.service.agency.domain.candidate.mapper.CandidateMapper;
import com.service.agency.domain.candidate.model.CandidateCreateDTO;
import com.service.agency.domain.candidate.repository.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CandidateServiceImpl implements CandidateService {

    private final CandidateRepository candidateRepository;
    private final CandidateMapper candidateMapper;

    @Autowired
    public CandidateServiceImpl(CandidateRepository candidateRepository,
        CandidateMapper candidateMapper) {
        this.candidateRepository = candidateRepository;
        this.candidateMapper = candidateMapper;
    }

    @Override
    public Candidate create(CandidateCreateDTO candidateCreateDTO) {
        Candidate candidate = candidateMapper.candidateCreateDTOToCandidate(candidateCreateDTO);
        return candidateRepository.save(candidate);
    }
}
