package com.edigestProject1.journalApp.controller;

import com.edigestProject1.journalApp.entity.JournalEntry;
import com.edigestProject1.journalApp.entity.User;
import com.edigestProject1.journalApp.services.JournalEntryService;
import com.edigestProject1.journalApp.services.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@Component
public class JournalEntryController {


    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    UserService userService;

    @GetMapping("/journalList/{userName}")
    public ResponseEntity<?> getAllJournalOfUsers(@PathVariable String userName){
        User user = userService.findByUserName(userName);
        List<JournalEntry> allJournal = user.getJournalEntries();
        if(allJournal != null && !allJournal.isEmpty()){
            return new ResponseEntity<>(allJournal,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/addJournal/{userName}")
    public ResponseEntity<JournalEntry> creatEntry(@RequestBody JournalEntry myEntry, @PathVariable String userName){
        try {
            journalEntryService.createEntry(myEntry,userName);
            return new ResponseEntity<>(myEntry,HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getJournal/{myid}")
    public ResponseEntity<JournalEntry> getById(@PathVariable ObjectId myid){
        Optional<JournalEntry> journalEntry = journalEntryService.getById(myid);
        return journalEntry.map(entry -> new ResponseEntity<>(entry,HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/updateJournal/{myid}")
    public ResponseEntity<JournalEntry> updateById(@PathVariable ObjectId myid, @RequestBody JournalEntry newEntry,@PathVariable String userName){
        JournalEntry oldEntry = journalEntryService.getById(myid).orElse(null);
        if(oldEntry != null){
            oldEntry.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("") ? newEntry.getTitle() : oldEntry.getTitle());
            oldEntry.setContent(newEntry.getContent() != null && !newEntry.getContent().equals("")? newEntry.getContent() : oldEntry.getContent());
            journalEntryService.createEntry(oldEntry);
            return new ResponseEntity<>(oldEntry,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{userName}/{pid}")
    public boolean deleteById(@PathVariable ObjectId pid,@PathVariable String userName){
        return journalEntryService.deleteById(pid,userName);
    }
}
