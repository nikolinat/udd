package com.service.agency.web;

import com.service.agency.infrastructure.elasticsearch.model.AdvancedQuery;
import com.service.agency.infrastructure.elasticsearch.model.ResultDataDTO;
import com.service.agency.infrastructure.elasticsearch.service.ElasticSearchService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/search")
public class SearchController {

    private final ElasticSearchService elasticSearchService;

    @Autowired
    public SearchController(ElasticSearchService elasticSearchService) {
        this.elasticSearchService = elasticSearchService;
    }

    @PostMapping("boolean")
    public ResponseEntity<List<ResultDataDTO>> search(
        @RequestBody AdvancedQuery advancedQuery) {
        return ResponseEntity.ok(elasticSearchService.search(advancedQuery));

    }

    @PostMapping("geolocation")
    public ResponseEntity<List<ResultDataDTO>> search(
        @RequestParam Long city, @RequestParam int radius) {
        return ResponseEntity.ok(elasticSearchService.searchGeoLocation(city, radius));

    }
}
