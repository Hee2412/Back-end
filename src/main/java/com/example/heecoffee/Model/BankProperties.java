package com.example.heecoffee.Model;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConfigurationProperties(prefix = "payment.bank")
@Data
public class BankProperties {
    private String bankCode;
    private String accountNo;
    private String accountName;
}
