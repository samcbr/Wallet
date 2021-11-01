package com.wallet.service;

import com.wallet.model.dto.ChangePasswordDto;
import com.wallet.model.dto.LoginRequestDto;
import com.wallet.model.dto.SignUpDto;
import com.wallet.model.dto.UpdateProfileDto;
import com.wallet.payload.WalletResponsePayload;
import org.springframework.http.ResponseEntity;

public interface CustomerService {
    public ResponseEntity<WalletResponsePayload> signUp(SignUpDto signUpDto);

    public ResponseEntity<WalletResponsePayload> logIn(LoginRequestDto loginRequestDto);

    public ResponseEntity<WalletResponsePayload> updateProfile(UpdateProfileDto updateProfileDto,String jwt);

    public ResponseEntity<WalletResponsePayload> changePassword(ChangePasswordDto changePasswordDto, String jwt);

    public ResponseEntity<WalletResponsePayload> viewProfile(String jwt);
}
