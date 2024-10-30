package com.edigestProject1.journalApp.controller;

import com.edigestProject1.journalApp.entity.User;
import com.edigestProject1.journalApp.services.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Component
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/getUsers")
    public List<User> getAll(){
        return userService.getAll();
    }

    @PostMapping("/saveUser")
    public void saveUser(@RequestBody User user){
        userService.save(user);
    }

    @PutMapping("/updateUser/{name}")
    public ResponseEntity<?> updateUser(@RequestBody User user, @PathVariable String name){
        User userInDb = userService.findByUserName(name);

            userInDb.setUsername(user.getUsername());
            userInDb.setPassword(user.getPassword());
            userService.save(userInDb);


        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @DeleteMapping("/deleteUser/{id}")
    public boolean deleteUser(@PathVariable ObjectId id){
         return userService.delete(id);
    }



}
