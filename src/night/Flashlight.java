package night;

import java.awt.Color;
import java.awt.Graphics2D;

public class Flashlight extends GameObject {
//	private Player owner;
	
	final private static int minradius = 5, maxradius = 30;
	private int radius;
	
	final private static float batteryUseSpeed = 0.0125f;
	final private static float batteryRegenSpeed = 2f * batteryUseSpeed;
	private float battery = 1; // we're going to do a thing and have this be between 0 and 1
	public boolean on = true;
	
	final public static float dmg = 0.3f;
	
	public Flashlight(GameHandler gh_, Player owner_, int x_, int y_) {
		super(gh_, x_, y_, minradius*2, minradius*2);
		
//		owner = owner_;
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
		g.drawRect(5, 30, 200, 20);
		g.fillRect(5, 30, (int) (battery * 200), 20);
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
	
	public void update() {
		setPos(gh.mh.x, gh.mh.y);
		on = gh.mh.mouseHeld;
		
		if (on) {
			battery -= batteryUseSpeed;
			if (battery <= 0) {
				// radius = minradius;
				on = false;
				battery = 0;
				gh.mh.mouseHeld = false;
				updateRadius(-2);
				return;
			}
			updateRadius(1);
		} else {
			battery += batteryRegenSpeed;
			if (battery > 1) {
				battery = 1;
			}
			updateRadius(-2);
		}
	}
	
	// https://stackoverflow.com/questions/401847/circle-rectangle-collision-detection-intersection
	public boolean intersects(GameObject g) {
		int dx = Math.abs(g.x - x);
		int dy = Math.abs(g.y + g.height - y);

	    if (dx > g.width / 2f + radius || dy > g.height / 2f + radius)
	    	return false;

	    if (dx <= g.width / 2f || dy <= g.height / 2f)
	    	return true;

	    double d = Math.pow(dx - g.width / 2f, 2) + Math.pow(dy - g.height / 2f, 2);
	    return d <= Math.pow(radius, 2);
	}
}
