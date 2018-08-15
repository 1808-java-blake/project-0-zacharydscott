package Daos;
import beans.User;

public interface UserDao {
	public static final UserDao cUserDao = UserSerializer.us;
	
	void logNewUser(User u);
	void deleteUser(User u);
	User findUser(String username, String password);
	User findUser(String username, User AccessingAccount);
	void updateUser(User u);
	String userList();
}
