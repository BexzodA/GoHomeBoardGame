import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class EndScreen extends JPanel{
	
	private static final long serialVersionUID = 7814363123340929978L;
	
	private JPanel contentPanel;
	private JLabel congrats;
	private JButton restart;
	
	public EndScreen(Player player, Window window) {
		
		this.setLayout(new BorderLayout());
		
		contentPanel = new JPanel();
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		
		congrats = new JLabel(player.getName() + " WON! CONGRATZ!");
		congrats.setForeground(player.getColor());
		congrats.setFont(new Font(congrats.getFont().getName(), Font.BOLD, 50));
		
		
		restart = new JButton("Restart?");
		restart.addActionListener((e)->{
			window.restart();
		});
		
		congrats.setAlignmentX(0.5f);
		restart.setAlignmentX(0.5f);
		restart.setFocusable(false);
		
		contentPanel.add(congrats);
		contentPanel.add(restart);
		
		this.add(Box.createVerticalStrut(250), BorderLayout.NORTH);
		this.add(contentPanel, BorderLayout.CENTER);
	}
	
}
