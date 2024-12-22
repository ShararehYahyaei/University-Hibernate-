package org.example.entity;


import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Name {
    @NotNull(message = "first name must not be null")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    private String firstName;
    @NotNull(message = "Last name must not be null")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters")
    private String lastName;

}
