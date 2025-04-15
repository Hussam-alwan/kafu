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

        // Verify that the address exists
        addressService.findById(problemDTO.getAddressId());

        // Verify that the submitting user exists
        userService.findById(problemDTO.getSubmittedById());

        // Verify that the approving gov exists if provided
        if (problemDTO.getApprovedById() != null) {
            govService.findById(problemDTO.getApprovedById());
        }

        problemDTO.setSubmissionDate(LocalDateTime.now());
        
        Problem problem = problemMapper.toEntity(problemDTO);
        problem = problemRepository.save(problem);
        return problemMapper.toDTO(problem);
    }

    @Transactional
    public ProblemDTO update(Long id, ProblemDTO problemDTO) {
        Problem problem = problemRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Problem not found"));

        // Verify that the address exists if it's being updated
        if (problemDTO.getAddressId() != null) {
            addressService.findById(problemDTO.getAddressId());
        }

        // Verify that the submitting user exists if it's being updated
        if (problemDTO.getSubmittedById() != null) {
            userService.findById(problemDTO.getSubmittedById());
        }

        // Verify that the approving gov exists if it's being updated
        if (problemDTO.getApprovedById() != null) {
            govService.findById(problemDTO.getApprovedById());
        }

        problemMapper.updateEntity(problem, problemDTO);
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
}
