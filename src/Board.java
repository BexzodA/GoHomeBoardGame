import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Stack;

import javax.swing.JPanel;

public class Board extends JPanel {
	
	private static final long serialVersionUID = 134606650518445714L;
	
	private BoardSlot slots [];
	
	private ArrayList<Player> players;
	private Stack<Player> turns;
	
	private static final int PLAYER_MOVE_TIME = 500;
	
	public Board(ArrayList<PlayerSelecter> playerConfigs) {
		super();
		this.setLayout(new GridBagLayout());
		
		this.setOpaque(true);
		this.setBackground(new Color(255,255,255));
		
		players = new ArrayList<Player>();
		turns = new Stack<Player>();
		
		initSlots();
		addObstacles();
		addComponents();
		
		for(PlayerSelecter e : playerConfigs) {
			Player player = e.generatePlayer(slots[0]);
			slots[0].addPlayer(player);
			players.add(player);
		}
		
		Collections.shuffle(players);
		
		for(Player e : players) {
			turns.push(e);
		}
		
		slots[0].setAsStart();
		slots[21].setAsHome();
		
		moveFowardPlayer(turns.pop(), 3);
	}
	
	private synchronized void moveFowardPlayer(Player player, int amount) {
		Thread moveFoward = new Thread(
				() ->
				{
					for(int i = 0; i < amount; i++) {
						BoardSlot location = player.getLocation();
						location.removePlayer(player);
						slots[location.getIndex() + 1].addPlayer(player);
						
						location.repaint();
						slots[location.getIndex() + 1].repaint();
						
						player.updateLocation(slots[location.getIndex() + 1]);
						
						try {
							Thread.sleep(PLAYER_MOVE_TIME);
						} catch(InterruptedException e) {
							e.printStackTrace();
						}	
						
						if(slots[location.getIndex() + 1].hasObstacle()) {
							moveBackPlayer(player, slots[location.getIndex() + 1].getObstacle().getSpacesToMove());
							return;
						}
					}
				}
		);
		moveFoward.start();
	}
	
	private synchronized void moveBackPlayer(Player player, int amount) {
		Thread moveBackward = new Thread(
				() ->{
					for(int i = 0; i < amount; i++) {
						BoardSlot location = player.getLocation();
						
						if(location.getIndex() == 0) {
							return;
						}
						
						location.removePlayer(player);
						slots[location.getIndex() - 1].addPlayer(player);
						
						location.repaint();
						slots[location.getIndex() - 1].repaint();
						
						player.updateLocation(slots[location.getIndex() - 1]);
						try {
							Thread.sleep(PLAYER_MOVE_TIME);
						} catch(InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
		);
		moveBackward.start();
	}
	
	private void initSlots() {
		slots = new BoardSlot[22];
		for (int i = 0; i < slots.length; i++) {
			slots[i] = new BoardSlot(i);
		}	
	}
	
	private void addComponents() {
		int x = 0;
		
		for (int i = 18; i > 10; i--) {
			placeComp(slots[i], this, x, 0, 1, 1);
			x++;
		}
		
		placeComp(slots[19], this, 0, 1, 1, 1);
		placeComp(slots[10], this, 7, 1, 1, 1);
		
		placeComp(slots[20], this, 0, 2, 1, 1);
		placeComp(slots[9], this, 7, 2, 1, 1);
		
		placeComp(slots[21], this, 0, 3, 1, 1);
		placeComp(slots[8], this, 7, 3, 1, 1);
		
		for(int i = 0; i < 8; i++) {
			placeComp(slots[i], this, i, 4, 1, 1);
		}
	}
	
	 public void placeComp(Component comp, Container panel, int x, int y, int w, int h) {
		    GridBagConstraints cons = new GridBagConstraints();
		    cons.gridx = x;
		    cons.gridy = y;
		    cons.gridwidth = w;
		    cons.gridheight = h;
		    cons.fill = GridBagConstraints.BOTH;
		    cons.insets = new Insets(1,1,1,1);
		    cons.weightx = 1.0f;
		    cons.weighty = 1.0f;
		    panel.add(comp, cons);
	}
	 
	public void addObstacles() {
		Random rng = new Random();
		int takenLocations [] = new int [6];
		int size = 0;
		for(int i = 0; i < 6; i++) {
			int location = rng.nextInt(20)+1;
			for (int j = 0; j < size; j++) {
				if(location == takenLocations[j]) {
					location = rng.nextInt(20)+1;
					j--;
				}
			}
			slots[location].addObstacle(new Obstacle(rng.nextInt(4)+1));
			takenLocations[i] = location;
			size++;
		}
	}
}