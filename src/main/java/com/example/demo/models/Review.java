package com.example.demo.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "review")
@Builder
public class Review {

    @Id
    private String id;

    @Field(type = FieldType.Text, name = "bookId")
    private String bookId;

    @Field(type = FieldType.Text, name = "userName")
    private String userName;

    @Field(type = FieldType.Text, name = "reviewText")
    private String reviewText;

    @Field(type = FieldType.Integer, name = "score")
    private int score;

    @Field(type = FieldType.Date, name = "reviewDate")
    private LocalDate reviewDate;
}
