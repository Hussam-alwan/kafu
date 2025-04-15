package com.kafu.kafu.gov;

import com.kafu.kafu.address.AddressService;
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

        if (govDTO.getAddressId() != null) {
            // Verify address exists
            addressService.findById(govDTO.getAddressId());
        }

        Gov gov = govMapper.toEntity(govDTO);
        gov = govRepository.save(gov);
        return govMapper.toDTO(gov);
    }

    @Transactional
    public GovDTO update(Long id, GovDTO govDTO) {
        Gov gov = govRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Gov not found"));

        if (!gov.getEmail().equals(govDTO.getEmail()) && 
            govRepository.existsByEmail(govDTO.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
        }

        if (govDTO.getAddressId() != null) {
            // Verify address exists
            addressService.findById(govDTO.getAddressId());
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
}
