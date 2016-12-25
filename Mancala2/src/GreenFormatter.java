import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;

/**
 * Concrete strategy/theme for green square pits
 * 
 * @author Corey Edwards, YanQiang Lu, Jeffrey Tran
 *
 */
public class GreenFormatter implements ThemeFormatter {

	/**
	 * format green color pits
	 */
	public Color formatPitColor() {
		return Color.decode("555555");
	}

	/**
	 * format square shaped pits
	 */
	public Shape formatShape(PitView pit) {
		return new Rectangle2D.Double(pit.getX(), pit.getY(), pit.getWidth(),
				pit.getHeight());
	}

}