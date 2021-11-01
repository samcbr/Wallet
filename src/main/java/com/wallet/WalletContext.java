package com.wallet;

import org.springframework.context.ConfigurableApplicationContext;

public class WalletContext {
    private static ConfigurableApplicationContext applicationContext;
    public static ConfigurableApplicationContext getApplicationContext(){
        return applicationContext;
    }
    public static void setApplicationContext(ConfigurableApplicationContext applicationContext1){
        applicationContext=applicationContext1;
    }
}
