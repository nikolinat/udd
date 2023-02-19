package com.service.agency.infrastructure.elasticsearch.service;

import com.service.agency.domain.candidate.model.CandidateGetDTO;
import com.service.agency.domain.city.entity.City;
import com.service.agency.domain.city.model.CityGetDTO;
import com.service.agency.domain.city.service.CityService;
import com.service.agency.infrastructure.elasticsearch.entity.IndexUnit;
import com.service.agency.infrastructure.elasticsearch.model.AdvancedQuery;
import com.service.agency.infrastructure.elasticsearch.model.CustomQueryBuilder;
import com.service.agency.infrastructure.elasticsearch.model.ResultDataDTO;
import com.service.agency.infrastructure.elasticsearch.model.SearchType;
import com.service.agency.infrastructure.elasticsearch.model.SimpleQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

@Service
public class ElasticSearchService {

    private final ElasticsearchRestTemplate elasticsearchRestTemplate;
    private final CityService cityService;

    @Autowired
    public ElasticSearchService(ElasticsearchRestTemplate elasticsearchRestTemplate,
        CityService cityService) {
        this.elasticsearchRestTemplate = elasticsearchRestTemplate;
        this.cityService = cityService;
    }

    public List<ResultDataDTO> search(AdvancedQuery advancedQuery) {
        List<ResultDataDTO> data = new ArrayList<>();
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        HighlightBuilder highlightBuilder = new HighlightBuilder();

        for (SimpleQuery query : advancedQuery.getQueries()) {
            if (Objects.equals(query.getValue(), "") || query.getLogicalOperator().equals("")) {
                continue;
            }

            queryBuilder = CustomQueryBuilder.buildBoolQuery(queryBuilder, query);

            switch (query.getField()) {
                case "firstName" -> highlightBuilder.field("firstName");
                case "lastName" -> highlightBuilder.field("lastName");
                case "professionalDegree" -> highlightBuilder.field("professionalDegree");
                case "coverLetter" -> highlightBuilder.field("coverLetter");
                case "cv" -> highlightBuilder.field("cv");
            }
        }

        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
            .withQuery(queryBuilder)
            .withHighlightBuilder(highlightBuilder)
            .build();

        SearchHits<IndexUnit> searchHits = elasticsearchRestTemplate.search(searchQuery,
            IndexUnit.class);

        for (SearchHit<IndexUnit> searchHit : searchHits.getSearchHits()) {
            StringBuilder highlight = new StringBuilder();
            Map<String, List<String>> highlightFieldsMap = searchHit.getHighlightFields();
            if (highlightFieldsMap.get("firstName") != null) {
                highlight.append(highlightFieldsMap.get("firstName").get(0));
            }

            if (highlightFieldsMap.get("lastName") != null) {
                highlight.append(" ").append(highlightFieldsMap.get("lastName").get(0));
            }

            highlight.append("...");

            for (String key : highlightFieldsMap.keySet()) {
                for (String searchHitContent : highlightFieldsMap.get(key)) {
                    highlight.append(searchHitContent).append("...");
                }

            }

            IndexUnit indexUnit = searchHit.getContent();
            City city = cityService.findByName(indexUnit.getCity());
            data.add(new ResultDataDTO(
                new CandidateGetDTO(indexUnit.getFirstName(), indexUnit.getLastName(),
                    indexUnit.getProfessionalDegree(),
                    new CityGetDTO(city.getId(), indexUnit.getCity(),
                        indexUnit.getLocation().getLon(),
                        indexUnit.getLocation().getLat())),
                highlight.toString()
            ));
        }
        return data;
    }

    public List<ResultDataDTO> searchGeoLocation(Long cityId, int radius) {
        List<ResultDataDTO> data = new ArrayList<>();
        City city = cityService.findById(cityId);
        QueryBuilder queryBuilder = CustomQueryBuilder.buildQuery(SearchType.geospatial, "location",
            city.getName(), city.getLongitude(), city.getLatitude(), radius);

        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
            .withQuery(queryBuilder)
            .build();

        SearchHits<IndexUnit> searchHits = elasticsearchRestTemplate.search(searchQuery,
            IndexUnit.class);

        for (SearchHit<IndexUnit> searchHit : searchHits.getSearchHits()) {
            IndexUnit indexUnit = searchHit.getContent();
            data.add(new ResultDataDTO(new CandidateGetDTO(indexUnit.getFirstName(),
                indexUnit.getLastName(),
                indexUnit.getProfessionalDegree(),
                new CityGetDTO(city.getId(), indexUnit.getCity(), indexUnit.getLocation().getLon(),
                    indexUnit.getLocation().getLat())),
                ""
            ));

        }
        return data;

    }
}
