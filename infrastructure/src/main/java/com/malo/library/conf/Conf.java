package com.malo.library.conf;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.jta.JtaTransactionManager;


public class Conf {

    PlatformTransactionManager platformTransactionManager(EntityManagerFactory emf){
        return new JpaTransactionManager(emf);
    }
}
