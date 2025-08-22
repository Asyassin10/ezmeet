package com.example.EzMeet.Models;

import jakarta.persistence.Entity;

import java.time.LocalDateTime;

import com.example.EzMeet.Enum.RoleType;

 import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
 import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "name", nullable = false, unique = true, length = 100)
    private String name;
    
    @Column(name = "first_name", nullable = false, unique = true, length = 100)
    private String first_name;
    
    @Column(name = "email", nullable = false, unique = true, length = 150)
    private String email;
    
    @Column(name = "password", nullable = false, length = 255)
    private String password;
    
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "email_verified", nullable = false)
    private boolean emailVerified = false;



	// Many users belong to one role
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;
    
 
    // Helper methods to check role
    public boolean isAdmin() {
        return role != null && RoleType.ADMIN.equals(role.getName());
    }
    
    public boolean isGuide() {
        return role != null && RoleType.GUIDE.equals(role.getName());
    }
    
    public boolean isVoyager() {
        return role != null && RoleType.VOYAGER.equals(role.getName());
    }
    
    // Alternative helper methods using role type
    public boolean hasRole(RoleType roleType) {
        return role != null && roleType.equals(role.getName());
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	
    
    public boolean isEmailVerified() {
		return emailVerified;
	}

	public void setEmailVerified(boolean emailVerified) {
		this.emailVerified = emailVerified;
	}
    
}

