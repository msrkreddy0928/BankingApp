package com.example.BankingAppdemo;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private String custName;
    private String custEmail;
    private String custPhoneNo;
    private String custKycNo;

}


