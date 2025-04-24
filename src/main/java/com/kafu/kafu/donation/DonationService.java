package com.kafu.kafu.donation;

import com.kafu.kafu.problem.ProblemService;
import com.kafu.kafu.user.User;
import com.kafu.kafu.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DonationService {
    private static final BigDecimal FEE_PERCENTAGE = new BigDecimal("0.01"); // 1%
    
    private final DonationRepository donationRepository;
    private final ProblemService problemService;
    private final UserService userService;

    public Page<Donation> findAll(Pageable pageable) {
        return donationRepository.findAll(pageable);
    }

    public List<Donation> findByProblemId(Long problemId) {
        return donationRepository.findByProblemId(problemId);
    }

    public List<Donation> findByDonorId(Long donorId) {
        return donationRepository.findByDonorId(donorId);
    }

    public Donation findById(Long id) {
        return donationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Donation not found"));
    }

    @Transactional
    public Donation create(DonationDTO donationDTO) {
        if (donationDTO.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A new donation cannot already have an ID");
        }

        // Verify that the problem exists
        if (donationDTO.getProblemId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Problem ID is required");
        }
        var problem = problemService.findById(donationDTO.getProblemId());
        if (problem == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Problem not found");
        }

        // Calculate fee and net amount
        BigDecimal amount = donationDTO.getAmount();
        BigDecimal fee = amount.multiply(FEE_PERCENTAGE).setScale(2, RoundingMode.HALF_UP);
        BigDecimal netAmount = amount.subtract(fee);
        donationDTO.setFee(fee);
        donationDTO.setNetAmount(netAmount);

        // Set donation date
        donationDTO.setDonationDate(LocalDateTime.now());

        // Map all simple fields
        Donation donation = new Donation();
        DonationMapper.updateEntity(donation, donationDTO);

        // Handle relations
        donation.setDonor(userService.getCurrentUser());
        donation.setProblem(problem);

        donation = donationRepository.save(donation);
        return donation;
    }

    @Transactional
    public Donation update(Long id, DonationDTO donationDTO) {
        Donation donation = donationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Donation not found"));

        // Map all simple fields
        DonationMapper.updateEntity(donation, donationDTO);

        // Handle relations
        if (donationDTO.getProblemId() != null) {
            var problem = problemService.findById(donationDTO.getProblemId());
            if (problem == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Problem not found");
            }
            donation.setProblem(problem);
        }

        if (donationDTO.getDonorId() != null) {
            var donorUser = userService.findById(donationDTO.getDonorId());
            if (donorUser == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Donor not found");
            }
            donation.setDonor(donorUser);
        }

        donation = donationRepository.save(donation);
        return donation;
    }

    @Transactional
    public void delete(Long id) {
        donationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Donation not found"));
        
        try {
            donationRepository.deleteById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Cannot delete donation as it is being referenced by other entities");
        }
    }
}
