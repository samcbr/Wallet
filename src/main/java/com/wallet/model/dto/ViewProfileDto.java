package com.wallet.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewProfileDto {

    private String firstName;

    private String lastName;

    private String emailId;
}
