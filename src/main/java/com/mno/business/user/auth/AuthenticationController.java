package com.mno.business.user.auth;

import com.mno.business.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;


    @PostMapping("register")
    public UserDto register(
            @RequestBody RegisterRequest request
    ) {
        return service.register(request);
    }

    @PostMapping("authenticate")
    public UserDto authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return service.authenticate(request);
    }

    @PostMapping("forGetPass")
    public UserDto forGetPass(@RequestBody AuthenticationRequest request ){
        return service.forGetPass(request);
    }



}

