package com.edigestProject1.journalApp.repository;

import com.edigestProject1.journalApp.entity.User;
import com.mongodb.client.MongoDatabase;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, ObjectId> {
    User findByusername(String name);
}
