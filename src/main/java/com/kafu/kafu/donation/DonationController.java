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
        return ResponseEntity.ok(donationService.findAll(pageable).map(DonationMapper::toDTO));
    }

    @GetMapping("/by-problem/{problemId}")
    public ResponseEntity<List<DonationDTO>> findByProblemId(@PathVariable Long problemId) {
        return ResponseEntity.ok(donationService.findByProblemId(problemId).stream().map(DonationMapper::toDTO).toList());
    }

    @GetMapping("/by-donor/{donorId}")
    public ResponseEntity<List<DonationDTO>> findByDonorId(@PathVariable Long donorId) {
        return ResponseEntity.ok(donationService.findByDonorId(donorId).stream().map(DonationMapper::toDTO).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DonationDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(DonationMapper.toDTO(donationService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<DonationDTO> create(@Valid @RequestBody DonationDTO donationDTO) {
        return ResponseEntity.ok(DonationMapper.toDTO(donationService.create(donationDTO)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DonationDTO> update(@PathVariable Long id, @Valid @RequestBody DonationDTO donationDTO) {
        return ResponseEntity.ok(DonationMapper.toDTO(donationService.update(id, donationDTO)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        donationService.delete(id);
        return ResponseEntity.ok().build();
    }
}
