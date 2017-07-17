package Managers;

public class MainManager {
	public static final String CONTEXT_ATTRIBUTE_NAME = "MainManager";
	
	private AccountManager accountManager;
	
	public MainManager(AccountManager accountManager) {
		this.accountManager = accountManager;
	}
	
	public AccountManager getAccountManager() {
		return accountManager;
	}
	
	
	
}
