import java.awt.Color;
import java.awt.Shape;

/**
 * Strategy interface for theme of board
 * 
 * @author Corey Edwards, YanQiang Lu, Jeffrey Tran
 *
 */
public interface ThemeFormatter {
	
	/**
	 * Color to set
	 * 
	 * @return color to set for pits
	 */
	Color formatPitColor();

	/**
	 * Shape of pits
	 * 
	 * @param pit
	 *            the pits
	 * @return shape to make pits
	 */
	Shape formatShape(PitView pit);
}
