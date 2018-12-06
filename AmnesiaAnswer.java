/**
 *  AmnesiaAnswer.java
 *  The AmnesiaAnswer class creates the answer choices for each question in the
 *  Amnesia game.
 *  @author Mauro Robles, Sean Njenga, and Zachary Desai
 *  Teacher: Mrs. Ishman
 *  Period: 4
 *  Date: 05-14-18
 */

public class AmnesiaAnswer
{
	// instance variables
	private String answer;
	private boolean correctness;

	/** Constructs an AmnesiaAnswer object with the given properties
	 *  @param a the given String for the answer text
	 *  @param c the given boolean for the answer's correctness
	 */
	public AmnesiaAnswer(String a, boolean c)
	{
		answer = a;
		correctness = c;
	}

	/** Returns the text for the answer
	 *  @return the String for the answer
	 */
	public String getAnswer()
	{
		return answer;
	}

	/** Returns whether or not the answer is correct
	 *  @return the boolean of the correctness for the answer
	 */
	public boolean isCorrect()
	{
		return correctness;
	}
}