package com.service.agency.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.agency.domain.application.model.ApplicationGetDTO;
import com.service.agency.domain.application.service.ApplicationService;
import com.service.agency.domain.candidate.model.CandidateCreateDTO;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/application")
public class ApplicationController {

    private final ApplicationService applicationService;

    @Autowired
    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping
    public ResponseEntity<ApplicationGetDTO> create(
        @RequestPart("cv") MultipartFile cv,
        @RequestPart("coverLetter") MultipartFile coverLetter,
        @RequestPart("candidate") String candidate,
        @RequestPart("company") String company,
        @RequestPart("worker") String worker) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        CandidateCreateDTO candidateCreateDTO = objectMapper.readValue(candidate, CandidateCreateDTO.class);
        return ResponseEntity.ok(applicationService.create(candidateCreateDTO, cv, coverLetter, company, worker));
    }

}
