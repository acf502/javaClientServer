package au.edu.uow.UserInterface;

import au.edu.uow.QuestionLibrary.*;
import java.util.*;
import java.io.*;

/**
 * @author Aaron Colin Foote acf502 4258770
 * Date Last Modified: Tuesday 15th October 2013
 * File Description: UserInterface.java for Assignment 4 of CSCI213
 */

public class UserInterface {

	private int qNum = 1;	//track current question number and total (once finished)

	/**
	 * This method gets the student's name
	 * @see au.edu.uow.UserInterface.Student#setName
	 * @return Student Student class with the value of student's name
	 */
	public Student getStudent() {
		System.out.print("Your name:");
		Student st = new Student();
		Scanner in = new Scanner(System.in);	//read in user input of name
		String name = in.nextLine();
		st.setName(name);
		return st;
	}
	
	/**
	 * This method runs the quiz
	 * @param quiz List of Question type classes, to be used in quiz
	 * @param student Student class to be updated throughout quiz duration
	 */
	public void startQuiz(List<Question> quiz, Student student) {
		int answer = 0;	//track user response

		for (Question question : quiz) {
			System.out.printf("%nQuestion No. %d:%n%n", qNum);
		 	for (String s : question.getQuestion()) {	//output question
  				System.out.println(s);
			}
			System.out.println("\nAnswer choices:");
			for (String s : question.getChoices()) {	//output choices
  				System.out.println(s);
			}
			System.out.print("\nChoose your answer:");
			answer = validateAnswer(question.getChoices().size());	//ensure user input is valid

			student.recordScore(question.compareAnswer(answer));	//pass correctness value to student class for updating
			qNum++;	//increment question number of next question
		}
	}
	
	/**
	 * This method receives each response and validates the response's integrity based on the circumstance
	 * @return The answer after validation
	 */
	public int validateAnswer(int maxRange) {
		int answer = 0;
		Scanner in = new Scanner(System.in);
		try {
			answer = in.nextInt();
			if (answer < 1 || answer > maxRange)
				throw new Exception();
		} catch (InputMismatchException e) {
			System.out.print("Invalid answer, input may not be of correct type. Please try again:");
			validateAnswer(maxRange);	//while user input is invalid, repeat input prompt
		}
		  catch (Exception ex) {
		  	System.out.print("Invalid answer, numeric answer range has been breached. Please try again:");
			validateAnswer(maxRange);	//while user input is invalid, repeat input prompt
		}
		return answer;
	}
	
	/**
	 * This method gets the student's marks
	 * @param student Student class with values filled with data
	 * @see au.edu.uow.UserInterface.Student#getName
	 * @see au.edu.uow.UserInterface.Student#getScore
	 */
	public void showStudentMarks(Student student) {
		System.out.printf("Result of %s: %d out of %d %n", student.getName(), qNum, student.getScore());
	}
}
