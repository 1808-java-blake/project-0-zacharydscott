package Daos;
import beans.Account;

public interface AccountDao {
		public static final AccountDao cAccountDao = AccountSerializer.us;
		
		void logNewAccount(Account a);
		void deleteAccount(Account a);
		Account findAccount(int accountNUmber);
		void updateAccount(Account a);
		int getAccountNumber();
		void incrementAccountNumber();
		String accountList();
	
}
