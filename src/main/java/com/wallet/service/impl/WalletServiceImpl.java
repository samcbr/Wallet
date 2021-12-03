package com.wallet.service.impl;

import java.util.*;
import com.wallet.dao.CustomerDao;
import com.wallet.dao.TransactionDao;
import com.wallet.exception.InsufficientBalanceException;
import com.wallet.exception.InvalidPaymentException;
import com.wallet.exception.UserIdDoesNotExistException;
import com.wallet.model.Customer;
import com.wallet.model.Transaction;
import com.wallet.model.dto.PaymentDto;
import com.wallet.model.dto.TransactionDto;
import com.wallet.payload.WalletResponsePayload;
import com.wallet.service.WalletService;
import com.wallet.util.JWTUtil;
import com.wallet.util.WalletLogger;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class WalletServiceImpl implements WalletService {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    CustomerDao customerDao;

    @Autowired
    TransactionDao transactionDao;

    @Autowired
    JWTUtil jwtUtil;

    @Transactional
    @Override
    public ResponseEntity<WalletResponsePayload> makePayment(PaymentDto paymentDto,String jwt) {

        WalletLogger.getLogger().debug("Entered service for make payment");

        Transaction transaction = modelMapper.map(paymentDto,Transaction.class);

        String sentBy = this.extractEmailId(jwt);

        transaction.setSentBy(sentBy);

        String sentTo = paymentDto.getSentTo();

        Customer sender = customerDao.findById(sentBy).orElseThrow(UserIdDoesNotExistException::new);

        Customer receiver = customerDao.findById(sentTo).orElseThrow(UserIdDoesNotExistException::new);

        if(sender.getAmount()>=paymentDto.getAmount()){

            if(sender.getEmailId().equals(receiver.getEmailId())) {
                throw new InvalidPaymentException();
            }

            sender.setAmount(sender.getAmount()-paymentDto.getAmount());
            customerDao.save(sender);
            receiver.setAmount(receiver.getAmount()+paymentDto.getAmount());
            customerDao.save(receiver);
            transaction = transactionDao.save(transaction);
        }
        else{
            throw new InsufficientBalanceException();
        }

        TransactionDto transactionDto = modelMapper.map(transaction,TransactionDto.class);

        return ResponseEntity.ok(new WalletResponsePayload(WalletResponsePayload.RESPONSE_STATUS.SUCCESS,transactionDto,"Payment successfull"));
    }

    @Transactional
    @Override
    public ResponseEntity<WalletResponsePayload> addMoney(double amount,String jwt) {

        WalletLogger.getLogger().debug("Entered service for add money");

        //fetch user ID from auth Token
        String emailId = this.extractEmailId(jwt);


        //make changes in customer wallet
        Customer customer = customerDao.findById(emailId).get();
        customer.setAmount(customer.getAmount()+amount);
        customerDao.save(customer);

        //add transaction with sentTo and sentBy set as user ID
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setSentBy(emailId);
        transaction.setSentTo(emailId);
        transactionDao.save(transaction);

        return ResponseEntity.ok(new WalletResponsePayload(WalletResponsePayload.RESPONSE_STATUS.SUCCESS,null,"Money added successfully"));
    }
    public String extractEmailId(String jwt){
        return jwtUtil.extractUsername(jwt);
    }

    @Override
    public ResponseEntity<WalletResponsePayload> getWalletTransactions(String jwt) {

        WalletLogger.getLogger().debug("Entered service for get wallet transactions");

        //fetch user ID from auth Token
        String emailId = this.extractEmailId(jwt);

        List<Transaction> transactions = transactionDao.findTransactions(emailId);

        return ResponseEntity.ok(new WalletResponsePayload(WalletResponsePayload.RESPONSE_STATUS.SUCCESS,transactions,"Successfully fetched transactions from emailID "+emailId));
    }
}
