package com.wallet.controller;

import com.wallet.WalletContext;
import com.wallet.model.dto.*;
import com.wallet.payload.WalletResponsePayload;
import com.wallet.service.CustomerService;
import com.wallet.util.WalletLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @PostMapping("/wallet/1.0/api/signUp")
    public ResponseEntity<WalletResponsePayload> signUp(@Valid @RequestBody SignUpDto signUpDto){
        WalletLogger.getLogger().debug("Entered controller for sign up");
        return customerService.signUp(signUpDto);
    }

    @PostMapping("/wallet/1.0/api/logIn")
    public ResponseEntity<WalletResponsePayload> login(@Valid @RequestBody LoginRequestDto loginRequestDto){
        WalletLogger.getLogger().debug("Entered controller for login");
        return customerService.logIn(loginRequestDto);
    }

    @PutMapping("/wallet/1.0/api/updateProfile")
    public ResponseEntity<WalletResponsePayload> updateProfile(@Valid @RequestBody UpdateProfileDto updateProfileDto,@RequestHeader(value="Authorization") String jwt){
        WalletLogger.getLogger().debug("Entered controller for update profile");
        return customerService.updateProfile(updateProfileDto,jwt);
    }

    @PutMapping("/wallet/1.0/api/changePassword")
    public ResponseEntity<WalletResponsePayload> changePassword(@Valid @RequestBody ChangePasswordDto changePasswordDto, @RequestHeader(value="Authorization") String jwt){
        WalletLogger.getLogger().debug("Entered controller for change password");
        return customerService.changePassword(changePasswordDto,jwt);
    }

    @GetMapping("/wallet/1.0/api/viewProfile")
    public ResponseEntity<WalletResponsePayload> viewProfile(@RequestHeader(value="Authorization") String jwt){
        WalletLogger.getLogger().debug("Entered controller for view profile");
        return customerService.viewProfile(jwt);
    }
}
