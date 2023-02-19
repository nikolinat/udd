package com.service.agency.domain.coverLetter.repository;

import com.service.agency.domain.coverLetter.entity.CoverLetter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoverLetterRepository extends JpaRepository<CoverLetter, Long> {

}
