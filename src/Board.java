import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import javax.swing.JPanel;

public class Board extends JPanel{
	
	private static final long serialVersionUID = 134606650518445714L;
	
	private JPanel south, west, north, east;
	private BoardSlot slots [];
	
	public Board(ArrayList<PlayerSelecter> playerConfigs) {
		super();
		this.setLayout(new BorderLayout());
		((BorderLayout)this.getLayout()).setVgap(-1);
		
		this.setOpaque(true);
		this.setBackground(new Color(255,255,255));
		
		initPanels();
		initSlots();
		addComponents();
		
		for(PlayerSelecter e : playerConfigs) {
			slots[0].addPlayer(e.generatePlayer());
		}
	}
	
	private void initPanels() {
		///SOUTH///
		south = new JPanel();
		south.setLayout(new FlowLayout());
		south.setOpaque(true);
		south.setBackground(new Color(255,255,255));
		
		((FlowLayout)south.getLayout()).setHgap(-1);
		((FlowLayout)south.getLayout()).setVgap(0);
		///////////
		
		///EAST///
		east = new JPanel();
		east.setLayout(new BorderLayout());
		
		((BorderLayout)east.getLayout()).setVgap(-1);
		
		east.setOpaque(true);
		east.setBackground(new Color(255,255,255));
		//////////
		
		///NORTH///
		north = new JPanel();
		north.setLayout(new FlowLayout());
		
		north.setOpaque(true);
		north.setBackground(new Color(255,255,255));
		
		((FlowLayout)north.getLayout()).setHgap(-1);
		((FlowLayout)north.getLayout()).setVgap(0);
		///////////
		
		///WEST///
		west = new JPanel();
		west.setLayout(new BorderLayout());
		
		((BorderLayout)west.getLayout()).setVgap(-1);
		
		west.setOpaque(true);
		west.setBackground(new Color(255,255,255));
		//////////
	}
	
	private void initSlots() {
		slots = new BoardSlot[22];
		for (int i = 0; i < slots.length; i++) {
			slots[i] = new BoardSlot(i + "");
			
			slots[i].setMinimumSize(new Dimension(162,138));
			slots[i].setPreferredSize(new Dimension(162,138));
			slots[i].setMaximumSize(new Dimension(162,138));

			if(i > -1 && i < 8)
				south.add(slots[i]);
			else if(i > 7 && i < 11) {
				if(i%3 == 0)
					east.add(slots[i], BorderLayout.CENTER);
				if(i%3 == 1)
					east.add(slots[i], BorderLayout.NORTH);
				if(i%3 == 2)
					east.add(slots[i], BorderLayout.SOUTH);
			} else if(i == 18) {
				for(int j = 18; j > 10; j--)
					north.add(slots[j]);
			} else {
				if(i%3 == 0)
					west.add(slots[i], BorderLayout.SOUTH);
				if(i%3 == 1)
					west.add(slots[i], BorderLayout.NORTH);
				if(i%3 == 2)
					west.add(slots[i], BorderLayout.CENTER);
			}
		}
	}
	
	private void addComponents() {
		this.add(south, BorderLayout.SOUTH);
		this.add(east, BorderLayout.EAST);  
		this.add(north, BorderLayout.NORTH);
		this.add(west, BorderLayout.WEST); 
	}
	
}