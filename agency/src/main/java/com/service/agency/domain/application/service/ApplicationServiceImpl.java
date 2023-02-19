package com.service.agency.domain.application.service;

import com.service.agency.domain.application.entity.Application;
import com.service.agency.domain.application.mapper.ApplicationMapper;
import com.service.agency.domain.application.model.ApplicationGetDTO;
import com.service.agency.domain.application.repository.ApplicationRepository;
import com.service.agency.domain.candidate.entity.Candidate;
import com.service.agency.domain.candidate.model.CandidateCreateDTO;
import com.service.agency.domain.candidate.service.CandidateService;
import com.service.agency.domain.coverLetter.entity.CoverLetter;
import com.service.agency.domain.cv.entity.CV;
import com.service.agency.infrastructure.elasticsearch.entity.IndexUnit;
import com.service.agency.infrastructure.elasticsearch.repository.ApplicationElasticSearchRepository;
import com.service.agency.infrastructure.files.FileService;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final ApplicationElasticSearchRepository applicationElasticSearchRepository;
    private final ApplicationMapper applicationMapper;
    private final FileService fileService;
    private final CandidateService candidateService;
    private static final Logger LOGGER = LogManager.getLogger(ApplicationServiceImpl.class);

    @Autowired
    public ApplicationServiceImpl(ApplicationRepository applicationRepository,
        ApplicationElasticSearchRepository applicationElasticSearchRepository,
        ApplicationMapper applicationMapper, FileService fileService,
        CandidateService candidateService) {
        this.applicationRepository = applicationRepository;
        this.applicationElasticSearchRepository = applicationElasticSearchRepository;
        this.applicationMapper = applicationMapper;
        this.fileService = fileService;
        this.candidateService = candidateService;
    }

    @Override
    public ApplicationGetDTO create(CandidateCreateDTO candidateCreateDTO, MultipartFile cvFile,
        MultipartFile coverLetterFile, String company, String worker) throws IOException {
        Candidate candidate = candidateService.create(candidateCreateDTO);

        LOGGER.trace(candidate.getCity().getName() + " - " + company + " - " + worker);
        Application jobApplication = new Application();
        jobApplication.setCandidate(candidate);
        Application savedApplication = applicationRepository.save(jobApplication);

        String cvPath = fileService.saveUploadedFile("CV" + savedApplication.getId().toString(),
            cvFile);
        String contentFromCV = fileService.readFromPDFFile(cvPath);
        CV cv = new CV();
        cv.setFile(cvPath);
        savedApplication.setCv(cv);

        String coverLetterPath = fileService.saveUploadedFile(
            "CoverLetter" + savedApplication.getId().toString(),
            coverLetterFile);
        String contentFromCoverLetter = fileService.readFromPDFFile(coverLetterPath);
        CoverLetter coverLetter = new CoverLetter();
        coverLetter.setFile(coverLetterPath);
        savedApplication.setCoverLetter(coverLetter);

        IndexUnit indexUnit = new IndexUnit(savedApplication.getId(),
            savedApplication.getCandidate().getFirstName(),
            savedApplication.getCandidate().getLastName(),
            savedApplication.getCandidate().getProfessionalDegree(), contentFromCV,
            contentFromCoverLetter,
            new GeoPoint(savedApplication.getCandidate().getCity().getLatitude(),
                savedApplication.getCandidate().getCity().getLongitude()),
            candidate.getCity().getName());

        applicationElasticSearchRepository.save(indexUnit);

        return applicationMapper.applicationToApplicationGetDTO(
            applicationRepository.save(savedApplication));
    }
}
