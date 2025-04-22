package com.kafu.kafu.gov;

import com.kafu.kafu.address.Address;
import com.kafu.kafu.address.AddressService;
import com.kafu.kafu.user.User;
import com.kafu.kafu.user.UserRepository;
import com.kafu.kafu.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class GovService {
    private final GovRepository govRepository;
    private final AddressService addressService;
    private final UserService userService;

    public Page<Gov> findAll(Pageable pageable) {
        return govRepository.findAll(pageable);
    }

    public Gov findById(Long id) {
        return govRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Gov not found"));
    }

    @Transactional
    public Gov create(GovDTO govDTO) {
        if (govDTO.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A new gov cannot already have an ID");
        }

        if (govRepository.existsByEmail(govDTO.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
        }
        Gov gov = GovMapper.toEntity(govDTO);

        if (govDTO.getAddressId() != null) {
            var address = addressService.findById(govDTO.getAddressId());
            gov.setAddress(address);
        }

        // Set parent gov if provided
        if (govDTO.getParentGovId() != null) {
            var parentGov = findById(govDTO.getParentGovId());
            // Check if the parent is not a top parent(ministry)
            if (parentGov.getParentGov() != null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot set a parent gov that already has a parent,multi level hierarchy is not allowed");
            }
            gov.setParentGov(parentGov);
        }

        gov = govRepository.save(gov);
        return gov;
    }

    @Transactional
    public Gov update(Long id, GovDTO govDTO) {
        Gov gov = govRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Gov not found"));

        if (govDTO.getEmail() != null && !govDTO.getEmail().equals(gov.getEmail()) && 
            govRepository.existsByEmail(govDTO.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
        }

        // Update the address if provided
        if (govDTO.getAddressId() != null) {
            Address address = addressService.findById(govDTO.getAddressId());
            gov.setAddress(address);
        }

        // Update parent gov if provided
        if (govDTO.getParentGovId() != null) {
            var parentGov = findById(govDTO.getParentGovId());
            // Check if the parent is not a top parent(ministry)
            if (parentGov.getParentGov() != null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot set a parent gov that already has a parent,multi level hierarchy is not allowed");
            }
            gov.setParentGov(parentGov);
        }

        GovMapper.updateEntity(gov, govDTO);
        gov = govRepository.save(gov);
        return gov;
    }

    @Transactional
    public void delete(Long id) {
        govRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Gov not found"));
        
        try {
            govRepository.deleteById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Cannot delete gov as it is being referenced by other entities");
        }
    }
}
