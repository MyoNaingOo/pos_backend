package com.mno.business.Store.Repo;

import com.mno.business.Store.entity.Store;
import com.mno.business.product.entity.Product;
import com.mno.business.shop.Shop;
import com.mno.business.user.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StoreRepo extends JpaRepository<Store, Long> {

    @Query(value = "SELECT * FROM store WHERE MONTH(time)= ?1 AND  YEAR(time)= ?2", nativeQuery = true)
    List<Store> findByMonthAndYear(int month, int year, Pageable pageable);

    @Query(value = "SELECT * FROM store WHERE MONTH(time)= ?1 AND  YEAR(time)= ?2 AND shop_id= ?3", nativeQuery = true)
    List<Store> findByMonthAndYearAndShop(int month, int year,Long shop_id, Pageable pageable);


    List<Store> findAllByProduct(Product product, Pageable pageable);

    List<Store> findAllByProductAndShop(Product product, Shop shop, Pageable pageable);


    List<Store> findAllByShop(Shop shop, Pageable pageable);


    @Transactional
    @Modifying
    @Query(value = "UPDATE store s SET s.update_bulk = ?2 WHERE s.id = ?1 ", nativeQuery = true)
    void changeUpdateBulk(Long id, int update_bulk);

    List<Store> findAllByUser(User user, Pageable pageable);

    @Query(value = "SELECT * FROM store WHERE product_id=?1 AND  shop_id= ?2 AND  time= (select MIN(time) from store where product_id=?1 and bulk>update_bulk and shop_id= ?2)", nativeQuery = true)
    Optional<Store> findAvailable(Long product_id,Long shop_id);

    @Query(value = " SELECT * FROM store WHERE product_id=?1 AND shop_id= ?2 AND  time= (select MAX(time) from store where product_id=?1 and bulk>=update_bulk and update_bulk!=0 and shop_id= ?2 )", nativeQuery = true)
    Optional<Store> findNowUseStatusByProduct(Long product_id,Long shop_id);


    @Query(value = "SELECT SUM(bulk)-SUM(update_bulk) FROM store WHERE product_id=?1 ORDER BY id DESC", nativeQuery = true)
    Optional<Integer> getProBalance(Long product_id);

    @Query(value = "SELECT SUM(bulk)-SUM(update_bulk) FROM store WHERE product_id=?1 AND shop_id = ?2 ORDER BY id DESC", nativeQuery = true)
    Optional<Integer> getProBalanceOfShop(Long product_id,Long shop_id);

    @Query(value = "SELECT * FROM store WHERE bulk!=update_bulk", nativeQuery = true)
    List<Store> getProductsBalance(Pageable pageable);


    @Query(value = "SELECT * FROM store WHERE bulk!=update_bulk AND shop_id = ?1", nativeQuery = true)
    List<Store> getProductsBalanceOfShop(Long shop_id,Pageable pageable);

    @Query(value = "SELECT * FROM store WHERE bulk=update_bulk", nativeQuery = true)
    List<Store> getProductsSold(Pageable pageable);

    @Query(value = "SELECT * FROM store WHERE bulk=update_bulk AND shop_id= ?1 ", nativeQuery = true)
    List<Store> getProductsSoldOfShop(Long shop_id,Pageable pageable);

    @Transactional
    @Modifying
    @Query(value = "update store set user_id=NULL where user_id=?1", nativeQuery = true)
    void removeUserByUser(Long user_id);



//    page


    @Query(value = "SELECT COUNT(id) FROM store " ,nativeQuery = true)
    int stores();

    @Query(value = "SELECT COUNT(id) FROM store WHERE shop_id= ?1" ,nativeQuery = true)
    int storesOfShop(Long id);


    @Query(value = "SELECT COUNT(id) FROM store WHERE bulk!=update_bulk", nativeQuery = true)
    int pageOfBalance();


    @Query(value = "SELECT COUNT(id) FROM store WHERE bulk!=update_bulk AND shop_id = ?1", nativeQuery = true)
    int pageOfBalanceAndShop(Long shop_id);

    @Query(value = "SELECT COUNT(id) FROM store WHERE bulk=update_bulk", nativeQuery = true)
    int pageOfSold();

    @Query(value = "SELECT COUNT(id) FROM store WHERE bulk=update_bulk AND shop_id= ?1 ", nativeQuery = true)
    int pageOfSoldAndShop(Long shop_id);



    @Query(value = "SELECT COUNT(id) FROM store WHERE MONTH(time)= ?1 AND  YEAR(time)= ?2", nativeQuery = true)
    int pageOfMonthAndYear(int month, int year);

    @Query(value = "SELECT COUNT(id) FROM store WHERE MONTH(time)= ?1 AND  YEAR(time)= ?2 AND shop_id= ?3", nativeQuery = true)
    int pageOfMonthAndYearAndShop(int month, int year,Long shop_id);

    @Query(value = "SELECT COUNT(id) FROM store WHERE product_id= ?1 AND shop_id= ?2", nativeQuery = true)
    int pageOfProductAndShop(Long product_id, Long shop_id);


    @Query(value = "SELECT COUNT(id) FROM store WHERE product_id= ?1", nativeQuery = true)
    int pageOfProduct(Long product_id);

    @Query("select COUNT(id) from Store where user_id = ?1")
    int pageOfUser(Long user_id);


}
