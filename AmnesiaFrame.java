/**
 *  AmnesiaFrame.java
 *  The AmnesiaFrame class creates the GUI as the user interface to display the
 *  mainframe and interact with the Amnesia game.
 *  @author Mauro Robles, Sean Njenga, and Zachary Desai
 *  Teacher: Mrs. Ishman
 *  Period: 4
 *  Date: 05-14-18
 */

// import statements
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;
import javax.swing.Timer;
import javax.swing.border.LineBorder;

public class AmnesiaFrame extends JFrame
{
	// instance variables
	private AmnesiaPlayer player;
	private int questionNum;
	private int questionCount;
	private int questionTime;
	private Timer questionTimer;
	private int screenNum;
	private boolean[] screenType;
	private AmnesiaAnswer[][] answerList;
	private JPanel panel;
	private JLabel backgroundLabel;
	private JLabel winnerLabel;
	private JButton sanityPoints;
	private JButton lifePoints;
	private JButton hintGifts;
	private JButton sanityGifts;
	private JButton timeGifts;
	private JButton pause;
	private JButton quit;
	private JButton proceed;
	private JButton question;
	private JButton time;
	private JButton answerA;
	private JButton answerB;
	private JButton answerC;
	private JButton winner;
	private MediaPlayer mediaPlayer;

	// class constants
	public final static int MAX_SANITY = 100;
	public final static int MAX_LIVES = 3;
	public final static int INSANITY = 50;
	public final static int SANITY_BONUS = 50;
	public final static int TIME_PENALTY = 10;
	public final static int ANSWER_PENALTY = 75;
	public final static int SECOND = 1000;
	public final static int QUESTION_TIME = 15;
	public final static int ANSWER_CHOICES = 3;
	public final static int NUM_GIFTS = 3;
	public final static int WIDTH_DIVISOR = 10;
	public final static int HEIGHT_DIVISOR = 25;
	public final static int SMALL_FONT = 16;
	public final static int MEDIUM_FONT = 26;
	public final static int LARGE_FONT = 36;
	public final static int ZERO = 0;
	public final static int ONE = 1;
	public final static int TWO = 2;
	public final static int THREE = 3;
	public final static int FOUR = 4;
	public final static int FIVE = 5;

	/** Constructs an AmnesiaFrame object with the standard defaults
	 *  @throws FileNotFoundException if a file is not found
	 *  @throws MalformedURLException if a file path is incorrect
	 */
	public AmnesiaFrame() throws FileNotFoundException, MalformedURLException
	{
		player = new AmnesiaPlayer();
		questionNum = ZERO;
		questionCount = ZERO;
		questionTime = ZERO;
		questionTimer = new Timer(SECOND, null);
		screenNum = ZERO - ONE;
		screenType = setScreens();
		answerList = setAnswers();
		panel = null;
		backgroundLabel = null;
		winnerLabel = null;
		sanityPoints = null;
		lifePoints = null;
		hintGifts = null;
		sanityGifts = null;
		timeGifts = null;
		pause = null;
		quit = null;
		proceed = null;
		question = null;
		time = null;
		answerA = null;
		answerB = null;
		answerC = null;
		winner = null;
		mediaPlayer = null;
    }

	/** Sets the array of screen types by reading the AmnesiaScreens.txt file
	 *  @return an array of booleans for whether or not the screen is a question
	 *  @throws FileNotFoundException if AmnesiaScreens.txt cannot be found
	 */
	public boolean[] setScreens() throws FileNotFoundException
	{
		Scanner scan = new Scanner(new File("AmnesiaScreens.txt"));
		boolean[] screens = new boolean[scan.nextInt()];
		while (scan.hasNext())
		{
			int index = scan.nextInt();
			String type = scan.next();
			if (type.equals("q"))
			{
				screens[index] = true;
				questionCount++;
			}
			else
			{
				screens[index] = false;
			}
		}
		return screens;
	}

	/** Sets the 2D array of answers by reading the AmnesiaAnswers.txt file
	 *  @return a 2D array of the AmnesiaAnswer objects for each question
	 *  @throws FileNotFoundException if AmnesiaAnswer.txt cannot be found
	 */
	public AmnesiaAnswer[][] setAnswers() throws FileNotFoundException
	{
		Scanner scan = new Scanner(new File("AmnesiaAnswers.txt"));
		AmnesiaAnswer[][] answers = new AmnesiaAnswer[questionCount][ANSWER_CHOICES];
		for (int row = ZERO; row < answers.length; row++)
		{
			for (int col = ZERO; col < answers[row].length; col++)
			{
				boolean correct = scan.hasNextInt();
				if (correct)
				{
					scan.nextInt();
				}
				answers[row][col] = new AmnesiaAnswer(scan.nextLine(), correct);
			}
			scan.nextLine();
		}
		return answers;
	}

	/** Shows the first screen by adding elements to the frame
	 *  @throws MalformedURLException if a file path is incorrect
	 */
	public void showScreen() throws MalformedURLException
	{
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setResizable(false);
		setUndecorated(true);
		setVisible(true);

		final int FRAME_WIDTH = getWidth();
		final int FRAME_HEIGHT = getHeight();
		final int BUTTON_WIDTH = getWidth() / WIDTH_DIVISOR;
		final int BUTTON_HEIGHT = getHeight() / HEIGHT_DIVISOR;

		panel = new JPanel();

		createBackground(FRAME_WIDTH, FRAME_HEIGHT);

		sanityPoints = new JButton("Sanity Points: " + player.getSanityPoints() + "/" + MAX_SANITY);
		sanityPoints.setBounds(ZERO, ZERO, BUTTON_WIDTH * THREE, BUTTON_HEIGHT * TWO);
		sanityPoints.setBackground(Color.black);
		sanityPoints.setForeground(Color.lightGray);
		sanityPoints.setFont(new Font("Courier", Font.BOLD, LARGE_FONT));
		sanityPoints.setEnabled(false);
		panel.add(sanityPoints);

		lifePoints = new JButton("Life Points: " + player.getLifePoints() + "/" + MAX_LIVES);
		lifePoints.setBounds(ZERO, BUTTON_HEIGHT * TWO, BUTTON_WIDTH * THREE, BUTTON_HEIGHT * TWO);
		lifePoints.setBackground(Color.black);
		lifePoints.setForeground(Color.lightGray);
		lifePoints.setFont(new Font("Courier", Font.BOLD, LARGE_FONT));
		lifePoints.setEnabled(false);
		panel.add(lifePoints);

		hintGifts = new JButton("Hint Gifts: " + player.getHintGifts());
		hintGifts.setBounds(ZERO, BUTTON_HEIGHT * FOUR, BUTTON_WIDTH, BUTTON_HEIGHT);
		hintGifts.setBackground(Color.black);
		hintGifts.setForeground(Color.lightGray);
		hintGifts.setFont(new Font("Courier", Font.BOLD, SMALL_FONT));
		hintGifts.setEnabled(false);
		hintGifts.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (player.getHintGifts() > ZERO && screenType[screenNum])
				{
					player.removeHintGift();
					hintGifts.setText("Hint Gifts: " + player.getHintGifts());
					int frontOrBack = ((int) (Math.random() * TWO));
					if (frontOrBack == ZERO)
					{
						if (!answerList[questionNum - ONE][ZERO].isCorrect())
						{
							answerA.setVisible(false);
						}
						else if (!answerList[questionNum - ONE][ONE].isCorrect())
						{
							answerB.setVisible(false);
						}
						else if (!answerList[questionNum - ONE][TWO].isCorrect())
						{
							answerC.setVisible(false);
						}
					}
					else
					{
						if (!answerList[questionNum - ONE][TWO].isCorrect())
						{
							answerC.setVisible(false);
						}
						else if (!answerList[questionNum - ONE][ONE].isCorrect())
						{
							answerB.setVisible(false);
						}
						else if (!answerList[questionNum - ONE][ZERO].isCorrect())
						{
							answerA.setVisible(false);
						}
					}
				}
				if (player.getHintGifts() == ZERO)
				{
					hintGifts.setEnabled(false);
				}
				time.grabFocus();
				question.grabFocus();
				answerA.grabFocus();
				answerB.grabFocus();
				answerC.grabFocus();
			}
		});
		panel.add(hintGifts);

		sanityGifts = new JButton("Sanity Gifts: " + player.getSanityGifts());
		sanityGifts.setBounds(BUTTON_WIDTH, BUTTON_HEIGHT * FOUR, BUTTON_WIDTH, BUTTON_HEIGHT);
		sanityGifts.setBackground(Color.black);
		sanityGifts.setForeground(Color.lightGray);
		sanityGifts.setFont(new Font("Courier", Font.BOLD, SMALL_FONT));
		sanityGifts.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (player.getSanityGifts() > ZERO)
				{
					player.removeSanityGift();
					sanityGifts.setText("Sanity Gifts: " + player.getSanityGifts());
					if (player.getSanityPoints() <= INSANITY)
					{
						panel.remove(backgroundLabel);
						repaint();
						File file = new File("screen" + screenNum + ".jpg");
    					try
    					{
    						URL link = file.toURI().toURL();
    						Icon icon = new ImageIcon(link);
							backgroundLabel = new JLabel(icon);
							backgroundLabel.setBounds(ZERO, ZERO, FRAME_WIDTH, FRAME_HEIGHT);
							panel.add(backgroundLabel);
    					}
    					catch (MalformedURLException malE)
    					{
    						System.out.println("malformed URL");
    					}
						mediaPlayer.stop();
    					String uriString = new File("sane.mp3").toURI().toString();
    					mediaPlayer = new MediaPlayer(new Media(uriString));
    					mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
    					mediaPlayer.play();
					}
					player.addSanityPoints(SANITY_BONUS);
					sanityPoints.setText("Sanity Points: " + player.getSanityPoints() + "/" + MAX_SANITY);
				}
				if (player.getSanityGifts() == ZERO)
				{
					sanityGifts.setEnabled(false);
				}
			}
		});
		panel.add(sanityGifts);

		timeGifts = new JButton("Time Gifts: " + player.getTimeGifts());
		timeGifts.setBounds(BUTTON_WIDTH * TWO, BUTTON_HEIGHT * FOUR, BUTTON_WIDTH, BUTTON_HEIGHT);
		timeGifts.setBackground(Color.black);
		timeGifts.setForeground(Color.lightGray);
		timeGifts.setFont(new Font("Courier", Font.BOLD, SMALL_FONT));
		timeGifts.setEnabled(false);
		timeGifts.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (player.getTimeGifts() > ZERO)
				{
					player.removeTimeGift();
					timeGifts.setText("Time Gifts: " + player.getTimeGifts());
					questionTime = QUESTION_TIME;
					time.setText("Time Left: " + questionTime);
				}
				if (player.getTimeGifts() == ZERO)
				{
					timeGifts.setEnabled(false);
				}
			}
		});
		panel.add(timeGifts);

		pause = new JButton("Pause");
		pause.setBounds(FRAME_WIDTH - (BUTTON_WIDTH * TWO / THREE), ZERO, BUTTON_WIDTH * TWO / THREE, BUTTON_HEIGHT * FIVE / TWO);
		pause.setBackground(Color.black);
		pause.setForeground(Color.lightGray);
		pause.setFont(new Font("Courier", Font.BOLD, MEDIUM_FONT));
		pause.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				questionTimer.stop();
				JWindow pauseWindow = new JWindow();
				JPanel pausePanel = new JPanel();
				pausePanel.setLayout(null);
				pausePanel.setBackground(Color.black);
				JButton resume = new JButton("Resume");
				resume.setBounds(FRAME_WIDTH / FOUR, FRAME_HEIGHT / FOUR, FRAME_WIDTH / TWO, FRAME_HEIGHT / TWO);
				resume.setBackground(Color.darkGray);
				resume.setForeground(Color.lightGray);
				resume.setFont(new Font("Courier", Font.BOLD, LARGE_FONT * FIVE));
				resume.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						pauseWindow.setVisible(false);
						if (screenType[screenNum])
						{
							questionTimer.start();
						}
					}
				});
				pausePanel.add(resume);
				pauseWindow.getContentPane().add(pausePanel);
				pauseWindow.setBounds(ZERO, ZERO, FRAME_WIDTH, FRAME_HEIGHT);
				pauseWindow.setVisible(true);
			}
		});
		panel.add(pause);

		quit = new JButton("Quit");
		quit.setBounds(FRAME_WIDTH - (BUTTON_WIDTH * TWO / THREE), BUTTON_HEIGHT * FIVE / TWO, BUTTON_WIDTH * TWO / THREE, BUTTON_HEIGHT * FIVE / TWO);
		quit.setBackground(Color.black);
		quit.setForeground(Color.lightGray);
		quit.setFont(new Font("Courier", Font.BOLD, MEDIUM_FONT));
		quit.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				System.exit(ZERO);
			}
		});
		panel.add(quit);

		proceed = new JButton("Continue");
		proceed.setBounds(ZERO, FRAME_HEIGHT - (BUTTON_HEIGHT * TWO), FRAME_WIDTH, BUTTON_HEIGHT * TWO);
		proceed.setBackground(Color.black);
		proceed.setForeground(Color.lightGray);
		proceed.setFont(new Font("Courier", Font.BOLD, MEDIUM_FONT));
		proceed.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					createBackground(FRAME_WIDTH, FRAME_HEIGHT);
				}
				catch (MalformedURLException malE)
				{
					System.out.println("malformed URL");
				}
			}
		});
		panel.add(proceed);

		panel.setLayout(null);
		add(panel);
		setVisible(true);
		sanityPoints.grabFocus();
		lifePoints.grabFocus();
		hintGifts.grabFocus();
		sanityGifts.grabFocus();
		timeGifts.grabFocus();
		pause.grabFocus();
		quit.grabFocus();
		proceed.grabFocus();
	}

	/** Sets the background for the current screen
	 *  @param width the int for the width of the screen
	 *  @param height the int for the height of the screen
	 *  @throws MalformedURLException if a file path is incorrect
	 */
	public void createBackground(int width, int height) throws MalformedURLException
	{
		if (pause != null)
		{
			pause.setEnabled(true);
		}

		if (question != null)
		{
			panel.remove(question);
		}
		if (time != null)
		{
			panel.remove(time);
		}
		if (answerA != null)
		{
			panel.remove(answerA);
		}
		if (answerB != null)
		{
			panel.remove(answerB);
		}
		if (answerC != null)
		{
			panel.remove(answerC);
		}
		for (Component comp : panel.getComponents())
		{
    			if (comp instanceof JLabel)
    			{
    				JLabel label = (JLabel) comp;
    				panel.remove(backgroundLabel);
    				repaint();
    			}
		}
		screenNum++;
		File file;
		if (player.getSanityPoints() > INSANITY)
		{
			file = new File("screen" + screenNum + ".jpg");
		}
		else
		{
			file = new File("screen" + screenNum + "i.jpg");
		}
    	URL link = file.toURI().toURL();
		Icon icon = new ImageIcon(link);
		backgroundLabel = new JLabel(icon);
		backgroundLabel.setBounds(ZERO, ZERO, width, height);
		panel.add(backgroundLabel);
		if (screenNum == 0)
		{
			new javafx.embed.swing.JFXPanel();
			String uriString = new File("intro.mp3").toURI().toString();
    		mediaPlayer = new MediaPlayer(new Media(uriString));
    		mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
    		mediaPlayer.play();
		}
		if (screenNum == 1)
		{
			mediaPlayer.stop();
    		String uriString = new File("sane.mp3").toURI().toString();
    		mediaPlayer = new MediaPlayer(new Media(uriString));
    		mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
    		mediaPlayer.play();
		}
		if (screenType[screenNum])
		{
			createQuestion(width, height);
			proceed.setEnabled(false);
		}
		else if (screenNum >= screenType.length - ONE)
		{
			winner = new JButton("WE HAVE A WINNER! Oh, it's you. Yay. Well, enjoy this GIF.");
			winner.setBackground(Color.green);
			winner.setBounds(ZERO, height / FOUR, width, height / FIVE);
			winner.setForeground(Color.black);
			winner.setFont(new Font("Courier", Font.BOLD, LARGE_FONT));
			winner.setEnabled(false);
			panel.add(winner);
			winner.grabFocus();
			File winnerFile = new File("winner.gif");
			URL winnerLink = winnerFile.toURI().toURL();
			Icon winnerIcon = new ImageIcon(winnerLink);
			winnerLabel = new JLabel(winnerIcon);
			winnerLabel.setBounds(width * THREE / FOUR / TWO, height / TWO, width / FOUR, height / THREE);
			panel.add(winnerLabel);
			proceed.setText("Exit Game");
			ActionListener[] proceedListeners = proceed.getActionListeners();
			for (ActionListener al : proceedListeners)
			{
				proceed.removeActionListener(al);
			}
			proceed.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					System.exit(ZERO);
				}
			});
			if (player.getLifePoints() > ZERO)
			{
				mediaPlayer.stop();
    			String uriString = new File("winner.mp3").toURI().toString();
    			mediaPlayer = new MediaPlayer(new Media(uriString));
    			mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
    			mediaPlayer.play();
			}
		}
	}

	/** Creates the question for the current screen if it is a question screen
	 *  @param width the int for the width of the screen
	 *  @param height the int for the height of the screen
	 */
	public void createQuestion(int width, int height)
	{
		if (player.getHintGifts() > ZERO)
		{
			hintGifts.setEnabled(true);
		}
		if (player.getSanityGifts() > ZERO)
		{
			sanityGifts.setEnabled(true);
		}
		if (player.getTimeGifts() > ZERO)
		{
			timeGifts.setEnabled(true);
		}
		questionNum++;
		questionTime = QUESTION_TIME;

		question = new JButton("Question " + questionNum + "/" + questionCount);
		question.setBounds(width * TWO / THREE, ZERO, width / FIVE, height / FIVE);
		question.setBackground(Color.black);
		question.setForeground(Color.lightGray);
		question.setFont(new Font("Courier", Font.BOLD, LARGE_FONT));
		question.setEnabled(false);
		panel.add(question);

		time = new JButton("Time Left: " + questionTime);
		time.setBounds(width * TWO / FIVE, ZERO, width / FIVE, height / FIVE);
		time.setBackground(Color.black);
		time.setForeground(Color.lightGray);
		time.setFont(new Font("Courier", Font.BOLD, LARGE_FONT));
		time.setEnabled(false);
		panel.add(time);

		if (questionNum >= questionCount)
		{
			hintGifts.setEnabled(false);
			sanityGifts.setEnabled(false);
			timeGifts.setEnabled(false);
		}

		answerA = new JButton(answerList[questionNum - ONE][ZERO].getAnswer());
		answerA.setBounds(ZERO, height * THREE / FOUR, width / THREE, height / FIVE / TWO);
		answerA.setBackground(Color.black);
		answerA.setForeground(Color.lightGray);
		answerA.setFont(new Font("Courier", Font.BOLD, SMALL_FONT));
		answerA.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				questionTimer.stop();
				pause.setEnabled(false);
				hintGifts.setEnabled(false);
				timeGifts.setEnabled(false);
				answerA.setEnabled(false);
				answerB.setEnabled(false);
				answerC.setEnabled(false);
				proceed.setEnabled(true);
				if (questionNum % ANSWER_CHOICES == ZERO && questionNum < questionCount)
				{
					try
					{
						rollDice();
					}
					catch (MalformedURLException malE)
					{
						System.out.println("malformed URL");
					}
				}
				if (answerList[questionNum - ONE][ZERO].isCorrect())
				{
					answerA.setBackground(Color.green);
				}
				else
				{
					answerA.setBackground(Color.red);
					if (answerList[questionNum - ONE][ONE].isCorrect())
					{
						answerB.setBackground(Color.green);
					}
					else if (answerList[questionNum - ONE][TWO].isCorrect())
					{
						answerC.setBackground(Color.green);
					}
					questionTimer.stop();
					if (questionNum < questionCount)
					{
						player.loseSanityPoints(ANSWER_PENALTY);
					}
					else
					{
						player.loseSanityPoints(ANSWER_PENALTY * TWO);
					}
					if (player.getSanityPoints() <= ZERO)
					{
						mediaPlayer.stop();
    					String uriString = new File("lifeLoss.mp3").toURI().toString();
    					mediaPlayer = new MediaPlayer(new Media(uriString));
    					mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
    					mediaPlayer.play();
						player.loseALife();
						JWindow loseALifeWindow = new JWindow();
						JPanel loseALifePanel = new JPanel();
						loseALifePanel.setLayout(null);
						loseALifePanel.setBackground(Color.black);
						JButton loseALifeButton = new JButton("You lost a life...click to resume.");
						loseALifeButton.setBounds(width / FOUR, height / FOUR, width / TWO, height / TWO);
						loseALifeButton.setBackground(Color.red);
						loseALifeButton.setForeground(Color.black);
						loseALifeButton.setFont(new Font("Courier", Font.BOLD, LARGE_FONT));
						loseALifeButton.addActionListener(new ActionListener()
						{
							public void actionPerformed(ActionEvent e)
							{
								loseALifeWindow.setVisible(false);
								if (player.getLifePoints() > ZERO)
								{
									mediaPlayer.stop();
    								String uriString = new File("sane.mp3").toURI().toString();
    								mediaPlayer = new MediaPlayer(new Media(uriString));
    								mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
    								mediaPlayer.play();
								}
							}
						});
						loseALifePanel.add(loseALifeButton);
						loseALifeWindow.getContentPane().add(loseALifePanel);
						loseALifeWindow.setBounds(ZERO, ZERO, width, height);
						loseALifeWindow.setVisible(true);
						lifePoints.setText("Life Points: " + player.getLifePoints() + "/" + MAX_LIVES);
						panel.remove(backgroundLabel);
						repaint();
						File file = new File("screen" + screenNum + ".jpg");
						try
						{
							URL link = file.toURI().toURL();
							Icon icon = new ImageIcon(link);
							backgroundLabel = new JLabel(icon);
							backgroundLabel.setBounds(ZERO, ZERO, width, height);
							panel.add(backgroundLabel);
						}
						catch (MalformedURLException malE)
						{
							System.out.println("malformed URL");
						}
					}
					sanityPoints.setText("Sanity Points: " + player.getSanityPoints() + "/" + MAX_SANITY);
					if (player.getLifePoints() <= ZERO)
					{
						sanityPoints.setBackground(Color.red);
						lifePoints.setBackground(Color.red);
						proceed.addActionListener(new ActionListener()
						{
							public void actionPerformed(ActionEvent e)
							{
								JWindow gameOverWindow = new JWindow();
								JPanel gameOverPanel = new JPanel();
								gameOverPanel.setLayout(null);
								gameOverPanel.setBackground(Color.black);
								JButton gameOverButton = new JButton("Sorry, YOU LOST!! Click to exit.");
								gameOverButton.setBounds(width / FOUR, height / FOUR, width / TWO, height / TWO);
								gameOverButton.setBackground(Color.red);
								gameOverButton.setForeground(Color.black);
								gameOverButton.setFont(new Font("Courier", Font.BOLD, LARGE_FONT));
								gameOverButton.addActionListener(new ActionListener()
								{
									public void actionPerformed(ActionEvent e)
									{
										System.exit(ZERO);
									}
								});
								gameOverPanel.add(gameOverButton);
								gameOverWindow.getContentPane().add(gameOverPanel);
								gameOverWindow.setBounds(ZERO, ZERO, width, height);
								gameOverWindow.setVisible(true);
								mediaPlayer.stop();
    							String uriString = new File("loser.mp3").toURI().toString();
    							mediaPlayer = new MediaPlayer(new Media(uriString));
    							mediaPlayer.play();
							}
						});
					}
					if (player.getSanityPoints() <= INSANITY)
					{
						mediaPlayer.stop();
						String uriString;
						if (player.getLifePoints() > ZERO)
						{
							uriString = new File("insane.mp3").toURI().toString();
						}
						else
						{
							uriString = new File("lifeLoss.mp3").toURI().toString();
						}
    					mediaPlayer = new MediaPlayer(new Media(uriString));
    					mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
    					mediaPlayer.play();
					}
				}
				if (player.getSanityPoints() <= INSANITY)
				{
					panel.remove(backgroundLabel);
					repaint();
					File file = new File("screen" + screenNum + "i.jpg");
    				try
    				{
    					URL link = file.toURI().toURL();
    					Icon icon = new ImageIcon(link);
						backgroundLabel = new JLabel(icon);
						backgroundLabel.setBounds(ZERO, ZERO, width, height);
						panel.add(backgroundLabel);
    				}
    				catch (MalformedURLException malE)
    				{
    					System.out.println("malformed URL");
    				}
				}
				if (questionNum == questionCount)
				{
					proceed.setText("Very well, you would not feel anything.");
				}
			}
		});
		answerA.setVisible(true);
		panel.add(answerA);

		answerB = new JButton(answerList[questionNum - ONE][ONE].getAnswer());
		answerB.setBounds(width / THREE, height * THREE / FOUR, width / THREE, height / FIVE / TWO);
		answerB.setBackground(Color.black);
		answerB.setForeground(Color.lightGray);
		answerB.setFont(new Font("Courier", Font.BOLD, SMALL_FONT));
		answerB.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				questionTimer.stop();
				pause.setEnabled(false);
				hintGifts.setEnabled(false);
				timeGifts.setEnabled(false);
				answerA.setEnabled(false);
				answerB.setEnabled(false);
				answerC.setEnabled(false);
				proceed.setEnabled(true);
				if (questionNum % ANSWER_CHOICES == ZERO && questionNum < questionCount)
				{
					try
					{
						rollDice();
					}
					catch (MalformedURLException malE)
					{
						System.out.println("malformed URL");
					}
				}
				if (answerList[questionNum - ONE][ONE].isCorrect())
				{
					answerB.setBackground(Color.green);
				}
				else
				{
					answerB.setBackground(Color.red);
					if (answerList[questionNum - ONE][ZERO].isCorrect())
					{
						answerA.setBackground(Color.green);
					}
					else if (answerList[questionNum - ONE][TWO].isCorrect())
					{
						answerC.setBackground(Color.green);
					}
					if (questionNum < questionCount)
					{
						player.loseSanityPoints(ANSWER_PENALTY);
					}
					else
					{
						player.loseSanityPoints(ANSWER_PENALTY * TWO);
					}
					if (player.getSanityPoints() <= ZERO)
					{
						mediaPlayer.stop();
    					String uriString = new File("lifeLoss.mp3").toURI().toString();
    					mediaPlayer = new MediaPlayer(new Media(uriString));
    					mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
    					mediaPlayer.play();
						player.loseALife();
						JWindow loseALifeWindow = new JWindow();
						JPanel loseALifePanel = new JPanel();
						loseALifePanel.setLayout(null);
						loseALifePanel.setBackground(Color.black);
						JButton loseALifeButton = new JButton("You lost a life...click to resume.");
						loseALifeButton.setBounds(width / FOUR, height / FOUR, width / TWO, height / TWO);
						loseALifeButton.setBackground(Color.red);
						loseALifeButton.setForeground(Color.black);
						loseALifeButton.setFont(new Font("Courier", Font.BOLD, LARGE_FONT));
						loseALifeButton.addActionListener(new ActionListener()
						{
							public void actionPerformed(ActionEvent e)
							{
								loseALifeWindow.setVisible(false);
								if (player.getLifePoints() > ZERO)
								{
									mediaPlayer.stop();
    								String uriString = new File("sane.mp3").toURI().toString();
    								mediaPlayer = new MediaPlayer(new Media(uriString));
    								mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
    								mediaPlayer.play();
								}
							}
						});
						loseALifePanel.add(loseALifeButton);
						loseALifeWindow.getContentPane().add(loseALifePanel);
						loseALifeWindow.setBounds(ZERO, ZERO, width, height);
						loseALifeWindow.setVisible(true);
						lifePoints.setText("Life Points: " + player.getLifePoints() + "/" + MAX_LIVES);
						panel.remove(backgroundLabel);
						repaint();
						File file = new File("screen" + screenNum + ".jpg");
						try
						{
							URL link = file.toURI().toURL();
							Icon icon = new ImageIcon(link);
							backgroundLabel = new JLabel(icon);
							backgroundLabel.setBounds(ZERO, ZERO, width, height);
							panel.add(backgroundLabel);
						}
						catch (MalformedURLException malE)
						{
							System.out.println("malformed URL");
						}
					}
					sanityPoints.setText("Sanity Points: " + player.getSanityPoints() + "/" + MAX_SANITY);
					if (player.getLifePoints() <= ZERO)
					{
						sanityPoints.setBackground(Color.red);
						lifePoints.setBackground(Color.red);
						proceed.addActionListener(new ActionListener()
						{
							public void actionPerformed(ActionEvent e)
							{
								JWindow gameOverWindow = new JWindow();
								JPanel gameOverPanel = new JPanel();
								gameOverPanel.setLayout(null);
								gameOverPanel.setBackground(Color.black);
								JButton gameOverButton = new JButton("Sorry, YOU LOST!! Click to exit.");
								gameOverButton.setBounds(width / FOUR, height / FOUR, width / TWO, height / TWO);
								gameOverButton.setBackground(Color.red);
								gameOverButton.setForeground(Color.black);
								gameOverButton.setFont(new Font("Courier", Font.BOLD, LARGE_FONT));
								gameOverButton.addActionListener(new ActionListener()
								{
									public void actionPerformed(ActionEvent e)
									{
										System.exit(ZERO);
									}
								});
								gameOverPanel.add(gameOverButton);
								gameOverWindow.getContentPane().add(gameOverPanel);
								gameOverWindow.setBounds(ZERO, ZERO, width, height);
								gameOverWindow.setVisible(true);
								mediaPlayer.stop();
    							String uriString = new File("loser.mp3").toURI().toString();
    							mediaPlayer = new MediaPlayer(new Media(uriString));
    							mediaPlayer.play();
							}
						});
					}
					if (player.getSanityPoints() <= INSANITY)
					{
						mediaPlayer.stop();
						String uriString;
						if (player.getLifePoints() > ZERO)
						{
							uriString = new File("insane.mp3").toURI().toString();
						}
						else
						{
							uriString = new File("lifeLoss.mp3").toURI().toString();
						}
    					mediaPlayer = new MediaPlayer(new Media(uriString));
    					mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
    					mediaPlayer.play();
					}
				}
				if (player.getSanityPoints() <= INSANITY)
				{
					panel.remove(backgroundLabel);
					repaint();
					File file = new File("screen" + screenNum + "i.jpg");
    				try
    				{
    					URL link = file.toURI().toURL();
    					Icon icon = new ImageIcon(link);
						backgroundLabel = new JLabel(icon);
						backgroundLabel.setBounds(ZERO, ZERO, width, height);
						panel.add(backgroundLabel);
    				}
    				catch (MalformedURLException malE)
    				{
    					System.out.println("malformed URL");
    				}
				}
				if (questionNum == questionCount)
				{
					proceed.setText("It doesn’t matter if you’re not ready; you will die.");
				}
			}
		});
		answerB.setVisible(true);
		panel.add(answerB);

		answerC = new JButton(answerList[questionNum - ONE][TWO].getAnswer());
		answerC.setBounds(width * TWO / THREE, height * THREE / FOUR, width / THREE, height / FIVE / TWO);
		answerC.setBackground(Color.black);
		answerC.setForeground(Color.lightGray);
		answerC.setFont(new Font("Courier", Font.BOLD, SMALL_FONT));
		answerC.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				questionTimer.stop();
				pause.setEnabled(false);
				hintGifts.setEnabled(false);
				timeGifts.setEnabled(false);
				answerA.setEnabled(false);
				answerB.setEnabled(false);
				answerC.setEnabled(false);
				proceed.setEnabled(true);
				if (questionNum % ANSWER_CHOICES == ZERO && questionNum < questionCount)
				{
					try
					{
						rollDice();
					}
					catch (MalformedURLException malE)
					{
						System.out.println("malformed URL");
					}
				}
				if (answerList[questionNum - ONE][TWO].isCorrect())
				{
					answerC.setBackground(Color.green);
				}
				else
				{
					answerC.setBackground(Color.red);
					if (answerList[questionNum - ONE][ZERO].isCorrect())
					{
						answerA.setBackground(Color.green);
					}
					else if (answerList[questionNum - ONE][ONE].isCorrect())
					{
						answerB.setBackground(Color.green);
					}
					questionTimer.stop();
					if (questionNum < questionCount)
					{
						player.loseSanityPoints(ANSWER_PENALTY);
					}
					else
					{
						player.loseSanityPoints(ANSWER_PENALTY * TWO);
					}
					if (player.getSanityPoints() <= 0)
					{
						mediaPlayer.stop();
    					String uriString = new File("lifeLoss.mp3").toURI().toString();
    					mediaPlayer = new MediaPlayer(new Media(uriString));
    					mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
    					mediaPlayer.play();
						player.loseALife();
						JWindow loseALifeWindow = new JWindow();
						JPanel loseALifePanel = new JPanel();
						loseALifePanel.setLayout(null);
						loseALifePanel.setBackground(Color.black);
						JButton loseALifeButton = new JButton("You lost a life...click to resume.");
						loseALifeButton.setBounds(width / FOUR, height / FOUR, width / TWO, height / TWO);
						loseALifeButton.setBackground(Color.red);
						loseALifeButton.setForeground(Color.black);
						loseALifeButton.setFont(new Font("Courier", Font.BOLD, LARGE_FONT));
						loseALifeButton.addActionListener(new ActionListener()
						{
							public void actionPerformed(ActionEvent e)
							{
								loseALifeWindow.setVisible(false);
								if (player.getLifePoints() > ZERO)
								{
									mediaPlayer.stop();
    								String uriString = new File("sane.mp3").toURI().toString();
    								mediaPlayer = new MediaPlayer(new Media(uriString));
    								mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
    								mediaPlayer.play();
								}
							}
						});
						loseALifePanel.add(loseALifeButton);
						loseALifeWindow.getContentPane().add(loseALifePanel);
						loseALifeWindow.setBounds(ZERO, ZERO, width, height);
						loseALifeWindow.setVisible(true);
						lifePoints.setText("Life Points: " + player.getLifePoints() + "/" + MAX_LIVES);
						panel.remove(backgroundLabel);
						repaint();
						File file = new File("screen" + screenNum + ".jpg");
						try
						{
							URL link = file.toURI().toURL();
							Icon icon = new ImageIcon(link);
							backgroundLabel = new JLabel(icon);
							backgroundLabel.setBounds(ZERO, ZERO, width, height);
							panel.add(backgroundLabel);
						}
						catch (MalformedURLException malE)
						{
							System.out.println("malformed URL");
						}
					}
					sanityPoints.setText("Sanity Points: " + player.getSanityPoints() + "/" + MAX_SANITY);
					if (player.getLifePoints() <= ZERO)
					{
						sanityPoints.setBackground(Color.red);
						lifePoints.setBackground(Color.red);
						proceed.addActionListener(new ActionListener()
						{
							public void actionPerformed(ActionEvent e)
							{
								JWindow gameOverWindow = new JWindow();
								JPanel gameOverPanel = new JPanel();
								gameOverPanel.setLayout(null);
								gameOverPanel.setBackground(Color.black);
								JButton gameOverButton = new JButton("Sorry, YOU LOST!! Click to exit.");
								gameOverButton.setBounds(width / FOUR, height / FOUR, width / TWO, height / TWO);
								gameOverButton.setBackground(Color.red);
								gameOverButton.setForeground(Color.black);
								gameOverButton.setFont(new Font("Courier", Font.BOLD, LARGE_FONT));
								gameOverButton.addActionListener(new ActionListener()
								{
									public void actionPerformed(ActionEvent e)
									{
										System.exit(ZERO);
									}
								});
								gameOverPanel.add(gameOverButton);
								gameOverWindow.getContentPane().add(gameOverPanel);
								gameOverWindow.setBounds(ZERO, ZERO, width, height);
								gameOverWindow.setVisible(true);
								mediaPlayer.stop();
    							String uriString = new File("loser.mp3").toURI().toString();
    							mediaPlayer = new MediaPlayer(new Media(uriString));
    							mediaPlayer.play();
							}
						});
					}
					if (player.getSanityPoints() <= INSANITY)
					{
						mediaPlayer.stop();
						String uriString;
						if (player.getLifePoints() > ZERO)
						{
							uriString = new File("insane.mp3").toURI().toString();
						}
						else
						{
							uriString = new File("lifeLoss.mp3").toURI().toString();
						}
    					mediaPlayer = new MediaPlayer(new Media(uriString));
    					mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
    					mediaPlayer.play();
					}
				}
				if (player.getSanityPoints() <= INSANITY)
				{
					panel.remove(backgroundLabel);
					repaint();
					File file = new File("screen" + screenNum + "i.jpg");
    				try
    				{
    					URL link = file.toURI().toURL();
    					Icon icon = new ImageIcon(link);
						backgroundLabel = new JLabel(icon);
						backgroundLabel.setBounds(ZERO, ZERO, width, height);
						panel.add(backgroundLabel);
    				}
    				catch (MalformedURLException malE)
    				{
    					System.out.println("malformed URL");
    				}
				}
				if (questionNum == questionCount)
				{
					proceed.setText("Yes, your brother forgives. Now time to die.");
				}
			}
		});
		answerC.setVisible(true);
		panel.add(answerC);

		time.grabFocus();
		question.grabFocus();
		answerA.grabFocus();
		answerB.grabFocus();
		answerC.grabFocus();

		ActionListener[] questionListeners = questionTimer.getActionListeners();
		for (ActionListener al : questionListeners)
		{
			questionTimer.removeActionListener(al);
		}
		questionTimer.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				questionTime--;
				time.setText("Time Left: " + questionTime);
				if (questionTime < ZERO)
				{
					player.loseSanityPoints(TIME_PENALTY);
				}
				if (player.getSanityPoints() >= INSANITY - FIVE && player.getSanityPoints() <= INSANITY)
				{
					panel.remove(backgroundLabel);
					repaint();
					File file = new File("screen" + screenNum + "i.jpg");
    				try
    				{
    					URL link = file.toURI().toURL();
    					Icon icon = new ImageIcon(link);
						backgroundLabel = new JLabel(icon);
						backgroundLabel.setBounds(ZERO, ZERO, width, height);
						panel.add(backgroundLabel);
    				}
    				catch (MalformedURLException malE)
    				{
    					System.out.println("malformed URL");
    				}
					mediaPlayer.stop();
    				String uriString = new File("insane.mp3").toURI().toString();
    				mediaPlayer = new MediaPlayer(new Media(uriString));
    				mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
    				mediaPlayer.play();
				}
				if (player.getSanityPoints() <= ZERO)
				{
					mediaPlayer.stop();
    				String uriString = new File("lifeLoss.mp3").toURI().toString();
    				mediaPlayer = new MediaPlayer(new Media(uriString));
    				mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
    				mediaPlayer.play();
    				
					questionTimer.stop();
					pause.setEnabled(false);
					hintGifts.setEnabled(false);
					timeGifts.setEnabled(false);
					answerA.setEnabled(false);
					answerB.setEnabled(false);
					answerC.setEnabled(false);
					proceed.setEnabled(true);

					if (answerList[questionNum - ONE][ZERO].isCorrect())
					{
						answerA.setBackground(Color.green);
					}
					if (answerList[questionNum - ONE][ONE].isCorrect())
					{
						answerB.setBackground(Color.green);
					}
					if (answerList[questionNum - ONE][TWO].isCorrect())
					{
						answerC.setBackground(Color.green);
					}

					if (questionNum % ANSWER_CHOICES == ZERO && questionNum < questionCount)
					{
						try
						{
							rollDice();
						}
						catch (MalformedURLException malE)
						{
							System.out.println("malformed URL");
						}
					}
					player.loseALife();
					JWindow loseALifeWindow = new JWindow();
					JPanel loseALifePanel = new JPanel();
					loseALifePanel.setLayout(null);
					loseALifePanel.setBackground(Color.black);
					JButton loseALifeButton = new JButton("You lost a life...click to resume.");
					loseALifeButton.setBounds(width / FOUR, height / FOUR, width / TWO, height / TWO);
					loseALifeButton.setBackground(Color.red);
					loseALifeButton.setForeground(Color.black);
					loseALifeButton.setFont(new Font("Courier", Font.BOLD, LARGE_FONT));
					loseALifeButton.addActionListener(new ActionListener()
					{
						public void actionPerformed(ActionEvent e)
						{
							loseALifeWindow.setVisible(false);
							if (player.getLifePoints() > ZERO)
							{
								mediaPlayer.stop();
    							String uriString = new File("sane.mp3").toURI().toString();
    							mediaPlayer = new MediaPlayer(new Media(uriString));
    							mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
    							mediaPlayer.play();
							}
						}
					});
					loseALifePanel.add(loseALifeButton);
					loseALifeWindow.getContentPane().add(loseALifePanel);
					loseALifeWindow.setBounds(ZERO, ZERO, width, height);
					loseALifeWindow.setVisible(true);
					lifePoints.setText("Life Points: " + player.getLifePoints() + "/" + MAX_LIVES);
					panel.remove(backgroundLabel);
					repaint();
					File file = new File("screen" + screenNum + ".jpg");
					try
					{
						URL link = file.toURI().toURL();
						Icon icon = new ImageIcon(link);
						backgroundLabel = new JLabel(icon);
						backgroundLabel.setBounds(ZERO, ZERO, width, height);
						panel.add(backgroundLabel);
					}
					catch (MalformedURLException malE)
					{
						System.out.println("malformed URL");
					}
				}
				if (player.getLifePoints() <= ZERO)
				{
					sanityPoints.setBackground(Color.red);
					lifePoints.setBackground(Color.red);
					proceed.addActionListener(new ActionListener()
					{
						public void actionPerformed(ActionEvent e)
						{
							JWindow gameOverWindow = new JWindow();
							JPanel gameOverPanel = new JPanel();
							gameOverPanel.setLayout(null);
							gameOverPanel.setBackground(Color.black);
							JButton gameOverButton = new JButton("Sorry, YOU LOST!! Click to exit.");
							gameOverButton.setBounds(width / FOUR, height / FOUR, width / TWO, height / TWO);
							gameOverButton.setBackground(Color.red);
							gameOverButton.setForeground(Color.black);
							gameOverButton.setFont(new Font("Courier", Font.BOLD, LARGE_FONT));
							gameOverButton.addActionListener(new ActionListener()
							{
								public void actionPerformed(ActionEvent e)
								{
									System.exit(ZERO);
								}
							});
							gameOverPanel.add(gameOverButton);
							gameOverWindow.getContentPane().add(gameOverPanel);
							gameOverWindow.setBounds(ZERO, ZERO, width, height);
							gameOverWindow.setVisible(true);
							mediaPlayer.stop();
    						String uriString = new File("loser.mp3").toURI().toString();
    						mediaPlayer = new MediaPlayer(new Media(uriString));
    						mediaPlayer.play();
						}
					});
				}
				sanityPoints.setText("Sanity Points: " + player.getSanityPoints() + "/" + MAX_SANITY);
			}
		});
		questionTimer.start();
	}

	/** Rolls the dice after the third and sixth questions in a new window
	 *  @throws MalformedURLException if a file path is incorrect
	 */
	public void rollDice() throws MalformedURLException
	{
		proceed.setEnabled(false);
		AmnesiaDice dice = new AmnesiaDice();
		Icon diceIcon = dice.getIcon();
		JLabel diceLabel = new JLabel(diceIcon);
		JButton oddRoll = new JButton("Pick Odd and Roll");
		JButton evenRoll = new JButton("Pick Even and Roll");
		JPanel dicePanel = new JPanel();
		dicePanel.setBorder(new LineBorder(Color.black));
		dicePanel.setBackground(Color.gray);
		dicePanel.setLayout(null);
		dicePanel.add(diceLabel);
		dicePanel.add(oddRoll);
		dicePanel.add(evenRoll);
		int xPos = getWidth() * FIVE / THREE / FOUR;
		int yPos = getHeight() * THREE / FOUR / TWO;
		int width = getWidth() / TWO / THREE;
		int height = getHeight() / FOUR;
		diceLabel.setBounds(width / TWO / THREE, FIVE, width * TWO / THREE, height * TWO / THREE);
		oddRoll.setBounds(FIVE, height * THREE / FOUR - FIVE, width / TWO - (FIVE * TWO), height / FOUR);
		evenRoll.setBounds(width / TWO + FIVE, height * THREE / FOUR - FIVE, width / TWO - (FIVE * TWO), height / FOUR);
		JWindow diceWindow = new JWindow();
		diceWindow.getContentPane().add(dicePanel);
		diceWindow.setBounds(xPos, yPos, width, height);
		diceWindow.setVisible(true);
		final int SEVEN = 7;
		final int ELEVEN = 11;
		oddRoll.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					boolean winner = dice.roll(false);
					diceLabel.setIcon(dice.getIcon());
					dicePanel.remove(oddRoll);
					dicePanel.remove(evenRoll);
					JLabel info;
					JLabel giftInfo;
					JButton diceProceed = new JButton("Click to continue.");
					if (winner)
					{
						int prizeNum = ((int) (Math.random() * NUM_GIFTS));
						if (prizeNum == ZERO)
						{
							info = new JLabel("Congratulations, you have won a HINT GIFT!");
							giftInfo = new JLabel("Use this gift to remove an incorrect answer choice.");
							player.addHintGift();
							hintGifts.setText("Hint Gifts: " + player.getHintGifts());
						}
						else if (prizeNum == ONE)
						{
							info = new JLabel("Congratulations, you have won a SANITY GIFT!");
							giftInfo = new JLabel("Use this gift to replenish your sanity points.");
							player.addSanityGift();
							sanityGifts.setText("Sanity Gifts: " + player.getSanityGifts());
							sanityGifts.setEnabled(true);
						}
						else
						{
							info = new JLabel("Congratulations, you have won a TIME GIFT!");
							giftInfo = new JLabel("Use this gift to add more time for a question.");
							player.addTimeGift();
							timeGifts.setText("Time Gifts: " + player.getTimeGifts());
						}
					}
					else
					{
						info = new JLabel("I'm sorry, but you did not win a prize.");
						giftInfo = null;
					}
					dicePanel.add(info);
					info.setBounds(FIVE, height * TWO / THREE, width - (FIVE * TWO), height / FOUR / TWO);
					if (giftInfo != null)
					{
						dicePanel.add(giftInfo);
						giftInfo.setBounds(FIVE, height * FOUR * TWO / ELEVEN, width - (FIVE * TWO), height / FOUR / TWO);
					}
					dicePanel.add(diceProceed);
					diceProceed.setBounds(FIVE, height * SEVEN / FOUR / TWO - FIVE, width - 10, height / FOUR / TWO);
					diceProceed.addActionListener(new ActionListener()
					{
						public void actionPerformed(ActionEvent e)
						{
							diceWindow.dispose();
							proceed.setEnabled(true);
						}
					});
					diceWindow.repaint();
				}
				catch (MalformedURLException malE)
				{
					System.out.println("malformed URL");
				}
			}
		});
		evenRoll.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					boolean winner = dice.roll(true);
					diceLabel.setIcon(dice.getIcon());
					dicePanel.remove(oddRoll);
					dicePanel.remove(evenRoll);
					JLabel info;
					JLabel giftInfo;
					JButton diceProceed = new JButton("Click to continue.");
					if (winner)
					{
						int prizeNum = ((int) (Math.random() * NUM_GIFTS));
						if (prizeNum == ZERO)
						{
							info = new JLabel("Congratulations, you have won a HINT GIFT!");
							giftInfo = new JLabel("Use this gift to remove an incorrect answer choice.");
							player.addHintGift();
							hintGifts.setText("Hint Gifts: " + player.getHintGifts());
						}
						else if (prizeNum == ONE)
						{
							info = new JLabel("Congratulations, you have won a SANITY GIFT!");
							giftInfo = new JLabel("Use this gift to replenish your sanity points.");
							player.addSanityGift();
							sanityGifts.setText("Sanity Gifts: " + player.getSanityGifts());
							sanityGifts.setEnabled(true);
						}
						else
						{
							info = new JLabel("Congratulations, you have won a TIME GIFT!");
							giftInfo = new JLabel("Use this gift to add more time for a question.");
							player.addTimeGift();
							timeGifts.setText("Time Gifts: " + player.getTimeGifts());
						}
					}
					else
					{
						info = new JLabel("I'm sorry, but you did not win a prize.");
						giftInfo = null;
					}
					dicePanel.add(info);
					info.setBounds(FIVE, height * TWO / THREE, width - (FIVE * TWO), height / FOUR / TWO);
					if (giftInfo != null)
					{
						dicePanel.add(giftInfo);
						giftInfo.setBounds(FIVE, height * FOUR * TWO / ELEVEN, width - (FIVE * TWO), height / FOUR / TWO);
					}
					dicePanel.add(diceProceed);
					diceProceed.setBounds(FIVE, height * SEVEN / FOUR / TWO - FIVE, width - 10, height / FOUR / TWO);
					diceProceed.addActionListener(new ActionListener()
					{
						public void actionPerformed(ActionEvent e)
						{
							diceWindow.dispose();
							proceed.setEnabled(true);
						}
					});
					diceWindow.repaint();
				}
				catch (MalformedURLException malE)
				{
					System.out.println("malformed URL");
				}
			}
		});
	}
}