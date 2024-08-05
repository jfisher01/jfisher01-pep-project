package DAO;

import Util.ConnectionUtil;
import Model.Account;
import java.sql.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class AccountDAO {

 
    /* 
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

*/

/**
 * 
 * @param userName
 * @param password
 * @return
 */
public Account getAccountByUsernameAndPassword(String userName, String password){
    Connection connection = ConnectionUtil.getConnection();
    try {
        String sql = "SELECT * FROM account WHERE username = ? AND password = ?";
        
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1, userName);
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

/**
 * 
 * @param account
 * @return
 */
public Account insertAccount(Account account){
    Connection connection = ConnectionUtil.getConnection();
    try {
 
        String sql = "INSERT INTO account(username, password) VALUES(?,?) " ;
        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        //write preparedStatement's setString and setInt methods here.
        //preparedStatement.setInt(1, account.getAccount_id());
        preparedStatement.setString(1, account.getUsername());
        preparedStatement.setString(2, account.getPassword());

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
}



