package com.service.agency.domain.candidate.entity;

import com.service.agency.domain.application.entity.Application;
import com.service.agency.domain.city.entity.City;
import com.service.agency.domain.profesionalDegree.ProfessionalDegree;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.Id;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Candidate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "First name should not be blank.")
    private String firstName;

    @NotBlank(message = "Last name should not be blank.")
    private String lastName;

    private ProfessionalDegree professionalDegree;

    @ManyToOne
    private City city;

    @OneToOne(mappedBy = "candidate")
    private Application jobApplication;

}
