package com.kafu.kafu.address;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;

    public Page<AddressDTO> findAll(Pageable pageable) {
        return addressRepository.findAll(pageable)
                .map(addressMapper::toDTO);
    }

    public AddressDTO findById(Long id) {
        return addressRepository.findById(id)
                .map(addressMapper::toDTO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Address not found"));
    }

    public Address getAddressEntity(Long id) {
        return addressRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Address not found"));
    }

    @Transactional
    public AddressDTO create(AddressDTO addressDTO) {
        if (addressDTO.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A new address cannot already have an ID");
        }

        Address address = addressMapper.toEntity(addressDTO);
        address = addressRepository.save(address);
        return addressMapper.toDTO(address);
    }

    @Transactional
    public AddressDTO update(Long id, AddressDTO addressDTO) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Address not found"));

        addressMapper.updateEntity(address, addressDTO);
        address = addressRepository.save(address);
        return addressMapper.toDTO(address);
    }

    @Transactional
    public void delete(Long id) {
        addressRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Address not found"));
        
        try {
            addressRepository.deleteById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Cannot delete address as it is being referenced by other entities");
        }
    }
}
