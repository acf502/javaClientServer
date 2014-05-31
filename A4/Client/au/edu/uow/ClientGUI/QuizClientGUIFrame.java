package au.edu.uow.ClientGUI;

import au.edu.uow.Networking.*;
import au.edu.uow.QuestionLibrary.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.border.EmptyBorder;
import javax.swing.*;
import java.util.*;
import java.util.List;
import java.io.*;

/**
 * @author Aaron Colin Foote acf502 4258770
 * Date Last Modified: Thursday 17th October 2013
 * File Description: QuizClientGUIFrame.java for Assignment 4 of CSCI213
 */

public class QuizClientGUIFrame implements ActionListener
{
	//GUI VARIABLES

	private JFrame frame;				//simple GUI JFrame (window)
	private JMenuBar menuBar;			//holds all menu items
	private JMenuItem connect, disconnect, setServer, exit, about, displayAllUsers;	//all menu items
	private JTextField statusField, nameEntry;	//textfields that appear throughout program
	private JPanel welcomePageBottom;		//holds the name entry textfield and status bar
	private JToolBar namePanel;			//holds the name entry field and dialog to the left of it
	private JLabel welcome;				//text on intro page

	//DATA AND OBJECT HOLDING

	private Student user;				//Student class object of current user
	private Client client;				//holds the client process and data

	//QUIZ GUI

	private JPanel bottomPanel, questionPanel, choicesPanel;	//panels to hold the next button, questions and choices
	private JTextArea questionArea;					//question appears here
	private JButton jtnProgress;					//Next and Get Marks button
	private ButtonGroup choicesGroup;				//ButtonGroup to hold all the choices for the user to make
	private int qCount;
	private Question q;

	/**
	 * This method is the constructor of the GUI JFrame, initialising the GUI to the default welcome screen.
	 * It then proceeds to the quiz after the connection is established
	 */
	public QuizClientGUIFrame(Client cl)
	{
		client = cl;
		frame = new JFrame("Quiz Client GUI");
		frame.setSize(800, 600);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setBackground(new Color(51, 153, 153));

		menuBar = new JMenuBar();
		createMenubar();

		addStatusBar();

		welcome = new JLabel("<html><center><font color=white size = 6>Java Quiz Client</font><font size = 5><br><br>created by Aaron Colin Foote<br><br>for<br><br>CSCI213 Assignment 4</font></center></html>", JLabel.CENTER);

		frame.add(welcome, BorderLayout.CENTER);
		
		frame.setVisible(true);

		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent event) {
				if (client.getIsConnected())
					client.Disconnect();
					exitProgram();
				}
		});
	}

	/**
	 * This method controls the flow of the quiz, re-directing the user to the results screen at the end
	 */
	private void runQuiz()
	{
		frame.remove(welcome);

		q = client.requestQuestion();
		qCount = 1;

		bottomPanel = new JPanel();
		bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); 
		bottomPanel.setBorder(new EmptyBorder(0, 0, 30, 0));

		questionPanel = new JPanel();
		questionPanel.setLayout(new GridLayout(2, 1));

		choicesPanel = new JPanel(new GridLayout(5, 0, 0, 0));
		choicesPanel.setBorder(new EmptyBorder(0, 45, 0, 0));

		questionArea = new JTextArea();
		questionArea.setBackground(Color.white);
		questionArea.setOpaque(true);
		questionArea.setBorder(new EmptyBorder(20, 30, 0, 0));
		questionArea.setEditable(false);
        	questionArea.add(new Label("Vertical gap:"));

		jtnProgress = new JButton("Next");
		jtnProgress.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int selected = 0, i = 0;
				for (Enumeration en = choicesGroup.getElements(); en.hasMoreElements(); i++) {
					JRadioButton b = (JRadioButton) en.nextElement();
					if (b.getModel() == choicesGroup.getSelection()) {
						selected = (i+1);
					}
				}

				if (q.compareAnswer(selected))
					user.recordScore(true);
				else
					user.recordScore(false);
				
				if (selected == 0)
					JOptionPane.showMessageDialog(frame, "Make a selection first!");

				else if (qCount == 4) {
					q = client.requestQuestion();
					displayQuestion(q);
					displayChoices(q);
					jtnProgress.setText("Get Marks");
					qCount++;
				}
				else if (qCount == 5) {
					showResults();
				}
				else {
					choicesGroup.getButtonCount();
					q = client.requestQuestion();
					displayQuestion(q);
					displayChoices(q);
					qCount++;
				}
			}
		});

		frame.add(questionPanel, BorderLayout.CENTER);
		frame.revalidate();
		frame.repaint();

		displayQuestion(q);
		displayChoices(q);

		bottomPanel.add(jtnProgress);
		frame.add(bottomPanel, BorderLayout.PAGE_END);
		frame.revalidate();
		frame.repaint();
	}

	/**
	 * Displays the current question in it's text area
	 */
	private void displayQuestion(Question question) {

		String questionText = "";
		List<String> questionList = new ArrayList<String>();
		questionList = question.getQuestion();
		for (String q : questionList) {
			questionText = questionText + q + "\n";
		}
		questionArea.setText(questionText);
		questionPanel.add(questionArea);

	}

	/**
	 * Displays the current question choices, below the question
	 */
	private void displayChoices(Question question) {

		choicesPanel.removeAll();
		frame.repaint();
		choicesGroup = new ButtonGroup();

		List<String> choices = new ArrayList<String>();
		choices = question.getChoices();
		for (String c : choices) {
			JRadioButton choiceButton = new JRadioButton(c);
			choicesGroup.add(choiceButton);
			choicesPanel.add(choiceButton);
		}
		questionPanel.add(choicesPanel);
	}

	/**
	 * This method displays the users results out of 5
	 */
	private void showResults()
	{
		client.Disconnect();
		disconnect.setEnabled(false);
		connect.setEnabled(false);
		setServer.setEnabled(false);
		displayAllUsers.setEnabled(false);

		frame.remove(statusField);
		frame.remove(questionPanel);
		frame.remove(bottomPanel);

		String result = user.getScore() + " out of 5";

		JLabel resultScreen = new JLabel("<html><center><font color=white size = 6>Final Score</font><font size = 5><br><br>" + result + "</font></center></html>", JLabel.CENTER);

		statusField.setText("Disconnected");

		frame.add(resultScreen, BorderLayout.CENTER);
		frame.add(statusField, BorderLayout.SOUTH);
		frame.revalidate();
		frame.repaint();
	}

	/**
	 * This method initialises the GUI's menubar
	 */
	private void createMenubar()
	{
		//CONNECTION MENU

		JMenu menu = new JMenu("Connection");

		connect = new JMenuItem("Connect");
		connect.addActionListener(this);
		menu.add(connect);

		disconnect = new JMenuItem("Disconnect");
		disconnect.addActionListener(this);
		menu.add(disconnect);
		disconnect.setEnabled(false);
		menu.addSeparator();

		setServer = new JMenuItem("Set Server");
		setServer.addActionListener(this);
		menu.add(setServer);
		menu.addSeparator();

		exit = new JMenuItem("Exit");
		exit.addActionListener(this);
		menu.add(exit);
		
		menuBar.add(menu);

		//HELP MENU

		menu = new JMenu("Help");

		about = new JMenuItem("About");
		about.addActionListener(this);
		menu.add(about);

		menuBar.add(menu);

		//USERS MENU

		menu = new JMenu("Users");

		displayAllUsers = new JMenuItem("Display All Users");
		displayAllUsers.addActionListener(this);
		menu.add(displayAllUsers);

		menuBar.add(menu);

		frame.setJMenuBar(menuBar);
	}

	/**
	 * This method adds in the statusBar of the GUI, which remains for the duration of the application (and changes as necessary)
	 */
	private void addStatusBar()
	{
		welcomePageBottom = new JPanel(new BorderLayout());

		statusField = new JTextField("Connect to the server first");
		statusField.setEditable(false);
		welcomePageBottom.add(statusField, BorderLayout.SOUTH);

		namePanel = new JToolBar();

		nameEntry = new JTextField(20);
		JLabel yourNameText = new JLabel("Your name: ");

		namePanel.addSeparator(new Dimension(200, 0));
		namePanel.add(yourNameText);
		namePanel.add(nameEntry);
		namePanel.addSeparator(new Dimension(300, 0));
		namePanel.setFloatable(false);

		welcomePageBottom.add(namePanel, BorderLayout.CENTER);		
		frame.add(welcomePageBottom, BorderLayout.SOUTH);
	}

	/**
	 * Adjusts the status bar to reflect the current connection status
	 */
	private void connectedStatusBar()
	{
		frame.remove(welcomePageBottom);

		if (client.getIsConnected())
			statusField.setText("Connected to " + client.getHostname() + ":" + client.getPort());
		else
			statusField.setText("Disconnected");

		frame.add(statusField, BorderLayout.SOUTH);
		frame.revalidate();
		frame.repaint();	
	}

	/**
	 * This method receives the performed action and acts on it based on what action was triggered
	 * @param e The action event triggered
	 */
	public void actionPerformed(ActionEvent e) {
                if (e.getSource() == exit) {
			client.Disconnect();
			System.exit(0);
		}
		else if (e.getSource() == about)
			showAbout();
		else if (e.getSource() == connect)
		{
			String name = nameEntry.getText().trim();
			if (name.length() > 0) {
				user = new Student();
				user.setName(name);
				client.Connect(user);
				if (client.getIsConnected()) {
					disconnect.setEnabled(true);
					connect.setEnabled(false);
					connectedStatusBar();
				}
				runQuiz();
			}
			else
				registerWarning();
		}
		else if (e.getSource() == disconnect)
		{
			if (client.getIsConnected()) {
				disconnect.setEnabled(false);
				connect.setEnabled(true);
				client.Disconnect();
				connectedStatusBar();
			}
		}
		else if (e.getSource() == setServer)
		{
			setServerDialog();
		}
		else if (e.getSource() == displayAllUsers)
		{
			showAllUsers();
		}	
        }

	/**
	 * This method creates a message dialog that shows the application information
	 */
	private void showAbout() {
		String message = "Java Quiz Client Ver 1.0\n   based on Java sockets\n   by Aaron Colin Foote";	//NEED TO GET SCORE
		JOptionPane.showMessageDialog(frame, message);
	}

	/**
	 * This method creates a message dialog that warns the user to enter a name of at least one character
	 */
	private void registerWarning() {
		JOptionPane.showMessageDialog(frame, "Please enter your name first");
	}

	/**
	 * This method sets the desired hostname and port number, with validation of user input
	 */
	private void setServerDialog()
	{
		boolean correctInput = false;
		String input;
		while (!correctInput) {
			Object inputObj = JOptionPane.showInputDialog(null, "Server:port","Set Server", 1, null, null, "localhost:40213");
			input = (String)inputObj;
			if (input.length() > 0 && input.contains(":"))
			{
				try {
					String serverPortArgs[] = input.split(":");
					client.setHostname(serverPortArgs[0]);
					client.setPort(Integer.parseInt(serverPortArgs[1]));
					correctInput = true;
				}
				catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Input was of correct length and included ':' but failed, ensure that the port is a number!", "Set Server", 1);
					e.printStackTrace();
				}
			}
			else
				JOptionPane.showMessageDialog(null, "Input was incorrect, please try again!", "Set Server", 1);
		}
	}

	/**
	 * This method will display to the user all of the other concurrent users
	 */
	private void showAllUsers() {
		String usersString = "Current users: ";
		Vector<String> users = client.requestUsers();
		for(int i = 0; i < users.size() - 1; i++){
			usersString += users.get(i) + ", ";
		}
		usersString += users.get(users.size() - 1);
		JOptionPane.showMessageDialog(frame, usersString);
	}

	/**
	 * This method runs the GUI frame
	 * @param args Holds the passed arguments to the class
	 */
	public void main(Client c) {
		client = c;
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {		
				new QuizClientGUIFrame(client);
			}
		});
	}

	/**
	 * This method exits the program, called from the window listener
	 */
	private void exitProgram() {
		System.exit(0);
	}
}
