package Daos;
import beans.User;

public interface UserDao {
	public static final UserDao cUserDao =  UserDaoJdbc.udj;
	
	boolean logNewUser(User u);
	void deleteUser(User u);
	User findUser(String username, String password);
	User findUser(String username, User AccessingAccount);
	boolean updateUser(User u);
	String userList();
}
