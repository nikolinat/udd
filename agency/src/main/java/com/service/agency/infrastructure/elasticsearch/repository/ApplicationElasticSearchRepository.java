package com.service.agency.infrastructure.elasticsearch.repository;

import com.service.agency.infrastructure.elasticsearch.entity.IndexUnit;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ApplicationElasticSearchRepository extends
    ElasticsearchRepository<IndexUnit, Long> {

}
