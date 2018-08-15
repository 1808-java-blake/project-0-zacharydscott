package Daos;

import java.io.*;
import beans.User;

public class UserSerializer implements UserDao{
	public static final UserSerializer us = new UserSerializer();

	@Override
	public void logNewUser(User u) {
		if (u ==null) {
			System.out.println("Something went wrong, no user was passed");
			return;
		}
		File f = new File("src/main/resources/users/" + u.getUsername() +".txt");
		if (f.exists() && !f.canWrite()) {
			System.out.println("This username is already taken");
			return;
		}
		try {
			f.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try (ObjectOutputStream saver = new ObjectOutputStream( 
				new FileOutputStream("src/main/resources/users/" + u.getUsername()+".txt"))) {
						System.out.println(u.getClass());
				saver.writeObject(u);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e){
						e.printStackTrace();
					}
				{
					
				}
	}

	@Override
	public void deleteUser(User u) {
		File f = new File("src/main/resources/users/" + u.getUsername() +".txt");
		if (f.exists()) {
			f.delete();
		} else {
			System.out.println("couldn't delete.");
		}
	}
	
	@Override
	public void updateUser(User u) {
		File f = new File("src/main/resources/users/" + u.getUsername() +".txt");
		if (f.exists()) {
			f.setExecutable(true);
			f.setWritable(true);
			this.logNewUser(u);
			f.setWritable(false);
		} else {
				System.out.println("couldn't delete");
			}
	}
	@Override
	public String userList() {
		StringBuilder userList = new StringBuilder();
		userList.append("List of Users on file: \n");
		File userFolder = new File("src/main/resources/users");
		String[] folderContents = userFolder.list();
		for (String s:folderContents) {
			s= s.replaceAll(".txt", "");
			userList.append(s+"\n");
		}
		return userList.toString();
	}

	@Override
	public User findUser(String username, String password) {
		if (username == null && password  == null) {
			System.out.println("Username or Password is incorrect.");
			return null;
		}
		try (ObjectInputStream reader = new ObjectInputStream( 
				new FileInputStream("src/main/resources/users/" + username + ".txt"))) {
			User u = (User) reader.readObject();
			if (u.getPassword().equals(password)) {
				return u;
			}
			System.out.println("Password is incorrect");
			return null;
		} catch (FileNotFoundException e) {
			System.out.println("This username doesn't match the files.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}
		return null;
	}

	@Override
	public User findUser(String username, User AccessingAccount) {
		if (username == null) {
			System.out.println("Username is incorrect");
			return null;
		} else if (!AccessingAccount.isAdmin()){
			System.out.println("You are not an Administrator, access denied.");
			return null;
		}
		try (ObjectInputStream reader = new ObjectInputStream( 
				new FileInputStream("src/main/resources/users/" + username + ".txt"))) {
			User u = (User) reader.readObject();
			return u;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}
		return null;
	}
	
}
