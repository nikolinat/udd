package com.service.agency.infrastructure.elasticsearch.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.service.agency.domain.profesionalDegree.ProfessionalDegree;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.GeoPointField;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

@Document(indexName = IndexUnit.INDEX_NAME)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IndexUnit {

    public static final String INDEX_NAME = "application";

    @Id
    @Field(type = FieldType.Long)
    private Long id;

    @Field(type = FieldType.Text)
    private String firstName;

    @Field(type = FieldType.Text)
    private String lastName;

    @Field(type = FieldType.Text)
    private ProfessionalDegree professionalDegree;

    @Field(type = FieldType.Text)
    private String cv;

    @Field(type = FieldType.Text)
    private String coverLetter;

    @GeoPointField
    @JsonFormat(shape = JsonFormat.Shape.OBJECT)
    private GeoPoint location;

    @Field(type = FieldType.Text)
    private String city;
}
