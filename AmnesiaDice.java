/**
 * AmnesiaDice.java
 * The AmnesiaDice class creates a JFrame that allows the player to select three dice sides (1-6)
 * and rolls them. After it is finished rolling it checks to see if one of the sides you selected
 * was landed on and if so the player will recieve a random gift.
 * @author Mauro Robles, Sean Njenga, and Zachary Desai
 * Teacher: Mrs. Ishman
 * Period: 4
 * Date: 05-14-18
 */

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Rectangle;
import java.awt.Font;
import java.awt.Image;

public class AmnesiaDice extends JFrame
{
	private JPanel contentPane;
	private JButton rollButton;
	private JLabel diceImages;
	private JLabel background;
	private JLabel roll;
	private JLabel diceSelected;
	private ArrayList<JButton> diceButtons;
	private String diceSel = "";
	private int diceNum = -1;
	private int count = 0;
	private int prevDiceNum;
	private AmnesiaPlayer player;
	private Timer timer;
	private int clicked = -1;

	/**
	 * Launches the application.
	 */
	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					AmnesiaDice diceFrame = new AmnesiaDice(null, null, null, null);
					diceFrame.setLocationRelativeTo(null);
					diceFrame.setUndecorated(true);
					diceFrame.setVisible(true);
					diceFrame.setAlwaysOnTop(true);
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
	}
	//Method to add to AmnesiaFrame to call the Frame when needed
	/**public void rollDice()
	{
		AmnesiaDice diceFrame = new AmnesiaDice(player, hintGifts, sanityGifts, timeGifts);
		diceFrame.setLocationRelativeTo(null);
		diceFrame.setUndecorated(true);
		diceFrame.setVisible(true);
		diceFrame.setAlwaysOnTop(true);
	}*/

	/**
	 * Initializes the dice frame and sets the starting dice image
	 * @param p, the player object that is being updated.
	 * @param hint, the JButton that is updated when they win a hint object.
	 * @param sanity, the JButton that is updated when they win a sanity object.
	 * @param time, the JButton that is updated when they win a time object.
	 */
	public AmnesiaDice(AmnesiaPlayer p, JButton hint, JButton sanity, JButton time)
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 450);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		diceImages = new JLabel();
		diceImages.setBounds(224, 86, 134, 131);
		diceImages.setIcon(new ImageIcon(new ImageIcon("D:\\eclipse-workspace\\EOY Project\\images\\dice\\dice1.png")
				.getImage().getScaledInstance(diceImages.getWidth(), diceImages.getHeight(), Image.SCALE_SMOOTH)));
		contentPane.add(diceImages);

		diceButtons = new ArrayList<JButton>();
		createButtons(hint, sanity, time);
		player = p;
	}
	
	/**
	 * Creates the buttons that are displayed in the frame
	 * @param hint, the JButton that is updated when they win a hint object.
	 * @param sanity, the JButton that is updated when they win a sanity object.
	 * @param time, the JButton that is updated when they win a time object.
	 */
	public void createButtons(JButton hint, JButton sanity, JButton time)
	{
		roll = new JLabel();
		roll.setForeground(new Color(255, 204, 51));
		roll.setFont(new Font("Times New Roman", Font.BOLD, 25));
		roll.setBounds(238, 50, 105, 35);
		contentPane.add(roll);
		
		rollButton = new JButton("Roll");
		rollButton.setFocusPainted(false);
		rollButton.setFont(new Font("Times New Roman", Font.PLAIN, 50));
		rollButton.setBounds(new Rectangle(327, 251, 214, 149));
		rollButton.setForeground(Color.BLACK);
		rollButton.setBackground(new Color(255, 204, 51));
		rollButton.setEnabled(false);
		rollButton.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				roll.setText("Rolling...");
				rotateDiceImages(hint, sanity, time);
			}
		});
		contentPane.add(rollButton);
		
		JButton dice1 = new JButton("1");
		dice1.setBackground(new Color(102, 153, 204));
		dice1.setFocusPainted(false);
		dice1.setFont(new Font("Times New Roman", Font.PLAIN, 62));
		dice1.setBounds(52, 251, 65, 69);
		diceButtons.add(dice1);
		diceSel = chooseDice(dice1);
		contentPane.add(dice1);
		
		JButton dice2 = new JButton("2");
		dice2.setBackground(new Color(102, 153, 204));
		dice2.setFocusPainted(false);
		dice2.setFont(new Font("Times New Roman", Font.PLAIN, 62));
		dice2.setBounds(127, 251, 65, 69);
		diceButtons.add(dice2);
		diceSel = chooseDice(dice2);
		contentPane.add(dice2);
		
		JButton dice3 = new JButton("3");
		dice3.setBackground(new Color(102, 153, 204));
		dice3.setFocusPainted(false);
		dice3.setFont(new Font("Times New Roman", Font.PLAIN, 62));
		dice3.setBounds(202, 251, 65, 69);
		diceButtons.add(dice3);
		diceSel = chooseDice(dice3);
		contentPane.add(dice3);
		
		JButton dice4 = new JButton("4");
		dice4.setBackground(new Color(102, 153, 204));
		dice4.setFocusPainted(false);
		dice4.setFont(new Font("Times New Roman", Font.PLAIN, 62));
		dice4.setBounds(52, 331, 65, 69);
		diceButtons.add(dice4);
		diceSel = chooseDice(dice4);
		contentPane.add(dice4);
		
		JButton dice5 = new JButton("5");
		dice5.setBackground(new Color(102, 153, 204));
		dice5.setFocusPainted(false);
		dice5.setFont(new Font("Times New Roman", Font.PLAIN, 62));
		dice5.setBounds(127, 331, 65, 69);
		diceButtons.add(dice5);
		diceSel = chooseDice(dice5);
		contentPane.add(dice5);
		
		JButton dice6 = new JButton("6");
		dice6.setBackground(new Color(102, 153, 204));
		dice6.setFocusPainted(false);
		dice6.setFont(new Font("Times New Roman", Font.PLAIN, 62));
		dice6.setBounds(201, 331, 65, 69);
		diceButtons.add(dice6);
		diceSel = chooseDice(dice6);
		contentPane.add(dice6);
		
		diceSelected = new JLabel("Dice Sides Selected: ");
		diceSelected.setToolTipText("Selected 3 & 6");
		diceSelected.setForeground(new Color(255, 204, 51));
		diceSelected.setFont(new Font("Tahoma", Font.BOLD, 14));
		diceSelected.setBounds(52, 228, 240, 24);
		contentPane.add(diceSelected);
		
		background = new JLabel();
		background.setBounds(0, 0, 600, 450);
		ImageIcon finishedImg = new ImageIcon(new ImageIcon("D:\\eclipse-workspace\\EOY Project\\images\\background2.png")
				.getImage().getScaledInstance(background.getWidth(), background.getHeight(), Image.SCALE_SMOOTH));
		background.setIcon(finishedImg);
		contentPane.add(background);
		
	}
	
	/** 
	 * Generates a random integer value from 1 to 6 inclusive, and make sure that
	 * it never lands on the same one twice.
	 */
	public void rollDice()
	{
		prevDiceNum = diceNum;
		diceNum = (int) (Math.random() * 6) + 1;
		if (prevDiceNum == diceNum)
			diceNum = (int) (Math.random() * 6) + 1;
	}

	/**
	 * When the user selects the sides of the dice they want the dice to land on
	 * it displays the sides of the dice selected with a integer representation of the die sides
	 * and disables the other sides/buttons from being clicked.
	 * @param diceSideClicked, the side of the dice that the player selected.
	 * @return, the string representation of the dice selected by the user.
	 */
	public String chooseDice(JButton diceSideSelected)
	{
		diceSideSelected.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if (count < 3)
				{
					diceSel += diceSideSelected.getText();
					if (count == 0 || count == 1)
					{
						diceSel += " & ";
					}
					else if (count == 2)
					{
						rollButton.setEnabled(true);
					}
					diceSelected.setText("Dice Sides Selected: " + diceSel);
					for (int x = 0; x < diceButtons.size(); x++)
					{
						diceButtons.get(Integer.valueOf(diceSideSelected.getText()) - 1).setEnabled(false);
					}
				}
				count++;
			}
		});
		return diceSel;
	}
	
	/**
	 * Shuffles through the dice images until it lands on one, then if either dice side selected matches
	 * the dice sides chosen it the player wins a random gift, and if does not match they get nothing.
	 * @param hint, the JButton that is updated when they win a hint object.
	 * @param sanity, the JButton that is updated when they win a sanity object.
	 * @param time, the JButton that is updated when they win a time object.
	 */
	public void rotateDiceImages(JButton hint, JButton sanity, JButton time)
	{
		timer = new Timer();
        timer.schedule(new TimerTask ()
    	{
        	int timeNum = 0;
        	public void run() 
            {
        		rollButton.setEnabled(false);
        		if (timeNum < 15)
            	{
            		rollDice();
            		diceImages.setIcon(new ImageIcon(new ImageIcon("D:\\eclipse-workspace\\EOY Project\\images\\dice\\dice5.png")
            				.getImage().getScaledInstance(diceImages.getWidth(), diceImages.getHeight(), Image.SCALE_SMOOTH)));
            		timeNum++;
            	}
            	else
            	{
            		timer.cancel();
            		roll.setText("");
					String val = String.valueOf(5);
					String firstDiceSelected = diceSel.substring(0, 1);
					String secondDiceSelected = diceSel.substring(4, 5);
					String thirdDiceSelected = diceSel.substring(8, diceSel.length());;
					if (val.equals(firstDiceSelected) || val.equals(secondDiceSelected) || val.equals(thirdDiceSelected))
					{ 
						int giftNum = (int)(Math.random() * 3);
						if (giftNum == 0)
						{
							player.addHintGift();
							hint.setText("Hint Gifts: " + player.getHintGifts());
							clicked = JOptionPane.showConfirmDialog(contentPane, "Congratulations, you have won a HINT GIFT!", "WON", JOptionPane.PLAIN_MESSAGE);
						}
						else if (giftNum == 1)
						{
							player.addSanityGift();
							sanity.setText("Sanity Gifts: " + player.getSanityGifts());
							clicked = JOptionPane.showConfirmDialog(contentPane, "Congratulations, you have won a SANITY GIFT!", "WON", JOptionPane.PLAIN_MESSAGE);
						}
						else if (giftNum == 2)
						{
							player.addTimeGift();
							time.setText("Time Gifts: " + player.getTimeGifts());
							clicked = JOptionPane.showConfirmDialog(contentPane, "Congratulations, you have won a TIME GIFT!", "WON", JOptionPane.PLAIN_MESSAGE);
						}
					}
					else
					{
						clicked = JOptionPane.showConfirmDialog(contentPane, "Congratulations, you have won a NOTHING!", "LOST", JOptionPane.PLAIN_MESSAGE);
					}
            	}
            	
            	if (clicked == 0)
            		dispose();
            }
    	}, 0, 3*100);
    }
}


