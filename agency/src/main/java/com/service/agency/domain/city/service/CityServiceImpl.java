package com.service.agency.domain.city.service;

import com.service.agency.domain.city.entity.City;
import com.service.agency.domain.city.mapper.CityMapper;
import com.service.agency.domain.city.model.CityGetDTO;
import com.service.agency.domain.city.repository.CityRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;
    private final CityMapper cityMapper;

    @Autowired
    public CityServiceImpl(CityRepository cityRepository, CityMapper cityMapper) {
        this.cityRepository = cityRepository;
        this.cityMapper = cityMapper;
    }

    @Override
    public City findByName(String name) {
        return cityRepository.findByName(name);
    }

    @Override
    public List<CityGetDTO> get() {
        List<City> cities = cityRepository.findAll();
        return cities.stream().map(cityMapper::cityToCityGetDTO).collect(Collectors.toList());
    }

    @Override
    public City findById(Long id) {
        return cityRepository.findById(id).orElse(null);
    }
}
