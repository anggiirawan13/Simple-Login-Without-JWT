package com.crud.controller;

import com.crud.request.LoginRequest;
import com.crud.response.GlobalResponse;
import com.crud.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/auth")
public class LoginController {
    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    @ResponseBody
    public GlobalResponse login(@RequestBody LoginRequest request) {
        return this.loginService.login(request);
    }
}
