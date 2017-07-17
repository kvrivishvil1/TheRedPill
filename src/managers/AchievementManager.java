package managers;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;

import achievement.Achievement;
import achievement.AchievementUnlocker;
import db.dao.QuizDao;

public class AchievementManager {
	private QuizDao quizDao;
	
	public AchievementManager(QuizDao quizDao){
		this.quizDao = quizDao;
	}
	
	/**
	 * Unlocks achievements after taking quiz, if necessary. 
	 * @param accountID for user who took quiz
	 * @param quizID for quiz which was taken
	 */
	public void updateAchievementsAfterQuiz(int accountID, int quizID){
		List<Achievement> allAchievements = quizDao.getAllAchievements();
		List<Achievement> allEarnedAchievements = quizDao.getAllAchievementsForUser(accountID);
		allAchievements.removeAll(allEarnedAchievements);
		
		Map<String, Integer> userData = new HashMap<String, Integer>();
		int maxScoreInQuiz = quizDao.getQuizMaxScore(quizID);
		userData.put("quiz_create", maxScoreInQuiz);
		int quizTakeCount = quizDao.getQuizTakeCountForUser(accountID);
		userData.put("quiz_take", quizTakeCount);
		int quizCreateCount = quizDao.getQuizCreateCountForUser(accountID);
		userData.put("quiz_create", quizCreateCount);
		
		AchievementUnlocker unlocker = new AchievementUnlocker(userData, allAchievements);
		List<Integer> unlockedAchievements = unlocker.unlockAchievements();
		quizDao.addUnlockedAchievements(unlockedAchievements, accountID);
	}

}
