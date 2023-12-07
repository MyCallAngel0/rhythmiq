package com.endava.app.controller;

import com.endava.app.model.request.UserRequest;
import com.endava.app.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Controller
@RequestMapping(path="/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<Object> getAllUsers() {
        try {
            log.info("Getting all users");
            return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/{user_id}")
    public ResponseEntity<Object> getUserById(@PathVariable(name="user_id") Long userId) {
        try {
            log.info("Getting song with id {}", userId);
            return new ResponseEntity<>(userService.get(userId), HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping(path="/profile/{username}")
    public String showProfile(@PathVariable(name = "username") String username, Model model) {
        model.addAttribute("user", userService.getByUsername(username));
        return "user";
    }

    @PostMapping(path="/add")
    public ResponseEntity<String> add(@RequestBody final UserRequest userRequest) {
        try {
            log.info("Creating a user");
            Long userID = userService.create(userRequest);
            log.info("User created with id {}", userID);
            return ResponseEntity.ok("User added with id : " + userID);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(path="/{user_id}")
    public ResponseEntity<String> update(@PathVariable(name="user_id") Long userId, @RequestBody final UserRequest userRequest) {
        try {
            log.info("Updating user with id {}", userId);
            userService.update(userId, userRequest);
            return ResponseEntity.ok("User updated");
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(path="/{user_id}")
    public ResponseEntity<String> delete(@PathVariable(name="user_id") Long userId) {
        try {
            log.info("Deleting user with id {}", userId);
            userService.delete(userId);
            return ResponseEntity.ok("User deleted");
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}