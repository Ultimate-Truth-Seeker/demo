package com.example.demo.repository;

import com.example.demo.model.DBUser;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface users extends MongoRepository<DBUser, String> {
}
