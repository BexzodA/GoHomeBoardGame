import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class PlayerSelecter extends JPanel{

	private static final long serialVersionUID = 1855246567470991077L;
	
	private JLabel label;
	private JTextField textField;
	private JButton pickColor;
	private String playerName = "No Name";
	private Color playerColor = Color.BLACK;
	
	public PlayerSelecter() {
		label = new JLabel("Enter Player Name: ");
		textField = new JTextField();
		
		textField.setMinimumSize(new Dimension(60,20));
		textField.setPreferredSize(new Dimension(60,20));
		
		textField.addActionListener((e)->
		{;
			playerName = textField.getText();
		});
		
		pickColor = new JButton("Pick Color");
		pickColor.setFocusable(false);
		pickColor.addActionListener((e)->
		{
			playerColor = JColorChooser.showDialog(pickColor, "Choose Color", Color.RED);
			pickColor.setForeground(playerColor);
			textField.setForeground(playerColor);
		});
		
		addComponents();
	}
	
	public void setText(String str) {
		textField.setText(str);
	}
	
	public void addComponents() {
		this.add(label);
		this.add(textField);
		this.add(pickColor);
	}
	
	public Color getPlayerColor() {
		return playerColor;
	}
	
	public Player generatePlayer(BoardSlot location) {
		playerName = textField.getText();
		return new Player(playerName, playerColor, location);
	}
	
}