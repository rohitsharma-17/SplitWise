package com.hashedin.controllers;

import com.hashedin.models.requests.UserRequest;
import com.hashedin.models.responses.UserResponse;
import com.hashedin.services.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Slf4j
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private IUserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> addUser(@RequestBody UserRequest userRequest) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(userService.addUser(userRequest));
    }

    @PutMapping(value = "/{email}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable("email") String email,@RequestBody UserRequest userRequest) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUser(email, userRequest));
    }

    @GetMapping(value = "/{email}")
    public ResponseEntity<UserResponse> getUser(@PathVariable("email") String email) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUser(email));
    }

    @DeleteMapping(value = "/{email}")
    public ResponseEntity<String> deleteUser(@PathVariable("email") String email) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(userService.deleteUser(email));
    }

    @GetMapping("/dueDetailOfUser")
    public ResponseEntity<Map<String,Double>> detailedDueOfUser(@RequestParam("email")String email) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(userService.detailedDueOfUser(email));
    }
}
