import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * The main menu to select board strategy/theme and amount of starting pebbles.
 * 
 * @author Corey Edwards, YanQiang Lu, Jeffrey Tran
 *
 */
public class MainMenuFrame {

	private final JFrame frame;
	private final JPanel frontPanel;
	private final GameModel model;
	private ThemeFormatter formatter;

	/**
	 * Constructor
	 */
	public MainMenuFrame() {
		frame = new JFrame();
		frontPanel = new JPanel();
		model = new GameModel();

		// Select number of stones: 3 or 4
		JLabel numberLabel = new JLabel("Select the number of stones: ");
		ButtonGroup ThreevFour = new ButtonGroup();
		JRadioButton threeButton = new JRadioButton("3");
		JRadioButton fourButton = new JRadioButton("4");
		ThreevFour.add(threeButton);
		ThreevFour.add(fourButton);

		// Select theme/strategy to apply
		JLabel styleLabel = new JLabel("Select the game style: ");
		JRadioButton greenButton = new JRadioButton("Green squares");
		JRadioButton redButton = new JRadioButton("Red circles");
		ButtonGroup GreenRed = new ButtonGroup();
		GreenRed.add(greenButton);
		GreenRed.add(redButton);

		frontPanel.add(numberLabel);
		frontPanel.add(threeButton);
		frontPanel.add(fourButton);
		frontPanel.add(styleLabel);
		frontPanel.add(greenButton);
		frontPanel.add(redButton);

		// "Start Game" Button and its Listener
		JButton startButton = new JButton();
		startButton.setText("Start Game!");
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// if both button are not selected, do nothing
				if (!threeButton.isSelected() && !fourButton.isSelected()
						|| !greenButton.isSelected() && !redButton.isSelected())
					return;
				// Response to the number of stones button
				if (threeButton.isSelected())
					model.setPebbles(3);
				else
					model.setPebbles(4);

				// Response to the choice of layout color
				if (greenButton.isSelected())
					formatter = new GreenFormatter();
				else
					formatter = new RedFormatter();

				// Start game, which remove main menu panel and enter game panel
				startGame();
			}
		});
		frontPanel.add(startButton);
		frame.setLayout(new BorderLayout());
		frame.add(frontPanel);
		frame.setTitle("Mancala Game");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.pack();
	}

	/**
	 * Starts the game, shows the mancala board held in the game panel
	 */
	private void startGame() {
		GamePanel gamePanel = new GamePanel(model, formatter);
		model.setCurrentState("STARTED");
		frame.remove(frontPanel);
		frame.add(gamePanel);
		frame.repaint();
		frame.setSize(1000, 600);
	}

}