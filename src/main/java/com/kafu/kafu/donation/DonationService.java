package com.kafu.kafu.donation;

import com.kafu.kafu.problem.ProblemService;
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
    private final DonationMapper donationMapper;
    private final ProblemService problemService;
    private final UserService userService;

    public Page<DonationDTO> findAll(Pageable pageable) {
        return donationRepository.findAll(pageable)
                .map(donationMapper::toDTO);
    }

    public List<DonationDTO> findByProblemId(Long problemId) {
        return donationRepository.findByProblemId(problemId)
                .stream()
                .map(donationMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<DonationDTO> findByDonorId(Long donorId) {
        return donationRepository.findByDonorId(donorId)
                .stream()
                .map(donationMapper::toDTO)
                .collect(Collectors.toList());
    }

    public DonationDTO findById(Long id) {
        return donationRepository.findById(id)
                .map(donationMapper::toDTO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Donation not found"));
    }

    public Donation getDonationEntity(Long id) {
        return donationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Donation not found"));
    }

    @Transactional
    public DonationDTO create(DonationDTO donationDTO) {
        if (donationDTO.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A new donation cannot already have an ID");
        }

        // Verify that the problem exists
        problemService.findById(donationDTO.getProblemId());

        // Set donor ID from security context or null for anonymous
        Long donor = getCurrentUserId();
        if(donor == null)
        {
            throw new RuntimeException("no user provided");
        }
        donationDTO.setDonorId(donor);

        // Calculate fee and net amount
        BigDecimal amount = donationDTO.getAmount();
        BigDecimal fee = amount.multiply(FEE_PERCENTAGE).setScale(2, RoundingMode.HALF_UP);
        BigDecimal netAmount = amount.subtract(fee);
        
        donationDTO.setFee(fee);
        donationDTO.setNetAmount(netAmount);

        // Set donation date
        donationDTO.setDonationDate(LocalDateTime.now());
        
        Donation donation = donationMapper.toEntity(donationDTO);
        donation = donationRepository.save(donation);
        return donationMapper.toDTO(donation);
    }

    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getPrincipal())) {
            if (authentication.getPrincipal() instanceof Jwt jwt) {
                try {
                    // Get the user ID from the "sub" claim of the JWT token
                    String sub = jwt.getSubject();
                    return Long.parseLong(sub);
                } catch (Exception e) {
                    return null;
                }
            }
        }
        return null;
    }

    @Transactional
    public DonationDTO update(Long id, DonationDTO donationDTO) {
        Donation donation = donationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Donation not found"));

        // Verify that the problem exists if it's being updated
        if (donationDTO.getProblemId() != null) {
            problemService.findById(donationDTO.getProblemId());
        }

        // Verify that the donor exists if it's being updated
        if (donationDTO.getDonorId() != null) {
            userService.findById(donationDTO.getDonorId());
        }

        donationMapper.updateEntity(donation, donationDTO);
        donation = donationRepository.save(donation);
        return donationMapper.toDTO(donation);
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
