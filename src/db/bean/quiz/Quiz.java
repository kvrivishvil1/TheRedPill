package db.bean.quiz;

import java.util.ArrayList;
import java.util.List;

public class Quiz {
	private boolean isPracticable;
	private String name;
	private boolean isRearangable;
	private int numQuestions;
	private ArrayList<Question> questions;

	public Quiz(String name, boolean isPacticable, boolean isRearangable) {
		this.name = name;
		this.isPracticable = isPacticable;
		this.isRearangable = isPacticable;
		numQuestions = 0;
		questions = new ArrayList<>();
	}

	public boolean isPracticable() {
		return isPracticable;
	}

	public String getName() {
		return name;
	}

	public boolean isRearangable() {
		return isRearangable;
	}

	public List<Question> getAllQuestions() {
		return questions;
	}

	public int getNumQuestions() {
		return numQuestions;
	}

	public void addQuestion(Question newQuestion) {
		numQuestions++;
		questions.add(newQuestion);
	}

}
