package night;

import java.awt.Color;
import java.awt.Graphics2D;

public class TextHandler extends GameObject {
	public String text;
	public int x, y;
	private Color c = Color.white;
	final private static int maxWidth = 25; // in CHARACTERS not pixels

	private String displayString = "";
	private int currentIndex = 1;
	private boolean finished = false;
	
	final private static float fps = 10f;
	
	public TextHandler(String t_, int x_, int y_, GameHandler g) {
		super(x_, y_, 0, 0);
		
		initialize(t_, x_, y_, g);
	}
	
	public void initialize(String t_, int x_, int y_, GameHandler g) {
		text = t_;
		x = x_;
		y = y_;
		
		t = new Timer(fps, g, this);
	}
	
	public void setColor(Color c_) {
		c = c_;
	}
	
	@Override
	public void draw(Graphics2D g) {
		g.setColor(c);
		
		
		// https://stackoverflow.com/questions/4413132/problems-with-newline-in-graphics2d-drawstring
		int y_ = y;
		for (String line : displayString.split("\n")) {
			g.drawString(line, x, y_ += g.getFontMetrics().getHeight());
		}
		
		// g.drawString(displayString, x, y);
	}
	
	@Override
	public void onTimer() {
		// update
		
		if (currentIndex > text.length()) finished = true;
		else {
			displayString = text.substring(0, currentIndex);
			
			String temp = "";
			
			for (int i = 0; i < displayString.split(" ").length; i++) {
				String word = displayString.split(" ")[i];
				String[] lines = temp.split("\n");
				String attempt = lines[lines.length-1] + " " + word;
				
				if (attempt.length() > maxWidth) temp += "\n" + word;
				else temp += " " + word;
			}
			
			displayString = temp.substring(1);
		}
		
		currentIndex++;
	}
	
	public void update() {
		if (finished) return;
		
		t.update();
	}
}
