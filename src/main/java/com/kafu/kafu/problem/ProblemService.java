package com.kafu.kafu.problem;

import com.kafu.kafu.address.AddressService;
import com.kafu.kafu.gov.GovService;
import com.kafu.kafu.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProblemService {
    private final ProblemRepository problemRepository;
    private final ProblemMapper problemMapper;
    private final AddressService addressService;
    private final UserService userService;
    private final GovService govService;

    public Page<ProblemDTO> findAll(Pageable pageable) {
        return problemRepository.findAll(pageable)
                .map(problemMapper::toDTO);
    }

    public ProblemDTO findById(Long id) {
        return problemRepository.findById(id)
                .map(problemMapper::toDTO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Problem not found"));
    }

    public Problem getProblemEntity(Long id) {
        return problemRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Problem not found"));
    }

    @Transactional
    public ProblemDTO create(ProblemDTO problemDTO) {
        if (problemDTO.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A new problem cannot already have an ID");
        }

        // Verify that the submitting user exists
        userService.findById(problemDTO.getSubmittedByUserId());

        // Verify that the approving gov exists if provided
        if (problemDTO.getApprovedByGovId() != null) {
            govService.findById(problemDTO.getApprovedByGovId());
        }

        // Save the address first if provided
        if (problemDTO.getAddress() != null) {
            var savedAddress = addressService.create(problemDTO.getAddress());
            problemDTO.setAddress(savedAddress);
        }

        problemDTO.setSubmissionDate(LocalDateTime.now());
        
        Problem problem = problemMapper.toEntity(problemDTO);
        
        // Set submitted by user
        if (problemDTO.getSubmittedByUserId() != null) {
            problem.setSubmittedByUser(userService.getUserEntity(problemDTO.getSubmittedByUserId()));
        }

        // Set initial timestamps
        problem.setSubmittedAt(LocalDateTime.now());
        problem.setStatus(ProblemStatus.PENDING_APPROVAL);

        problem = problemRepository.save(problem);
        return problemMapper.toDTO(problem);
    }

    @Transactional
    public ProblemDTO update(Long id, ProblemDTO problemDTO) {
        Problem problem = problemRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Problem not found"));

        // Verify that the submitting user exists if it's being updated
        if (problemDTO.getSubmittedByUserId() != null) {
            userService.findById(problemDTO.getSubmittedByUserId());
        }

        // Verify that the approving gov exists if it's being updated
        if (problemDTO.getApprovedByGovId() != null) {
            govService.findById(problemDTO.getApprovedByGovId());
        }

        // Update the address if provided
        if (problemDTO.getAddress() != null) {
            var existingAddress = problem.getAddress();
            if (existingAddress != null) {
                // Update existing address
                problemDTO.getAddress().setId(existingAddress.getId());
                var updatedAddress = addressService.update(existingAddress.getId(), problemDTO.getAddress());
                problemDTO.setAddress(updatedAddress);
            } else {
                // Create new address
                var savedAddress = addressService.create(problemDTO.getAddress());
                problemDTO.setAddress(savedAddress);
            }
        }

        problemMapper.updateEntity(problem, problemDTO);

        // Update timestamps based on status changes
        if (problemDTO.getStatus() != null && problemDTO.getStatus() != problem.getStatus()) {
            switch (problemDTO.getStatus()) {
                case APPROVED:
                    problem.setApprovedAt(LocalDateTime.now());
                    break;
                case REJECTED:
                    problem.setRejectedAt(LocalDateTime.now());
                    break;
                case RESOLVED:
                    problem.setResolvedAt(LocalDateTime.now());
                    break;
                default:
                    break;
            }
        }

        problem = problemRepository.save(problem);
        return problemMapper.toDTO(problem);
    }

    @Transactional
    public void delete(Long id) {
        problemRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Problem not found"));
        
        try {
            problemRepository.deleteById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Cannot delete problem as it is being referenced by other entities");
        }
    }

    @Transactional
    public ProblemDTO approve(Long id, Long govId) {
        Problem problem = getProblemEntity(id);
        
        if (problem.getStatus() != ProblemStatus.PENDING_APPROVAL) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only pending problems can be approved");
        }

        problem.setApprovedByGov(govService.getGovEntity(govId));
        problem.setApprovedAt(LocalDateTime.now());
        problem.setStatus(ProblemStatus.APPROVED);

        problem = problemRepository.save(problem);
        return problemMapper.toDTO(problem);
    }

    @Transactional
    public ProblemDTO reject(Long id, String reason) {
        Problem problem = getProblemEntity(id);
        
        if (problem.getStatus() != ProblemStatus.PENDING_APPROVAL) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only pending problems can be rejected");
        }

        problem.setRejectionReason(reason);
        problem.setRejectedAt(LocalDateTime.now());
        problem.setStatus(ProblemStatus.REJECTED);

        problem = problemRepository.save(problem);
        return problemMapper.toDTO(problem);
    }

    @Transactional
    public ProblemDTO resolve(Long id) {
        Problem problem = getProblemEntity(id);
        
        if (problem.getStatus() != ProblemStatus.APPROVED) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only approved problems can be resolved");
        }

        problem.setResolvedAt(LocalDateTime.now());
        problem.setStatus(ProblemStatus.RESOLVED);

        problem = problemRepository.save(problem);
        return problemMapper.toDTO(problem);
    }

    public List<ProblemDTO> findByCategoryId(Long categoryId) {
        return problemRepository.findByCategoryId(categoryId)
            .stream().map(problemMapper::toDTO).collect(Collectors.toList());
    }

    public List<ProblemDTO> findByApprovedByGovId(Long govId) {
        return problemRepository.findByApprovedByGovId(govId)
            .stream().map(problemMapper::toDTO).collect(Collectors.toList());
    }

    @Transactional
    public ProblemDTO updateRealFields(Long id, ProblemDTO dto) {
        Problem problem = getProblemEntity(id);
        if (dto.getIsReal() != null) problem.setIsReal(dto.getIsReal());
        if (dto.getForContribution() != null) problem.setForContribution(dto.getForContribution());
        if (dto.getForDonation() != null) problem.setForDonation(dto.getForDonation());
        problem = problemRepository.save(problem);
        return problemMapper.toDTO(problem);
    }

    @Transactional
    public ProblemDTO updateDetails(Long id, ProblemDTO dto) {
        Problem problem = getProblemEntity(id);
        if (dto.getTitle() != null) problem.setTitle(dto.getTitle());
        if (dto.getDescription() != null) problem.setDescription(dto.getDescription());
        problem = problemRepository.save(problem);
        return problemMapper.toDTO(problem);
    }
}
