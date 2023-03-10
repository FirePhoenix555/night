package night;

import java.awt.Color;
import java.awt.Graphics2D;

public class GameObject {
	public int x, y;
	public int width, height;
	
	public float health = 1;
	protected Timer t;
	
	protected GameHandler gh;
	
	public GameObject(GameHandler gh_, int x_, int y_, int width_, int height_) {
		x = x_;
		y = y_;
		width = width_;
		height = height_;
		gh = gh_;
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
	
	public void setTexture() {
		// TODO
	}
	
	public boolean intersects(GameObject g) {
		
		if (x > g.x + g.width || y > g.y + g.height || x + width < g.x || y + height < g.y) {
			return false;
		}
		
		
		return true;
	}
}