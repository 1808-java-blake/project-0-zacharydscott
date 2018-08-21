package Screens;

import java.util.ArrayList;
import java.util.Scanner;

import Daos.AccountDao;
import Daos.UserDao;
import beans.Account;
import beans.User;

public class AccountScreen implements Screen {
	private UserDao ud = UserDao.cUserDao;
	private AccountDao ad = AccountDao.cAccountDao;
	private Scanner scan = new Scanner(System.in);
	private User cUser;
	private Account cAccount;
	private String output;

	public AccountScreen(User u, Account acc,String output) {
		this.cUser = u;
		this.cAccount = acc;
		this.output = output;
	}

	public Screen start() {
		System.out.println(header);
		System.out.println(output);
		System.out.println("What do you want to do?");
		System.out.println("1: Display account Info \n2: withdraw \n3: deposit \n4: "
				+ "transfer funds \n5: Display transaction History \n6: Return to Home");
		int userSelection;
		try {
			userSelection = Integer.valueOf(scan.nextLine());
		} catch (NumberFormatException e) {
			System.out.println("Invalid selection");
			return new AccountScreen(cUser, cAccount,"");
		}
		switch (userSelection) {
		// Switch case used to translate user input into each function
		case 1:
			output = "Account number: " + cAccount.getAccountNumber() + ". Acount Type: " + cAccount.getAccountType()
					+ ". Current Balance:" + cAccount.getBalance();
			break;

		case 2:
			System.out.println("Withdrawl amount:");
			Long withdrawl;
			try {
				withdrawl = Long.valueOf(scan.nextLine());
				
			if (withdrawl > 0 && withdrawl < cAccount.getBalance()) {
				cAccount.withdraw(withdrawl);
				output = "Withdraw Successful. Remaining Balance is: " +cAccount.getBalance();
			} else {
				output = "Withdrawl Failed. Make sure amount is positive and less tha balance.";
			}
			}catch (NumberFormatException e) {
				output = "Illegal Entry: enter number";
			}
			
			break;

		case 3:
			System.out.println("Deposit amount: ");
			Long deposit;
			try {
				deposit = Long.valueOf(scan.nextLine());
			if (deposit > 0) {
				cAccount.deposit(deposit);
				output = "Deposit Successful. Remaining Balance is: " +cAccount.getBalance();
			} else {
				output = "Deposit Failed. Make sure amount is positive.";
			}
			}catch (NumberFormatException e) {
				output = "Illegal Entry: enter number";
			}
			break;
		case 4:
			System.out.println("Enter wiring account number");
			int wiringAccNum;
			try {
				wiringAccNum = Integer.valueOf(scan.nextLine());
			Account wiringAccount = ad.findAccount(wiringAccNum);
			if (wiringAccount == null) {
				output = "Wiring account not found. Try again.";
				return new AccountScreen(cUser, cAccount,"");
			}
			System.out.println("Enter wiring amount: ");
			Long wiringAmount = 0L;
			try {
			wiringAmount = Long.valueOf(scan.nextLine());
			} catch (NumberFormatException e) {
				output = "Illegal Entry: enter number";
			}
			if (wiringAmount <= 0) {
				output = "Invalid wiring amount";
				break;
			}
			cAccount.withdraw(wiringAmount);
			wiringAccount.deposit(wiringAmount);
			ad.updateAccount(wiringAccount);
			output = "Funds Transferred!";
			}catch (NumberFormatException e) {
				output = "Illegal Entry: enter number";
			}
			break;
		case 5:
			output = ad.getTransactions(cAccount);
			return new AccountScreen(cUser,cAccount, output);
		case 6:
			ad.updateAccount(cAccount);
			ud.updateUser(cUser);
			output = "Account Closed. Back to Home Screen";
			return new HomeScreen(cUser,output);
		default:
			output = "Invalid Selection";
			return new AccountScreen(cUser, cAccount,"");
			
		}
		ad.updateAccount(cAccount);
		ud.updateUser(cUser);
		return new AccountScreen(cUser, cAccount,output);
	}
}
