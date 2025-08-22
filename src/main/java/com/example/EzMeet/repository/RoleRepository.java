package com.example.EzMeet.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.EzMeet.Enum.RoleType;
import com.example.EzMeet.Models.Role;
 
public interface RoleRepository  extends JpaRepository<Role, Long>{
	
	Optional<Role> findByName(RoleType name);
  
}
