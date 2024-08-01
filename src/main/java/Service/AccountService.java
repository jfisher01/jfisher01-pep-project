package Service;
import DAO.AccountDAO;

import Model.Account;
import java.util.List;

public class AccountService {

    private AccountDAO accountDAO;


    public AccountService(){
        accountDAO = new AccountDAO();
    }
 
    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
}

    public List<Account> getAllAccounts() {
        return accountDAO.getAllAccounts();
    }

    public Account logIntoAccount(String username, String password){

      return accountDAO.getAccountByUsernameAndPassword(username, password);
}

 public Account getUserAccountById(int id){
     return accountDAO.getAccountById(id);
}


public Account addAccount(Account account) {
          
    return accountDAO.insertAccount(account);
   
}


/* /
  public Account updateAccountsInfo(int account_id, Account account){

           if(this.accountDAO.getAccountById(account_id) == null ){
            
            return null;
           }      
           else{ 
        
           this.accountDAO.updateAccount(account_id, account);
          return this.accountDAO.getAccountById(account_id);
           }
    }
*/
}

