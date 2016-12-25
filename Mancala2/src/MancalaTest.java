/**
 * Mancala Test class
 * Mancala Game
 * 
 * Rules: The first player chooses their pit color and how many pits for the 
 * game. While moving counter clockwise, the player places one stone into 
 * each hole until they have no more stones. If a player places the last 
 * Mancala stone into their own Mancala then that player gets a free turn.
 * If, while depositing the stones counterclockwise, the player arrives at
 * their opponents mancala, they should skip it and then continue to the next
 * pit. If a player places the final Mancala stone into an empty pit on 
 * their side of the board, then that player captures that stone and the
 * stones in the hole opposite from that one. The capture stones are then
 * placed into the player's mancala. The game ends when there are no more
 * Mancala stones in any six pits on one side of the board. The remaing 
 * stones are placed into the mancala of the player whose side the stones
 * occupied
 * 
 * @author Corey Edwards, YanQiang Lu, Jeffrey Tran
 * @date Novemeber 25,2015
 * @version 1.5
 */
public class MancalaTest 
{
    /**
     * Opens the main menu which prompts the user for input before starting the game.
     * 
     * @author Corey Edwards, YanQiang Lu, Jeffrey Tran
     */
    public static void main(String[] args)
    {
        new MainMenuFrame();
    }
}
