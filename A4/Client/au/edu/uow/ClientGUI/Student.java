package au.edu.uow.ClientGUI;

/**
 * @author Aaron Colin Foote acf502 4258770
 * Date Last Modified: Tuesday 15th October 2013
 * File Description: Student.java for Assignment 4 of CSCI213
 */
 
public class Student {

	/**
	 * This attribute holds the student's name
	 */
	private String name;
	
	/**
	 * This attribute holds the student's total score
	 */
	private int score;

	Student() {
		name = "";
		score = 0;
	}

	/**
	 * This method sets the student's name
	 * @param name The Student's name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * This method gets the student's name
	 * @return String value of student's name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * This method records the user's score
	 * @param isCorrect Determines whether to increase the student's score
	 */
	public void recordScore(boolean isCorrect) {
		if (isCorrect) {
			score++;	//if boolean passed to function is "true" (user answered correctly), increase score
		}
	}
	
	/**
	 * This method gets the student's score
	 * @return The Student's score
	 */
	public int getScore() {
		return score;
	}
}
