package Revature.Project1;

import org.junit.jupiter.api.Test;

import Daos.AccountDao;
import Daos.UserDao;
import beans.Account;
import beans.User;

public class BankTest {

	@Test
	public void UserDaoFileCreationAndRetreivalTest() {
		UserDao ud = UserDao.cUserDao;
		User testUser = new User("Tester123User", "", "pass", 1, "");
		ud.logNewUser(testUser);
		User foundUser = ud.findUser("Tester123User", "pass");
		assertEquals(testUser, foundUser);
	}

	@Test
	public void UserDaoUpdates() {
		UserDao ud = UserDao.cUserDao;
		User testUser = ud.findUser("Tester123User", "pass");
		testUser.setAge(2);
		testUser.setName("TesterGuy");
		ud.updateUser(testUser);
		User updatedUser = ud.findUser("Tester123User", "pass");
		assertEquals(testUser,updatedUser);
	}

	@Test
	public void AccountDaoFileCreationAndRetreivalTest() {
		AccountDao ad = AccountDao.cAccountDao;
		Account testAccount = new Account("TestType", null, 100L);
		ad.logNewAccount(testAccount);
		Account foundAccount = ad.findAccount(testAccount.getAccountNumber());
		assertEquals(testAccount, foundAccount);
	}
	@Test
	public void updateAccount() {
		AccountDao ad = AccountDao.cAccountDao;
		Account testAccount = new Account("TestType", null, 100L);
		ad.logNewAccount(testAccount);
		Account foundAccount = ad.findAccount(testAccount.getAccountNumber());
		foundAccount.setAccountType("NewType");
		testAccount.setAccountType("newType");
		ad.updateAccount(foundAccount);
		assertEquals(testAccount, ad.findAccount(testAccount.getAccountNumber()));
	}
}
