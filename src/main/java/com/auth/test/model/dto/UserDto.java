package com.auth.test.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public final class UserDto {

   @NotNull
   private final String username;
   @NotNull
   private final String password;
   @Pattern(
           regexp = "^[A-Za-z0-9+_.-]+@([A-Za-z0-9.-]+\\.[A-Za-z]{2,})$",
           message = "Invalid email format")
   @NotNull
   private final String email;
   @NotNull
   private final String firstName;
   private final String lastName;
}
