package com.wallet.controller;

import com.wallet.api.WalletControllerApi;
import com.wallet.model.dto.PaymentDto;
import com.wallet.payload.WalletResponsePayload;
import com.wallet.service.WalletService;

import com.wallet.util.WalletLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;


import javax.validation.Valid;
import java.util.Map;

@CrossOrigin
@RestController
public class WalletController implements WalletControllerApi {

    @Autowired
    WalletService walletService;

    @Override
    public ResponseEntity<WalletResponsePayload> makePayment(@Valid PaymentDto paymentDto,@RequestHeader(value="Authorization") String jwt) {
        WalletLogger.getLogger().debug("Entered controller for make payment");
        return walletService.makePayment(paymentDto,jwt);
    }

    @Override
    public ResponseEntity<WalletResponsePayload> addMoney(Map<String,Double> amount, @RequestHeader(value="Authorization") String jwt) {
        WalletLogger.getLogger().debug("Entered controller for add money");
        return walletService.addMoney(amount.get("amount"),jwt);
    }

    @Override
    public ResponseEntity<WalletResponsePayload> getWalletTransactions(@RequestHeader(value="Authorization") String jwt) {
        WalletLogger.getLogger().debug("Entered controller for get wallet transaction");
        return walletService.getWalletTransactions(jwt);
    }


}
