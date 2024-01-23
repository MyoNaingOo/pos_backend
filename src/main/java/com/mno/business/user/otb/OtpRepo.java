package com.mno.business.user.otb;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OtpRepo extends JpaRepository<Otp,Long> {

    Optional<Otp> findByOtp(int otp);

    @Modifying
    @Transactional
    @Query(value = "delete from otp where gmail=?1",nativeQuery = true)
    void deleteAllByGmail(String gmail);

}
