package com.wallet.dao;

import com.wallet.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.*;

public interface TransactionDao extends JpaRepository<Transaction,Long> {

    @Query("select u from Transaction u where u.sentTo = ?1 or u.sentBy = ?1")
    List<Transaction> findTransactions(String emailId);

}
