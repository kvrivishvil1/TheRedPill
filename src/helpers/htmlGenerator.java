package helpers;

import db.bean.quiz.Question;

public class htmlGenerator {
	private static String diplayQuestionResponse(Question question, int questionPosition) {
		String html = "";
		String note = question.getNote();
		String questionText = question.getSubquestions().get(0).getQuestion();
		if (note.length() != 0)
			html += "<p class = \"note\">" + note + "</p>";
		html += "<div class = \"question-response\">";
		html += " <p class = \"question\"><b>" + questionText + "</b></p>";
		html += "<div id = \"" + questionPosition + "\">";
		html += "<input type = \"text\" class = \"answer-field\"/> </div></div>";
		return html;
	}

	private static String displayFillTheBlank(Question question, int questionPosition) {
		String html = "";
		String note = question.getNote();
		String questionText = question.getSubquestions().get(0).getQuestion();
		if (note.length() != 0)
			html += "<p class = \"note\">" + note + "</p>";
		html += "<div class = \"fill-blank\">";
		html += "<p class = \"question-basic\"><b>" + questionText + "</b></p>";
		html += "<div id = \"" + questionPosition + "\">";
		html += "<input type = \"text\" class = \"answer-field\"/> </div></div>";
		return html;
	}

	private static String displayMultipleChoice(Question question, int questionPosition) {
		String html = "";
		String note = question.getNote();
		String questionText = question.getSubquestions().get(0).getQuestion();
		if (note.length() != 0)
			html += "<p class = \"note\">" + note + "</p>";
		html += "<div class = \"mult-choice\">";
		html += "<p class = \"question-basic\"><b>" + questionText + "</b></p>";
		html += "<div id = \"" + questionPosition + "\">";
		for (int i = 0; i < question.getOptions().size(); i++) {
			html += "<input type=\"radio\" name=\"option\" value=\"" + question.getOptions().get(i).getOption() + "\"/>"
					+ question.getOptions().get(i).getOption() + "<br>";
		}
		html += "</div></div>";
		return html;
	}

	private static String displayPictureResponse(Question question, int questionPosition) {
		String html = "";
		String imagageSrc = question.getSubquestions().get(0).getQuestion();
		String note = question.getNote();
		html += "<p class = \"question-note\"><b>" + note + "</b></p>";
		html += "<div class = \"pic-response\">";
		html += "<img src =\"" + imagageSrc + "\" class = \"image-question\"><br><br>";
		html += "<div id = \"" + questionPosition + "\">";
		html += "<input type = \"text\" class = \"answer-field\"> </div></div>";
		return html;
	}

	private static String displayMultiAnswer(Question question, int questionPosition) {
		String html = "";
		String note = question.getNote();
		String questionText = question.getSubquestions().get(0).getQuestion();
		if (note.length() != 0) {
			html += "<p class = \"note\">" + note + "</p>";
		}
		html += "<div class = \"mult-answer\">";
		html += "<p class = \"question-basic\"><b>" + questionText + "<b></p>";
		html += "<div id = \"" + questionPosition + "\">";
		for (int i = 0; i < question.getSubquestions().get(0).getAnswers().size(); i++) {
			html += (i + 1) + ") <input type=\"text\" class =\"multiple-answer\"/><br>";
		}
		html += "</div></div>";
		return html;
	}

	private static String displayMultipleChoiceMultipleAnswer(Question question, int questionPosition) {
		String html = "";
		String note = question.getNote();
		String questionText = question.getSubquestions().get(0).getQuestion();
		if (note.length() != 0)
			html += "<p class = \"note\">" + note + "</p>";
		html += "<div class = \"mult-choice-mult-answer\">";
		html += "<p class = \"question-basic\"><b>" + questionText + "</b></p>";
		html += "<div id = \"" + questionPosition + "\">";
		for (int i = 0; i < question.getOptions().size(); i++) {
			html += "<input type=\"checkbox\" value=\"" + question.getOptions().get(i).getOption() + "\">"
					+ question.getOptions().get(i).getOption() + "<br>";
		}
		html += "</div></div>";
		return html;
	}

	private static String displayMatching(Question question, int questionPosition) {
		String html = "";
		String note = question.getNote();
		if (note.length() != 0)
			html += "<p class = \"note\"><b>" + note + "<b></p>";
		html += "<div class = \"matching\">";
		html += "<div id = \"" + questionPosition + "\">";
		html += "<table>";
		for (int i = 0; i < question.getSubquestions().size(); i++) {
			html += "<tr>";
			html += "<td>" + question.getSubquestions().get(i).getQuestion() + "</td>";
			html += "<td><select class = \"match-opt\">";
			for (int j = 0; j < question.getOptions().size(); j++) {
				html += "<option>" + question.getOptions().get(j).getOption() + "</option>";
			}
			html += "</select></td>";
			html += "</tr>";
		}
		html += "</table></div></div>";
		return html;
	}

	public static String displaySingleQuestion(Question question, int position) {
		int type = question.getQuestionType();
		switch (type) {
		case 1:
			return diplayQuestionResponse(question, position);
		case 2:
			return displayFillTheBlank(question, position);
		case 3:
			return displayMultipleChoice(question, position);
		case 4:
			return displayPictureResponse(question, position);
		case 5:
			return displayMultiAnswer(question, position);
		case 6:
			return displayMultipleChoiceMultipleAnswer(question, position);
		case 7:
			return displayMatching(question, position);
		}
		return null;
	}
}
