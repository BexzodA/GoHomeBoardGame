import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class GoHomeBoardGame {
	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			SwingUtilities.invokeLater(()->{
				new Window("GoHomeBoardGame", 1295, 720);
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
