package night;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class MouseHandler implements MouseListener {
	
	public boolean mouseHeld = false;
	public int x, y;
	
	public void updateMouseLocation(JPanel f) {
		PointerInfo a = MouseInfo.getPointerInfo();
		Point b = a.getLocation();
		SwingUtilities.convertPointFromScreen(b, f);
		x = (int) b.getX();
		y = (int) b.getY();
		
//		System.out.println("(" + x + ", " + y + ")");
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
//		System.out.println("(" + e.getX() + ", " + e.getY() + ")");
		mouseHeld = true;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		mouseHeld = false;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}
}
