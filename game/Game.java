package game;

import java.util.Scanner;

import data.Dice;
import data.Player;

/**
 * 
 * @author (Yam Marcos Garcia)
 * @version (July 7, 2019)
 * 
 *          This is part of the Skunk game BCIT's CSC program assignment.
 * 
 *          The class sets the game up to be called by the Main.
 *          
 *          It starts setting an array with a letter for each round;
 *          running, a boolean used a few times on while loops;
 *          roundCount guarantee the game won't continue after the last round;
 *          Scanner object allows the player's input;
 *          p1 and com are initiated (Glados name may be changed)
 *          d1 and d2 are Dice objects
 *          diceSum is a variable of the class Game, not Dice class;
 *      
 */
public class Game {

	private String[] round = { "S", "K", "U", "N", "K" };
	private boolean running = false;
	int roundCount = 0;
	Scanner scanner = new Scanner(System.in);
	Player p1 = new Player();
	Player com = new Player("Glados", 0, true);
	Dice d1 = new Dice(1);
	Dice d2 = new Dice(1);

	int diceSum = 0;

	/**
	 * The game loop.
	 * 
	 * It has a loop for the first answer which can close the game or call a
	 * newGame().
	 *
	 * The second loop doesn't have a break, it only ends by calling closing(). It
	 * checks the roundCount before calling a new round. When every round ends it
	 * increase round count by 1.
	 * 
	 */
	public Game() {
		titleScreen();

		System.out.println("	Y or N");

		while (scanner.hasNext()) {
			boolean play = true;
			while (play) {
				String choice = scanner.next();
				//choice is the standard variable for the player's input
				if (choice.equalsIgnoreCase("Y")) {
					play = false;
					newGame();
				} else if (choice.equalsIgnoreCase("N")) {
					play = false;
					closing();
				} else {
					System.out.println("Please Input Y or N");
				}
			}
			while (running) {
				p1.setRoundSum(0);
				com.setRoundSum(0);
//				lastRound();
				System.out.printf("\n%s's points: %d, and %s's points: %d \n", p1.getName(), p1.getPoints(),
						com.getName(), com.getPoints());
				if (roundCount < 5) {
					System.out.println("Round :" + round[roundCount]);
					newRound();
				} else {
					running = false;
					closing();
				}

//				System.out.printf("%s's score: %d points\n", p1.getName(), p1.getPoints());

//				roundCount++;
			}
		}
	}

	/**
	 * The method address a name to the player and resets the necessary variables to
	 * star the game.
	 * 
	 * This may be called again when the game ends.
	 */
	private void newGame() {

		System.out.println("Please type your name:");
		String name;
		// reset most variables to prepare to a new game
		p1.setName(name = scanner.next().trim());
		p1.setStanding(true);
		p1.setPoints(0);
		p1.setRoundSum(0);
		com.setStanding(true);
		com.setPoints(0);
		com.setRoundSum(0);
		roundCount = 0;
		System.out.printf("\nHi %s, you will play against %s.\nFrom now on you both are standing.\n", p1.getName(),
				com.getName());
		running = true;
	}

	/**
	 * The method let the player choose 2 or 3 dice for every launching, in case
	 * they are feeling luck enough to try 3.
	 * 
	 * Q option shows up after typing something else than 2 or 3 (dice)
	 */
	public void newRound() {

		p1.setStanding(true);
		com.setStanding(true);

		while (p1.isStanding()) {
			System.out.println("Do you want to play with 2 or 3 dice?");
			String choice = scanner.next();
			//choice is the standard variable for the player's input
			if (choice.equals("2")) {
				playTwoDice();
			} else if (choice.equals("3")) {
				playThreeDice();
			} else if (choice.equalsIgnoreCase("Q")) {
				closing();
				break;

			} else {
				// Q is now allowed, in case the player wants to quit.
				// the option Q only shows up after a wrong char is typed.
				// this way the console don't get crowded
				System.out.println("Please type 2 or 3.\nIf you don't want to play you can type 'Q'");
			}
		}
		// by this point the player is not standing
		roundCount++;
//		lastRound();
	}

	/**
	 * The method checks if the count is still below 5. if that's the case, is run
	 * closing() to finish the game.
	 */
	public void lastRound() {
		System.out.println("is last round?" + " " + roundCount);
		if (roundCount == 5) {
			closing();
		}
	}

	/**
	 * The method is called by the constructor in case to player chooses to play
	 * with two dices for this round. If the value is 1 in both dice, all points are
	 * wiped out and the next round is loaded. If the value is 1 in one die, the
	 * round ends and no point is gathered carried.
	 * 
	 * If no dice shows one, it adds the sum of points, checks the roundCount and
	 * call playerStanding().
	 */

	public void playTwoDice() {

		boolean rollTheDice = true;

		while (rollTheDice) {
			d1.rollDice();
			d2.rollDice();
			rollTheDice = false;
			diceSum = d1.getValue() + d2.getValue();// -----------

			System.out.println("Dice's values are " + d1.getValue() + " and " + d2.getValue());
			if (d1.getValue() == 1 && d2.getValue() == 1) {
				p1.setPoints(0);
				System.out.println("\nBoth dice are value one!\n You lost all your points\n");

				p1.setStanding(false);

			} else if (d1.getValue() == 1 || d2.getValue() == 1) {
				p1.setRoundSum(0);
				System.out.println("\nOne die happened to be one. \nThis wipe out your round points!\n");
				p1.setStanding(false);

			} else if (d1.getValue() < 1 || d2.getValue() < 1 || d1.getValue() > 6 || d2.getValue() > 6) {
				throw new IllegalArgumentException(
						"value can't be more than 6 or less than 1 \n" + d1.getValue() + " and " + d2.getValue());

			} else {

				System.out.println("Dice sum is " + diceSum);// -----------
				p1.setRoundSum(p1.getRoundSum() + diceSum);
				com.setRoundSum(com.getRoundSum() + diceSum);

				playerStanding();

			}
		}
	}

	/**
	 * Similar to the method above, it roll 3 dice. It guarantees more points,
	 * bigger chance to lose the round, and fewer to lose everything.
	 */
	public void playThreeDice() {
		Dice d1 = new Dice(1);
		Dice d2 = new Dice(1);
		Dice d3 = new Dice(1);
		boolean rollTheDice = true;

		while (rollTheDice) {
			d1.rollDice();
			d2.rollDice();
			d3.rollDice();
			rollTheDice = false;
			diceSum = d1.getValue() + d2.getValue() + d3.getValue();

			System.out.println("Dice's values are " + d1.getValue() + ", " + d2.getValue() + ", and " + d3.getValue());
			if (d1.getValue() == 1 && d2.getValue() == 1 && d3.getValue() == 1) {
				p1.setPoints(0);
				com.setPoints(0);
				System.out
						.println("\nSorry all three dice are one!\n You lost all your points, better luck next time!");

				p1.setStanding(false);
				com.setStanding(false);

			} else if (d1.getValue() == 1 || d2.getValue() == 1 || d3.getValue() == 1) {
				p1.setRoundSum(0);
				com.setRoundSum(0);
				System.out.println(
						"\nOne die happened to be one. \nSorry, but this wipe out your round points! \nLet us go to the next round!\n");
				p1.setStanding(false);
				com.setStanding(false);
				
			} else if (d1.getValue() < 1 || d2.getValue() < 1 || d1.getValue() > 6 || d2.getValue() > 6
					|| d3.getValue() < 1 || d3.getValue() > 6) {
				throw new IllegalArgumentException("value can't be more than 6 or less than 1 \n" + d1.getValue()
						+ " and " + d2.getValue() + " and " + d3.getValue());
			} else {

				System.out.println("Dice sum is " + diceSum);
				p1.setRoundSum(p1.getRoundSum() + diceSum);
				com.setRoundSum(com.getRoundSum() + diceSum);

				lastRound();
				playerStanding();

			}
		}
	}

	/**
	 * The method ask if the player want to stay standing every time the dice are
	 * rolled.
	 * 
	 * at this point Y, N or Q can be typed. Q quits the game, calling closing()
	 * method.
	 */
	public void playerStanding() {

		boolean keepAsking = true;
		System.out.println("Do you wanna stay standing? \nY or N?");
		String choice = scanner.next();
		//choice is the standard variable for the player's input
		while (keepAsking) {
			if (choice.equalsIgnoreCase("Y")) {
				p1.setStanding(true);
				keepAsking = false;
			} else if (choice.equalsIgnoreCase("N")) {
				p1.setPoints(p1.getPoints() + p1.getRoundSum());
				p1.setStanding(false);
				keepAsking = false;
				computerTurn(); // calls the computer turn

			} else if (choice.equalsIgnoreCase("Q")) {
				keepAsking = false;
				closing();
				break;
			} else {
				System.out.println("Not a valid choice. If you wanna stop playing type 'Q'");
				break;
			}
		}

	}
//Maybe enough for extra marks on AI
	/**
	 * The method is very similar to playerStanding() and playTwoDice(). Glados
	 * (Portal reference) will try to catch up if it is losing.
	 * 
	 * Glados won't pass until it has more points
	 * 
	 * It makes the game more competitive, otherwise it doesn't seem like you are playing against someone.
	 * 
	 * If it's the last round Glados won't pass until it can win.
	 */
	public void computerTurn() {

		while (com.isStanding()) {

			if (p1.getPoints() > com.getPoints() + com.getRoundSum() && roundCount == 5) {
				computerRolling();
				System.out.println(com.getName()+": LAST ROUND!\n I won't pass!");

			} else if (p1.getPoints() > com.getPoints() + com.getRoundSum()) {
				computerRolling();

			} else if (p1.getRoundSum() >= com.getRoundSum()) {
				computerRolling();

			} else {
				System.out.println(com.getName() + " passed too");
				com.setPoints(com.getRoundSum());
				com.setStanding(false);
			}

		}
		com.setStanding(true);

	}
	
	/**
	 * The method is similar to playTwoDice(), but only triggers if the player is winning .
	 * Glados won't pass until it has more points
	 */

	public void computerRolling() {
		com.setStanding(true);
		d1.rollDice();
		d2.rollDice();

		diceSum = d1.getValue() + d2.getValue();
		if (d1.getValue() == 1 && d2.getValue() == 1) {
			com.setPoints(0);
			System.out.println("\nBoth dice are value one!\n " + com.getName() + " lost all points");

			com.setStanding(false);

		} else if (d1.getValue() == 1 || d2.getValue() == 1) {
			com.setRoundSum(0);
			System.out.println("\nOne die happened to be one. \n" + com.getName() + " lost all round points!\n");
			com.setStanding(false);

		} else if (d1.getValue() < 1 || d2.getValue() < 1 || d1.getValue() > 6 || d2.getValue() > 6) {
			throw new IllegalArgumentException(
					"value can't be more than 6 or less than 1 \n" + d1.getValue() + " and " + d2.getValue());

		} else {

			System.out.println("Dice sum for " + com.getName() + " is:" + diceSum);// -----------
			com.setRoundSum(com.getRoundSum() + diceSum);
		}
	}

	/**
	 * The method prints the title screen
	 */
	public void titleScreen() {
		System.out
				.println(" " + "                   		@@@@@@@#\r\n" + "                            @@@@(  @@@@\r\n"
						+ "                          *@@@   .@@@@@\r\n" + "                          @@@   @@@@@@ \r\n"
						+ "                          @@.  @@@@    \r\n" + "                          @@#  @@@     \r\n"
						+ "   /|  /|                  .@  %@      \r\n" + "  , @@@%#(       &*.    /    @ @@      \r\n"
						+ " (@@%@@@@%, ,.     %@@@@@  @,@ @       \r\n" + "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@&        \r\n"
						+ "%@@@@@@@@@@@@@@@@@@@@@@@@@@@@          \r\n" + "     &@@@@@@@@@@@@@@@@@@@@@@@          \r\n"
						+ "       @@@@@@@@@@@@@@@@@@@@@           \r\n" + "       @@ #@@@&(. ,@@@@@@@@            \r\n"
						+ "      @@  @@,       @@@  @@@           \r\n" + "     /@  @@%       @@. @@@# ");
		System.out.println("\n	SKUNK \nWould you like to play?");
	}

	/**
	 * The method closes the game. Once called, it can only open a new one, by
	 * typing Y, or end the game, typing N or Q.
	 */
	public void closing() {
		if (com.getPoints() < p1.getPoints()) {
			System.out.printf("Goog Job! %s, you finished with %d points and %s with %d.\n", p1.getName(),
					p1.getPoints(), com.getName(), com.getPoints());
		} else if (com.getPoints() > p1.getPoints()) {
			System.out.printf("You got beaten, %s! You finished with %d points and %s with %d.\n", p1.getName(),
					p1.getPoints(), com.getName(), com.getPoints());
		} else if (com.getPoints() == p1.getPoints()) {
			System.out.printf("Same score, %s. You both have finished with %d points\n", p1.getName(), p1.getPoints());
		}
		boolean keepAsking = true;
		System.out.println("Do you wanna play again? \nY or N?");
		String choice = scanner.next();
		//choice is the standard variable for the player's input
		while (keepAsking) {
			if (choice.equalsIgnoreCase("Y")) {
				keepAsking = false;
				newGame();
			} else if (choice.equalsIgnoreCase("N")) {
				keepAsking = false;
				System.out.println("Thanks for playing");
				scanner.close();
				System.exit(0);
			} else if (choice.equalsIgnoreCase("Q")) {
				keepAsking = false;
				System.out.println("Thanks for playing");
				scanner.close();
				System.exit(0);
				break;
			} else {
				System.out.println("Not a valid choice. If you wanna stop playing you can also type 'Q' or 'N'");
				break;
			}
		}
	}

}
