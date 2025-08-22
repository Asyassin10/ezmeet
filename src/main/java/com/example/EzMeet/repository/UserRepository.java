package com.example.EzMeet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.EzMeet.Enum.RoleType;
 import com.example.EzMeet.Models.User;

 import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Repository
public interface UserRepository  extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    Page<User> findByRole_Name(RoleType roleType, Pageable pageable);
	
}
