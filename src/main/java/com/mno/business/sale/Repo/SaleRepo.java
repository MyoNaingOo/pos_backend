package com.mno.business.sale.Repo;

import com.mno.business.sale.entity.Sale;
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


}
