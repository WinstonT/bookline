package com.example.demo.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Cart {

    @Field(type = FieldType.Object, value = "book")
    private Book book;

    @Field(type = FieldType.Integer, value = "quantity")
    private int quantity;
}
