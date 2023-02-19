package com.service.agency.domain.application.entity;

import com.service.agency.domain.candidate.entity.Candidate;
import com.service.agency.domain.coverLetter.entity.CoverLetter;
import com.service.agency.domain.cv.entity.CV;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    private Candidate candidate;

    @OneToOne(cascade = CascadeType.ALL)
    private CV cv;

    @OneToOne(cascade = CascadeType.ALL)
    private CoverLetter coverLetter;

}
