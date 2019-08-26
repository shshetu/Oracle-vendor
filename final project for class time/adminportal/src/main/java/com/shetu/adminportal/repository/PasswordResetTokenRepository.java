package com.shetu.adminportal.repository;

import com.shetu.adminportal.domain.User;
import com.shetu.adminportal.domain.security.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.stream.Stream;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken,Long> {

    //methods
    //findBy token, find by user
    PasswordResetToken findByToken(String token);

    PasswordResetToken findByUser(User user);

    //find everything by Expiry Date less than today's date
    Stream<PasswordResetToken> findAllByExpiryDateLessThan(Date now);

    @Modifying
    @Query("delete from PasswordResetToken t where t.expiryDate<= ?1")
    void deleteAllExpiredSince(Date now);
}
