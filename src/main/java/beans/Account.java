package beans;

import java.io.Serializable;
import java.util.ArrayList;

import Daos.AccountDao;
/**Accounts call on the current account number to generate unique account numbers.
 * Withdraw checks that amount is less than balance, so balance cannot be negative.
 * 
 *
 */
public class Account implements Serializable{
	private int accountNumber;
	private String accountType;
	private String[] accountHolders;
	private long balance;
	private ArrayList<Long> transactions;
	private static AccountDao cAccountDao = AccountDao.cAccountDao;
	
	public Account(int accountNumber,  long balance, String accountType) {
		super();
		this.accountType = accountType;
		this.accountHolders = new String[1];
		this.balance = balance;
		this.transactions = new ArrayList<Long>();
		deposit(balance);
		this.accountNumber = accountNumber;
	}
	
	public Account(long balance, String accountType) {
		super();
		this.accountType = accountType;
		this.accountHolders = new String[1];
		this.balance = balance;
		this.transactions = new ArrayList<Long>();
		deposit(balance);
	}
	
	public Account(String accountType, String[] accountHolders, long balance) {
		super();
		this.accountType = accountType;
		this.accountHolders = accountHolders;
		this.balance = 0L;
		this.transactions = new ArrayList<Long>();
		this.deposit(balance);
	}

	public String withdraw(Long amount) {
		if (amount < this.balance && amount >= 0) {
			this.balance -= amount;
			return null;
		} else {
			return "Failed to withdrawl. Check that withdrawl is less than balance.";
		}

	}
	public void deposit(Long amount) {
		if (amount >= 0) {
		this.balance += amount;
		}
	}

	public String getAccountHoldersList() {
		StringBuilder build = new StringBuilder();
		for (String s:this.accountHolders) {
			build.append(s);
		}
		return build.toString();
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String[] getAccountHolders() {
		String[] clone = new String[accountHolders.length];
		for (int i = 0; i <this.accountHolders.length; i++) {
			clone[i] = this.accountHolders[i];
		}
		return clone;
	}

	public void setAccountHolders(String[] accountHolders) {
		this.accountHolders = accountHolders;
	}

	public long getBalance() {
		return balance;
	}

	public int getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}

}
