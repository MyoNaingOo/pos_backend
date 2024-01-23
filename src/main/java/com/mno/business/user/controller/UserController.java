package com.mno.business.user.controller;

import com.mno.business.config.JwtService;
import com.mno.business.user.auth.AuthenticationService;
import com.mno.business.user.dto.UserDto;
import com.mno.business.user.entity.User;
import com.mno.business.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;


    @DeleteMapping("delete")
    public void delete(HttpServletRequest request) {
        User user = jwtService.getuser(request);
        userService.deleteUser(user.getId());
    }

    @DeleteMapping("delete/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        userService.deleteUser(id);
    }

    @GetMapping("users/page/{num}")
    public List<UserDto> getusers(@PathVariable("num") int num) {
        List<User> users = userService.getUsers(num);
        return userService.ListMapper(users);
    }

    @GetMapping("userid/{id}")
    public UserDto getUser(@PathVariable("id") Long id) {
        User user = userService.getUser(id).orElse(null);
        assert user != null;
        return userService.mapper(user);

    }

    @GetMapping("username/{name}")
    public UserDto getUsername(@PathVariable("name") String name) {
        User user = userService.userfindByName(name);
        return userService.mapper(user);
    }

    @GetMapping("usergmaill/{gmail}")
    public UserDto getUserEmail(@PathVariable("email") String gmail) {
        User user = userService.userfindByGmail(gmail);
        return userService.mapper(user);

    }

    @GetMapping("user")
    private UserDto getUserByOwner(HttpServletRequest request) {
        User user = jwtService.getuser(request);
        return userService.mapper(user);
    }

    @PostMapping("image")
    public void changeImage(@RequestBody UserDto user, HttpServletRequest request) {
        User userjwt = jwtService.getuser(request);
        userService.changeImage(userjwt.getId(), user.getUser_img());
    }

    @PostMapping("changePass")
    public void changePass(@RequestBody UserDto user, HttpServletRequest request) {
        User userjwt = jwtService.getuser(request);
        userService.changePass(userjwt.getId(), user.getPassword());
    }

    @PostMapping("changeName")
    public void changeName(@RequestBody UserDto user, HttpServletRequest request) {
        User userjwt = jwtService.getuser(request);
        userService.changeName(userjwt.getId(), user.getName());
    }

    @PostMapping("changeAddress")
    public void changeAddress(@RequestBody UserDto user, HttpServletRequest request) {
        User userjwt = jwtService.getuser(request);
        userService.changeAddress(userjwt.getId(), user.getAddress());
    }

}
