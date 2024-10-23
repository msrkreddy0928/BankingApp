package com.example.BankingAppdemo.Service;


import com.example.BankingAppdemo.Balance;
import com.example.BankingAppdemo.DAO.UserDao;
import com.example.BankingAppdemo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public String  createAccount(User user){

        return  userDao.createAccount(user);

    }

    public String deposit(Balance balance){

        return userDao.deposit(balance);
    }


     public String checkBalance(String accountNo){

        return userDao.checkBalance(accountNo);
     }


}
