package com.mno.business.Store.Repo;

import com.mno.business.Store.entity.Store;
import com.mno.business.product.entity.Product;
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


    List<Store> findAllByProduct(Product product, Pageable pageable);


    @Transactional
    @Modifying
    @Query(value = "UPDATE store s SET s.update_bulk = ?2 WHERE s.id = ?1 ", nativeQuery = true)
    void changeUpdateBulk(Long id, int update_bulk);

    List<Store> findAllByUser(User user, Pageable pageable);

    @Query(value = "SELECT * FROM store WHERE product_id=?1 AND  time= (select MIN(time) from store where product_id=?1 and bulk>update_bulk )", nativeQuery = true)
    Optional<Store> findAvailable(Long product_id);

    @Query(value = " SELECT * FROM store WHERE product_id=?1 AND  time= (select MAX(time) from store where product_id=?1 and bulk>=update_bulk and update_bulk!=0)", nativeQuery = true)
    Optional<Store> findNowUseStatusByProduct(Long product_id);


    @Query(value = "SELECT SUM(bulk)-SUM(update_bulk) FROM store WHERE product_id=?1 ORDER BY id DESC", nativeQuery = true)
    Optional<Integer> getProBalance(Long product_id);


    @Query(value = "SELECT * FROM store WHERE bulk!=update_bulk", nativeQuery = true)
    List<Store> getProductsBalance(Pageable pageable);


    @Query(value = "SELECT * FROM store WHERE bulk=update_bulk", nativeQuery = true)
    List<Store> getProductsSold(Pageable pageable);

    @Transactional
    @Modifying
    @Query(value = "update store set user_id=NULL where user_id=?1", nativeQuery = true)
    void removeUserByUser(Long user_id);


}
