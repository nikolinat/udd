package com.service.agency.infrastructure.elasticsearch.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SimpleQuery {

    private String field;
    private String value;
    private String logicalOperator;

}
