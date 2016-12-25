import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

/**
 * A view class used to create and manipulate pit objects.
 * 
 * @author Corey Edwards, YanQiang Lu, Jeffrey Tran
 */
public class PitView {
	private int xCoord;
	private int yCoord;
	private int width;
	private int height;
	private int pebbles;
	private Color color;
	private Shape shape;
	private final int PEBBLE_SIZE = 15;
	private final int PADDING = 5;

	public PitView(int x, int y, int w, int h, Color c) {
		this.xCoord = x;
		this.yCoord = y;
		this.height = h;
		this.width = w;
		this.color = c;
		this.shape = new Ellipse2D.Double(x, y, width, height);
	}

	/**
	 * Fills/draws the pits, called by paintComponent in MancalaBoard
	 */
	public void fill(Graphics2D g) {
		g.setColor(color);
		g.fill(shape);

		// show number of pebbles as a number
		if (pebbles > 0) {
			g.setColor(Color.ORANGE);
			Font font = new Font("Arial", Font.PLAIN, 18);
			g.setFont(font);
			g.drawString("" + pebbles, xCoord + 5, yCoord + 16);
		}

		// draw out the pebbles on the board
		double x = xCoord + PADDING;
		double y = yCoord + 15;
		for (int i = 0; i < pebbles; i++) {
			x += PEBBLE_SIZE;
			if (i > 0 && i % 4 == 0) {
				y += PEBBLE_SIZE;
				x = xCoord + PADDING + PEBBLE_SIZE;
			}
			Ellipse2D.Double stone = new Ellipse2D.Double(x, y, PEBBLE_SIZE,
					PEBBLE_SIZE);
			g.setPaint(Color.ORANGE);
			g.fill(stone);
			g.setColor(Color.BLACK);
			g.draw(stone);
		}
	}

	/**
	 * Checks if mouse was clicked inside pit
	 */
	public boolean contains(Point2D mousePointer) {
		return shape.contains(mousePointer);
	}

	/**
	 * Place i amount of pebbles into pit
	 * @param i amount of pebbles
	 */
	public void placePebbles(int i) {
		pebbles = i;
	}

	/**
	 * set the shape of the pits
	 * 
	 * @param s
	 *            the shape to format pits
	 */
	public void setShape(Shape s) {
		shape = s;
	}

	// these 4 needed for formatter strategy:
	public int getX() {
		return xCoord;
	}

	public int getY() {
		return yCoord;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}
