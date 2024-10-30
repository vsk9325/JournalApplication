package com.edigestProject1.journalApp.repository;

import com.edigestProject1.journalApp.entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;


public interface JournalEntryRepository extends MongoRepository<JournalEntry, ObjectId> {


}
