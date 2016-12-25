import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * This class is a view/controller that holds the main board and the undo button
 * below it.
 * 
 * @author Corey Edwards, YanQiang Lu, Jeffrey Tran
 *
 */
public class GamePanel extends JPanel implements ChangeListener {

	private ThemeFormatter formatter;
	private final GameModel model;
	private MancalaBoard mancalaBoard;

	/**
	 * Constructor method for this panel holds the board
	 * 
	 * @param model
	 *            the MVC model
	 * @param formatter
	 *            the strategy/theme to use
	 */
	public GamePanel(GameModel model, ThemeFormatter formatter) {
		super();
		this.model = model;
		model.attach(this);
		this.formatter = formatter;
		mancalaBoard = new MancalaBoard(model, formatter);

		JButton undo = new JButton("undo");
		undo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				model.undo();
			}
		});
		setLayout(new BorderLayout());
		add(mancalaBoard, BorderLayout.CENTER);
		add(undo, BorderLayout.SOUTH);
	}

	/**
	 * Repaint when notified of state change
	 */
	public void stateChanged(ChangeEvent e) {
		repaint();
	}
}