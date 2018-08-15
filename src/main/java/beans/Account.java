package beans;

import java.io.Serializable;
import java.util.ArrayList;

import Daos.AccountDao;

public class Account implements Serializable{
	private int accountNumber;
	private String accountType;
	private String[] accountHolders;
	private long balance;
	private ArrayList<Long> transactions;
	private static AccountDao cAccountDao = AccountDao.cAccountDao;
	
	public Account(String accountType, String[] accountHolders, long balance, ArrayList<Long> transactions) {
		super();
		this.accountType = accountType;
		this.accountHolders = accountHolders;
		this.balance = 0L;
		this.transactions = transactions;
		deposit(balance);
		this.accountNumber = cAccountDao.getAccountNumber();
		cAccountDao.incrementAccountNumber();
	}
	
	public Account(String accountType, String[] accountHolders, long balance) {
		super();
		this.accountType = accountType;
		this.accountHolders = accountHolders;
		this.balance = 0L;
		this.transactions = new ArrayList<Long>();
		this.deposit(balance);
		this.accountNumber = cAccountDao.getAccountNumber();
		cAccountDao.incrementAccountNumber();
	}

	public String withdraw(Long amount) {
		if (amount < this.balance) {
			this.balance -= amount;
			this.transactions.add(-amount);
			return null;
		} else {
			return "Failed to withdrawl. Check that withdrawl is less than balance.";
		}

	}

	public String getAccountHoldersList() {
		StringBuilder build = new StringBuilder();
		for (String s:this.accountHolders) {
			build.append(s);
		}
		return build.toString();
	}
	public void deposit(Long amount) {
		this.balance += amount;
		this.transactions.add(amount);
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

	public String getTransactions() {
		StringBuilder transactionList = new StringBuilder();
		transactionList.append("Initial Balance: " + String.valueOf(transactions.get(0))+"\n");
		transactionList.append("Current Balance: " + this.getBalance()+ "\n" +
		"Transaction History:\n");
		long entry;
		for (int i = 1; i< this.transactions.size(); i++) {
			entry = transactions.get(i);
			if (entry >= 0 ) {
				transactionList.append("  "+ i + ": Deposited " + String.valueOf(entry) + "\n");
			}
			else {
				transactionList.append("  " +i + ": Withrdrew " + String.valueOf(-entry) + "\n");
			}
		}
		return transactionList.toString();
	}		


	public int getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}

}
