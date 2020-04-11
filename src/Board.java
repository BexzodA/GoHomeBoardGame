import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Board extends JPanel {
	
	private static final long serialVersionUID = 134606650518445714L;
	
	private BoardSlot slots [];
	
	private ArrayList<BoardSlot> playerLocations;
	
	public Board(ArrayList<PlayerSelecter> playerConfigs) {
		super();
		this.setLayout(new GridBagLayout());
		
		this.setOpaque(true);
		this.setBackground(new Color(255,255,255));
		
		playerLocations = new ArrayList<BoardSlot>();
		
		initSlots();
		//slots[2].setIcon(new ImageIcon(".//src//assets//obstacle32.png"));
		slots[2].setObstacle(true);
		addComponents();
		
		for(PlayerSelecter e : playerConfigs) {
			slots[0].addPlayer(e.generatePlayer());
		}
		
		playerLocations.add(slots[0]);
		
	}
	
	private void initSlots() {
		slots = new BoardSlot[22];
		for (int i = 0; i < slots.length; i++) {
			slots[i] = new BoardSlot();
		}	
	}
	
	private void addComponents() {
		int x = 0;
		for (int i = 18; i > 10; i--) {
			placeComp(slots[i], this, x, 0, 1, 1);
			x++;
		}
		placeComp(slots[19], this, 0, 1, 1, 1);
		placeComp(slots[10], this, 7, 1, 1, 1);
		
		placeComp(slots[20], this, 0, 2, 1, 1);
		placeComp(slots[9], this, 7, 2, 1, 1);
		
		placeComp(slots[21], this, 0, 3, 1, 1);
		placeComp(slots[8], this, 7, 3, 1, 1);
		
		for(int i = 0; i < 8; i++) {
			placeComp(slots[i], this, i, 4, 1, 1);
		}
	}
	
	 public void placeComp(Component comp, Container panel, int x, int y, int w, int h) {
		    GridBagConstraints cons = new GridBagConstraints();
		    cons.gridx = x;
		    cons.gridy = y;
		    cons.gridwidth = w;
		    cons.gridheight = h;
		    cons.fill = GridBagConstraints.BOTH;
		    cons.insets = new Insets(1,1,1,1);
		    cons.weightx = 1.0f;
		    cons.weighty = 1.0f;
		    panel.add(comp, cons);
	}
}