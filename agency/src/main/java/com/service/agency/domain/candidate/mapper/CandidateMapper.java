package com.service.agency.domain.candidate.mapper;

import com.service.agency.domain.candidate.entity.Candidate;
import com.service.agency.domain.candidate.model.CandidateCreateDTO;
import com.service.agency.domain.candidate.model.CandidateGetDTO;
import com.service.agency.domain.city.service.CityServiceImpl;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = CityServiceImpl.class)
public interface CandidateMapper {
    Candidate candidateCreateDTOToCandidate(CandidateCreateDTO candidateCreateDTO);

    CandidateGetDTO candidateToCandidateGetDTO(Candidate candidate);

}
