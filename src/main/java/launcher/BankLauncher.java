package launcher;
import Screens.LoginScreen;
import Screens.Screen;

public class BankLauncher {
	public static void main(String[] args) {
		Screen currScreen = new LoginScreen("");
		boolean terminate = false;
		while(!terminate) {
			currScreen = currScreen.start();
		}
	}
}
