import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JButton;

public class BoardSlot extends JButton {
	
	private static final long serialVersionUID = 1L;
	
	private static final Color highlighColor = new Color(0,0,0);
	private static final Color pressedColor = new Color(230,230,230);
	private static final Color textColor = new Color(0,0,0);
	
	private ArrayList<Player> playersPresent = new ArrayList<Player>();
	private Obstacle obstacle;
	
	private boolean isHome = false;
	private boolean isStart = false;
	
	public BoardSlot() {
		this("");
		initPlayerReferences();
	}
	
	public BoardSlot(String str) {
		super(str);
		setContentAreaFilled(false);
		initPlayerReferences();
	}
	
	public BoardSlot(Player[] players) {
		initPlayerReferences();
	}
	
	public BoardSlot(Obstacle obstacle) {
		initPlayerReferences();
	}
	
	public void initPlayerReferences() {
		for(int i = 0; i < Player.getNumOfPlayers(); i++) {
			playersPresent.add(null);
		}
	}
	
	@Override
	public void paintComponent(Graphics gfx) {
		if(getModel().isRollover()) {
			gfx.setColor(highlighColor);
			gfx.drawRect(2, 2, getWidth() - 5, getHeight() - 5);
		}
		if(getModel().isPressed()) {
			gfx.setColor(pressedColor);
			gfx.fillRect(3, 3, getWidth() - 6, getHeight() - 6);
		}
		gfx.setColor(textColor);
		gfx.drawChars(getText().toCharArray(), 0, getText().length(), getWidth()/2, getHeight()/2);
	}

	@Override
	public void paintBorder(Graphics gfx) {
		gfx.setColor(highlighColor);
		gfx.drawRect(1, 1, getWidth() - 3, getHeight() - 3);
	}
	
}
