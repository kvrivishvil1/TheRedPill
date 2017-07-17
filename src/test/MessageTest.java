package test;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

import db.bean.Challenge;
import db.bean.FriendRequest;
import db.bean.Friendship;
import db.bean.Note;

public class MessageTest {

	@Test
	public void testNoteSimple() {
		Date date = new Date();
		Note note = new Note(1, 2, date, "Very Simple Test");
		assertEquals(note.getSenderID(), 1);
		assertEquals(note.getRecieverID(), 2);
		assertEquals(note.getDate(), date);
		assertEquals(note.getText(), "Very Simple Test");
	}
	
	@Test
	public void testFriendRequestSimple() {
		Date date = new Date();
		FriendRequest request = new FriendRequest(1, 2, date);
		assertEquals(request.getSenderID(), 1);
		assertEquals(request.getRecieverID(), 2);
		assertEquals(request.getDate(), date);
	}
	
	@Test
	public void testChallengeSimple() {
		Date date = new Date();
		Challenge challenge = new Challenge(1,1, 2, date,  0);
		assertEquals(challenge.getSenderID(), 1);
		assertEquals(challenge.getRecieverID(), 2);
		assertEquals(challenge.getDate(), date);
		assertEquals(challenge.getQuizID(), 1);
		assertEquals(challenge.getMaxScore(), 0);
	}
	
}
