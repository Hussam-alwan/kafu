package com.kafu.kafu.gov;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/govs")
@RequiredArgsConstructor
public class GovController {
    private final GovService govService;

    @GetMapping
    public ResponseEntity<List<GovDTO>> findAll() {
        return ResponseEntity.ok(govService.findAll().stream().map(GovMapper::toDTO).toList());
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
