package com.example.demo.repositories;

import com.example.demo.models.Order;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface OrderRepository extends ElasticsearchRepository<Order, String> {

    List<Order> findAllBy();

    List<Order> findByUserId(String userId);

    Order findOrderById(String id);

    List<Order> getOrderById(String id);

    void deleteAll();
}
