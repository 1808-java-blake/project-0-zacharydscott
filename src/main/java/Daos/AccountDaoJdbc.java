package Daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Util.DBConnectionUtil;
import beans.Account;
import beans.User;

public class AccountDaoJdbc implements AccountDao{
	private DBConnectionUtil dBCU = DBConnectionUtil.dBCU;
	public static final AccountDaoJdbc adj = new AccountDaoJdbc();      

	@Override
	public int logNewAccount(Account a) {
		try{ 
			Connection conn = dBCU.getConnection();
			PreparedStatement ps = conn.prepareStatement(
					"INSERT INTO accounts (balance, type) VALUES (?,?)",Statement.RETURN_GENERATED_KEYS);
		
			ps.setDouble(1, a.getBalance());
			ps.setString(2, a.getAccountType());
			int rows = ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
		    if (rs.next()) {
		        a.setAccountNumber(rs.getInt("account_id"));
		         }
		    conn.close();
		} catch (SQLException e) {
			System.out.println("Account Couldn't be added.");
		}
		return 0;
	}
	@Override
	public void connectUserAccount(User u, Account a) {
		try{
			Connection conn = dBCU.getConnection();
			PreparedStatement ps = conn.prepareStatement(
				"INSERT INTO user_account_junction (username,account_id) "
				+ "VALUES (?,?);");
			ps.setString(1, u.getUsername());
			ps.setInt(2, a.getAccountNumber());
			int result = ps.executeUpdate();
			conn.close();
		} catch (SQLException e) {
			System.out.println("something went wrong");
		}
	}
	@Override
	public void deleteAccount(Account a) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Account findAccount(int accountNumber) {
		try{ 
			Connection conn = dBCU.getConnection();
			PreparedStatement ps = conn.prepareStatement(
					"SELECT * FROM accounts "
					+ "WHERE account_id = ?");
			ps.setInt(1, accountNumber);
			ResultSet rs = ps.executeQuery();
			int accNum;
			Long balance;
			String type;
			while (rs.next()) {
				accNum = rs.getInt("account_id");
				balance = rs.getLong("balance");
				type = rs.getString("type");
				Account foundAccount = new Account(accNum, balance, type);
				conn.close();
				return foundAccount;
				}
		} catch (SQLException e) {
			System.out.println("Account Couldn't be added.");
		}
		
		return null;
	}

	@Override
	public void updateAccount(Account a) {
		try{ 
			Connection conn = dBCU.getConnection();
			PreparedStatement ps = conn.prepareStatement(
					"UPDATE accounts "
					+ "SET account_id =?, balance = ?,type = ? "
					+ "WHERE account_id = ?;");
			ps.setInt(1, a.getAccountNumber());
			ps.setDouble(2, a.getBalance());
			ps.setString(3, a.getAccountType());
			ps.setInt(4, a.getAccountNumber());
			int recordCreated = ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Account Couldn't be updated.");
		}

		
	}

	@Override
	public String accountList() {
		try {
			Connection conn = dBCU.getConnection();
			StringBuilder userList = new StringBuilder();
			userList.append("List of accounts on file: \n");
			PreparedStatement ps = conn.prepareStatement("SELECT account_id, balance FROM accounts");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				userList.append("Account ID: " + rs.getInt("account_id") + " Balance: " + rs.getLong("balance"));
				userList.append("\n");
			}
			conn.close();
			return userList.toString();
		} catch (SQLException e) {
				System.out.println("Account List failed to generate.");
		}
	return null;	
	}
	@Override
	public String getUserAccounts(User u) {
		try {
			Connection conn = dBCU.getConnection();
			StringBuilder userList = new StringBuilder();
			userList.append("List of accounts on file for "+u.getUsername()+": \n");
			PreparedStatement ps = conn.prepareStatement("select account_id, balance from accounts " + 
					"where account_id in (select account_id from user_account_junction " + 
					"where username = ?)");
			ps.setString(1, u.getUsername());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				userList.append("Account ID: " + rs.getInt("account_id") + " Balance: " + rs.getLong("balance"));
				userList.append("\n");
				u.addAccount(rs.getInt("account_id"));
			}
			conn.close();
			return userList.toString();
		} catch (SQLException e) {
				System.out.println("Account List failed to generate.");
		}
		return null;
	}
	@Override
	public String getTransactions(Account a) {
		try {
			Connection conn = dBCU.getConnection();
		PreparedStatement ps = conn.prepareStatement("SELECT transaction_num, transaction_amount from transaction_history " + 
				"WHERE account_id = ?  ORDER BY transaction_num ASC;");
		ps.setInt(1, a.getAccountNumber());
		ResultSet rs = ps.executeQuery();
		StringBuilder transactionList = new StringBuilder();
		transactionList.append("Current Balance: " + a.getBalance()+ "\n" +
		"Transaction History:\n");
		Long entry;
		while(rs.next()) {
			entry = rs.getLong("transaction_amount");
			if (entry >= 0 ) {
				transactionList.append("  "+ rs.getInt("transaction_num") + ": Deposited " + String.valueOf(entry) + "\n");
			}
			else {
				transactionList.append("  " + rs.getInt("transaction_num") + ": Withrdrew " + String.valueOf(-entry) + "\n");
			}
		}
		conn.close();
		return transactionList.toString();
		} catch(SQLException e) {
			System.out.println("couldn't get it.");
			return "Failed to generate";
		}
	}		

}


