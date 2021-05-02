package com.crud.service;

import com.crud.entity.User;
import com.crud.helper.NullEmptyChecker;
import com.crud.repository.UserRepository;
import com.crud.request.LoginRequest;
import com.crud.response.GlobalResponse;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.DefaultJwtSignatureValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.UUID;

@Service
public class LoginService {
    @Autowired
    private UserRepository userRepository;

    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
    private final String SECRET_KEY = UUID.randomUUID().toString();

    public GlobalResponse login(LoginRequest request) {
        GlobalResponse response = new GlobalResponse();

        try {
            String username = request.getUsername();
            String password = request.getPassword();

            User userExist = userRepository.findUserByUsername(username);
            if (NullEmptyChecker.isNullOrEmpty(userExist)) {
                response.setMessages("User Not Found!");
                return response;
            } else {
                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                if (!passwordEncoder.matches(password, userExist.getPassword())) {
                    response.setMessages("Username Or Password Is Wrong!!");
                    return response;
                }
            }

            response.setSuccess(true);
            response.setMessages("Login Succeeded!");
            response.setResult(this.createToken(userExist));
        } catch (Exception e) {
            response.setMessages("Login Failed! Because an error occurred on the server!");
            response.setAdditionalEntity(e.toString());
        }

        return response;
    }

    private String createToken(User user) {
        String token = "";

        try {
            Key signKey = this.createBase64SignKey();

            token = Jwts.builder()
                    .setAudience(user.getName())
                    .setSubject(user.getUsername() + user.getAge())
                    .signWith(signatureAlgorithm, signKey)
                    .compact();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return token;
    }

    public boolean tokenIsValid(String token) {
        boolean tokenValid = false;

        try {
            Key signKey = this.createBase64SignKey();

            DefaultJwtSignatureValidator validator = new DefaultJwtSignatureValidator(this.signatureAlgorithm, signKey);

            String[] arrToken = token.split("\\.");
            String tokenWithoutSignature = arrToken[0] + "." + arrToken[1];
            String signature = arrToken[2];
            if (validator.isValid(tokenWithoutSignature, signature)) {
                tokenValid = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return tokenValid;
    }

    private Key createBase64SignKey() {
        Key signKey = null;

        try {
            byte[] base64SecretKey = DatatypeConverter.parseBase64Binary(this.SECRET_KEY);
            signKey = new SecretKeySpec(base64SecretKey, this.signatureAlgorithm.getJcaName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return signKey;
    }

}
