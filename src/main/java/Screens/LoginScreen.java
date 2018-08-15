package Screens;

import java.util.Scanner;

import Daos.UserDao;
import beans.User;

public class LoginScreen implements Screen {
	private Scanner scan = new Scanner(System.in);
	private UserDao ud = UserDao.cUserDao;
	private String output;
	
	public LoginScreen(String output) {
		this.output = output;
	}
	public Screen start() {
		System.out.println(Screen.header);
		System.out.println(output);
		System.out.println("Welcome: type \"L\" for Login, and \"R\" for registration.");
		String s = scan.nextLine();
		switch (s) {
			case "L":
				System.out.println("Username:");
				String un = scan.nextLine();
				System.out.println("Password:");
				String pw = scan.nextLine();
				User cUser = ud.findUser(un,pw);
				if (!(cUser == null)) {
					
					return new HomeScreen(cUser,"Welcome Back " + cUser.getName() +". What do you want to do?");
				} else {
					output = "Incorrect Login Info, Try Again.";
					return new LoginScreen(output);						
					}
			case "R":
				System.out.println("Going to reigstration...");
				return new RegistrationScreen("");
			default:
				return new LoginScreen("");
		}
	}
}