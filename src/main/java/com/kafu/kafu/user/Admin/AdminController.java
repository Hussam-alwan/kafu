package com.kafu.kafu.user.Admin;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
// @PreAuthorize("hasRole('ADMIN')")  // Temporarily commented for testing
public class AdminController {
    
    private final AdminService adminService;
    
    @GetMapping("/statistics")
    public ResponseEntity<AdminStatisticsDTO> getSystemStatistics() {
        AdminStatisticsDTO statistics = adminService.getSystemStatistics();
        return ResponseEntity.ok(statistics);
    }
}