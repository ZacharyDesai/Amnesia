/**
 *  AmnesiaPlayer.java
 *  The AmnesiaPlayer class creates the player for the game and stores its
 *  points and gifts.
 *  @author Mauro Robles, Sean Njenga, and Zachary Desai
 *  Teacher: Mrs. Ishman
 *  Period: 4
 *  Date: 05-14-18
 */

public class AmnesiaPlayer
{
	// instance variables
	private int sanityPoints;
	private int lifePoints;
	private int hintGifts;
	private int sanityGifts;
	private int timeGifts;

	// class constants
	public static final int MAX_SANITY = 100;
	public static final int MAX_LIVES = 3;
	public static final int INITIAL_GIFTS = 1;
	public static final int LIFELESS = 0;

	/** Constructs an AmnesiaPlayer with the default counters
	 */
	public AmnesiaPlayer()
	{
		sanityPoints = MAX_SANITY;
		lifePoints = MAX_LIVES;
		hintGifts = INITIAL_GIFTS;
		sanityGifts = INITIAL_GIFTS;
		timeGifts = INITIAL_GIFTS;
	}

	/** Returns the number of sanity points the player has
	 *  @return the int for the player's sanity points
	 */
	public int getSanityPoints()
	{
		return sanityPoints;
	}

	/** Returns the number of life points the player has
	 *  @return the int for the player's life points
	 */
	public int getLifePoints()
	{
		return lifePoints;
	}

	/** Returns the number of hint gifts the player has
	 *  @return the int for the player's hint gifts
	 */
	public int getHintGifts()
	{
		return hintGifts;
	}

	/** Returns the number of sanity gifts the player has
	 *  @return the int for the player's sanity gifts
	 */
	public int getSanityGifts()
	{
		return sanityGifts;
	}

	/** Returns the number of time gifts the player has
	 *  @return the int for the player's time gifts
	 */
	public int getTimeGifts()
	{
		return timeGifts;
	}

	/** Adds a hint gift to the player's inventory
	 */
	public void addHintGift()
	{
		hintGifts++;
	}

	/** Adds a sanity gift to the player's inventory
	 */
	public void addSanityGift()
	{
		sanityGifts++;
	}

	/** Adds a time gift to the player's inventory
	 */
	public void addTimeGift()
	{
		timeGifts++;
	}

	/** Removes a hint gift from the player's inventory
	 */
	public void removeHintGift()
	{
		hintGifts--;
	}

	/** Removes a hint gift from the player's inventory
	 */
	public void removeSanityGift()
	{
		sanityGifts--;
	}

	/** Removes a hint gift from the player's inventory
	 */
	public void removeTimeGift()
	{
		timeGifts--;
	}

	/** Adds the given number of points to the player's sanity
	 *  @param gain the int for the given number of sanity points to add
	 */
	public void addSanityPoints(int gain)
	{
		sanityPoints += gain;
		if (sanityPoints > MAX_SANITY)
		{
			sanityPoints = MAX_SANITY;
		}
	}

	/** Subtracts the given number of points from the player's sanity
	 *  @param loss the int for the given number of sanity points to subtract
	 */
	public void loseSanityPoints(int loss)
	{
		sanityPoints -= loss;
	}

	/** Resets the player's sanity points and subtracts a life point when the
	 *  player loses sanity (when the player's sanity points fall to zero), but
	 *  does not reset the sanity points if the life points fall to zero
	 */
	public void loseALife()
	{
		lifePoints--;
		if (lifePoints > LIFELESS)
		{
			sanityPoints = MAX_SANITY;
		}
		if (lifePoints <= LIFELESS)
		{
			sanityPoints = LIFELESS;
		}
	}
}