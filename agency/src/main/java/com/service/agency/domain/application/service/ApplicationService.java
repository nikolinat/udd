package com.service.agency.domain.application.service;

import com.service.agency.domain.application.model.ApplicationGetDTO;
import com.service.agency.domain.candidate.model.CandidateCreateDTO;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

public interface ApplicationService {

    ApplicationGetDTO create(CandidateCreateDTO candidateCreateDTO, MultipartFile cv,
        MultipartFile coverLetter, String company, String worker) throws IOException;

}
