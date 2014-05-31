package au.edu.uow.QuestionLibrary;

import java.util.List;
import java.util.ArrayList;

/**
 * @author Aaron Colin Foote acf502 4258770
 * Date Last Modified: Tuesday 15th October 2013
 * File Description: TrueAndFalseQuestion.java for Assignment 4 of CSCI213
 */

public class TrueAndFalseQuestion implements Question {

	private String question;
	private boolean answer;
	
	/**
	 * This method sets the question text, as passed from the xml file
	 * @see #setAnswer(String)
	 */
	public void setQuestion(String q) {			//trim question string, removing whitespace
		question = q.substring(1, q.length()-1);
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
	 * @see #setQuestion(String)
	 */
	public void setAnswer(String a) {
		answer = true;					//default value for answer
		a = a.substring(2,a.length()-1);		//trim whitespace and first letter of answer, to ignore capitalization
		if (a.equals("alse")) {				//test for string value and set boolean value of answer
			answer = false;
		}
		else if (a.equals("rue")){
			answer = true;
		}
		
	}
	
	/**
	 * This method gets the question choices
	 * @return The list of choices
	 * @see #getQuestion()
	 */
	public List<String> getChoices() {
		List<String> cList = new ArrayList<String>();	//prepare list for choices
		String t = "1: True";				//default values for all TF questions
		String f = "2: False";
		cList.add(t);					//add default values to list
		cList.add(f);
		return cList;
	}
	
	/**
	 * This method compares the answer given by the quiz-taker with the correct answer
	 * @param ans The student's answer
	 * @return True for the correct answer; false for incorrect answers.
	 */		
	public boolean compareAnswer(int ans) {
		if (ans == 1 && answer == true) {	//test user input matches corresponding boolean value of answer
			return true;
		}
		else if (ans == 2 && answer == false) {
			return true;
		}
		else
			return false;
	}

}
