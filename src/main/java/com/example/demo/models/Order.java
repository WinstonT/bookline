package com.example.demo.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;
import java.util.List;

@Document(indexName = "order")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    private String id;

    @Field(type = FieldType.Text, value = "userId")
    private String userId;

    @Field(value = "cartItems")
    private List<Cart> cartItems;

    @Field(type = FieldType.Long, value = "totalPrice")
    private long totalPrice;

    @Field(type = FieldType.Date, value = "transactionDate", format = DateFormat.custom, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime transactionDate;

    @Field(type = FieldType.Text, value = "shippingAddress")
    private String shippingAddress;

    @Field(type = FieldType.Text, value = "paymentType")
    private String paymentType;

    @Field(type = FieldType.Text, value = "orderStatus")
    private String orderStatus;
}
