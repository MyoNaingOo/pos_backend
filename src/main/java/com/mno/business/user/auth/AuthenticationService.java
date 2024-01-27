package com.mno.business.user.auth;

import com.mno.business.config.JwtService;
import com.mno.business.user.dto.UserDto;
import com.mno.business.user.entity.Role;
import com.mno.business.user.entity.Token;
import com.mno.business.user.entity.TokenType;
import com.mno.business.user.entity.User;
import com.mno.business.user.otb.OtpService;
import com.mno.business.user.repo.TokenRepo;
import com.mno.business.user.repo.UserRepo;
import com.mno.business.user.service.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Data
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepo userRepo;
    private final TokenRepo tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final OtpService otpService;
    private final UserService userService;

    @Value("${my-user.admin.gmail}")
    private String adminGmail;


    public UserDto register(RegisterRequest request) {
        User user = userRepo.findByNameOrGmail(request.name, request.gmail).orElse(null);

        if (user == null) {
            return createUser(request);
        } else {

            if (user.getName().equals(request.name)) {

                if (user.getGmail().equals(request.gmail)) {

                    return UserDto.builder().gmailNotAvailable(true).nameNotAvailable(true).build();
                } else {
                    return UserDto.builder().nameNotAvailable(true).build();
                }
            } else if (user.getGmail().equals(request.gmail)) {

                return UserDto.builder().gmailNotAvailable(true).build();
            }

        }
        return null;
    }


    private UserDto createUser(RegisterRequest request) {

        if (Objects.equals(request.gmail, getAdminGmail())) {

            User user = User.builder()
                    .name(request.name)
                    .gmail(request.gmail)
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(Role.ADMIN)
                    .build();
            var savedUser = userRepo.save(user);
            var jwtToken = jwtService.generateToken(user);
            saveUserToken(savedUser, jwtToken);
            otpService.sendOtp(request.getGmail(), jwtToken);
            return userService.mapper(savedUser);

        } else {

            User user = User.builder()
                    .name(request.name)
                    .gmail(request.gmail)
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(Role.USER)
                    .build();
            var savedUser = userRepo.save(user);
            var jwtToken = jwtService.generateToken(user);
            saveUserToken(savedUser, jwtToken);
            otpService.sendOtp(request.getGmail(), jwtToken);
            return userService.mapper(savedUser);
        }

    }


    public UserDto authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getGmail(), request.getPassword()));
        var user = userRepo.findByGmail(request.getGmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        removeAllTokenByUser(user);
        saveUserToken(user, jwtToken);
        otpService.sendOtp(request.getGmail(), jwtToken);
        return userService.mapper(user);

    }

    public UserDto forGetPass(AuthenticationRequest request) {

        User user = userRepo.findByGmail(request.getGmail()).orElse(null);

        var jwtToken = jwtService.generateToken(user);
        assert user != null;
        removeAllTokenByUser(user);
        saveUserToken(user, jwtToken);
        otpService.sendOtp(request.getGmail(), jwtToken);

        return userService.mapper(user);

    }

    public void saveUserToken(User user, String jwtToken) {
        var token = Token.builder().user(user).token(jwtToken).tokenType(TokenType.BEARER).expired(false).revoked(false).build();
        tokenRepository.save(token);
    }

    private void removeAllTokenByUser(User user) {
        tokenRepository.deleteAllByUser(user.getId());
    }
}

