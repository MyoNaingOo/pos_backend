package com.mno.business.user.repo;

import java.util.List;
import java.util.Optional;

import com.mno.business.user.entity.Token;
import com.mno.business.user.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepo extends JpaRepository<Token, Long> {

    @Query(value = """
      select t from Token t inner join User u\s
      on t.user.id = u.id\s
      where u.id = :id and (t.expired = false or t.revoked = false)\s
      """)
    List<Token> findAllValidTokenByUser(Long id);

    Optional<Token> findByToken(String token);


    @Modifying
    @Transactional
    @Query(value = " delete from token where user_id=?1",nativeQuery = true)
    void deleteAllByUser(Long user_id);

}
