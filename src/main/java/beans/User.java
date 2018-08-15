package beans;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable{
	private String username;
	private String name;
	private String password;
	private int age;
	private String mailingAddress;
	private ArrayList<Integer> accounts;
	private Boolean admin;
	
	public User() {
		super();
	}
	
	public User(String username, String name, String password, int age, String mailingAddress) {
		super();
		this.username = username;
		this.name = name;
		this.password = password;
		this.age = age;
		this.mailingAddress = mailingAddress;
		this.accounts = new ArrayList<Integer>();
		this.admin = false;
	}

	public User(String username, String name, String password, int age, String mailingAddress, ArrayList<Integer> accounts) {
		super();
		this.username = username;
		this.name = name;
		this.password = password;
		this.age = age;
		this.mailingAddress = mailingAddress;
		this.accounts = accounts;
		this.admin = false;
	}

	public User(String username, String name, String password, int age, String mailingAddress, ArrayList<Integer> accounts, Boolean isAdmin) {
		super();
		this.username = username;
		this.name = name;
		this.password = password;
		this.age = age;
		this.mailingAddress = mailingAddress;
		this.accounts = accounts;
		this.admin = isAdmin;
	}

	public String getUsername() {
		return this.username;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getMailingAddress() {
		return mailingAddress;
	}

	public void setMailingAddress(String mailingAddress) {
		this.mailingAddress = mailingAddress;
	}

	public ArrayList<Integer> getAccounts() {
		return accounts;
	}

	public void addAccount(Account acc) {
		this.accounts.add(acc.getAccountNumber());
	}
	
	public void giveAdmin(User accessingUser) {
		if (accessingUser.isAdmin()) {
			this.admin = true;
		} else {
			System.out.println("Requires Current Administrator to give Admin status.");
		}
	}
	public boolean isAdmin() {
		return this.admin;
	}

	@Override
	public String toString() {
		return "User [username=" + username + ", name=" + name + ", password=" + password + ", age=" + age
				+ ", mailingAddress=" + mailingAddress + ", accounts=" + accounts + ", isAdmin=" + admin + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accounts == null) ? 0 : accounts.hashCode());
		result = prime * result + age;
		result = prime * result + ((admin == null) ? 0 : admin.hashCode());
		result = prime * result + ((mailingAddress == null) ? 0 : mailingAddress.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (accounts == null) {
			if (other.accounts != null)
				return false;
		} else if (!accounts.equals(other.accounts))
			return false;
		if (age != other.age)
			return false;
		if (admin == null) {
			if (other.admin != null)
				return false;
		} else if (!admin.equals(other.admin))
			return false;
		if (mailingAddress == null) {
			if (other.mailingAddress != null)
				return false;
		} else if (!mailingAddress.equals(other.mailingAddress))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
}
