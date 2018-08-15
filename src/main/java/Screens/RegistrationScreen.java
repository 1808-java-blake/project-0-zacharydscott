package Screens;

import java.util.Scanner;

import Daos.UserDao;
import beans.User;

public class RegistrationScreen implements Screen{
	private Scanner scan = new Scanner(System.in);
	private UserDao ud = UserDao.cUserDao;
	private String output;
	
	public RegistrationScreen(String output) {
		this.output = output;
	}
	
	public Screen start() {
		System.out.println(Screen.header);
		System.out.println(output);
		System.out.println("Welcome to registration:\n Enter Username:");
		String username = scan.nextLine();
		System.out.println("Set Password");
		String password= scan.nextLine();
		System.out.println("Verify Password");
		String passwordVerify = scan.nextLine();
		if (!(password.equals(passwordVerify))) {
			output = "Passwords didn't match";
			return new RegistrationScreen(output);
		}
		System.out.println("Enter Name");
		String name= scan.nextLine();
		System.out.println("Enter Age");
		int age;
		try {
			age = Integer.valueOf(scan.nextLine());
			} catch (NumberFormatException e) {
				output = "number required for age";
				return new RegistrationScreen(output);
			}
		System.out.println("Enter Mailing Address");
		String mailingAddress = scan.nextLine();
		User newUser = new User(username, name, password, age, mailingAddress);
		ud.logNewUser(newUser);
		return new LoginScreen("Registration Successful. Feel free to login now.");
	}
}
