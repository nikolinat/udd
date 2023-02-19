package com.service.agency.domain.city.model;

public record CityGetDTO(
    Long id,
    String name,
    double longitude,
    double latitude) {

}
