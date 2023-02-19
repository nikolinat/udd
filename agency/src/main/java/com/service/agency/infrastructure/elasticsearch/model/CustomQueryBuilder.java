package com.service.agency.infrastructure.elasticsearch.model;

import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

public class CustomQueryBuilder {

    public static QueryBuilder buildQuery(SearchType queryType, String field, String value,
        double longitude, double latitude, int radius)
        throws IllegalArgumentException {
        String errorMessage = "";
        if (field == null || field.equals("")) {
            errorMessage += "Field not specified";
        }
        if (value == null) {
            if (!errorMessage.equals("")) {
                errorMessage += "\n";
            }
            errorMessage += "Value not specified";
        }
        if (!errorMessage.equals("")) {
            throw new IllegalArgumentException(errorMessage);
        }

        QueryBuilder retVal;
        if (queryType.equals(SearchType.regular)) {
            retVal = QueryBuilders.termQuery(field, value);
        } else if (queryType.equals(SearchType.fuzzy)) {
            retVal = QueryBuilders.fuzzyQuery(field, value)
                .fuzziness(Fuzziness.fromEdits(1));
        } else if (queryType.equals(SearchType.prefix)) {
            retVal = QueryBuilders.prefixQuery(field, value);
        } else if (queryType.equals(SearchType.range)) {
            String[] values = value.split(" ");
            retVal = QueryBuilders.rangeQuery(field).from(values[0]).to(values[1]);
        } else if (queryType.equals(SearchType.geospatial)) {
            retVal = QueryBuilders.geoDistanceQuery(field)
                .point(latitude, longitude)
                .distance(radius, DistanceUnit.KILOMETERS);
        } else {
            retVal = QueryBuilders.matchPhraseQuery(field, value);
        }

        return retVal;
    }

    public static BoolQueryBuilder buildBoolQuery(QueryBuilder queryBuilder, SimpleQuery query) {
        QueryBuilder newQuery = buildQuery(SearchType.phrase, query.getField(), query.getValue(), 0,
            0, 0);

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        if (query.getLogicalOperator().equalsIgnoreCase("MUST")) {
            boolQueryBuilder.must(newQuery);
        } else if (query.getLogicalOperator().equalsIgnoreCase("AND")) {
            boolQueryBuilder.must(queryBuilder);
            boolQueryBuilder.must(newQuery);
        } else if (query.getLogicalOperator().equalsIgnoreCase("OR")) {
            boolQueryBuilder.should(queryBuilder);
            boolQueryBuilder.should(newQuery);
        }else if(query.getLogicalOperator().equalsIgnoreCase("NOT")){
            boolQueryBuilder.must(queryBuilder);
            boolQueryBuilder.mustNot(newQuery);
        }
        return boolQueryBuilder;
    }

}
