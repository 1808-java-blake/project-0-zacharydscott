package launcher;

import java.util.ArrayList;

import Daos.AccountDao;
import Daos.UserDao;
import beans.Account;
import beans.User;
/**this was a testing class, used to test new implementations 
 * and generate the first admin account.
 */
public class artificialAdmin {
	public static void main(String[] args) {
		UserDao ud = UserDao.cUserDao;
		AccountDao ad = AccountDao.cAccountDao;
		System.out.println(ud.userList());
		System.out.println(ad.accountList());
	}
}
