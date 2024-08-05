package Service;

import DAO.AccountDAO;

import Model.Account;

public class AccountService {

    private AccountDAO accountDAO;

    public AccountService() {
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    /*
     * 1. The body will contain a representation of a JSON Account - will not
     * contain an account_id.
     * The registration will be successful if and only if the username is not blank
     * the password is at least 4 characters long
     * Account with that username does not already exist
     * If all these conditions are met, the response body should contain a JSON of
     * the Account, including its account_id.
     * The response status should be 200 OK
     * If the registration is not successful, the response status should be 400.
     * (Client error)
     */

    public Account addAccount(Account account) {

        if ((account.getUsername() != " ") && (account.getPassword().length() >= 4)
                && (account.getUsername() == null)) {
            return accountDAO.insertAccount(account);
        } else {
            return null;
        }
    }

    /*
     * 2: Our API should be able to process User logins.
     * User should be able to verify my login on the endpoint POST
     * localhost:8080/login
     * The request body will contain a JSON representation of an Account, not
     * containing an account_id.
     * - The login will be successful if and only if the username and password
     * provided in the request body JSON match a real account existing on the
     * database
     * If successful, the response body should contain a JSON of the account in the
     * response body, including its account_id.
     * The response status should be 200 OK, which is the default
     * If the login is not successful, the response status should be 401.
     * (Unauthorized)
     * 
     */

    public Account logIntoAccount(String username, String password) {

        if (this.accountDAO.getAccountByUsernameAndPassword(username, password) != null) {

            return accountDAO.getAccountByUsernameAndPassword(username, password);

        }

        else {
            return null;
        }
    }

    /*
     * public Account getUserAccountById(int id){
     * return accountDAO.getAccountById(id);
     * }
     * 
     */

    /*
     * public List<Account> getAllAccounts() {
     * return accountDAO.getAllAccounts();
     * }
     * 
     */

}