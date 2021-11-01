package com.wallet.api;

import com.wallet.model.dto.PaymentDto;
import com.wallet.payload.WalletResponsePayload;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

public interface WalletControllerApi {

    @ApiOperation(value="Payment made to provided ID", response = WalletResponsePayload.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully made payment to given wallet",response = WalletResponsePayload.class),
            @ApiResponse(code = 400, message = "Account balance is insufficient to make payment",response = WalletResponsePayload.class)
    })
    @PostMapping("/wallet/1.0/api/makePayment")
    public ResponseEntity<WalletResponsePayload> makePayment(@RequestBody PaymentDto paymentDto,String jwt);

    @ApiOperation(value="Money added to wallet", response = WalletResponsePayload.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully added money to wallet",response = WalletResponsePayload.class),
            @ApiResponse(code = 400, message = "Unable to add money to wallet",response = WalletResponsePayload.class)
    })
    @PutMapping("/wallet/1.0/api/addMoney")
    public ResponseEntity<WalletResponsePayload> addMoney(@RequestBody Map<String,Double> amount, String jwt);

    @GetMapping("/wallet/1.0/api/getWalletTransactions")
    public ResponseEntity<WalletResponsePayload> getWalletTransactions(String jwt);


}
