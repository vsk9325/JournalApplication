package com.edigestProject1.journalApp.services;

import com.edigestProject1.journalApp.entity.JournalEntry;
import com.edigestProject1.journalApp.entity.User;
import com.edigestProject1.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    UserService userService;

    @Transactional
    public boolean createEntry(JournalEntry myEntry, String userName){
       try {
           User user = userService.findByUserName(userName);
           myEntry.setDate(LocalDate.now());
           JournalEntry saved = journalEntryRepository.save(myEntry);
           user.getJournalEntries().add(saved);
           userService.save(user);
           return true;
       }
       catch (Exception e){
           System.out.println(e);
           throw new RuntimeException("An error occured while saving the journal entry.",e);
       }
    }

    public boolean createEntry(JournalEntry myEntry){
        journalEntryRepository.save(myEntry);
        return true;
    }

    public List<JournalEntry> getAll(){
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> getById(ObjectId myid){
        return journalEntryRepository.findById(myid);
    }

    public boolean deleteById(ObjectId pid,String userName){
        User user = userService.findByUserName(userName);
        user.getJournalEntries().removeIf(x-> x.getId().equals(pid));
        userService.save(user);
        journalEntryRepository.deleteById(pid);
        return true;
    }
}
