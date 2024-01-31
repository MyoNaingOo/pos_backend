package com.mno.business.user.service;

import com.mno.business.Store.Repo.StoreRepo;
import com.mno.business.helper.PageDto;
import com.mno.business.image.ImageService;
import com.mno.business.product.Repo.ProductRepo;
import com.mno.business.sale.Repo.SaleRepo;
import com.mno.business.shop.Shop;
import com.mno.business.shop.ShopSer;
import com.mno.business.user.dto.UserDto;
import com.mno.business.user.entity.User;
import com.mno.business.user.entity.UserInfo;
import com.mno.business.user.otb.OtpRepo;
import com.mno.business.user.repo.TokenRepo;
import com.mno.business.user.repo.UserInfoRepo;
import com.mno.business.user.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;
    private final ProductRepo productRepo;
    private final StoreRepo storeRepo;
    private final SaleRepo saleRepo;
    private final TokenRepo tokenRepo;
    private final OtpRepo otpRepo;
    private final UserInfoRepo userInfoRepo;
    private final ImageService imageService;
    private final ShopSer shopSer;


    public UserDto mapper(User user) {
        return UserDto.builder()
                .id(user.getId())
                .gmail(user.getGmail())
                .name(user.getName())
                .role(user.getRole())
                .build();

    }

    public User responeUser(User user) {
        return User.builder()
                .id(user.getId())
                .gmail(user.getGmail())
                .name(user.getName())
                .role(user.getRole())
                .build();

    }

    public List<UserDto> ListMapper(List<User> users) {
        List<UserDto> userDtos = new ArrayList<>();
        users.forEach(
                user -> {
                    UserDto userDto = mapper(user);
                    userDtos.add(userDto);
                }
        );
        return userDtos;
    }

    public UserInfo userInfoMapper(UserInfo userInfo){
        User user = responeUser(userInfo.getUser());
        return UserInfo.builder()
                .id(userInfo.getId())
                .shop(userInfo.getShop())
                .user(user)
                .user_img(userInfo.getUser_img())
                .build();
    }

    public List<UserInfo> userInfosMapper(List<UserInfo> userInfos){
        List<UserInfo> userInfoList = new ArrayList<>();

        userInfos.forEach(
                userInfo -> {
                    UserInfo userInfo1 = userInfoMapper(userInfo);
                    userInfoList.add(userInfo1);
                }
        );

        return userInfoList;
    }



    public void addNewUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
    }

    public User userfindByName(String name) {
        return userRepo.findByName(name).orElseThrow(null);
    }

    public User userfindByGmail(String gmail) {
        return userRepo.findByGmail(gmail).orElse(null);
    }

    public List<User> getUsers(int num) {
        Pageable pageable = PageRequest.of(num, 20, Sort.by("id").descending());
        return userRepo.findAll(pageable).getContent();
    }

    public Optional<User> getUser(Long id) {
        return userRepo.findById(id);
    }

    public UserInfo getuserInfo(Long user_id){
        return userInfoRepo.findByUserId(user_id).orElse(null);
    }


    public List<UserInfo> getusersInfoByShop(int num,Shop shop){
        Pageable pageable = PageRequest.of(num, 20, Sort.by("id").descending());
        return userInfoRepo.findAllByShop(shop,pageable);

    }



    @Async
    public void deleteUser(Long id) {

        User user = userRepo.findById(id).orElse(null);
        assert user != null;

        productRepo.removeUserByUser(user.getId());
        saleRepo.removeUserByUser(user.getId());
        storeRepo.removeUserByUser(user.getId());
        otpRepo.deleteAllByGmail(user.getGmail());
        tokenRepo.deleteAllByUser(user.getId());
        UserInfo userInfo = getuserInfo(user.getId());
        if (userInfo!= null){
            imageService.deleteByName(userInfo.getUser_img());
            userInfoRepo.deleteById(userInfo.getId());
        }
        userRepo.deleteById(id);

    }


    public void changeImage(Long userInfo_id, String image) {
        userInfoRepo.updateUserImg(userInfo_id,image);
    }

    public void changePass(Long id, String password) {
        String pass = passwordEncoder.encode(password);
        userRepo.changePassword(id, pass);
    }

    public void changeName(Long id, String name) {
        userRepo.changeName(id, name);
    }


    public void updateShop(Long user_infoId,Long shop_id){
        userInfoRepo.updateShop(user_infoId,shop_id);
    }

    public void newUser(User user,Shop shop){
        Shop shop1 = shopSer.shop(shop.getId());
        UserInfo userInfo = UserInfo.builder()
                .user(user)
                .shop(shop1)
                .build();
        userInfoRepo.save(userInfo);

    }

    public PageDto users(){

        int users = userRepo.getUserCount();
        int page_size = users / 20;
        return PageDto.builder().page_size(page_size).number(users).build();
    }

    public PageDto usersOfShop(Shop shop){
        int users = userInfoRepo.getUserCountOfShop(shop.getId());
        int page_size = users / 20;
        return PageDto.builder().page_size(page_size).number(users).build();
    }



}
