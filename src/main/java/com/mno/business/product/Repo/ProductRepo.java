package com.mno.business.product.Repo;

import com.mno.business.product.entity.Product;
import com.mno.business.shop.Shop;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepo extends JpaRepository<Product, Long> {

    @Query(value = " SELECT * FROM product WHERE MONTH(time)=?1 AND YEAR(time)=?2", nativeQuery = true)
    List<Product> findByMonth(int month, int year, Pageable pageable);

    @Query(value = "SELECT COUNT(id) FROM product WHERE MONTH(time)=?1 AND YEAR(time)=?2",nativeQuery = true)
    int findByMonthCount(int month, int year);

    @Transactional
    @Modifying
    @Query(value = "update product set user_id=NULL where user_id=?1", nativeQuery = true)
    void removeUserByUser(Long user_id);

    Optional<Product> findByCode(String code);

    List<Product> findAllByCategory(String category,Pageable pageable);

    List<Product> findByNameContainingOrDescriptionContainingOrCategoryContaining(String name, String description,String category, Pageable pageable);


    @Query(value = "SELECT COUNT(id) FROM product",nativeQuery = true)
    int productsCount();

    @Query(value = "SELECT COUNT(id) FROM product WHERE category= ?1", nativeQuery = true)
    List<Product> findCountByCategory(String category, Pageable pageable);


    @Query(value = "select COUNT(id) from product p where p.name like concat('%', ?1, '%') and p.description like concat('%', ?2, '%')",nativeQuery = true)
    int pageOfNameContainingAndDescriptionContaining(String name, String description);


}
