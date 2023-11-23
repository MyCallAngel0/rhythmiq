package com.endava.app.Controller;

import com.endava.app.Services.UserService;
import com.endava.app.model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<Object> getAllUsers() {
        try {
            return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/{user_id}")
    public ResponseEntity<Object> getUserById(@PathVariable(name = "user_id") Long userId) {
        try {
            return new ResponseEntity<>(userService.get(userId), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(path = "/add")
    public ResponseEntity<String> add(@RequestBody final UserDTO userDTO) {
        try {
            Long userID = userService.create(userDTO);
            return ResponseEntity.ok("User added with id : " + userID);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(path = "/{user_id}")
    public ResponseEntity<String> update(@PathVariable(name = "user_id") Long userId, @RequestBody final UserDTO userDTO) {
        try {
            userService.update(userId, userDTO);
            return ResponseEntity.ok("User updated");
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(path = "/{user_id}")
    public ResponseEntity<String> delete(@PathVariable(name = "user_id") Long userId) {
        try {
            userService.delete(userId);
            return ResponseEntity.ok("User deleted");
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}