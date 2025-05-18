package com.kafu.kafu.gov;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
        return ResponseEntity.ok(govService.findAll().stream().map(govService::replaceUrlsWithPresigned).map(GovMapper::toDTO).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GovDTO> findById(@PathVariable Long id) {
        Gov gov = govService.findById(id);
        gov = govService.replaceUrlsWithPresigned(gov);
        return ResponseEntity.ok(GovMapper.toDTO(gov));
    }

    @PostMapping
    public ResponseEntity<GovDTO> create(@Valid @RequestBody GovDTO govDTO) {
        Gov created = govService.create(govDTO);
        created = govService.replaceUrlsWithPresigned(created);
        return ResponseEntity
                .created(URI.create("/api/v1/govs/" + created.getId()))
                .body(GovMapper.toDTO(created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GovDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody GovDTO govDTO) {
        Gov gov = govService.update(id, govDTO);
        gov = govService.replaceUrlsWithPresigned(gov);
        return ResponseEntity.ok(GovMapper.toDTO(gov));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        govService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/logo")
    public ResponseEntity<String> uploadProfilePhoto(
            @PathVariable Long id,
            @RequestParam String contentType) {
        return ResponseEntity.ok(govService.uploadLogo(id, contentType));
    }

}
