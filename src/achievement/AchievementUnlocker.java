package achievement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AchievementUnlocker {
	private List<Achievement> lockedAchievements;
	private Map<String, Integer> userData;
	
	/**
	 * Constructor for AchievementUnlocker object
	 * 
	 * @param userData which will be used to unlock new achievements
	 * @param lockedAchievements list of locked achievements
	 */
	public AchievementUnlocker(Map<String, Integer> userData, List<Achievement> lockedAchievements){
		this.lockedAchievements = lockedAchievements;
		this.userData = userData;
	}
	
	/**
	 * Checks locked achievements, whether they can be unlocked or not
	 * @return The list of unlocked achievement's IDs
	 */
	public List<Integer> unlockAchievements(){
		List<Integer> unlockedAchievements = new ArrayList<Integer>();
		for(int i = 0; i < lockedAchievements.size(); i++){
			Achievement toCheck = lockedAchievements.get(i);
			if(toCheck.allPropertiesSatisfied(userData)){
				 unlockedAchievements.add(toCheck.getAchievementID());
			}
		}		
		return unlockedAchievements;
	}
	
}
