package com.service.agency.infrastructure.elasticsearch.model;

public enum SearchType {
    regular,
    fuzzy,
    phrase,
    range,
    prefix,
    match,
    geospatial
}
