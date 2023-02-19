package com.service.agency.domain.city.mapper;

import com.service.agency.domain.city.entity.City;
import com.service.agency.domain.city.model.CityGetDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CityMapper {

    CityGetDTO cityToCityGetDTO(City city);

}
