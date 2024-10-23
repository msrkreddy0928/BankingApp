package com.example.BankingAppdemo.Controller;

import com.example.BankingAppdemo.Balance;
import com.example.BankingAppdemo.Service.UserService;

import com.example.BankingAppdemo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mybank")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/createAcc")
    public String createAccount(@RequestBody User user){

        return userService.createAccount(user);

    }

    @PostMapping("/deposit")
    public String deposit(@RequestBody Balance balance){

        return userService.deposit(balance);

    }

    @GetMapping("/checkbalance")
    public String checkBalance(@RequestParam("accountNo") String accountNo){
        return userService.checkBalance(accountNo);
    }


}

