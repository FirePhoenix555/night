package night;

import java.awt.Color;
import java.awt.Graphics2D;

public class Flashlight extends GameObject {
	private Player owner;
	
	final private static int minradius = 5, maxradius = 30;
	private int radius;
	
	public Flashlight(Player owner_, int x_, int y_) {
		super(x_, y_, minradius*2, minradius*2);
		
		owner = owner_;
		radius = minradius;
	}
	
	@Override
	public void draw(Graphics2D g) {
		g.setColor(new Color(255, 255, 150, 100)); // yellow but translucent
		g.fillOval(x - radius, y - radius, radius * 2, radius * 2);
	}
	
	public void setPos(int x_, int y_) {
		x = x_;
		y = y_;
	}
	
	public void updateRadius(int amt) {
		radius += amt;
		if (radius < minradius) radius = minradius;
		else if (radius > maxradius) radius = maxradius;
	}
}
