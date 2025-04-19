package com.kafu.kafu.gov;

import com.kafu.kafu.address.AddressService;
import com.kafu.kafu.user.User;
import com.kafu.kafu.user.UserRepository;
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
    private final GovMapper govMapper;
    private final AddressService addressService;
    private final UserRepository userRepository;

    public Page<GovDTO> findAll(Pageable pageable) {
        return govRepository.findAll(pageable)
                .map(govMapper::toDTO);
    }

    public GovDTO findById(Long id) {
        return govRepository.findById(id)
                .map(govMapper::toDTO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Gov not found"));
    }

    public GovDTO findByEmail(String email) {
        return govRepository.findByEmail(email)
                .map(govMapper::toDTO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Gov not found"));
    }

    public Gov getGovEntity(Long id) {
        return govRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Gov not found"));
    }

    @Transactional
    public GovDTO create(GovDTO govDTO) {
        if (govDTO.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A new gov cannot already have an ID");
        }

        if (govRepository.existsByEmail(govDTO.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
        }

        // Save the address first if provided
        if (govDTO.getAddress() != null) {
            var savedAddress = addressService.create(govDTO.getAddress());
            govDTO.setAddress(savedAddress);
        }

        // Set parent gov if provided
        if (govDTO.getParentGovId() != null) {
            var parentGov = getGovEntity(govDTO.getParentGovId());
            // Check if the parent is not a top parent(ministry)
            if (parentGov.getParentGov() != null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot set a parent gov that already has a parent");
            }
            Gov gov = govMapper.toEntity(govDTO);
            gov.setParentGov(parentGov);
            gov = govRepository.save(gov);
            return govMapper.toDTO(gov);
        }

        Gov gov = govMapper.toEntity(govDTO);
        gov = govRepository.save(gov);
        return govMapper.toDTO(gov);
    }

    @Transactional
    public GovDTO update(Long id, GovDTO govDTO) {
        Gov gov = govRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Gov not found"));

        if (govDTO.getEmail() != null && !govDTO.getEmail().equals(gov.getEmail()) && 
            govRepository.existsByEmail(govDTO.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
        }

        // Update the address if provided
        if (govDTO.getAddress() != null) {
            var existingAddress = gov.getAddress();
            if (existingAddress != null) {
                // Update existing address
                govDTO.getAddress().setId(existingAddress.getId());
                var updatedAddress = addressService.update(existingAddress.getId(), govDTO.getAddress());
                govDTO.setAddress(updatedAddress);
            } else {
                // Create new address
                var savedAddress = addressService.create(govDTO.getAddress());
                govDTO.setAddress(savedAddress);
            }
        }

        // Update parent gov if provided
        if (govDTO.getParentGovId() != null) {
            var parentGov = getGovEntity(govDTO.getParentGovId());
            
            // Check if the parent already has a parent
            if (parentGov.getParentGov() != null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot set a parent gov that already has a parent");
            }

            gov.setParentGov(parentGov);
        }

        govMapper.updateEntity(gov, govDTO);
        gov = govRepository.save(gov);
        return govMapper.toDTO(gov);
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

    @Transactional
    public void associateUser(Long govId, Long userId) {
        Gov gov = getGovEntity(govId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        user.setGov(gov);
        userRepository.save(user);
    }
}
