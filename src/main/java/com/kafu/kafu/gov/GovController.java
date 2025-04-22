package com.kafu.kafu.gov;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.net.URI;

@RestController
@RequestMapping("/api/v1/govs")
@RequiredArgsConstructor
public class GovController {
    private final GovService govService;

    @GetMapping
    public ResponseEntity<Page<GovDTO>> findAll(Pageable pageable) {
        return ResponseEntity.ok(govService.findAll(pageable).map(GovMapper::toDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GovDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(GovMapper.toDTO(govService.findById(id)));
    }

    @PostMapping
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GovDTO> create(@Valid @RequestBody GovDTO govDTO) {
        Gov created = govService.create(govDTO);
        return ResponseEntity
                .created(URI.create("/api/v1/govs/" + created.getId()))
                .body(GovMapper.toDTO(created));
    }

    @PutMapping("/{id}")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<GovDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody GovDTO govDTO) {
        return ResponseEntity.ok(GovMapper.toDTO(govService.update(id, govDTO)));
    }

    @DeleteMapping("/{id}")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        govService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
