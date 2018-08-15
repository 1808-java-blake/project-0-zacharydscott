package Screens;

import java.util.Scanner;

import Daos.AccountDao;
import Daos.UserDao;
import beans.Account;
import beans.User;

public class AccountRegistrationScreen implements Screen {
	private UserDao ud = UserDao.cUserDao;
	private AccountDao ad = AccountDao.cAccountDao;
	private Scanner scan = new Scanner(System.in);
	private User cUser;

	public AccountRegistrationScreen(User cUser) {
		this.cUser = cUser;
	}

	public Screen start() {
		System.out.println(Screen.header);
		System.out.println("Welcome to Account Registration. \n What kind of Account?");
		System.out.println("Type 1 for Checking, 2 for Savings, 3 for Joint Checking, 4 for Joint Savings");
		int numHolders;
		String accType;
		String accTypeSelection = scan.nextLine();
		String[] holders;
		switch (accTypeSelection) {
		case "1":
			numHolders = 1;
			accType = "Checking";
			break;
		case "2":
			numHolders = 1;
			accType = "Savings";
			break;
		case "3":
			numHolders = 2;
			accType = "Joint Checking";
			break;
		case "4":
			numHolders = 2;
			accType = "Joint Savings";
			break;
		default:
			System.out.println("Invalid selection. Please try again");
			return new AccountRegistrationScreen(cUser);

		}
		User secondUser = null;
		if (numHolders == 1) {
			holders = new String[1];
			holders[0] = cUser.getUsername();
		} else if (numHolders == 2) {
			holders = new String[2];
			holders[0] = cUser.getUsername();
			System.out.println("Type username of other account holder");
			holders[1] = scan.nextLine();
			System.out.println("Type this user's password for verification");
			String password = scan.nextLine();
			secondUser = ud.findUser(holders[1], password);
			if (secondUser == null) {
				holders = new String[0];
				System.out.println("Second User couldn't be accessed.");
				return new AccountRegistrationScreen(cUser);
				}
		}
		else {
			System.out.println("Something went wrong. Please try again.");
			
			return new AccountRegistrationScreen(cUser);
			}
		System.out.println("Enter initial balance");
		Long balance;
		try {
			balance = Long.valueOf(scan.nextLine());
		} catch (NumberFormatException e) {
			System.out.println("Initial Balance must be a pure number");
			return new AccountRegistrationScreen(cUser);
		}
		Account newAccount = new Account(accType, holders, balance);
		cUser.addAccount(newAccount);
		ad.logNewAccount(newAccount);
		ud.updateUser(cUser);
		if (secondUser != null) {
			secondUser.addAccount(newAccount);
			ud.updateUser(secondUser);
		}
		System.out.println("Succes. Acount created with a balance of " + newAccount.getBalance());
		return new HomeScreen(cUser,"Account Registered! Acount created with a balance of " 
		+ newAccount.getBalance() + ". What now?");
	}

}
