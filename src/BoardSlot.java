import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;

import javax.swing.JButton;

public class BoardSlot extends JButton{
	
	private static final long serialVersionUID = 1L;
	
	private Color highlightColor = new Color(0,0,0);
	private Color pressedColor = new Color(230,230,230);
	private Color textColor = new Color(0,0,0);
	
	private ArrayList<Player> playersPresent = new ArrayList<Player>();
	private Obstacle obstacle;
	
	private boolean isHome = false;
	private boolean isStart = false;
	private boolean playerIsPresent = false;
	
	public BoardSlot() {
		this("");
	}
	
	public BoardSlot(String str) {
		super(str);
		this.setFont(new Font(this.getFont().getName(), Font.BOLD, 10));
		this.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				System.out.println("TEST");
				calculateFontSize();
			}
		});
		calculateFontSize();
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
		this.setFont(new Font(this.getFont().getName(), Font.BOLD, (int)((Window.getWidth() + Window.getHeight()) / 160.0f)));
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
				gfx.drawString(p.getName(), getWidth()/2 - greatestLength * gfx.getFontMetrics().charWidth('a') / 2, getHeight()/2 + yOffSet * gfx.getFontMetrics().getHeight());
				yOffSet += 1;
			}
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