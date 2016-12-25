import java.awt.Color;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;


/**
 * Concrete strategy/theme for red circle pits
 *
 * @author Corey Edwards, YanQiang Lu, Jeffrey Tran
 */
public class RedFormatter implements ThemeFormatter
{
    /**
     * format red color pits
     */
    public Color formatPitColor()
    {
        return Color.RED;
    }

	/**
	 * format circle shaped pits
	 */
	public Shape formatShape(PitView pit) {
		return new Ellipse2D.Double(pit.getX(), pit.getY(), pit.getWidth(), pit.getHeight());
	}
    
}
