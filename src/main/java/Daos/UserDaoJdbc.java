package Daos;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import Util.DBConnectionUtil;
import beans.User;

public class UserDaoJdbc implements UserDao{
	private DBConnectionUtil dBCU = DBConnectionUtil.dBCU;
	public static final UserDaoJdbc udj = new UserDaoJdbc();


	@Override
	public boolean logNewUser(User u) {
		try{ 
			Connection conn = dBCU.getConnection();
			PreparedStatement ps = conn.prepareStatement(
					"INSERT INTO users (username, password,full_name, age,is_admin) VALUES (?,?,?,?,?)");
			ps.setString(1, u.getUsername());
			ps.setString(2, u.getPassword());
			ps.setString(3, u.getName());
			ps.setInt(4, u.getAge());
			ps.setBoolean(5,u.isAdmin());
			int recordCreated = ps.executeUpdate();
			return true;
		} catch (SQLException e) {
			System.out.println("User Couldn't be added.");
			return false;
		}
		
	}

	@Override
	public void deleteUser(User u) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public User findUser(String username, String password) {
		try{ 
			Connection conn = dBCU.getConnection();
			PreparedStatement ps = conn.prepareStatement(
					"SELECT * FROM users "
					+ "WHERE username = ? and password = ?");
			ps.setString(1, username);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			String un;
			String pass;
			String name;
			int age;
			Boolean isAdmin;
			while (rs.next()) {
				un = rs.getString("username");
				pass = rs.getString("password");
				name = rs.getString("full_name");
				age = rs.getInt("age");
				isAdmin = rs.getBoolean("is_admin");
				User foundUser = new User(un,pass,name,age,isAdmin);
				return foundUser;
				}
		} catch (SQLException e) {
			System.out.println("User Couldn't be added.");
		}
		
		return null;
	}

	@Override
	public User findUser(String username, User AccessingAccount) {
		if (AccessingAccount.isAdmin()){
			try{ 
				Connection conn = dBCU.getConnection();
				PreparedStatement ps = conn.prepareStatement(
						"SELECT * FROM users "
						+ "WHERE username = ?");
				ps.setString(1, username);
				ResultSet rs = ps.executeQuery();
				String un;
				String pass;
				String name;
				int age;
				while (rs.next()) {
					un = rs.getString("username");
					pass = rs.getString("password");
					name = rs.getString("full_name");
					age = rs.getInt("age");
					User foundUser = new User(un,pass,name,age);
					conn.close();
					return foundUser;
					}
			} catch (SQLException e) {
				System.out.println("User Couldn't be added.");
			}
		}
		return null;
	}

	@Override
	public boolean updateUser(User u) {
		try{ 
			Connection conn = dBCU.getConnection();
			PreparedStatement ps = conn.prepareStatement(
					"UPDATE users SET "
					+ "username =?, password = ?,full_name = ?, age = ? "
					+ "WHERE username = ?;");
			ps.setString(1, u.getUsername());
			ps.setString(2, u.getPassword());
			ps.setString(3, u.getName());
			ps.setInt(4, u.getAge());
			ps.setString(5, u.getUsername());
			int recordCreated = ps.executeUpdate();
			conn.close();
			return true;
		} catch (SQLException e) {
			return false;
		}
		
	}

	@Override
	public String userList() {
		try {

			Connection conn = dBCU.getConnection();
			StringBuilder userList = new StringBuilder();
			userList.append("List of Users on file: \n");
			PreparedStatement ps = conn.prepareStatement("SELECT username FROM users");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				userList.append(rs.getString("username"));
				userList.append("\n");
			}
			conn.close();
			return userList.toString();
		} catch (SQLException e) {

		}
	return null;	
	}
}
