package com.notifications.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    private Integer serialNumber;
    private String accountHolderName;
    private String addressLine1;
    private String city;
    private int zipcode;
    private String county;
    private String accountNumber;
    private int accountBalance;
    private String emailId;
    private boolean mailSentStatus;

}
