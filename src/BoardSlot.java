import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class BoardSlot extends JButton{
	
	private enum State{
		START,
		EMPTY,
		OBSTACLE,
		HOME;
	}
	
	private static final long serialVersionUID = 1L;
	
	private Color highlightColor = new Color(0,0,0);
	private Color pressedColor = new Color(230,230,230);
	private Color textColor = new Color(0,0,0);
	
	private ArrayList<Player> playersPresent = new ArrayList<Player>();
	private Obstacle obstacle;
	
	private boolean playerIsPresent = false;
	
	private static final float fontScaleFactor = 160.f;
	
	private State slotState = State.EMPTY;
	
	public BoardSlot() {
		this("");
	}
	
	public BoardSlot(String str) {
		super(str);
		this.setFont(new Font(this.getFont().getName(), Font.BOLD, 10));
		this.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				calculateFontSize();
			}
		});
		calculateFontSize();
	}
	
	public void addObstacle(Obstacle obs) {
		obstacle = obs;
		slotState = State.OBSTACLE;
	}
	
	public void addPlayer(Player player) {
		playersPresent.add(player);
		updateColor();
		playerIsPresent = true;
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
	
	private void calculateFontSize() {
		this.setFont(new Font(this.getFont().getName(), Font.BOLD, (int)((Window.getWidth() + Window.getHeight()) / fontScaleFactor)));
	}
	
	public void setAsStart() {
		if(slotState == State.EMPTY) {
			slotState = State.START;
		}
		else
			System.err.println("Attempted to change the state of a slot more than once");
	}
	
	public void setAsHome() {
		if(slotState == State.EMPTY) {
			slotState = State.HOME;
		}
		else
			System.err.println("Attempted to change the state of a slot more than once");
	}
	
	@Override
	public void paintComponent(Graphics gfx) {
		gfx.clearRect(0, 0, getWidth(), getHeight());
		if(getModel().isRollover()) {
			gfx.setColor(highlightColor);
			gfx.drawRect(3, 3, getWidth() - 7, getHeight() - 7);
		}
		if(getModel().isPressed()) {
			gfx.setColor(pressedColor);
			gfx.fillRect(3, 3, getWidth() - 6, getHeight() - 6);
		}
		gfx.setColor(textColor);
		if(playerIsPresent) {		
			int yOffSet = 0;
			int greatestLength = 0;
			
			for(Player p : playersPresent) {
				if(p.getName().length() > greatestLength) {
					greatestLength = p.getName().length();
				}
			}
			
			for(Player p : playersPresent) {
				gfx.setColor(p.getColor());
				gfx.drawString(p.getName(), getWidth()/2 - greatestLength * gfx.getFontMetrics().charWidth('a') / 2, getHeight()/2 + yOffSet * gfx.getFontMetrics().getHeight());
				yOffSet += 1;
			}
		}
		
		switch(slotState) {
			case OBSTACLE :
					ImageIcon icon = new ImageIcon(".//src//assets//obstacle.png");
					Image image = icon.getImage();
					gfx.drawImage(image, 32, 16, getWidth() - 64, getHeight() - 32, null);
					gfx.setColor(Color.GREEN);
					int xOffSet = gfx.getFontMetrics().charWidth(obstacle.getSpacesToMove() + 48) / 2;
					gfx.drawString(obstacle.getSpacesToMove() + "", getWidth()/2 - xOffSet, getHeight()/2 - getHeight()/50);
					break;
			case START :
					gfx.setColor(Color.CYAN);
					gfx.drawString("START!", getWidth()/2 - 6 * gfx.getFontMetrics().charWidth('T') / 2, getHeight()/4);
					break;
		}
	}
	
	@Override
	public void paintBorder(Graphics gfx) {
		gfx.setColor(highlightColor);
		gfx.drawRect(1, 1, getWidth() - 3, getHeight() - 3);
		if(playerIsPresent) {
			gfx.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
			gfx.drawRect(2, 2, getWidth() - 5, getHeight() - 5);
		}
	}
	
}