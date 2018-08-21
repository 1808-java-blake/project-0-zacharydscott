package Daos;
import beans.Account;
import beans.User;

public interface AccountDao {
		public static final AccountDao cAccountDao = AccountDaoJdbc.adj;
		
		int logNewAccount(Account a);
		void deleteAccount(Account a);
		Account findAccount(int accountNumber);
		void updateAccount(Account a);
		String accountList();
		void connectUserAccount(User u, Account a);
		String getUserAccounts(User u);
		String getTransactions(Account a);
	
}
