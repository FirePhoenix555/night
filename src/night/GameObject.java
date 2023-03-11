package night;

import java.awt.Color;
import java.awt.Graphics2D;

public class GameObject {
	public int x, y;
	public int width, height;
	
	public float health = 1;
	protected Timer t;
	
	public GameObject(int x_, int y_, int width_, int height_) {
		x = x_;
		y = y_;
		width = width_;
		height = height_;
	}
	
	public void draw(Graphics2D g) {
		g.setColor(Color.white);
		g.fillRect(x, y, width, height);
	}
	
	public void onTimer() {
		// 
	}
	
	public void resetTimer() {
		t = null;
	}
}