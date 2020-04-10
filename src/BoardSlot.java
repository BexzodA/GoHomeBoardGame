import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JButton;

public class BoardSlot extends JButton {
	
	private static final long serialVersionUID = 1L;
	
	private Color highlightColor = new Color(0,0,0);
	private Color pressedColor = new Color(230,230,230);
	private Color textColor = new Color(0,0,0);
	
	private ArrayList<Player> playersPresent = new ArrayList<Player>();
	private Obstacle obstacle;
	
	private boolean isHome = false;
	private boolean isStart = false;
	
	public BoardSlot() {
		
	}
	
	public BoardSlot(String str) {
		super(str);
	}
	
	public void addPlayer(Player player) {
		playersPresent.add(player);
		updateColor();
	}
	
	public void updateColor() {
		int r = 0, g = 0, b = 0;
		
		for(Player p : playersPresent) {
			r += p.getColor().getRed();
			g += p.getColor().getGreen();
			b += p.getColor().getBlue();
		}
		
		r /= playersPresent.size();
		g /= playersPresent.size();
		b /= playersPresent.size();
		
		highlightColor = new Color(r,g,b);
	}
	
	@Override
	public void paintComponent(Graphics gfx) {
		if(getModel().isRollover()) {
			gfx.setColor(highlightColor);
			gfx.drawRect(2, 2, getWidth() - 5, getHeight() - 5);
		} else {
			gfx.clearRect(0, 0, getWidth(), getHeight());
		}
		if(getModel().isPressed()) {
			gfx.setColor(pressedColor);
			gfx.fillRect(3, 3, getWidth() - 6, getHeight() - 6);
		} else {
			gfx.clearRect(3, 3, getWidth() - 6, getHeight() - 6);
		}
		gfx.setColor(textColor);
		gfx.drawChars(getText().toCharArray(), 0, getText().length(), getWidth()/2, getHeight()/2);
		System.out.println(getModel().isRollover());
	}

	@Override
	public void paintBorder(Graphics gfx) {
		gfx.setColor(highlightColor);
		gfx.drawRect(1, 1, getWidth() - 3, getHeight() - 3);
	}
	
}
