package com.mno.business.product.Repo;

import com.mno.business.product.entity.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepo extends JpaRepository<Product, Long> {

    @Query(value = " SELECT * FROM product WHERE MONTH(time)=?1 AND YEAR(time)=?2", nativeQuery = true)
    List<Product> findByMonth(int month, int year, Pageable pageable);


    @Transactional
    @Modifying
    @Query(value = "update product set user_id=NULL where user_id=?1", nativeQuery = true)
    void removeUserByUser(Long user_id);

    List<Product> findByNameContaining(String name, Pageable pageable);

}
