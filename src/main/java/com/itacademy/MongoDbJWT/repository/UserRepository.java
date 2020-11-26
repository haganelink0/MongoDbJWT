package com.itacademy.MongoDbJWT.repository;

import com.itacademy.MongoDbJWT.dto.response.UserPlayerResponse;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UserPlayerResponse, String> {
    UserPlayerResponse findByName(String name);
}
