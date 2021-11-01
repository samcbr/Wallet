package com.wallet.util;

import com.wallet.WalletMain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WalletLogger {
    private static final Logger logger = LoggerFactory.getLogger(WalletMain.class);
    public static Logger getLogger(){
        return logger;
    }
}
