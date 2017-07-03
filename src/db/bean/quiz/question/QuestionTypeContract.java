package db.bean.quiz.question;

/**
 * @author tamar question type enumeration agreement
 */
public class QuestionTypeContract {
	public static final int QUESION_RESPONSE = 1;
	public static final int FILL_IN_THE_BLANK = 2;
	public static final int MULTIPLE_CHOICE = 3;
	public static final int PICTURE_RESPONCE = 4;
	public static final int MULTI_ANSWER = 5;
	public static final int MULTIPLE_CHOICE_MULTIPLE_ANSWER = 6;
	public static final int MATCHING_QUESTION = 7;

	public static String getClassName(int type) {
		switch (type) {
		case 1:
			return "QuestionResponseQuestion";
		case 2:
			return "FillTheBlankQuestion";
		case 3:
			return "MultipleChoiceQuestion";
		case 4:
			return "PictureResponseQuestion";
		case 5:
			return "MultiAnswerQuestion";
		case 6:
			return "MultipleChoiceMultipleAnswerQuestion";
		case 7:
			return "MatchingQuestion";
		}
		return null;
	}
}
