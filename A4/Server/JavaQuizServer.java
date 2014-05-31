import java.io.*;
import java.net.*;
import java.util.*;

import au.edu.uow.Networking.*;
import au.edu.uow.QuestionLibrary.*;
import au.edu.uow.UserInterface.*;

/**
  * @author Aaron Colin Foote
  * User: acf502 4258770
  * Date: Thursday 17th October 2013
  * File Desc. : JavaQuizServer.java file for CSCI203 Assignment 4
  */

public class JavaQuizServer
{
	/**
	 * Main method of server, produces threads to handle clients as each client connects and disconnects
	 */
	public static void main(String[] args )
	{  
		int port = 40213;
		String hostname = "";
		String hostIP ="";

		if (args.length > 1) {
			System.out.println("Usage: JavaQuizServer OR JavaQuizServer portNumber ");
			System.exit(0);
		}
		else if (args.length == 1) {
			port = Integer.parseInt(args[0]);			
		}
		System.out.println("JavaQuizServer listening at: " + port);
		try {

			// get host information
			hostname = InetAddress.getLocalHost().getHostName();
			hostIP = InetAddress.getLocalHost().getHostAddress();

			ServerSocket serverSocket = new ServerSocket(port);
			
			QuestionLibrary questionLib = new QuestionLibrary();
			questionLib.buildLibrary("questions.xml");
			
			//MODIFIED FROM HERE:
			while (true) {
			
				Socket incoming = serverSocket.accept();
				Runnable clientHandler = new JavaClientHandler(incoming, hostname, questionLib);
				Thread t = new Thread(clientHandler);
				t.start();
			}
			
		}
		catch (IOException e)
		{  
			e.printStackTrace();
		}
	}
}
