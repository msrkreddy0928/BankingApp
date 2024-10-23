package com.example.BankingAppdemo.DAO;


import com.example.BankingAppdemo.Balance;
import com.example.BankingAppdemo.User;
import org.springframework.beans.factory.annotation.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.Random;

@Repository
public class UserDao {

    @Value("${db.url}")
    private String url;

    @Value("${db.userName}")
    private String userName;

    @Value("${db.pass}")
    private String password;


    private static final Logger LOGGER = LoggerFactory.getLogger(UserDao.class);
    private Connection con = null;


    public boolean isEmailExist(String email){

        String query = "SELECT COUNT(*) FROM myaccount WHERE custEmail = ?;";
        try{
            con = DriverManager.getConnection(url, userName, password);
             PreparedStatement pstmt = con.prepareStatement(query);
             pstmt.setString(1,email);
             ResultSet result = pstmt.executeQuery();


             while(result.next()){
                 int count = result.getInt(1);
                 if (count>0){
                     return false;
                 }

             }
             return true;
        }
        catch (SQLException e) {
            throw new RuntimeException(e);

        } finally {
            try {
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public boolean isPhoneNoExist(String phoneNo){

        String query = "SELECT COUNT(*) FROM myaccount WHERE custPhoneNo = ?;";
        try{
            con = DriverManager.getConnection(url, userName, password);
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1,phoneNo);
            ResultSet result = pstmt.executeQuery();


            while(result.next()){
                int count = result.getInt(1);
                if (count>0){
                    return false;
                }

            }
            return true;
        }
        catch (SQLException e) {
            throw new RuntimeException(e);

        } finally {
            try {
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }



    }

    public boolean isKycNoExist(String kycNo){

        String query = "SELECT COUNT(*) FROM myaccount WHERE custKycNo = ?;";
        try{
            con = DriverManager.getConnection(url, userName, password);
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1,kycNo);
            ResultSet result = pstmt.executeQuery();


            while(result.next()){
                int count = result.getInt(1);
                if (count>0){
                    return false;
                }

            }
            return true;
        }
        catch (SQLException e) {
            throw new RuntimeException(e);

        } finally {
            try {
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }



    }

    public String createAccount(User user) {


        if(user.getCustPhoneNo().length()!=10){
            return "Entered Phone number is not valid ! please check ";
        }


        if(!user.getCustEmail().substring(user.getCustEmail().length()-4).equalsIgnoreCase(".com") || user.getCustEmail().length()<6 || user.getCustEmail().indexOf('@')==-1 ){

            return "Entered email is not valid ! please check";
        }

        if(!isEmailExist(user.getCustEmail())){

            return "Account already exists with the given email :"+user.getCustEmail();

        }
        if(!isPhoneNoExist(user.getCustPhoneNo())){

            return "Account already exists with the given  phone number :"+user.getCustPhoneNo();

        }
        if(!isKycNoExist(user.getCustKycNo())){

            return "Account already exists with the given  kyc number :"+user.getCustKycNo();

        }




        try {

            con = DriverManager.getConnection(url, userName, password);
            LOGGER.info("Connection Created");

            String query = "INSERT INTO myaccount (custName, custEmail, custPhoneNo, custKycNo) VALUES ('"
                    + user.getCustName() + "', '"
                    + user.getCustEmail() + "', '"
                    + user.getCustPhoneNo() + "', '"
                    + user.getCustKycNo() + "');";

            Statement st = con.createStatement();

            st.executeUpdate(query);

            String query1 = "SELECT custId FROM myaccount WHERE custPhoneNo = ?";

            PreparedStatement pstmt = con.prepareStatement(query1);

            pstmt.setString(1, user.getCustPhoneNo());
            ResultSet result = pstmt.executeQuery();
            int custId = 0;

            if (result.next()) {
                custId = result.getInt("custId");

            } else {

                LOGGER.info("customer id is not created ");

            }

            String accountNum = String.valueOf( 28396+custId);


            String query2 = "INSERT INTO bankaccount(custId,accNum,balance,branch,ifscCode,accType,status) VALUES ('" + custId + "', '" + accountNum + "', 0, 'Madhapur', 'ICICI', 'Saving','active');";

            st.executeUpdate(query2);


            return (" Welcome " + user.getCustName() + " your account is created with account number: " + accountNum);


        } catch (SQLException e) {
            throw new RuntimeException(e);

        } finally {
            try {
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }


    }

    public String deposit(Balance balance) {

        try {

            con = DriverManager.getConnection(url, userName, password);
            LOGGER.info("Connection Created");

            String query = "SELECT balance FROM bankaccount WHERE accNum = ?";

            PreparedStatement pstmt = con.prepareStatement(query);

            pstmt.setString(1, balance.getAccountNum());
            ResultSet result = pstmt.executeQuery();

             double last_balance = 0;

            if (result.next()) {
                last_balance = result.getInt("balance");

            } else {


            }

            double updated_bal = last_balance + balance.getAmount();

            String query1 = "UPDATE bankaccount SET balance = ? WHERE accNum = ?;";

            PreparedStatement prst1 = con.prepareStatement(query1);

            prst1.setDouble(1, updated_bal);
            prst1.setString(2, balance.getAccountNum());

            prst1.executeUpdate();

            String query2 = "select balance from bankaccount where accNum = ?";

            PreparedStatement prst = con.prepareStatement(query2);
            prst.setString(1, balance.getAccountNum());

            ResultSet result1 = prst.executeQuery();

            int bal = 0;

            if (result1.next()) {
                bal = result1.getInt("balance");
                System.out.println("Account Balance: " + bal);
            } else {
                System.out.println("No account found with the given account number.");
            }


            return "your updated balance is : " + bal;


        } catch (SQLException e) {
            throw new RuntimeException(e);

        } finally {
            try {
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public String checkBalance(String accountNo) {


        try {

            con = DriverManager.getConnection(url, userName, password);
            LOGGER.info("Connection Created");

            String query1 = "select balance from bankaccount where accNum = ?";

            PreparedStatement prst = con.prepareStatement(query1);
            prst.setString(1, accountNo);

            ResultSet result1 = prst.executeQuery();

            int bal = 0;

            if (result1.next()) {
                bal = result1.getInt("balance");
                System.out.println("Account Balance: " + bal);
            } else {
                System.out.println("No account found with the given account number.");
            }

            return "your current balance : "+bal;


        } catch (SQLException e) {
            throw new RuntimeException(e);


        } finally {
            try {
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }




    }
}