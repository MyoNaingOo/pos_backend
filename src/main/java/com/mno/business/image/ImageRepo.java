package com.mno.business.image;

import com.mno.business.image.ImageData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface ImageRepo extends JpaRepository<ImageData,Long> {


    Optional<ImageData> findByName(String name);

    @Transactional
    @Modifying
    @Query("delete from ImageData i where i.name = ?1")
    void deleteByName(String name);


}
