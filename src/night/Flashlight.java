package night;

import java.awt.Color;
import java.awt.Graphics2D;

public class Flashlight extends GameObject {
	private Player owner;
	
	final private static int minradius = 5, maxradius = 30;
	private int radius;
	
	final private static float batterySpeed = 0.025f;
	private float battery = 1; // we're going to do a thing and have this be
							   // between 0 and 1
	private boolean on = true;
	
	public Flashlight(Player owner_, int x_, int y_) {
		super(x_, y_, minradius*2, minradius*2);
		
		owner = owner_;
		radius = minradius;
	}
	
	@Override
	public void draw(Graphics2D g) {
		if (on) {
			g.setColor(new Color(255, 255, 150, 100)); // yellow but translucent
			g.fillOval(x - radius, y - radius, radius * 2, radius * 2);
		}
		else {
			g.setColor(Color.lightGray);
			g.drawOval(x - radius, y - radius, radius * 2, radius * 2);
		}
		
		// The battery
		g.setColor(Color.white);
		g.drawRect(5, 5, 200, 20);
		g.fillRect(5, 5, (int) (battery * 200), 20);
	}
	
	private void setPos(int x_, int y_) {
		x = x_;
		y = y_;
	}
	
	private void updateRadius(int amt) {
		radius += amt;
		if (radius < minradius) radius = minradius;
		else if (radius > maxradius) radius = maxradius;
	}
	
	public void update(int x_, int y_, boolean mouseHeld) {
		setPos(x_, y_);
		
		if (battery > 0) on = mouseHeld;
		
		if (on) {
			updateRadius(1);
			battery -= batterySpeed;
			if (battery <= 0) {
				// radius = minradius;
				on = false;
				battery = 0;
			}
		} else {
			battery += batterySpeed;
			updateRadius(-2);
			if (battery > 1) {
				battery = 1;
			}
		}
	}
}