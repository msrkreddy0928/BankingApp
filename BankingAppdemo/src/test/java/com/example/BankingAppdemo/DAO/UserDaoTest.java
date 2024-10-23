package com.example.BankingAppdemo.DAO;

import com.example.BankingAppdemo.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Value;

import javax.xml.transform.Result;
import java.sql.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class UserDaoTest {


    private UserDao userDao;
    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;
    private ResultSet mockResultSet;


    @BeforeEach
    public void setUp() throws SQLException{
        String url = "jdbc:mysql://localhost:3306/mybank";
        String userName="root";
        String pass = "Reddy0928@#";
        userDao = new UserDao();
        mockConnection = mock(Connection.class);
        mockConnection = DriverManager.getConnection(url,userName,pass);
        mockPreparedStatement = mock(PreparedStatement.class);
        mockResultSet=mock(ResultSet.class);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

    }



    @Test
    public void testCreateUser() throws SQLException {

        User user = new User();

        user.setCustName("jack");
        user.setCustEmail("jack@gmail.com");
        user.setCustPhoneNo("9347592301");
        user.setCustKycNo("230674");
      String query = "insert into myaccount(CustName,CustEmail,CustPhoneNo,CustKycNo) values(?,?,?,?);";
      mockPreparedStatement = mockConnection.prepareStatement(query);
      mockPreparedStatement.setString(1,user.getCustName());
      mockPreparedStatement.setString(2,user.getCustEmail());
      mockPreparedStatement.setString(3,user.getCustPhoneNo());
      mockPreparedStatement.setString(4,user.getCustKycNo());
      when(mockPreparedStatement.executeUpdate()).thenReturn(1 );
      String result = userDao.createAccount(user);
      assertEquals("welcome john Doe your account is created with account number:230674",result);


    }



}


