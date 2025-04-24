package com.kafu.kafu.problem;

import com.kafu.kafu.address.Address;
import com.kafu.kafu.address.AddressService;
import com.kafu.kafu.problem.dto.ProblemDetailsDTO;
import com.kafu.kafu.problem.dto.ProblemDTO;
import com.kafu.kafu.problem.dto.ProblemRejectionDTO;
import com.kafu.kafu.problem.dto.ProblemSearchCriteria;
import com.kafu.kafu.problem.dto.RealFieldsDTO;
import com.kafu.kafu.problemcategory.ProblemCategory;
import com.kafu.kafu.problemcategory.ProblemCategoryService;
import com.kafu.kafu.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProblemService {
    private final ProblemRepository problemRepository;
    private final AddressService addressService;
    private final UserService userService;
    private final ProblemCategoryService problemCategoryService;

    public Page<Problem> search(ProblemSearchCriteria criteria) {
        Specification<Problem> spec = ProblemSpecification.withSearchCriteria(criteria);
        return problemRepository.findAll(spec, criteria.getPageable());
    }

    public Problem findById(Long id) {
        return problemRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Problem not found"));
    }

    @Transactional
    public Problem create(ProblemDTO problemDTO) {
        //set submitted uuser id here

        if (problemDTO.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A new problem cannot already have an ID");
        }

        // Verify that the submitting user exists
        var user =  userService.getCurrentUser();

        // Get the address if provided
        Address address = null;
        if (problemDTO.getAddressId() != null) {
            address = addressService.findById(problemDTO.getAddressId());
        }

        Problem problem = new Problem();
        problem.setTitle(problemDTO.getTitle());
        problem.setDescription(problemDTO.getDescription());
        problem.setSubmittedByUser(user);
        problem.setAddress(address);
        
        // Set default values
        problem.setIsReal(false);
        problem.setForContribution(false);
        problem.setForDonation(false);
        problem.setSubmissionDate(LocalDateTime.now());
        problem.setStatus(ProblemStatus.PENDING_APPROVAL);
        problem.setRejectionReason("");

        problem = problemRepository.save(problem);
        return problem;
    }

    @Transactional
    public Problem update(Long id, ProblemDTO problemDTO) {
        Problem problem = findById(id);

        // Verify that the submitting user exists if it's being updated
        if (problemDTO.getSubmittedByUserId() != null) {
            problem.setSubmittedByUser(userService.findById(problemDTO.getSubmittedByUserId()));
        }

        // Verify that the approving user exists if it's being updated
        if (problemDTO.getApprovedByUserId() != null) {
            problem.setApprovedByUser(userService.findById(problemDTO.getApprovedByUserId()));
        }

        // Update the address if provided
        if (problemDTO.getAddressId() != null) {
            problem.setAddress(addressService.findById(problemDTO.getAddressId()));
        }

        // Update the category if provided
        if (problemDTO.getCategoryId() != null) {
            problem.setCategory(problemCategoryService.findById(problemDTO.getAddressId()));
        }

        ProblemMapper.updateEntity(problem, problemDTO);

        problem = problemRepository.save(problem);
        return problem;
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
    public Problem approve(Long id) {
        Problem problem = findById(id);
        
        if (problem.getStatus() != ProblemStatus.PENDING_APPROVAL) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only pending problems can be approved");
        }

        problem.setApprovedByUser(userService.getCurrentUser());
        problem.setStatus(ProblemStatus.APPROVED);

        problem = problemRepository.save(problem);
        return problem;
    }

    @Transactional
    public Problem reject(Long id, ProblemRejectionDTO rejectionDTO) {
        Problem problem = findById(id);

        if(rejectionDTO.getIsReal())
        {
            throw new RuntimeException("is real field must be set to false");
        }

        if (problem.getStatus() != ProblemStatus.PENDING_APPROVAL) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only pending problems can be rejected");
        }

        problem.setIsReal(false);
        problem.setRejectionReason(rejectionDTO.getRejectionReason());
        problem.setStatus(ProblemStatus.REJECTED);

        problem.setApprovedByUser(userService.getCurrentUser());
        problem = problemRepository.save(problem);
        return problem;
    }

    @Transactional
    public Problem updateRealFields(Long id, RealFieldsDTO dto) {
        //set approved here
        Problem problem = findById(id);
        if (dto.getIsReal() != null) problem.setIsReal(dto.getIsReal());
        if (dto.getForContribution() != null) problem.setForContribution(dto.getForContribution());
        if (dto.getForDonation() != null) problem.setForDonation(dto.getForDonation());

        problem.setApprovedByUser(userService.getCurrentUser());

        problem = problemRepository.save(problem);
        return problem;
    }

    @Transactional
    public Problem updateDetails(Long id, ProblemDetailsDTO dto) {
        Problem problem = findById(id);
        if (dto.getTitle() != null) problem.setTitle(dto.getTitle());
        if (dto.getDescription() != null) problem.setDescription(dto.getDescription());
        if (dto.getAddressId() != null) problem.setAddress(addressService.findById(dto.getAddressId()));
        if (dto.getCategoryId() != null && problem.getStatus() == ProblemStatus.PENDING_APPROVAL) problem.setCategory(problemCategoryService.findById(dto.getCategoryId()));
        problem = problemRepository.save(problem);
        return problem;
    }
}
