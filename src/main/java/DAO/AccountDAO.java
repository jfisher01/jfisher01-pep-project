package DAO;

import Util.ConnectionUtil;
import Model.Account;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class AccountDAO {

    
    public List<Account> getAllAccounts(){

        Connection connection = ConnectionUtil.getConnection();

        List<Account> account = new ArrayList<>();

        try {
            String sql = "SELECT * FROM account";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Account accounts = new Account(rs.getInt("account_id"), rs.getString("username"),
                        rs.getString("password"));
                account.add(accounts);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return account;
    }


  public Account getAccountByUsernameAndPassword(String username, String password){
        Connection connection = ConnectionUtil.getConnection();
        try {
    
            String sql = "SELECT * FROM account WHERE username = ? AND password = ?";
            
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //write preparedStatement's setString and setInt methods here.
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){

                Account account = new Account(rs.getInt("account_id"), rs.getString("username"),
                        rs.getString("password"));

                return account;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }


  public Account getAccountById(int id){
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "SELECT * FROM account WHERE account_id = ?";
            
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){

                Account account = new Account(rs.getInt("account_id"), rs.getString("username"),
                        rs.getString("password"));

                return account;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }


 public Account insertAccount(Account account){
        Connection connection = ConnectionUtil.getConnection();
        try {
     
            String sql = "INSERT INTO account(account_id, username, password) VALUES(?,?,?) " ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            //write preparedStatement's setString and setInt methods here.
            preparedStatement.setInt(1, account.getAccount_id());
            preparedStatement.setString(2, account.getUsername());
            preparedStatement.setString(3, account.getPassword());

            preparedStatement.executeUpdate();
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if(pkeyResultSet.next()){
                int generated_account_id = (int) pkeyResultSet.getLong(1);
                return new Account(generated_account_id, account.getUsername(), account.getPassword());
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

/* 
  public void updateAccount(int id, Account account){ 
        Connection connection = ConnectionUtil.getConnection();
        try {
            //Write SQL logic here
            String sql = "UPDATE account SET username = ?,password = ? WHERE account_id = ? ";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //write PreparedStatement setString and setInt methods here.
            
            preparedStatement.setString(1,account.getUsername());
            preparedStatement.setString(2,account.getPassword());
           preparedStatement.setInt(3, id );

            preparedStatement.executeUpdate();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
*/
}


