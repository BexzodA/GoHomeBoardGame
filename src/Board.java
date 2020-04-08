import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

public class Board {
	
	private Container parent;
	private JPanel south, west, north, east;
	
	public Board(Container parent) {
		
		this.parent = parent;
		
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
		
		initSlots();
		addToParent();	
	}
	
	private void initSlots() {
		JButton button [] = new JButton[22];
		for (int i = 0; i < button.length; i++) {
			button[i] = new BoardSlot(i + "");
			button[i].setMinimumSize(new Dimension(162,138));
			button[i].setPreferredSize(new Dimension(162,138));
			button[i].setMaximumSize(new Dimension(162,138));
			if(i > -1 && i < 8)
				south.add(button[i]);
			else if(i > 7 && i < 11) {
				if(i%3 == 0)
					east.add(button[i], BorderLayout.CENTER);
				if(i%3 == 1)
					east.add(button[i], BorderLayout.NORTH);
				if(i%3 == 2)
					east.add(button[i], BorderLayout.SOUTH);
			} else if(i == 18) {
				for(int j = 18; j > 10; j--)
					north.add(button[j]);
			} else {
				if(i%3 == 0)
					west.add(button[i], BorderLayout.SOUTH);
				if(i%3 == 1)
					west.add(button[i], BorderLayout.NORTH);
				if(i%3 == 2)
					west.add(button[i], BorderLayout.CENTER);
			}
		}
	}
	
	private void addToParent() {
		parent.add(south, BorderLayout.SOUTH);
		parent.add(east, BorderLayout.EAST);
		parent.add(north, BorderLayout.NORTH);
		parent.add(west, BorderLayout.WEST);
	}
	
}
