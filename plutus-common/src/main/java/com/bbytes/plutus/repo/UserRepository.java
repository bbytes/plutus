package com.bbytes.plutus.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bbytes.plutus.model.User;

public interface UserRepository extends MongoRepository<User, String> {
  
}
