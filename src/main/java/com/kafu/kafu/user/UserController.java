package com.kafu.kafu.user;

import com.kafu.kafu.donation.DonationDTO;
import com.kafu.kafu.donation.DonationMapper;
import com.kafu.kafu.donation.DonationService;
import com.kafu.kafu.user.DTO.UserChartDataDTO;
import com.kafu.kafu.user.DTO.UserDTO;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService   userService;
    private final UserFileService userFileService;
    private final DonationService donationService;


    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable Long id) {
        User user = userService.findById(id);
        user = userService.replaceUrlsWithPresigned(user);
        return ResponseEntity.ok(UserMapper.toDTO(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody UserDTO userDTO) {
        User user = userService.update(id, userDTO);
        user = userService.replaceUrlsWithPresigned(user);
        return ResponseEntity.ok(UserMapper.toDTO(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
    @Operation(summary = "associateUser",description = "add a user to a gov entity")
    @PostMapping("/{userId}/associate-user")
    public ResponseEntity<Void> associateUser(@RequestParam Long govId, @PathVariable Long userId) {
        userService.associateUser(govId, userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public ResponseEntity<UserDTO> getCurrentUser() {
        User user = userService.getCurrentUser();
        user = userService.replaceUrlsWithPresigned(user);
        return ResponseEntity.ok(UserMapper.toDTO(user));
    }

    @PostMapping("/{userId}/profile-photo")
    public ResponseEntity<String> uploadProfilePhoto(
            @PathVariable Long userId,
            @RequestParam String contentType) {
        return ResponseEntity.ok(userFileService.uploadProfilePhoto(userId, contentType));
    }

    @PostMapping("/{userId}/cv")
    public ResponseEntity<String> uploadCV(
            @PathVariable Long userId,
            @RequestParam String contentType) {
        return ResponseEntity.ok(userFileService.uploadCV(userId, contentType));
    }

    @GetMapping("/me/donations")
    public ResponseEntity<List<DonationDTO>> getMyDonations() {
        return ResponseEntity.ok(donationService.findAllDonationsForCurrentUser().stream().map(DonationMapper::toDTO).toList());
    }

    @PutMapping("/{userId}/add-role")
    public ResponseEntity<Void> addUserRoleIfNotExists(
            @PathVariable Long userId,
            @RequestParam String newRole) {
        userService.addUserRoleIfNotExistsByUserId(userId, newRole);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    // get the chart data for the current user
    @GetMapping("/{userId}/statistics")
    public ResponseEntity<List<UserChartDataDTO>> getUserStatistics(
        @PathVariable Long userId,
        @RequestParam Integer year) {
    
        List<UserChartDataDTO> statistics = userService.getUserStatistics(userId, year);
        return ResponseEntity.ok(statistics);
    }
    
    @GetMapping("/me/roles")
    public ResponseEntity<List<String>> getCurrentUserRoles() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/{userId}/statistics/yearly")
    public ResponseEntity<UserChartDataDTO> getYearlyStatistics(
        @PathVariable Long userId,
        @RequestParam Integer year) {

        UserChartDataDTO yearlyStats = userService.getYearlyStatistics(userId, year);
        return ResponseEntity.ok(yearlyStats);
    }
}
