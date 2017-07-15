package achievement;

public class Property {
	private int propertyID;
	private String parameter;
	private int activationBoundValue;
	private String activationBoundType;

	/**
	 * Constructor for Property object
	 * 
	 * @param propertyID
	 * @param parameter
	 * @param activationBoundValue
	 * @param activationBoundType
	 */
	public Property(int propertyID, String parameter, int activationBoundValue, String activationBoundType) {
		this.propertyID = propertyID;
		this.parameter = parameter;
		this.activationBoundValue = activationBoundValue;
		this.activationBoundType = activationBoundType;
	}

	/**
	 * Gets ID number of property
	 * 
	 * @return propertyID
	 */
	public int getPropertyID() {
		return propertyID;
	}

	/**
	 * Returns parameter which is used to define whether achievement should be
	 * earned or not
	 * 
	 * @return parameter's name
	 */
	public String getParameter() {
		return parameter;
	}

	/**
	 * Returns bound value of property activation
	 * 
	 * @return The activation bound value
	 */
	public int getActivationBoundValue() {
		return activationBoundValue;
	}

	/**
	 * Returns bound type of property activation
	 * 
	 * @return The activation bound type
	 */
	public String getActivationBoundType() {
		return activationBoundType;
	}

	/**
	 * Checks whether property will be satisfied for specified value
	 * 
	 * @param currentValue
	 *            specified value
	 * @return true, if it will be satisfied. Otherwise, false.
	 */
	public boolean isSatisfied(int currentValue) {
		switch (activationBoundType) {
		case "==":
			return (currentValue == activationBoundValue);
		case ">=":
			return (currentValue >= activationBoundValue);
		case "<=":
			return (currentValue <= activationBoundValue);
		default:
			return false;
		}
	}
}
