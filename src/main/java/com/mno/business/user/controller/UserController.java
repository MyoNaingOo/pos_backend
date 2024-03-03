package com.mno.business.user.controller;

import com.mno.business.config.JwtService;
import com.mno.business.helper.PageDto;
import com.mno.business.user.dto.UserDto;
import com.mno.business.user.entity.User;
import com.mno.business.user.entity.UserInfo;
import com.mno.business.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v2/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;


    @DeleteMapping("delete")
    public void delete(HttpServletRequest request) {
        User user = jwtService.getuser(request);
        userService.deleteUser(user.getId());
    }

    @GetMapping("userid/{id}")
    public UserInfo getUser(@PathVariable("id") Long id) {
        UserInfo userInfo = userService.getuserInfo(id);
        return userService.userInfoMapper(userInfo);
    }

    @GetMapping("shop/page/{num}")
    public List<UserInfo> getUsernameOfShop(
            @PathVariable("num") int num,
            @RequestParam(value = "pageSize", defaultValue = "20") int pageSize,
            @RequestParam(value = "desc", defaultValue = "true") boolean desc,
            HttpServletRequest request
    ) {
        UserInfo userInfo = jwtService.getUserInfo(request);
        List<UserInfo> userInfos = userService.getusersInfoByShop(num, userInfo.getShop(), pageSize, desc);
        return userService.userInfosMapper(userInfos);
    }

    @GetMapping("shop/page")
    private PageDto pageOfShop(@RequestParam(value = "pageSize", defaultValue = "20") int pageSize,
                               HttpServletRequest request) {
        UserInfo userInfo = jwtService.getUserInfo(request);
        return userService.usersOfShop(userInfo.getShop(), pageSize);
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
    private User getUser(HttpServletRequest request) {
        User user = jwtService.getuser(request);
        return userService.responeUser(user);
    }

    @GetMapping("userInfo")
    private UserInfo getUserInfo(HttpServletRequest request) {
        UserInfo userInfo = jwtService.getUserInfo(request);
        return userService.userInfoMapper(userInfo);
    }

    @PutMapping("change/image")
    public void changeImage(@RequestBody UserDto user, HttpServletRequest request) {
        UserInfo userInfo = jwtService.getUserInfo(request);
        userService.changeImage(userInfo.getId(), user.getUser_img());
    }

    @PutMapping("change/password")
    public void changePass(@RequestBody UserDto user, HttpServletRequest request) {
        User userjwt = jwtService.getuser(request);
        userService.changePass(userjwt.getId(), user.getPassword());
    }

    @PutMapping("change/name")
    public void changeName(@RequestBody UserDto user, HttpServletRequest request) {
        User userjwt = jwtService.getuser(request);
        userService.changeName(userjwt.getId(), user.getName());
    }

    @PutMapping("change/shop")
    public void changeShop(@RequestBody UserDto userDto, @RequestParam(value = "new_user", required = false, defaultValue = "false") boolean new_user, HttpServletRequest request) {

        if (!new_user) {
            UserInfo userInfo = jwtService.getUserInfo(request);
            userService.updateShop(userInfo.getId(), userDto.getShop().getId());
        } else {
            User user = jwtService.getuser(request);
            userService.newUser(user, userDto.getShop());

        }
    }


}
