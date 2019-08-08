package data;

/**
 * 
 * @author (Yam Marcos Garcia)
 * @version (July 7, 2019)
 * 
 *          This is part of the Skunk game BCIT's CSC program assignment.
 * 
 *          The class contains information about the player
 * 
 */
public class Player {

	private String name;
	private int points;
	private boolean standing;
	private int roundSum;

	/**
	 * overloaded constructor 
	 * 
	 * @param name
	 * @param points
	 * @param standing
	 */
	public Player(String name, int points, boolean standing) {
		setName(name);
		setPoints(points);
		setStanding(standing);
	}

	/**
	 * no-args constructor 
	 * allow the game to start with a preset player to be filled
	 */
	public Player() {
		setName("John Doe");
		setPoints(0);
		setStanding(false);
	}

	/**
	 * 
	 * @return
	 * roundSum as an int
	 */
	public int getRoundSum() {
		return roundSum;
	}

	/**
	 * 
	 * @param roundSum
	 */
	public void setRoundSum(int roundSum) {
		if (roundSum >= 0) {
			this.roundSum = roundSum;
		} else {
			throw new IllegalArgumentException("Round sum cannot be null");
		}
	}

	/**
	 * @return the name and a String
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		if (name != null) {
			this.name = name;
		} else {
			throw new IllegalArgumentException("Name can't be null");
		}
	}

	/**
	 * @return the points as an int
	 */
	public int getPoints() {
		return points;
	}

	/**
	 * @param points the points to set
	 */
	public void setPoints(int points) {
		if (points >= 0) {
			this.points = points;
		} else {
			throw new IllegalArgumentException("Points can't be negative");
		}
	}

	/**
	 * @return the standing as a boolean
	 * 
	 * defines if the player is up for a next round or satisfied with their points
	 */
	public boolean isStanding() {
		return standing;
	}

	/**
	 * @param standing the standing to set
	 */
	public void setStanding(boolean standing) {
		this.standing = standing;
	}

}
