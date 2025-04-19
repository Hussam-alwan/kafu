package com.kafu.kafu.donation;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/donations")
@RequiredArgsConstructor
public class DonationController {
    private final DonationService donationService;

    @GetMapping
    public ResponseEntity<Page<DonationDTO>> findAll(Pageable pageable) {
        return ResponseEntity.ok(donationService.findAll(pageable));
    }

    @GetMapping("/by-problem/{problemId}")
    public ResponseEntity<List<DonationDTO>> findByProblemId(@PathVariable Long problemId) {
        return ResponseEntity.ok(donationService.findByProblemId(problemId));
    }

    @GetMapping("/by-donor/{donorId}")
    public ResponseEntity<List<DonationDTO>> findByDonorId(@PathVariable Long donorId) {
        return ResponseEntity.ok(donationService.findByDonorId(donorId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DonationDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(donationService.findById(id));
    }

    @PostMapping
    public ResponseEntity<DonationDTO> create(@Valid @RequestBody DonationDTO donationDTO) {
        return ResponseEntity.ok(donationService.create(donationDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DonationDTO> update(@PathVariable Long id, @Valid @RequestBody DonationDTO donationDTO) {
        return ResponseEntity.ok(donationService.update(id, donationDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        donationService.delete(id);
        return ResponseEntity.ok().build();
    }
}
