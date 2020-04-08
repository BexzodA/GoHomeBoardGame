import javax.swing.SwingUtilities;

public class GoHomeBoardGame {
	
	public static void main(String[] args) {
		try {
			SwingUtilities.invokeLater(()->{
				new Window("GoHomeBoardGame", 1294, 720);
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
