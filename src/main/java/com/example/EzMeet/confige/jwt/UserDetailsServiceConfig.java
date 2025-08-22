package com.example.EzMeet.confige.jwt;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.example.EzMeet.repository.RoleRepository;
import com.example.EzMeet.repository.UserRepository;
import com.example.EzMeet.service.CustomUserDetailsService;

@Configuration
public class UserDetailsServiceConfig {

    @Bean
    public CustomUserDetailsService customUserDetailsService(UserRepository userRepository,
                                                             RoleRepository roleRepository,
                                                             PasswordEncoder passwordEncoder) {
        return new CustomUserDetailsService(userRepository, roleRepository, passwordEncoder);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
