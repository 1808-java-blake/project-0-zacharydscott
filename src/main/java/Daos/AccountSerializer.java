package Daos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Writer;
import java.util.Scanner;

import beans.Account;

public class AccountSerializer implements AccountDao{
	public static final AccountSerializer us = new AccountSerializer();

	@Override
	public void logNewAccount(Account a) {
			if (a ==null) {
				return;
			}
			File f = new File("src/main/resources/accounts/" + a.getAccountNumber() +".txt");
			if (f.exists() && !(f.canWrite())) {
				System.out.println("already exists");
				return;
			}
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try (ObjectOutputStream saver = new ObjectOutputStream( 
					new FileOutputStream("src/main/resources/accounts/" + a.getAccountNumber()+".txt"))) {
							System.out.println(a.getClass());
					saver.writeObject(a);
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						} catch (IOException e){
							e.printStackTrace();
						}
					{
						
					}
		}

	@Override
	public void deleteAccount(Account a) {
		if (a == null) {
			System.out.println("No account passed");
			return;
		}
		File f = new File("src/main/resources/accounts/"+ a.getAccountNumber() +".txt");
		f.delete();
	}

	@Override
	public Account findAccount(int accountNumber) {
		File f = new File("src/main/resources/accounts/" + accountNumber + ".txt");
		if(f.exists()) {
			try {ObjectInputStream reader = new ObjectInputStream(
				new FileInputStream("src/main/resources/accounts/" + accountNumber + ".txt"));
				Account acc = (Account) reader.readObject();
				return acc;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}	
		} 
		return null;
	}
	@Override
	public String accountList() {
		StringBuilder userList = new StringBuilder();
		userList.append("List of accounts on file: \n");
		File userFolder = new File("src/main/resources/accounts");
		String[] folderContents = userFolder.list();
		for (String s:folderContents) {
			s= s.replaceAll(".txt", "");
			userList.append(s+"\n");
		}
		return userList.toString();
	}

	@Override
	public void updateAccount(Account a) {
		File f = new File("src/main/resources/accounts/" + a.getAccountNumber() + ".txt");
		if (f.exists()) {
			f.setExecutable(true);
			f.setWritable(true);
			this.logNewAccount(a);
			f.setWritable(false);
		} else {
				System.out.println("couldn't delete");
			}
	}

	@Override
	public int getAccountNumber() {
		try {
			Scanner intGetter  = new Scanner(new File("src/main/resources/accountNumber"));
			int val = intGetter.nextInt(); 
			intGetter.close();
			return val;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public void incrementAccountNumber() {
		int val;
		try {
			Scanner intGetter  = new Scanner(new File("src/main/resources/accountNumber"));
			val = intGetter.nextInt() +1; 
			intGetter.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}
		try {
			Writer intSetter  = new FileWriter("src/main/resources/accountNumber");
			intSetter.write(String.valueOf(val)); 
			intSetter.close();
			return;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
	
