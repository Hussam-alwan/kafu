package com.kafu.kafu.address;

import com.kafu.kafu.exception.ApplicationErrorEnum;
import com.kafu.kafu.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AddressService {
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;

    public Page<Address> findAll(Pageable pageable) {
        return addressRepository.findAll(pageable);
    }

    public Address findById(Long id) {
        return addressRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ApplicationErrorEnum.ADDRESS_NOT_FOUND));
    }

    @Transactional
    public Address create(AddressDTO addressDTO) {
        if (addressDTO.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A new address cannot already have an ID");
        }

        validateCity(addressDTO);

        Address address = AddressMapper.toEntity(addressDTO);
        address = addressRepository.save(address);
        return address;
    }

    @Transactional
    public Address update(Long id, AddressDTO addressDTO) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ApplicationErrorEnum.ADDRESS_NOT_FOUND));

        validateCity(addressDTO);

        AddressMapper.updateEntity(address, addressDTO);
        address = addressRepository.save(address);
        return address;
    }

    private static void validateCity(AddressDTO addressDTO) {
        if (addressDTO.getCity() == null || City.valueOf(addressDTO.getCity()) == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid city. Must be one of: " + java.util.Arrays.toString(City.values()));
        }
    }

    @Transactional
    public void delete(Long id) {
        addressRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ApplicationErrorEnum.ADDRESS_NOT_FOUND));
        
        try {
            addressRepository.deleteById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Cannot delete address as it is being referenced by other entities");
        }
    }

    public List<Map<String, String>> getCities() {
        List<Map<String, String>> cities = new ArrayList<>();
        for (City city : City.values()) {
            Map<String, String> cityMap = new HashMap<>();
            cityMap.put("english", city.getEnglishName());
            cityMap.put("arabic", city.getArabicName());
            cityMap.put("value", city.name());
            cities.add(cityMap);
        }
        return cities;
    }
}
