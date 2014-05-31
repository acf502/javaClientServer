import au.edu.uow.Networking.*;
import au.edu.uow.QuestionLibrary.*;
import au.edu.uow.ClientGUI.*;

/**
  * @author Aaron Colin Foote
  * User: acf502 4258770
  * Date: Thursday 17th October 2013
  * File Desc. : JavaQuizClient.java file for CSCI203 Assignment 4
  */

public class JavaQuizClient
{
	/**
	 * This is the main method, which initialises and runs the GUI's connection to the server
	 */
	public static void main(String[] args)
	{
		Client client = new Client();	//Declare a new client

		if (args.length < 1 || args.length > 2) {	/* test amount of arguments is either none or one */
			System.out.println("Usage: JavaQuizClient serverHostname  OR  JavaQuizClient serverHostname:portNumber");
			System.exit(0);
		}
		else {
			if (args[0].contains(":")) {				//split passed argument at ":" character (if there is one)
				String arguments[] = args[0].split(":");
				client.setHostname(arguments[0]);		//set hostname to pre :
				client.setPort(Integer.parseInt(arguments[1]));	//set port to post :
			}
			else
				client.setHostname(args[0]);			//otherwise, just set hostname to argument
		}
		
		QuizClientGUIFrame frame = new QuizClientGUIFrame(client);	//start new GUI using client
	}
}
