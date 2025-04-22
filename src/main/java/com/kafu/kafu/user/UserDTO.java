package com.kafu.kafu.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.time.LocalDate;

@Data
public class UserDTO {
    private Long id;

    private String keycloakId;

    @NotBlank(message = "First name is required")
    @Size(max = 50, message = "First name cannot exceed 50 characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(max = 50, message = "Last name cannot exceed 50 characters")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Size(max = 100, message = "Email cannot exceed 100 characters")
    private String email;

    @NotBlank(message = "Phone is required")
    @Size(max = 20, message = "Phone cannot exceed 20 characters")
    private String phone;

    private LocalDate dateOfBirth;

    @Size(max = 100, message = "College degree cannot exceed 100 characters")
    private String collegeDegree;

    @Size(max = 50, message = "Occupation cannot exceed 50 characters")
    private String occupation;

    private String cvUrl;
    private String photoUrl;
    private String description;
    
    private Long addressId;
    private Long govId;
}
