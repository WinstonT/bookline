package com.example.demo.repositories;

import com.example.demo.models.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface UserRepository extends ElasticsearchRepository<User, String>{

    User findUserById(String id);

    List<User> findUserByUserEmail(String email);

    void deleteById(String id);

    List<User> findAllBy();
}
