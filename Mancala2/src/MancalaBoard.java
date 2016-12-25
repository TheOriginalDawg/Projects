import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * A controller/view class that holds the mancala board to be played with. This
 * class draws the board and provides listeners to receive input from the user.
 * 
 * @author Corey Edwards, YanQiang Lu, Jeffrey Tran
 *
 */
public class MancalaBoard extends JPanel implements ChangeListener {

	private final GameModel model;
	private ArrayList<PitView> pits;
	private ThemeFormatter formatter;
	private int[] pebbles;

	/**
	 * Constructor
	 * 
	 * @param model
	 *            MVC model
	 * @param formater
	 *            strategy to format theme
	 */
	public MancalaBoard(GameModel model, ThemeFormatter formater) {
		this.model = model;
		this.formatter = formater;
		this.pebbles = new int[14];
		model.attach(this);

		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				for (int i = 0; i < pits.size(); i++) {
					if (pits.get(i).contains(e.getPoint()) && model.canMove(i)) {
						model.move(i);
						return;
					}
				}
			}
		});
		createMancalaBoard();
	}

	/**
	 * Create the mancala board
	 */
	private void createMancalaBoard() {
		pits = new ArrayList<PitView>();

		// variables for pit locations
		final int diameter = 100;
		final int topRow = 75;
		final int botRow = 350;
		final int spacing = 125;
		Color color = formatter.formatPitColor();

		// draw the pits and mancalas
		for (int i = spacing; i <= 6 * spacing; i += 125) {
			pits.add(new PitView(i, botRow, diameter, diameter, color));
		}
		pits.add(new PitView(875, 120, diameter, 3 * diameter, color));
		for (int i = 6 * spacing; i >= spacing; i -= 125) {
			pits.add(new PitView(i, topRow, diameter, diameter, color));
		}
		pits.add(new PitView(10, 120, diameter, 3 * diameter, color));
		int c = 0;
		for (PitView p : pits) {
			if (c == 6 || c == 13) {
				p.placePebbles(0);
				p.setShape(new Rectangle2D.Double(p.getX(), p.getY(), p
						.getWidth(), p.getHeight()));
			} else {
				p.placePebbles(model.getPebbles());
				p.setShape(formatter.formatShape(p));
			}
			c++;
		}
	}

	/**
	 * Paint the components of the board
	 */
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;

		for (PitView p : pits) {
			p.fill(g2);
		}

		// Player turn label
		String playerText = "";
		// Pit and mancala labels
		String player1 = "Turn: Player A";
		String player2 = "Turn: Player B";
		String winner = "";
		String mancalaB = "Mancala B";
		String mancalaA = "Mancala A";
		String labelB = "B6                    B5                     B4                     B3                    B2                     B1";
		String labelA = "A1                    A2                     A3                     A4                     A5                     A6";

		// draw A and B labels for pits and mancalas
		g.setColor(Color.BLACK);
		g2.drawString(labelB, getX() + 165, getY() + 70);
		g2.drawString(labelA, getX() + 165, getY() + 470);
		g2.drawString(mancalaB, getX() + 17, getY() + 110);
		g2.drawString(mancalaA, getX() + 883, getY() + 440);

		// When the game is over
		if (model.getCurrentState() == GameModel.GameState.GAMEOVER) {
			// if the game is ended, print winner
			int scoreA = model.getScore(GameModel.Player.A);
			int scoreB = model.getScore(GameModel.Player.B);
			playerText = "Player B score: " + scoreB + "    "
					+ " Player A score: " + scoreA;
			winner = model.declareWinner();
		} else {
			// Print which player is playing
			if (model.getCurrentPlayer() == GameModel.Player.A) {
				playerText = player1;
			} else {
				playerText = player2;
			}
		}

		g.setColor(Color.RED);
		Font font = new Font("Verdana", Font.PLAIN, 26);
		g2.setFont(font);
		FontMetrics f = g.getFontMetrics();

		if (playerText.equals(player1)) {
			g2.drawString(playerText,
					getX() + getWidth() / 2 - f.stringWidth(playerText + "")
							/ 2, getY() + 510);
		} else if (playerText.equals(player2)) {
			g2.drawString(playerText,
					getX() + getWidth() / 2 - f.stringWidth(playerText + "")
							/ 2, getY() + 30);
		} else {
			g2.drawString(playerText,
					getX() + getWidth() / 2 - f.stringWidth(playerText + "")
							/ 2, getY() + 30);
			g2.drawString(winner,
					getX() + getWidth() / 2 - f.stringWidth(winner + "") / 2,
					getY() + 280);
		}

	}

	/**
	 * Updates pits upon state change
	 */
	public void stateChanged(ChangeEvent e) {
		pebbles = model.getPits();
		int count = 0;
		for (PitView p : pits) {
			p.placePebbles(pebbles[count++]);
		}
		repaint();
	}

}
