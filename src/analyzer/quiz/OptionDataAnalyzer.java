package analyzer.quiz;

import java.util.ArrayList;
import java.util.List;

import db.bean.quiz.Option;

public class OptionDataAnalyzer {
	private String[] options;

	/**
	 * Constructor for options' list analyzer
	 * 
	 * @param options
	 *            list of objects which should be analyzed
	 */
	public OptionDataAnalyzer(String[] options) {
		this.options = options;
	}

	/**
	 * Returns the list of options for passed data
	 * 
	 * @return The list of analyzed options
	 */
	public List<Option> getOptions() {
		List<Option> optionsList = new ArrayList<Option>();
		if (options != null) {
			for (int i = 0; i < options.length; i++) {
				Option option = new Option(options[i]);
				optionsList.add(option);
			}
		}
		return optionsList;
	}

}
