package com.service.agency.infrastructure.elasticsearch.model;

import java.util.ArrayList;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdvancedQuery {

    private ArrayList<SimpleQuery> queries;
}
