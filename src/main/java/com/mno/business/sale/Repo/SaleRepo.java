package com.mno.business.sale.Repo;

import com.mno.business.sale.entity.Sale;
import com.mno.business.shop.Shop;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SaleRepo extends JpaRepository<Sale, Long> {


    @Query(value = " SELECT * FROM sale WHERE MONTH(time)=?1 AND YEAR(time)=?2", nativeQuery = true)
    List<Sale> findByMonth(int month, int year, Pageable pageable);

    @Transactional
    @Modifying
    @Query(value = "update sale set user_id=NULL where user_id=?1", nativeQuery = true)
    void removeUserByUser(Long user_id);


    List<Sale> findAllByShop(Shop shop, Pageable pageable);

    @Query(value = " SELECT * FROM sale WHERE MONTH(time)=?1 AND YEAR(time)=?2 AND shop_id= ?3", nativeQuery = true)
    List<Sale> findByMonthOfShop(int month, int year, Long shop_id, Pageable pageable);


    @Query(value = "SELECT COUNT(id) FROM sale WHERE shop_id= ?1" ,nativeQuery = true)
    int saleOfShop(Long shop_id);


    @Query(value = "SELECT COUNT(id) FROM sale " ,nativeQuery = true)
    int sales();

    @Query(value = " SELECT COUNT(id) FROM sale WHERE MONTH(time)=?1 AND YEAR(time)=?2 AND shop_id= ?3", nativeQuery = true)
    int findCountByMonthOfShop(int month, int year, Long shop_id);


    @Query(value = " SELECT COUNT(id) FROM sale WHERE MONTH(time)=?1 AND YEAR(time)=?2", nativeQuery = true)
    int findCountByMonth(int month, int year);

}
