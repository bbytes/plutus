package com.bbytes.plutus.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bbytes.plutus.model.PlutusUser;

public interface UserRepository extends MongoRepository<PlutusUser, String> {
  
}
