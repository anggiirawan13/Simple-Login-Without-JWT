package com.crud.service;

import com.crud.entity.User;
import com.crud.helper.NullEmptyChecker;
import com.crud.repository.UserRepository;
import com.crud.request.UserRequest;
import com.crud.response.GlobalResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private  LoginService loginService;

    public GlobalResponse createUser(UserRequest request, String token) {
        GlobalResponse response = new GlobalResponse();

        try {
            if (this.loginService.tokenIsValid(token)) {
                if (NullEmptyChecker.isNullOrEmpty(userRepository.findUserByUsername(request.getUsername()))) {
                    String name = request.getName();
                    int age = request.getAge();
                    String username = request.getUsername();
                    String rawPassword = request.getPassword();

                    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                    String encPassword = passwordEncoder.encode(rawPassword);

                    User user = new User();
                    user.setName(name);
                    user.setAge(age);
                    user.setUsername(username);
                    user.setPassword(encPassword);

                    User userCreated = userRepository.save(user);

                    response.setSuccess(true);
                    response.setMessages("Created User Succeeded!");
                    response.setResult(userCreated);
                } else {
                    response.setMessages("Username Already Exist!");
                }
            } else {
                response.setMessages("Token Invalid!");
            }
        } catch (Exception e) {
            response.setMessages("Created User Failed! Because an error occurred on the server!");
            response.setAdditionalEntity(e.toString());
        }

        return response;
    }
}
