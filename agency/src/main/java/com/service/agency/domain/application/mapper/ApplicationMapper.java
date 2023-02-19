package com.service.agency.domain.application.mapper;

import com.service.agency.domain.application.entity.Application;
import com.service.agency.domain.application.model.ApplicationGetDTO;
import com.service.agency.domain.candidate.mapper.CandidateMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {CandidateMapper.class})
public interface ApplicationMapper {
    ApplicationGetDTO applicationToApplicationGetDTO(Application jobApplication);

}
