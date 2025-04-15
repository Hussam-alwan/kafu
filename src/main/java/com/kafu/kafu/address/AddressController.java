package com.kafu.kafu.address;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;

@RestController
@RequestMapping("/api/v1/addresses")
@RequiredArgsConstructor
public class AddressController {
    private final AddressService addressService;

    @GetMapping
    public ResponseEntity<Page<AddressDTO>> findAll(Pageable pageable) {
        return ResponseEntity.ok(addressService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(addressService.findById(id));
    }

    @PostMapping
    public ResponseEntity<AddressDTO> create(@Valid @RequestBody AddressDTO addressDTO) {
        AddressDTO created = addressService.create(addressDTO);
        return ResponseEntity
                .created(URI.create("/api/v1/addresses/" + created.getId()))
                .body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody AddressDTO addressDTO) {
        return ResponseEntity.ok(addressService.update(id, addressDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        addressService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
