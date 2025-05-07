package com.kafu.kafu.address;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/addresses")
@RequiredArgsConstructor
public class AddressController {
    private final AddressService addressService;

//    @GetMapping
    public ResponseEntity<Page<AddressDTO>> findAll(Pageable pageable) {
        return ResponseEntity.ok(addressService.findAll(pageable)
                .map(AddressMapper::toDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(AddressMapper.toDTO(addressService.findById(id)));
    }

    @PostMapping
    public ResponseEntity<AddressDTO> create(@Valid @RequestBody AddressDTO addressDTO) {
        Address created = addressService.create(addressDTO);
        return ResponseEntity
                .created(URI.create("/api/v1/addresses/" + created.getId()))
                .body(AddressMapper.toDTO(created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody AddressDTO addressDTO) {
        Address address = addressService.update(id, addressDTO);
        return ResponseEntity.ok(AddressMapper.toDTO(address));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        addressService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/cities")
    public ResponseEntity<List<Map<String, String>>> getCities() {
        return ResponseEntity.ok(addressService.getCities());
    }
}
