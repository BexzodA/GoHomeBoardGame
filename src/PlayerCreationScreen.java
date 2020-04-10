import java.awt.BorderLayout;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class PlayerCreationScreen extends JPanel{
	
	private static final long serialVersionUID = -7405944370736581485L;
	
	Window w;
	
	private JLabel title;
	private JPanel contentPanel;
	private JLabel selectPlayerAmount;
	private String playerAmount [] = {"2","3","4"};
	private JComboBox<String> playerCountSelection;
	private ArrayList<PlayerSelecter> selectedAmount;
	private int displayedAmount = 0;
	
	public PlayerCreationScreen(Window window){
		w = window;
		title = new JLabel("GO HOME!", SwingConstants.CENTER);
		title.setFont(new Font(title.getFont().getFontName(), Font.BOLD, 100));
		this.setLayout(new BorderLayout());
		contentPanel = new JPanel();
		
		selectedAmount = new ArrayList<PlayerSelecter>();
		
		playerCountSelection = new JComboBox<String>(playerAmount);
		
		playerCountSelection.addActionListener((e)->{
			resizeToSelectedAmount();
		});
		
		playerCountSelection.setFocusable(false);
		
		selectPlayerAmount = new JLabel("Select Player Amount: ");
		
		JPanel playercountselectionpanel = new JPanel();
		
		playercountselectionpanel.add(selectPlayerAmount);
		playercountselectionpanel.add(playerCountSelection);
		
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		contentPanel.add(playercountselectionpanel);
		
		((BorderLayout)this.getLayout()).setVgap(50);
		addComponents();
		addPlayerSelecters();
		
		JButton go = new JButton("GO!");
		go.addActionListener((e)->
		{
			w.switchToBoard();
		});
		
		contentPanel.add(go);
	}
	
	private void addComponents() {
		this.add(title, BorderLayout.NORTH);
		this.add(Box.createVerticalStrut(75), BorderLayout.SOUTH);
		this.add(Box.createHorizontalStrut(350), BorderLayout.EAST);
		this.add(Box.createHorizontalStrut(350), BorderLayout.WEST);
		this.add(contentPanel, BorderLayout.CENTER);
	}
	
	public void resizeToSelectedAmount() {
		int amount = Integer.parseInt((String) playerCountSelection.getSelectedItem());
		if(displayedAmount == amount) {
			return;
		}
		if(displayedAmount < amount) {
			for(int i = 2; i < amount; i++) {
				if(!selectedAmount.get(i).isVisible()) {
					selectedAmount.get(i).setVisible(true);
					displayedAmount++;
				}
			}
		} else if(displayedAmount > amount) {
			for(int i = displayedAmount - 1; i > amount - 1; i--) {
				selectedAmount.get(i).setVisible(false);
				displayedAmount--;
			}
		}
		this.updateUI();
	}
	
	public void addPlayerSelecters() {
		for(int i = 0; i < 4; i++) {
			PlayerSelecter player = new PlayerSelecter();
			selectedAmount.add(player);
			contentPanel.add(player);
		}
		
		selectedAmount.get(selectedAmount.size() - 1).setVisible(false);
		selectedAmount.get(selectedAmount.size() - 2).setVisible(false);
		
		displayedAmount = 2;
	}
	
}