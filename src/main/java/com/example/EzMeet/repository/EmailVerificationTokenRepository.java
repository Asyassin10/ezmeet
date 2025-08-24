package com.example.EzMeet.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.EzMeet.Models.EmailVerificationToken;

@Repository
public interface EmailVerificationTokenRepository extends JpaRepository<EmailVerificationToken, Long>{
	
	Optional<EmailVerificationToken> findByToken(String token);

}
