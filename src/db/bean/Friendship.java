package db.bean;

import java.util.Date;

public class Friendship {
	private int firstFriendUserID;
	private int secondFriendUserID;
	private Date date;

	/**
	 * Gets two users and creates friendship between them. First friend is the
	 * one with the smaller user ID number, the second friend is the one with
	 * the bigger user ID. If creator of this object misleadingly passes ID
	 * numbers, they will still be set correctly
	 * 
	 * @param firstFriendUserID
	 *            first user with the smaller user ID number
	 * @param secondFriendUserID
	 *            second user with the bigger user ID number
	 * @param date
	 *            their friendship's creation date
	 */
	public Friendship(int firstFriendUserID, int secondFriendUserID, Date date) {
		if (firstFriendUserID < secondFriendUserID) {
			this.firstFriendUserID = firstFriendUserID;
			this.secondFriendUserID = secondFriendUserID;
		} else {
			this.firstFriendUserID = secondFriendUserID;
			this.secondFriendUserID = firstFriendUserID;
		}
		this.date = date;
	}

	/**
	 * Returns first friend with the smaller user ID number
	 * 
	 * @return friend with the smaller user ID number
	 */
	public int getFirstFriendUserID() {
		return firstFriendUserID;

	}

	/**
	 * Returns second friend with the bigger user ID number
	 * 
	 * @return friend with the bigger user ID number
	 */
	public int getSecondFriendUserID() {
		return secondFriendUserID;
	}

	/**
	 * Returns the date when the friendship was created
	 * 
	 * @return the friendship's creation date
	 */
	public Date getDate() {
		return date;
	}

}
