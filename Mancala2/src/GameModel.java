import java.util.ArrayList;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * The Model method for the Mancala game. Controls operations behind the scene
 * and passes information into the views/controllers
 * 
 * @author Corey Edwards, YanQiang Lu, Jeffrey Tran
 *
 */
public class GameModel {

	public static enum GameState {
		CREATING, STARTED, GAMEOVER
	};

	public static enum Player {
		A, B
	};

	// array index of mancala A and B
	private static final int PLAYER_A_MANCALA = 6;
	private static final int PLAYER_B_MANCALA = 13;
	private static final int NUMBER_OF_PITS = 14;
	private final int MAX_UNDOS = 3;
	private int[] pits;
	private int[] previousTurn; // stores last turn for undo
	private int numberOfPebbles;
	private int undosUsedA;
	private int undosUsedB;
	private boolean allowUndo;
	private boolean lastPebbleInMancala;
	private Player currentPlayer;
	private GameState gameState;
	private ArrayList<ChangeListener> listeners;
	private String winner;

	/**
	 * Constructor method, create the board in a started st
	 */
	public GameModel() {
		gameState = GameState.CREATING;
		listeners = new ArrayList<ChangeListener>();
		previousTurn = new int[NUMBER_OF_PITS];
		pits = new int[NUMBER_OF_PITS];
		currentPlayer = Player.A;
		undosUsedA = 0;
		undosUsedB = 0;
		allowUndo = false; // no turns taken yet
	}

	/**
	 * Return the player that owns the pit
	 * 
	 * @param pit
	 *            pit that was clicked
	 * @return player who's pit was clicked
	 */
	private Player getPlayer(int pit) {
		if (pit >= 0 && pit <= PLAYER_A_MANCALA) {
			return Player.A;
		} else
			return Player.B;
	}

	/**
	 * Change current player
	 */
	private void changeTurns() {
		currentPlayer = currentPlayer == Player.A ? Player.B : Player.A;
	}

	/**
	 * Check is player is allowed to make a move
	 * 
	 * @param pit
	 *            pit that was clicked
	 * @return true if allowed to move, else false
	 */
	public boolean canMove(int pit) {
		// can't move if: clicked on a mancala, game is not started,
		// pit is empty, pit belongs to other player
		if (pit == PLAYER_A_MANCALA || pit == PLAYER_B_MANCALA
				|| gameState != GameState.STARTED || pits[pit] == 0
				|| getPlayer(pit) != currentPlayer) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Checks whether pit is a mancala or not
	 * 
	 * @param pit
	 *            pit that was clicked
	 * @return true if mancala, false if pit
	 */
	private boolean isMancala(int pit) {
		return (pit == PLAYER_A_MANCALA || pit == PLAYER_B_MANCALA);
	}

	/**
	 * Check opposite pit to steal pebbles from
	 * 
	 * @param pit
	 *            pit that was clicked
	 * @return the pit opposite of clicked pit
	 */
	private int oppositePit(int pit) {
		return Math.abs(pit - 12);
	}

	/**
	 * Collect pebbles into Mancalas at the end of the game.
	 */
	private void collectPebbles() {
		for (int i = 0; i < NUMBER_OF_PITS; ++i)
			if (!isMancala(i)) {
				if (getPlayer(i) == Player.A) {
					pits[PLAYER_A_MANCALA] += pits[i];
					pits[i] = 0;
				} else {
					pits[PLAYER_B_MANCALA] += pits[i];
					pits[i] = 0;
				}
			}
	}

	/**
	 * Check if game is over
	 * 
	 * @return true if game is over
	 */
	private boolean gameOver() {
		int pebblesA = 0;
		int pebblesB = 0;
		for (int i = 0; i < NUMBER_OF_PITS; ++i)
			if (!isMancala(i)) {
				if (getPlayer(i) == Player.A) {
					pebblesA += pits[i];
				} else {
					pebblesB += pits[i];
				}
			}
		return (pebblesA == 0 || pebblesB == 0);
	}

	/**
	 * Moves pebbles
	 * 
	 * @param pitNum
	 *            pit that was clicked
	 */
	public void move(int pitNum) {
		// save state for potential undo
		savePreviousTurn();
		allowUndo = true;

		// take pebbles from selected pit
		int numPebblesToMove = pits[pitNum];
		pits[pitNum] = 0;
		// distribute pebbles counter clock-wise
		int distributeTo = pitNum;
		while (numPebblesToMove > 0) {
			distributeTo = (distributeTo + 1) % NUMBER_OF_PITS;

			// Skip opponent's mancala
			if (isMancala(distributeTo)
					&& getPlayer(distributeTo) != currentPlayer) {
				continue;
			} else {
				// Place stone in pit
				pits[distributeTo]++;
				numPebblesToMove--;
			}
		}

		// Compute who is next
		// where is the last pit where a piece was placed

		// own mancala
		if (getPlayer(distributeTo) == currentPlayer && isMancala(distributeTo)) {
			// free turn
			lastPebbleInMancala = true;
		}
		// empty pit on your side
		else if (getPlayer(distributeTo) == currentPlayer
				&& pits[distributeTo] == 1
				&& pits[oppositePit(distributeTo)] > 0) {
			// capture pieces
			int captured = pits[distributeTo] + pits[oppositePit(distributeTo)];
			pits[distributeTo] = pits[oppositePit(distributeTo)] = 0;
			if (currentPlayer == Player.A) {
				pits[PLAYER_A_MANCALA] += captured;
			} else {
				pits[PLAYER_B_MANCALA] += captured;
			}
			// Give turn to next player
			lastPebbleInMancala = false;
			changeTurns();
		} else {
			// Give turn to next player.
			lastPebbleInMancala = false;
			changeTurns();
		}

		// Check if the game is done.
		if (gameOver()) {
			collectPebbles();
			gameState = GameState.GAMEOVER;
			declareWinner();
		}
		notifyViews();
	}

	/**
	 * 
	 * @return
	 */
	public String declareWinner() {

		if (pits[PLAYER_B_MANCALA] > pits[PLAYER_A_MANCALA]) {
			winner = "Player B won!";
		} else if (pits[PLAYER_B_MANCALA] < pits[PLAYER_A_MANCALA]) {
			winner = "Player A won!";
		} else {
			winner = "DRAW GAME!";
		}
		return winner;
	}

	/**
	 * Called by main menu to set initial amount of pebbles
	 * 
	 * @param pebbles
	 *            number of pebbles to place
	 */
	public void setPebbles(int pebbles) {
		for (int i = 0; i < pits.length; i++)
			if (!isMancala(i))
				pits[i] = pebbles;

		notifyViews();
		numberOfPebbles = pebbles;
	}

	/**
	 * Get the number of pebbles
	 * 
	 * @return number of pebbles
	 */
	public int getPebbles() {
		return numberOfPebbles;
	}

	/**
	 * Set the current state of the game
	 * 
	 * @param state
	 */
	public void setCurrentState(String state) {
		if (state.equals("CREATING"))
			gameState = GameState.CREATING;
		else if (state.equals("STARTED"))
			gameState = GameState.STARTED;
		else
			gameState = GameState.GAMEOVER;
	}

	/**
	 * Getter for current player.
	 * 
	 * @return the current player.
	 */
	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	/**
	 * Getter for current state.
	 * 
	 * @return the current state
	 */
	public GameState getCurrentState() {
		return gameState;
	}

	/**
	 * Gets the pit array with amount of stones in each
	 * 
	 * @return the pit array
	 */
	public int[] getPits() {
		return pits;
	}

	/**
	 * Return the score of the requested player.
	 * 
	 * @param p
	 *            requester player
	 * @return the score
	 */
	public int getScore(Player p) {
		if (p == Player.A)
			return pits[PLAYER_A_MANCALA];
		if (p == Player.B)
			return pits[PLAYER_B_MANCALA];
		return 0;
	}

	/**
	 * Undos the last move, if possible.
	 */
	public void undo() {
		if (!allowUndo)
			return;

		boolean possible = false;

		switch (currentPlayer) {
		case A: {
			if (lastPebbleInMancala && undosUsedA < MAX_UNDOS) {
				undosUsedA++;
				possible = true;
			} else if (!lastPebbleInMancala && undosUsedB < MAX_UNDOS) {
				// If the last pebbles was in Mancala, switch the current player
				// to the other side.
				undosUsedB++;
				currentPlayer = Player.B;
				possible = true;
			}
			break;
		}
		case B: {
			if (lastPebbleInMancala && undosUsedB < MAX_UNDOS) {
				undosUsedB++;
				possible = true;
			} else if (!lastPebbleInMancala && undosUsedA < MAX_UNDOS) {
				// If the last pebbles was in Mancala, switch the current player
				// to the other side.
				undosUsedA++;
				possible = true;
				currentPlayer = Player.A;
			}
			break;
		}
		}

		if (possible) {
			if (gameState.equals(GameState.GAMEOVER)) {
				gameState = GameState.STARTED;
			}
			pits = previousTurn.clone();
			notifyViews();
			allowUndo = false;
		}
	}

	/**
	 * Store previous turn into another array for potential undos
	 */
	public void savePreviousTurn() {
		previousTurn = pits.clone();
	}

	/**
	 * Updates listeners with changes in the model
	 */
	public void notifyViews() {
		for (ChangeListener l : listeners) {
			l.stateChanged(new ChangeEvent(this));
		}
	}

	/**
	 * Attach listeners to model
	 * 
	 * @param l
	 *            ChangeListener to attach
	 */
	public void attach(ChangeListener l) {
		listeners.add(l);
	}

}
