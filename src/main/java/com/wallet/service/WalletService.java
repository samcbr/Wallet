package com.wallet.service;

import com.wallet.model.dto.PaymentDto;
import com.wallet.payload.WalletResponsePayload;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


public interface WalletService {

    public ResponseEntity<WalletResponsePayload> makePayment(PaymentDto paymentDto,String jwt);

    public ResponseEntity<WalletResponsePayload> addMoney(double amount,String jwt);

    public ResponseEntity<WalletResponsePayload> getWalletTransactions(String jwt);
}
