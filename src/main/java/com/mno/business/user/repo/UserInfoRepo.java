package com.mno.business.user.repo;

import com.mno.business.shop.Shop;
import com.mno.business.user.entity.UserInfo;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserInfoRepo extends JpaRepository<UserInfo,Long> {

    @Query(value = "SELECT * FROM user_info WHERE user_id = ?1",nativeQuery = true)
    Optional<UserInfo> findByUserId(Long user_id);

    List<UserInfo> findAllByShop(Shop shop, Pageable pageable);

    @Modifying
    @Transactional
    @Query(value = "UPDATE user_info  SET user_img = ?2 WHERE id= ?1",nativeQuery = true)
    void updateUserImg(Long id,String  user_img);

    @Modifying
    @Transactional
    @Query(value = "UPDATE user_info  SET shop_id= ?2 WHERE id= ?1",nativeQuery = true)
    void updateShop(Long id,Long Shop_id);



    @Query(value = "SELECT COUNT(id) FROM user_info WHERE shop_id= ?1",nativeQuery = true)
    Integer getUserCountOfShop(Long shop_id);

}
