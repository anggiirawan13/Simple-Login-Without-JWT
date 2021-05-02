package com.crud.controller;

import com.crud.request.UserRequest;
import com.crud.response.GlobalResponse;
import com.crud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/create")
    @ResponseBody
    public GlobalResponse createUser(@RequestBody UserRequest request, @RequestHeader("token") String token) {
        return this.userService.createUser(request, token);
    }
}
