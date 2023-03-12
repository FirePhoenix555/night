package night;

import java.awt.Color;
import java.awt.Graphics2D;

public class Wall extends GameObject {
	private GameObject door;
	private boolean doored = false;
	private boolean isVertical = false;
	
	private Scene doorLeadsTo;
	
	private int thw, thh, lhx, lhy;
	
	public Wall(GameHandler gh_, int x_, int y_, int width_, int height_) {
		super(gh_, x_, y_, width_, height_);
		
		if (width_ < height_) isVertical = true;
	}
	public void setDoor(int x_, int y_, int width_, int height_, Scene dlt) {
		door = new GameObject(gh, x_, y_, width_, height_);
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
		
		GameObject th = new GameObject(gh, x, y, thw, thh);
		GameObject lh = new GameObject(gh, lhx, lhy, x + width - lhx, y + height - lhy);
		
		if (th.intersects(g) || lh.intersects(g)) {
			// restrict movement
			return true;
		}
		
		return false;
	}
	
	public boolean intersects(Player p, SceneManager sm) {
		
		if (intersects(p)) return true;
		
		if (doored && door.intersects(p)) {
			
			if (isVertical) {
//				p.y = door.y + door.height/2;
				
				if (door.x == 0) p.x = gh.width - door.width - p.width - 1;
				else p.x = door.width + 1;
			} else {
//				p.x = door.x + door.width/2;
				
				if (door.y == 0) p.y = gh.height - door.height - p.height - 1;
				else p.y = door.height + 1;
			}
			
			sm.setScene(doorLeadsTo);
		}
		
		return false;
	}
}
