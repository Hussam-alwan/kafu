package com.kafu.kafu.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


    @GetMapping
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<UserDTO>> findAll(Pageable pageable) {
        return ResponseEntity.ok(userService.findAllDtos(pageable));
    }

    @GetMapping("/{id}")
//    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    public ResponseEntity<UserDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.findDtoByIdOrError(id));
    }

    // this endpoint will not be needed as sign up will be handled by keycloack
    // and syncing users will be handled by a filter
//    @PostMapping
//    public ResponseEntity<UserDTO> create(@Valid @RequestBody UserDTO userDTO) {
//        User user = userService.create(userDTO);
//        return ResponseEntity.status(201).body(UserMapper.toDTO(user));
//    }

    @PutMapping("/{id}")
    //    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    public ResponseEntity<UserDTO> update(
            @PathVariable Long id,
            @Valid @RequestBody UserDTO userDTO) {
        User user = userService.update(id, userDTO);
        return ResponseEntity.ok(UserMapper.toDTO(user));
    }

    @DeleteMapping("/{id}")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{userId}/associate-user")
    public ResponseEntity<Void> associateUser(@RequestParam Long govId, @PathVariable Long userId) {
        userService.associateUser(govId, userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public ResponseEntity<UserDTO> getCurrentUser() {
        User user = userService.getCurrentUser();
        return ResponseEntity.ok(UserMapper.toDTO(user));
    }
}
