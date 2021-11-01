package com.wallet.service.impl;

import com.wallet.dao.CustomerDao;
import com.wallet.model.Customer;
import com.wallet.util.WalletLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;


@Service
public class WalletUserDetailsService implements UserDetailsService {

    @Autowired
    CustomerDao customerDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        WalletLogger.getLogger().debug("Entered service for load user bt user name");

        Customer customer = customerDao.findById(username).orElseThrow(RuntimeException::new);

        return new User(customer.getEmailId(),customer.getPassword(),new ArrayList<>());
    }
}
