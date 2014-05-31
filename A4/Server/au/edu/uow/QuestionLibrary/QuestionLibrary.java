package au.edu.uow.QuestionLibrary;

import java.util.List;
import java.util.ArrayList;
import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.util.Random;
import java.util.Collections;

/**
 * @author Aaron Colin Foote acf502 4258770
 * Date Last Modified: Tuesday 15th October 2013
 * File Description: QuestionLibrary.java for Assignment 4 of CSCI213
 */

public class QuestionLibrary {

	/**
	 * This attribute holds the library of questions
	 */
	private static List<Question> questions;
	private static List<Question> quizQuestions;
	
	/**
	 * This method retrieves the library of questions from a file
	 * NOT YET FULLY IMPLEMNETED
	 * @param qFile The name of the file to retrieve the questions from
	 * @return True for successful building of library, False on instance that file can't be found
	 */
	public static boolean buildLibrary(String qFile) {
		try {
			int MCCount = 0, TFCount = 0;	//limit the amount of nodes added by each type of question in later for loops
			File fXmlFile = new File(qFile);
			if (!(fXmlFile.isFile() && fXmlFile.canRead())) {	//test file exists and is readable
				return false;
			}
			questions = new ArrayList<Question>();	//build library of questions in this list

			//prepares xml document for passing data
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);

			NodeList nListM = doc.getElementsByTagName("MQuestion");

			for (; MCCount < nListM.getLength(); MCCount++) {	//load in Multiple Choice questions to list
		 
				Node nNode = nListM.item(MCCount);	//retrieve each MC question
		 
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					MultipleChoiceQuestion mc = new MultipleChoiceQuestion();

					//fill question data for question, answer and choices
					mc.setQuestion(eElement.getElementsByTagName("question").item(0).getTextContent());
					mc.setAnswer(eElement.getElementsByTagName("answer").item(0).getTextContent());
					mc.setChoices(eElement.getElementsByTagName("choices").item(0).getTextContent());
					
					//pass filled data node to the question list
					questions.add(mc);
	 			}
	 		}

	 		NodeList nListTF = doc.getElementsByTagName("TFQuestion");

			for (; TFCount < nListTF.getLength(); TFCount++) {	//repeat for TrueAndFalse questions
		 
				Node nNode = nListTF.item(TFCount);
		 
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
		 
					Element eElement = (Element) nNode;
					TrueAndFalseQuestion tf = new TrueAndFalseQuestion();
					tf.setQuestion(eElement.getElementsByTagName("question").item(0).getTextContent());
					tf.setAnswer(eElement.getElementsByTagName("answer").item(0).getTextContent());
					questions.add(tf);
	 			}
	 		}
		}
		catch (Exception e) {	//in the case of a failed file opening...
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * This method inserts the loaded questions into a list format.
	 * @param noOfQuestions A specified sentinel value to end making the list at. It is assumed that the noOfQuestions parameter does not exceed the total amount of questions available
	 * @return List of questions
	 */
	public static List<Question> makeQuiz(int noOfQuestions) {
		long seed = System.nanoTime();				//spawn seed for randomness of quiz
		quizQuestions = new ArrayList<Question>();		//set up final list of questions to use in quiz
		Collections.shuffle(questions, new Random(seed));	//questions library is shuffled
		quizQuestions = questions.subList(0, noOfQuestions);	//retrieve first n-questions from question library
		return quizQuestions;					//return new list of n-questions
	}
}
