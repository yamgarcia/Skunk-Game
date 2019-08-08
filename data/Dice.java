package data;

import java.util.Random;

/**
 * 
 * @author (Yam Marcos Garcia)
 * @version (July 7, 2019)
 * 
 *          This is part of the Skunk game BCIT's CSC program assignment.
 * 
 *          The class contains information about the dice
 * 
 */

public class Dice {

	Random r = new Random();
	
	private int value;

	/**
	 * @return the r as an object
	 * object of the Random class
	 */
	public Random getR() {
		return r;
	}

	/**
	 * @param r the r to set
	 * object of the Random class
	 */
	public void setR(Random r) {
		this.r = r;
	}


	/**
	 * 
	 * @param value
	 */
	public Dice(int value) {
		setValue(value);
	}

	/**
	 * @return the value as an int
	 */
	public int getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(int value) {
		if(value >= 1 && value <= 6) {
			this.value = value;
		} else {
			throw new IllegalArgumentException ("value can't be more than 6 or less than 1");
		}
	}
	
	/**
	 * 
	 * @return the new, random, value of that dice as an int
	 */
	public int rollDice() {
		setValue(r.nextInt(5)+1);
		return this.value;
	}
	
	
	
}
