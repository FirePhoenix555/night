package night;

import java.awt.Color;
import java.awt.Graphics2D;

public class Wall extends GameObject {
	private GameObject door;
	private boolean doored = false;
	private boolean isVertical = false;
	
	private Scene doorLeadsTo;
	
	private int thw, thh, lhx, lhy;
	
	public Wall(int x_, int y_, int width_, int height_) {
		super(x_, y_, width_, height_);
		
		if (width_ < height_) isVertical = true;
	}
	public void setDoor(int x_, int y_, int width_, int height_, Scene dlt) {
		door = new GameObject(x_, y_, width_, height_);
		doored = true;
		
		thw = door.x - x;
		thh = door.y - y;
		
		lhx = door.x + door.width;
		lhy = door.y + door.height;
		
		if (isVertical) { // vertical
			thw = width;
			lhx = x;
		} else { // horizontal
			thh = height;
			lhy = y;
		}
		
		doorLeadsTo = dlt;
	}
	
	@Override
	public void draw(Graphics2D g) {
		g.setColor(Color.gray);
		
		if (!doored) g.fillRect(x, y, width, height);
		else {
//			GameObject th = new GameObject(x, y, thw, thh);
//			GameObject lh = new GameObject(lhx, lhy, x + width - lhx, y + height - lhy);
//			
//			th.draw(g);
//			lh.draw(g);
			
			g.fillRect(x, y, thw, thh);
			g.fillRect(lhx, lhy, x + width - lhx, y + height - lhy);
		}
		
	}
	
	@Override
	public boolean intersects(GameObject g) {
		if (!doored) return super.intersects(g);
		
		GameObject th = new GameObject(x, y, thw, thh);
		GameObject lh = new GameObject(lhx, lhy, x + width - lhx, y + height - lhy);
		
		if (th.intersects(g) || lh.intersects(g)) {
			// restrict movement
			return true;
		}
		
		return false;
	}
	
	public boolean intersects(GameObject g, SceneManager sm) {
		
		if (intersects(g)) return true;
		if (!doored) return false; // you're done with this and you didn't intersect anyway
		
		if (door.intersects(g)) {
			sm.setScene(doorLeadsTo);
		}
		
		return false;
	}
}
