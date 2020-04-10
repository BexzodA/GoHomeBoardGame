import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Window {
	
	private JFrame window;
	private PlayerCreationScreen pcs;
	private Board gameBoard;
	
	public Window(String name, int width, int height) {
		window = new JFrame(name);
		window.setSize(width, height);
		window.setResizable(false);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setLocationRelativeTo(null);
		
		window.getContentPane().setBackground(new Color(255,255,255));
		
		pcs = new PlayerCreationScreen(this);
		gameBoard = new Board();
		
		window.getContentPane().add(pcs);
		
		window.setVisible(true);
	}
	
	public void switchToBoard() {
		window.getContentPane().remove(pcs);
		window.getContentPane().add(gameBoard);
		((JPanel)window.getContentPane()).updateUI();
	}
	
}
