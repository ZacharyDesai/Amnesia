/**                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     nnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn
 *  AmnesiaGame.java
 *  The AmnesiaGame class runs the game while testing the functionality of it
 *  by checking how all of the Amnesia classes interact with each other.
 *  @author Mauro Robles, Sean Njenga, and Zachary Desai
 *  Teacher: Mrs. Ishman
 *  Period: 4
 *  Date: 05-14-18
 */

// import statements
import java.io.FileNotFoundException;
import java.net.MalformedURLException;

public class AmnesiaGame
{
	/** Serves as the main class for the project by creating and displaying an AmnesiaFrame
	 *  @param args the String[] arguments made to run the game
	 *  @throws FileNotFoundException if a file in AmnesiaFrame does not exist
	 *  @throws MalformedURLException if a file path in AmnesiaFrame is incorrect
	 */
	public static void main(String[] args) throws FileNotFoundException, MalformedURLException
	{
		AmnesiaFrame gameFrame = new AmnesiaFrame();
		gameFrame.showScreen();
	}
}