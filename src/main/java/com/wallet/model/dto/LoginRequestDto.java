package com.wallet.model.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class LoginRequestDto {
    @Email(message = "Invalid emailId")
    private String emailId;

    @NotBlank(message = "Password cannot be empty")
    private String password;
}
