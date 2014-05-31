package au.edu.uow.QuestionLibrary;

import java.util.List;
import java.util.ArrayList;
import java.lang.Integer;

/**
 * @author Aaron Colin Foote acf502 4258770
 * Date Last Modified: Tuesday 15th October 2013
 * File Description: MultipleChoiceQuestion.java for Assignment 4 of CSCI213
 */
 
public class MultipleChoiceQuestion implements Question {

	private String question;
	private String answer;
	private String[] choices;
	
	/**
	 * This method sets the question text, as passed from the xml file
	 * @param q The passed question string
	 * @see #setAnswer(String)
	 * @see #setChoices(String)
	 */
	public void setQuestion(String q) {
		question = q.substring(1, q.length()-1);	//trim question string, removing whitespace
	}
	
	/**
	 * This method gets the question text,
	 * @return The question text in a list
	 * @see #getChoices()
	 */
	public List<String> getQuestion() {
		List<String> qList = new ArrayList<String>();	//return queston in list format
		qList.add(question);
		return qList;
	}
	
	/**
	 * This method sets the question answer, as passed from the xml file
	 * @param a The correct answer value
	 * @see #setQuestion(String)
	 * @see #setChoices(String)
	 */
	public void setAnswer(String a) {
		answer = (a.substring(1,2));			//trim answer from string, removing whitespace
	}
	
	/**
	 * This method sets the question choices, as passed from the xml file
	 * @param choiceLine The choices that can be made as a single String variable
	 * @see #setQuestion(String)
	 * @see #setAnswer(String)
	 */
	public void setChoices(String choiceLine) {
		choices = choiceLine.split("\\n");		//split choices by newline character
	}
	
	/**
	 * This method gets the question choices
	 * @return The list of choices
	 * @see #getQuestion()
	 */
	public List<String> getChoices() {
		List<String> cList = new ArrayList<String>();		//prepare new list to return choices
		for (int i = 1; i < choices.length; i++) {		//append choice value to start of each choice
			String choice = i + ": " + choices[(i)];
			cList.add(choice);
		}
		return cList;						//return choice list with corresponding values
	}
	
	/**
	 * This method compares the answer given by the quiz-taker with the correct answer
	 * @param ans The student's answer
	 * @return True for the correct answer; false for incorrect answers.
	 */		
	public boolean compareAnswer(int ans) {
		if (Integer.toString(ans).equals(answer)) {		//compare user answer with the string value of the real answer
			return true;
		}
		else {
			return false;
		}
	}

}
