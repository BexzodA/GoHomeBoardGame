import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Window {
	
	private JFrame window;
	private Board gameBoard;
	//private JPanel south, west, north, east;
	
	public Window(String name, int width, int height) {
		window = new JFrame(name);
		window.setSize(width, height);
		window.setResizable(false);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setLocationRelativeTo(null);
		
		window.getContentPane().setBackground(new Color(255,255,255));
		window.getContentPane().setLayout(new BorderLayout());
		
		((BorderLayout)window.getContentPane().getLayout()).setVgap(-1);
		
		gameBoard = new Board(window.getContentPane());
		
		window.setVisible(true);
	}
	
}
