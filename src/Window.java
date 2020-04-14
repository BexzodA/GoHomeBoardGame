import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Window {
	
	private JFrame window;
	private PlayerCreationScreen pcs;
	private Board gameBoard;
	private EndScreen es;
	
	private static float width;
	private static float height;
	
	public Window(String name, int width, int height) {
		window = new JFrame(name);
		
		window.setMinimumSize(new Dimension(1280, 720));
		window.setPreferredSize(new Dimension(1920, 1080));
		
		window.setSize(new Dimension(width, height));
		
		Window.width = width;
		Window.height = height;
		
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setLocationRelativeTo(null);
				
		pcs = new PlayerCreationScreen(this);
		
		window.getContentPane().add(pcs);
		
		window.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				Window.width = window.getWidth();
				Window.height = window.getHeight();
			}
		});
		
		window.setVisible(true);
	}
	
	public void switchToEndScreen() {
		if(gameBoard.isGameWon()) {
			es = new EndScreen(gameBoard.getWinner(), this);
			window.getContentPane().remove(gameBoard);
			window.getContentPane().add(es);
			((JPanel)window.getContentPane()).updateUI();
		}
	}
	
	public void restart() {
		window.getContentPane().removeAll();
		pcs = new PlayerCreationScreen(this);
		window.getContentPane().add(pcs);
		((JPanel)window.getContentPane()).updateUI();
	}
	
	public void switchToBoard() {
		window.getContentPane().remove(pcs);
		gameBoard = new Board(pcs.getPlayerSelecters(), this);
		window.getContentPane().add(gameBoard);
		((JPanel)window.getContentPane()).updateUI();
	}
	
	public static float getScreenRatio() {
		return width/height;
	}
	
	public static float getWidth() {
		return width;
	}
	
	public static float getHeight() {
		return height;
	}
}