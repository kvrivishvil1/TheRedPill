package achievement;

import java.util.List;
import java.util.Map;

public class Achievement {
	private int achievementID;
	private String name;
	private List<Property> properties;
	
	/**
	 * Constructor for Achievement object 
	 * @param achievementID 
	 * @param name for achievement
	 * @param properties the list of achievement's properties
	 */
	public Achievement(int achievementID, String name, List<Property> properties){
		this.achievementID = achievementID;
		this.name = name;
		this.properties = properties;
	}
	
	/**
	 * Gets achievement's ID number
	 * @return id number of the achievement
	 */
	public int getAchievementID(){
		return achievementID;
	}
	
	/** 
	 * Gets achievement's name
	 * @return The name of an achievement
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * Gets list of achievement's properties
	 * @return The list of achievement's properties
	 */
	public List<Property> getProperties(){
		return properties;
	}
	
	/**
	 * Checks if all achievement properties are satisfied for passed data
	 * @param userData passed data
	 * @return true, if they are satisfied. Otherwise, false.
	 */
	public boolean allPropertiesSatisfied(Map<String, Integer> userData){
		int propertiesSatisfied = 0;
		for(int i = 0; i < properties.size(); i++){
			Property currentProperty = properties.get(i);
			String neededParameter = currentProperty.getParameter();
			int currentValue = userData.get(neededParameter);
			if(currentProperty.isSatisfied(currentValue))
				propertiesSatisfied++;
		}
		return propertiesSatisfied == properties.size();
	}
}
