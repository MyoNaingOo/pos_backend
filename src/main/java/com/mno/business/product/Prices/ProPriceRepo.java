package com.mno.business.product.Prices;

import com.mno.business.product.entity.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProPriceRepo extends JpaRepository<ProPrice,Long> {


    @Query(value = "SELECT * FROM pro_price WHERE product_id=?1 AND date=(SELECT MAX(date) FROM pro_price WHERE product_id=?1) ORDER BY id DESC LIMIT 1 ",nativeQuery = true)
    Optional<ProPrice> findLtsProPrice(Long product_id);


    @Query(value = "SELECT * FROM pro_price WHERE product_id= ?1",nativeQuery = true)
    List<ProPrice> findAllByProduct(Long product_id, Pageable pageable);


}
