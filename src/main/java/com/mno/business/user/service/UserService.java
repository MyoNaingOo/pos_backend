package com.mno.business.user.service;

import com.mno.business.Store.Repo.StoreRepo;
import com.mno.business.image.ImageService;
import com.mno.business.product.Repo.ProductRepo;
import com.mno.business.sale.Repo.SaleRepo;
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



    public UserDto mapper(User user) {
        return UserDto.builder()
                .id(user.getId())
                .gmail(user.getGmail())
                .name(user.getName())
                .address(user.getAddress())
                .role(user.getRole())
                .build();

    }

    public User responeUser(User user) {
        return User.builder()
                .id(user.getId())
                .gmail(user.getGmail())
                .name(user.getName())
                .address(user.getAddress())
                .role(user.getRole())
                .build();

    }

    public List<UserDto> ListMapper(List<User> users) {
        List<UserDto> userDtos = new ArrayList<UserDto>();
        users.forEach(
                user -> {
                    UserDto userDto = mapper(user);
                    userDtos.add(userDto);
                }
        );
        return userDtos;
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

    @Async
    public void deleteUser(Long id) {

        User user = userRepo.findById(id).orElse(null);
        assert user != null;

        productRepo.removeUserByUser(user.getId());
        saleRepo.removeUserByUser(user.getId());
        storeRepo.removeUserByUser(user.getId());
        otpRepo.deleteAllByGmail(user.getGmail());
        tokenRepo.deleteAllByUser(user.getId());
        userRepo.deleteById(id);
    }

    public User updateUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    public void changeImage(Long id, String image) {
//        userRepo.updateImage(id, image);
    }

    public void changePass(Long id, String password) {
        String pass = passwordEncoder.encode(password);
        userRepo.changePassword(id, pass);
    }

    public void changeName(Long id, String name) {
        userRepo.changeName(id, name);
    }

    public void changeAddress(Long id, String address) {
        userRepo.changeAddress(id, address);
    }

}
