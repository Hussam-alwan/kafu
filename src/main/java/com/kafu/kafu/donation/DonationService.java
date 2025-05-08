package com.kafu.kafu.donation;

import com.kafu.kafu.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DonationService {
    
    private final DonationRepository donationRepository;
    private final UserService userService;

    public Page<Donation> findAll(Pageable pageable) {
        return donationRepository.findAll(pageable);
    }

    public Page<Donation> findByProblemId(Long problemId, Pageable pageable) {
        return donationRepository.findByProblem_Id(problemId,pageable);
    }

    public List<Donation> findProblemDonationsForCurrentUser(Long problemId) {
        Long donorId = userService.getCurrentUser().getId();
        return donationRepository.findByDonor_IdAndProblem_Id(donorId,problemId);
    }

    public Donation findById(Long id) {
        return donationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Donation not found"));
    }
}
