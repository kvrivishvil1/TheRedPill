package managers;

import db.dao.MessageDao;

public class MainManager {
	public static final String CONTEXT_ATTRIBUTE_NAME = "MainManager";
	
	private AccountManager accountManager;
	private QuizManager quizManager;
	private MessageManager messageManager;
	
	public MainManager(AccountManager accountManager, QuizManager quizManager, MessageManager messageManager) {
		this.accountManager = accountManager;
		this.quizManager = quizManager;
		this.messageManager = messageManager;
	}
	
	/**
	 * @return Account Manager
	 */
	public AccountManager getAccountManager() {
		return accountManager;
	}
	
	/**
	 * @return Quiz Manager
	 */
	public QuizManager getQuizManager() {
		return quizManager;
	}
	
	/**
	 * @return Message Manager
	 */
	public MessageManager getMessageManager() {
		return messageManager;
	}
}
