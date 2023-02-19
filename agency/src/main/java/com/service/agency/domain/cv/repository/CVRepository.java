package com.service.agency.domain.cv.repository;

import com.service.agency.domain.cv.entity.CV;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CVRepository extends JpaRepository<CV, Long> {

}
