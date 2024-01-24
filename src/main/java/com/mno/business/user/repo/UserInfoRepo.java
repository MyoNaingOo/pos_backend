package com.mno.business.user.repo;

import com.mno.business.shop.Shop;
import com.mno.business.user.entity.UserInfo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserInfoRepo extends JpaRepository<UserInfo,Long> {

    @Query(value = "SELECT * FROM user_info WHERE user_id = ?1",nativeQuery = true)
    Optional<UserInfo> findByUserId(Long user_id);

    List<UserInfo> findAllByShop(Shop shop, Pageable pageable);

}
