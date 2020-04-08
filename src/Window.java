import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Window {
	
	private JFrame window;
	
	private JPanel south, west, north, east;
	
	public Window(String name, int width, int height) {
		window = new JFrame(name);
		window.setSize(width, height);
		window.setResizable(false);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setLocationRelativeTo(null);
		
		window.getContentPane().setBackground(new Color(255,255,255));
		window.getContentPane().setLayout(new BorderLayout());
		
		((BorderLayout)window.getContentPane().getLayout()).setVgap(-1);
		
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
		
		window.getContentPane().add(south, BorderLayout.SOUTH);
		window.getContentPane().add(east, BorderLayout.EAST);
		window.getContentPane().add(north, BorderLayout.NORTH);
		window.getContentPane().add(west, BorderLayout.WEST);
		
		window.setVisible(true);
	}
	
}
