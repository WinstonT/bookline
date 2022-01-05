package com.example.demo.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(indexName = "book")
public class Book {

    @Id
    private String id;

    @Field(type = FieldType.Text, name="bookTitle")
    private String bookTitle;

    @Field(type = FieldType.Text, name="bookAuthor")
    private String bookAuthor;

    @Field(type = FieldType.Text, name="bookCategory")
    private String bookCategory;

    @Field(type = FieldType.Text, name="bookImage")
    private String bookImage;

    @Field(type = FieldType.Text, name="bookDescription")
    private String bookDescription;

    @Field(type = FieldType.Text, name="yearPublished")
    private String yearPublished;

    @Field(type = FieldType.Double, name="bookRating")
    private double bookRating;

    @Field(type = FieldType.Text, name="bookPages")
    private String bookPages;

    @Field(type = FieldType.Text, name="bookIsbn") //use isbn 13
    private String bookIsbn;

    @Field(type = FieldType.Integer, name="bookPrice")
    private int bookPrice;
}
