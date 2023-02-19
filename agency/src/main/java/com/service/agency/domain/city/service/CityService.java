package com.service.agency.domain.city.service;

import com.service.agency.domain.city.entity.City;
import com.service.agency.domain.city.model.CityGetDTO;
import java.util.List;

public interface CityService {

    City findByName(String name);

    List<CityGetDTO> get();

    City findById(Long id);

}
