package com.kafu.kafu.user;

import com.kafu.kafu.address.AddressService;
import com.kafu.kafu.gov.GovService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AddressService addressService;
    private final GovService govService;

    public Page<UserDTO> findAll(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(userMapper::toDTO);
    }

    public UserDTO findById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toDTO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    public UserDTO findByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userMapper::toDTO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    public User getUserEntity(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    @Transactional
    public UserDTO create(UserDTO userDTO) {
        if (userDTO.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A new user cannot already have an ID");
        }

        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
        }

        // Save the address first if provided
        if (userDTO.getAddress() != null) {
            var savedAddress = addressService.create(userDTO.getAddress());
            userDTO.setAddress(savedAddress);
        }

        User user = userMapper.toEntity(userDTO);

        // Set gov if provided
        if (userDTO.getGovId() != null) {
            user.setGov(govService.getGovEntity(userDTO.getGovId()));
        }

        user = userRepository.save(user);
        return userMapper.toDTO(user);
    }

    @Transactional
    public UserDTO update(Long id, UserDTO userDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        if (userDTO.getEmail() != null && !userDTO.getEmail().equals(user.getEmail()) && 
            userRepository.existsByEmail(userDTO.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
        }

        // Update the address if provided
        if (userDTO.getAddress() != null) {
            var existingAddress = user.getAddress();
            if (existingAddress != null) {
                // Update existing address
                userDTO.getAddress().setId(existingAddress.getId());
                var updatedAddress = addressService.update(existingAddress.getId(), userDTO.getAddress());
                userDTO.setAddress(updatedAddress);
            } else {
                // Create new address
                var savedAddress = addressService.create(userDTO.getAddress());
                userDTO.setAddress(savedAddress);
            }
        }

        userMapper.updateEntity(user, userDTO);

        // Update gov if provided
        if (userDTO.getGovId() != null) {
            user.setGov(govService.getGovEntity(userDTO.getGovId()));
        }

        user = userRepository.save(user);
        return userMapper.toDTO(user);
    }

    @Transactional
    public void delete(Long id) {
        userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        
        try {
            userRepository.deleteById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Cannot delete user as it is being referenced by other entities");
        }
    }
}
