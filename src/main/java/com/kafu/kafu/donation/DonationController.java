package com.kafu.kafu.donation;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/problems/{problemId}/donations")
@RequiredArgsConstructor
public class DonationController {
    private final DonationService donationService;

    @GetMapping
    public ResponseEntity<Page<DonationDTO>> findByProblemId(@PathVariable Long problemId,Pageable pageable) {
        return ResponseEntity.ok(donationService.findByProblemId(problemId,pageable).map(DonationMapper::toDTO));
    }

    @GetMapping("/me")
    public ResponseEntity<List<DonationDTO>> findDonationsForCurrentUser(@PathVariable Long problemId) {
        return ResponseEntity.ok(donationService.findProblemDonationsForCurrentUser(problemId).stream().map(DonationMapper::toDTO).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DonationDTO> findById(@PathVariable Long problemId,@PathVariable Long id) {
        return ResponseEntity.ok(DonationMapper.toDTO(donationService.findById(id)));
    }

//    make this /donate 500usd

}
