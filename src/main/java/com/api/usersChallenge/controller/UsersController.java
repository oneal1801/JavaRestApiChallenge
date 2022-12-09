package com.api.usersChallenge.controller;

import com.api.usersChallenge.models.UserEntity;
import com.api.usersChallenge.repository.PhoneEntityRepository;
import com.api.usersChallenge.repository.UserEntityRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@RestController
@NoArgsConstructor
@AllArgsConstructor
@RequestMapping("users")
@Slf4j
public class UsersController {
    @Autowired
    private UserEntityRepository userEntityRepository;
    @Autowired
    private PhoneEntityRepository phoneEntityRepository;


    @GetMapping(path = "getAllUsers", produces = "application/json")
    public ResponseEntity<List<UserEntity>> getAllUsers() {
        try {
            List<UserEntity> users = new ArrayList<UserEntity>(userEntityRepository.findAll());
            if (users.isEmpty()) {
                return new ResponseEntity<>(users, HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path ="/getUserById/{id}", produces = "application/json")
    public ResponseEntity<UserEntity> getUserById(@PathVariable("id") String id) {
        UserEntity user = userEntityRepository.findById(UUID.fromString(id)).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping(path ="/registerUser", produces = "application/json")
    public ResponseEntity<UserEntity> registerUser(@RequestBody @Valid UserEntity userPayload) {
        String password = new BCryptPasswordEncoder().encode(userPayload.getPassword());
        UserEntity newUser = UserEntity.builder().name(userPayload.getName()).email(userPayload.getEmail()).password(password).build();
        userEntityRepository.save(newUser);
        UUID userId = newUser.getId();
        log.debug("the id is "+ userId);
        /*
        TODO find a better way to save the phone in same transaction
        newUser.setPhone(userPayload.getPhone());
        userEntityRepository.save(newUser);
        */
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @PutMapping("/updateUserById/{id}")
    public ResponseEntity<UserEntity> updateUserById(@PathVariable String id, @RequestBody UserEntity userPayload) {
        Optional<UserEntity> userToBeUpdated = userEntityRepository.findById(UUID.fromString(id));
        if (userToBeUpdated.isPresent()) {
            UserEntity newUserData = userToBeUpdated.get();
            newUserData.setName(userPayload.getName());
            newUserData.setEmail(userPayload.getEmail());
            newUserData.setPassword(userPayload.getPassword());
            newUserData.setPhone(userPayload.getPhone());

            UserEntity result = userEntityRepository.save(newUserData);
            return new ResponseEntity<>(result, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/deleteUserById/{id}")
    public ResponseEntity<HttpStatus> deleteUserById(@PathVariable String id) {
        userEntityRepository.deleteById(UUID.fromString(id));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
