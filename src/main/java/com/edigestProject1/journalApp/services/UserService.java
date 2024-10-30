package com.edigestProject1.journalApp.services;

import com.edigestProject1.journalApp.entity.User;
import com.edigestProject1.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Optional;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAll(){
        return userRepository.findAll();
    }

    public void save(User user){
        userRepository.save( user);
    }

    public Optional<User> findById( ObjectId Id){
        return userRepository.findById(Id);
    }

    public User findByUserName(String name){
        return userRepository.findByusername(name);
    }
    public boolean delete(ObjectId myid){
        userRepository.deleteById(myid);
        return true;
    }


}
