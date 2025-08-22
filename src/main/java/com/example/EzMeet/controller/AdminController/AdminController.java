package com.example.EzMeet.controller.AdminController;


 

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.EzMeet.dto.admin.CreateVoyagureRequest;
import com.example.EzMeet.dto.admin.UpdateVoyagureRequest;
import com.example.EzMeet.dto.admin.VoyagureResponse;
import com.example.EzMeet.service.admin.VoyagureManagment;

import jakarta.validation.Valid;
 

@RestController
public class AdminController {
	
    private final VoyagureManagment voyagureManagment;	   
    
    public AdminController(VoyagureManagment voyagureManagment) {
        this.voyagureManagment = voyagureManagment;     
    }
    
    
    
    
    
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/voyagers")
    public ResponseEntity<Page<VoyagureResponse>> getVoyagers(
            Pageable pageable
    ) {
        Page<VoyagureResponse> voyagers = voyagureManagment.getAllVoyagers(pageable);
        return ResponseEntity.ok(voyagers);
    }
    
    
    
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/voyager/{id}")
    public ResponseEntity<?> getVoyagerById(@PathVariable Long id) {
        var voyagerOpt = voyagureManagment.getVoyagerById(id);

        if (voyagerOpt.isEmpty()) {
            return ResponseEntity.status(404).body("Voyager not found with id: " + id);
        }

         var voyagerResponse = new VoyagureResponse(
                voyagerOpt.get().getName(),
                voyagerOpt.get().getFirst_name(),
                voyagerOpt.get().getEmail(),
                voyagerOpt.get().getRole().getName()
        );

        return ResponseEntity.ok(voyagerResponse);
    }
    
    

	@GetMapping("/admin/test")
	public String admintest() {
		return "admin";
	}
	
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/admin/create/voyager")
	public ResponseEntity<?> createVoyager(@Valid @RequestBody CreateVoyagureRequest request) {
	    return ResponseEntity.ok(voyagureManagment.createVoyager(request));
	}
	
	
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/admin/voyager/{id}")
    public ResponseEntity<?> deleteVoyager(@PathVariable Long id) {
        try {
            var voyagerOpt = voyagureManagment.getVoyagerById(id); 
            if (voyagerOpt.isEmpty()) {
                return ResponseEntity.status(404).body("Voyager not found with id: " + id);
            }
            voyagureManagment.deleteVoyager(id);
            return ResponseEntity.ok().body("Voyager deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Failed to delete voyager: " + e.getMessage());
        }
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/admin/voyager/{id}")
    public ResponseEntity<?> updateVoyager(@PathVariable Long id, @Valid @RequestBody UpdateVoyagureRequest request) {
 
        try {
            var voyagerOpt = voyagureManagment.getVoyagerById(id); 
            if (voyagerOpt.isEmpty()) {
                return ResponseEntity.status(404).body("Voyager not found with id: " + id);
            }
            voyagureManagment.updateVoyager(id, request);
            var voyagerResponse = new VoyagureResponse(
                    voyagerOpt.get().getName(),
                    voyagerOpt.get().getFirst_name(),
                    voyagerOpt.get().getEmail(),
                    voyagerOpt.get().getRole().getName()
            );

            return ResponseEntity.ok(voyagerResponse);
        } catch (Exception e) {
            return ResponseEntity.status(400).body("Failed to update voyager: " + e.getMessage());
        }
    }

 
}


		

