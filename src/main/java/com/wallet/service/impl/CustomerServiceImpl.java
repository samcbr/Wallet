package com.wallet.service.impl;

import com.wallet.dao.CustomerDao;
import com.wallet.exception.InvalidPasswordException;
import com.wallet.exception.PasswordDoesNotMatchException;
import com.wallet.exception.UserIdDoesNotExistException;
import com.wallet.exception.UserNameAlreadyExistsException;
import com.wallet.model.Customer;
import com.wallet.model.dto.*;
import com.wallet.payload.WalletResponsePayload;
import com.wallet.service.CustomerService;
import com.wallet.util.JWTUtil;
import com.wallet.util.WalletLogger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerDao customerDao;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    WalletUserDetailsService walletUserDetailsService;

    @Autowired
    JWTUtil jwtUtil;

    @Override
    public ResponseEntity<WalletResponsePayload> signUp(SignUpDto signUpDto) {

        WalletLogger.getLogger().debug("Entered service for sign up");

        Customer customer = modelMapper.map(signUpDto, Customer.class);
        customer.setAmount(0.0);
        customer.setAuthToken("");

        if (!customerDao.existsById(customer.getEmailId()))
            customerDao.save(customer);
        else
            throw new UserNameAlreadyExistsException();

        return ResponseEntity.status(HttpStatus.CREATED).body(new WalletResponsePayload(WalletResponsePayload.RESPONSE_STATUS.SUCCESS, signUpDto, "Account creation successful"));
    }

    @Override
    public ResponseEntity<WalletResponsePayload> logIn(LoginRequestDto loginRequestDto) {

        WalletLogger.getLogger().debug("Entered service for log in");

        //Handle BadCredentialsException
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDto.getEmailId(), loginRequestDto.getPassword()));

        //Handle UserNameNotFoundException(TBD)
        UserDetails userDetails = walletUserDetailsService.loadUserByUsername(loginRequestDto.getEmailId());

        String authToken = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new WalletResponsePayload(WalletResponsePayload.RESPONSE_STATUS.SUCCESS, authToken, "Login Successful"));
    }

    @Override
    public ResponseEntity<WalletResponsePayload> updateProfile(UpdateProfileDto updateProfileDto,String jwt) {

        WalletLogger.getLogger().debug("Entered service for update profile");

        //Extract email Id from auth Token
        String emailId = jwtUtil.extractUsername(jwt);

        Customer customer = customerDao.findById(emailId).orElseThrow(UserIdDoesNotExistException::new);

        customer.setFirstName(updateProfileDto.getFirstName());
        customer.setLastName(updateProfileDto.getLastName());
        customerDao.save(customer);

        return ResponseEntity.ok(new WalletResponsePayload(WalletResponsePayload.RESPONSE_STATUS.SUCCESS,null,"Profile Updated successfully"));
    }

    @Override
    public ResponseEntity<WalletResponsePayload> changePassword(ChangePasswordDto changePasswordDto, String jwt) {

        WalletLogger.getLogger().debug("Entered service for change password");

        //Extract email Id from auth Token
        String emailId = jwtUtil.extractUsername(jwt);

        Customer customer = customerDao.findById(emailId).orElseThrow(UserIdDoesNotExistException::new);

        if(!changePasswordDto.getOldPassword().equals(customer.getPassword())){
            throw new InvalidPasswordException();
        }

        if(!changePasswordDto.getPassword().equals(changePasswordDto.getConfirmPassword())){
            throw new PasswordDoesNotMatchException();
        }

        customer.setPassword(changePasswordDto.getPassword());
        customerDao.save(customer);

        return ResponseEntity.ok(new WalletResponsePayload(WalletResponsePayload.RESPONSE_STATUS.SUCCESS,null,"Password updated successfully"));
    }

    @Override
    public ResponseEntity<WalletResponsePayload> viewProfile(String jwt) {

        WalletLogger.getLogger().debug("Entered service for view profile");

        //Extract email Id from auth Token
        String emailId = jwtUtil.extractUsername(jwt);

        Customer customer = customerDao.findById(emailId).orElseThrow(UserIdDoesNotExistException::new);

        ViewProfileDto viewProfileDto = modelMapper.map(customer,ViewProfileDto.class);

        return ResponseEntity.ok(new WalletResponsePayload(WalletResponsePayload.RESPONSE_STATUS.SUCCESS,viewProfileDto,"Successfully fetched user profile"));
    }

}
