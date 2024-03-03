package com.mno.business.user.controller;


import com.mno.business.config.JwtService;
import com.mno.business.helper.PageDto;
import com.mno.business.shop.Shop;
import com.mno.business.shop.ShopSer;
import com.mno.business.user.dto.UserDto;
import com.mno.business.user.entity.Role;
import com.mno.business.user.entity.User;
import com.mno.business.user.entity.UserInfo;
import com.mno.business.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("api/v2/panel/user")
@RequiredArgsConstructor
public class UserPanelControl {

    private final UserService userService;
    private final ShopSer shopSer;
    private final JwtService jwtService;

    @GetMapping("page")
    private PageDto page() {
        return userService.users();
    }

    @GetMapping("page/{num}")
    public List<UserDto> getusers(
            @PathVariable("num") int num,
            @RequestParam(value = "pageSize", defaultValue = "20") int pageSize,
            @RequestParam(value = "desc", defaultValue = "true") boolean desc
    ) {
        List<User> users = userService.getUsers(num, pageSize, desc);
        return userService.ListMapper(users);
    }

    @DeleteMapping("delete/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        userService.deleteUser(id);
    }


    @GetMapping("shop/page/{num}")
    public List<UserInfo> getUsernameOfShop(
            @PathVariable("num") int num,
            @RequestParam(value = "pageSize", defaultValue = "20") int pageSize,
            @RequestParam(value = "desc", defaultValue = "true") boolean desc,
            @RequestParam("shop_id") Long shop_id) {
        Shop shop = shopSer.shop(shop_id);
        List<UserInfo> userInfos = userService.getusersInfoByShop(num, shop,pageSize,desc);
        return userService.userInfosMapper(userInfos);
    }

    @GetMapping("shop/page")
    private PageDto pageOfShop(
            @RequestParam("shop_id") Long shop_id,
            @RequestParam(value = "pageSize", defaultValue = "20") int pageSize
    ) {
        Shop shop = shopSer.shop(shop_id);
        return userService.usersOfShop(shop,pageSize);
    }


    @PutMapping("setCEO")
    private void setCeo(@RequestParam("user_id") Long user_id, HttpServletRequest request) {
        User user = jwtService.getuser(request);

        if (user.getRole() == Role.OWNER) {
            userService.setRole(user_id, Role.CEO);
        }

    }

    @PutMapping("change/role")
    private void setRole(@RequestParam("user_id") Long user_id, @RequestParam("roleAccess") String role) {

        if (Objects.equals(role, Role.USER.name())) {
            userService.setRole(user_id, Role.USER);
        } else if (Objects.equals(role, Role.USERMANAGER.name())) {
            userService.setRole(user_id, Role.USERMANAGER);
        } else if (Objects.equals(role, Role.STOREWORKER.name())) {
            userService.setRole(user_id, Role.STOREWORKER);
        } else if (Objects.equals(role, Role.STOREMANAGER.name())) {
            userService.setRole(user_id, Role.STOREMANAGER);
        } else if (Objects.equals(role, Role.SALEWORKER.name())) {
            userService.setRole(user_id, Role.SALEWORKER);
        } else if (Objects.equals(role, Role.SALEMANAGER.name())) {
            userService.setRole(user_id, Role.SALEMANAGER);
        }
    }


}
