package com.example.EzMeet.service.admin;

import java.time.LocalDateTime;
 import java.util.Optional;
 
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.EzMeet.Enum.RoleType;
import com.example.EzMeet.Models.Role;
import com.example.EzMeet.Models.User;
import com.example.EzMeet.dto.admin.CreateVoyagureRequest;
import com.example.EzMeet.dto.admin.UpdateVoyagureRequest;
import com.example.EzMeet.dto.admin.VoyagureResponse;
import com.example.EzMeet.repository.RoleRepository;
import com.example.EzMeet.repository.UserRepository;
import com.example.EzMeet.service.EmailService;
import com.example.EzMeet.service.EmailVerificationService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class VoyagureManagment {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailVerificationService emailVerificationService;
 
    public VoyagureManagment(UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder,
            EmailVerificationService emailVerificationService) {
				this.userRepository = userRepository;
				this.roleRepository = roleRepository;
				this.passwordEncoder = passwordEncoder;
				this.emailVerificationService = emailVerificationService; 
				}
    


    public Page<VoyagureResponse> getAllVoyagers(Pageable pageable) {
        return userRepository.findByRole_Name(RoleType.VOYAGER, pageable)
                .map(u -> new VoyagureResponse(
                        u.getName(),
                        u.getFirst_name(),
                        u.getEmail(),
                        u.getRole().getName()
                ));
    }

     public VoyagureResponse createVoyager(CreateVoyagureRequest request) {
    	 
	    if (userRepository.existsByEmail(request.email())) {
	        throw new RuntimeException("This email already exist");
	    }
	    
        Role role = roleRepository.findByName(RoleType.VOYAGER)
                .orElseThrow(() -> new RuntimeException("Role not found: VOYAGER"));

        User user = new User();
        user.setName(request.name());
        user.setFirst_name(request.firstName());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setCreatedAt(LocalDateTime.now());
        user.setRole(role);
        user.setEmailVerified(false);
        User saved = userRepository.save(user);
        emailVerificationService.sendVerificationEmail(saved);
         return new VoyagureResponse(
                saved.getName(),
                saved.getFirst_name(),
                saved.getEmail(),
                saved.getRole().getName()
        );
    }
     
     public Optional<User> getVoyagerById(Long id) {
    	    return userRepository.findById(id)
    	            .filter(u -> u.getRole().getName() == RoleType.VOYAGER);
    }
 
     public void deleteVoyager(Long id) {
         userRepository.deleteById(id);
     }
     
     
     public Optional<VoyagureResponse> updateVoyager(Long id, UpdateVoyagureRequest request) {
    	    return userRepository.findById(id)
    	            .filter(user -> RoleType.VOYAGER.equals(user.getRole().getName()))
    	            .map(user -> {
    	                user.setName(request.name());
    	                user.setFirst_name(request.firstName());
    	                user.setEmail(request.email());
    	                User updated = userRepository.save(user);
    	                return new VoyagureResponse(
    	                        updated.getName(),
    	                        updated.getFirst_name(),
    	                        updated.getEmail(),
    	                        updated.getRole().getName()
    	                );
    	            });
    	}

}
