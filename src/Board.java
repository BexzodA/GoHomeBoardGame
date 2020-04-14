import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Stack;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Board extends JPanel {
	
	private static final long serialVersionUID = 134606650518445714L;
	
	private Window window;
	
	private BoardSlot slots [];
	private JLabel curPlayerTurn;
	private JButton play;
	private JButton autoPlay;
	
	private ArrayList<Player> players;
	private Stack<Player> turnStack;
	private Stack<Player> que;
	
	public static final int PLAYER_MOVE_TIME = 1000;
	
	private Player currentPlayer;
	private Player highestScore;
	private Deck deck;
	
	private boolean movingPlayer = false;
	private boolean switchingPlayer = false;
	
	private boolean gameWon = false;
	private boolean autoPlaying = false;
	
	private boolean doNotWait = false;
	
	private Thread autoPlayThread;
	
	private Player winner = null;
	
	private boolean drawing = false;
	


	public Board(ArrayList<PlayerSelecter> playerConfigs, Window window) {
		super();
		this.setLayout(new GridBagLayout());
		
		this.window = window;
		
		deck = new Deck(this);
		
		players = new ArrayList<Player>();
		turnStack = new Stack<Player>();
		que = new Stack<Player>();
		
		initSlots();
		addObstacles();
		
		for(PlayerSelecter e : playerConfigs) {
			Player player = e.generatePlayer(slots[0]);
			slots[0].addPlayer(player);
			players.add(player);
		}
		
		Collections.shuffle(players);
		
		for(Player e : players) {
			turnStack.push(e);
		}
		
		currentPlayer = turnStack.peek();
		highestScore = currentPlayer;
		
		addUtilities();
		addComponents();
		
		
		slots[0].setAsStart();
		slots[21].setAsHome();
		
	}
	
	public boolean isDrawing() {
		return drawing;
	}

	public void setDrawing(boolean drawing) {
		this.drawing = drawing;
	}
	
	public boolean isDoNotWait() {
		return doNotWait;
	}
	
	public void dontWait() {
		doNotWait = true;
	}
	
	public Player getCurPlayer() {
		return currentPlayer;
	}

	
	public synchronized void move(int amount) {
		if(!movingPlayer) {
			movingPlayer = true;
			Player player = popPlayersTurn();
			Thread move = new Thread(
					() ->
					{
						int amountCopy = amount;
						int delta = 1;
						for(int i = 0; i < amountCopy; i++) {
							BoardSlot location = player.getLocation();
							
							if(location.getIndex() == 0 && delta == -1) {
								updateLabel();
								movingPlayer = false;
								if(autoPlaying) {
									synchronized(autoPlayThread) {
										autoPlayThread.notify();
									}
								}
								return;
							}
							
							location.removePlayer(player);
							slots[location.getIndex() + delta].addPlayer(player);
							
							location.repaint();
							slots[location.getIndex() + delta].repaint();
							
							player.updateScore(delta);
							
							player.updateLocation(slots[location.getIndex() + delta]);
							location = player.getLocation();
							
							try {
								Thread.sleep(PLAYER_MOVE_TIME);
							} catch(InterruptedException e) {
								e.printStackTrace();
							}	
							
							if(location.getIndex() == 21 && delta == 1) {
								updateLabel();
								movingPlayer = false;
								gameWon = true;
								winner = player;
								window.switchToEndScreen();
								return;
							}
							
							if(slots[location.getIndex()].hasObstacle() && i == amountCopy - 1 && delta == 1) {
								delta = -1;
								amountCopy += slots[location.getIndex()].getObstacle().getSpacesToMove();
							} else if(location.hasObstacle() && i == amountCopy - 1 && delta == -1) {
								amountCopy += location.getObstacle().getSpacesToMove();
							}
							
						}
						updateLabel();
						movingPlayer = false;
						if(autoPlaying) {
							synchronized(autoPlayThread) {
								autoPlayThread.notify();
							}
						}
					}
			);
			move.start();
		}
	}
	
	public void updateHighest() {
		for(Player p : players) {
			if(p.getScore() > highestScore.getScore()) {
				highestScore = p;
			}
		}
	}
	
	public void switchPlayer() {
		movingPlayer = true;
		switchingPlayer = true;
		if(autoPlaying) {
			if(currentPlayer.getScore() < highestScore.getScore())
				highestScore.getLocation().getActionListeners()[0].actionPerformed(null);
			else {
				popPlayersTurn();
				updateLabel();
				endSwitching();
			}
		}
	}
	
	public boolean isSwitchtingPlayer() {
		return switchingPlayer;
	}
	
	public void endSwitching() {
		switchingPlayer = false;
		movingPlayer = false;
		if(autoPlaying) {
			try {
				Thread.sleep(1000);
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
			synchronized(autoPlayThread) {
				autoPlayThread.notify();
			}
		}
	}
	
	public Player popPlayersTurn() {
		if(turnStack.isEmpty()) {
			repopulateTurnStack();
		}
		Player player = turnStack.pop();
		que.push(player);
		if(turnStack.size() > 0) {
			currentPlayer = turnStack.peek();
		}
		else {
			repopulateTurnStack();
			currentPlayer = turnStack.peek();
		}
		return player;
	}
	
	private void repopulateTurnStack() {
		while(!que.empty()) {
			Player p = que.pop();
			turnStack.push(p);
		}
	}
	
	private void initSlots() {
		slots = new BoardSlot[22];
		for (int i = 0; i < slots.length; i++) {
			slots[i] = new BoardSlot(i);
			slots[i].addActionListener(new SwitchPlayerListener(this, slots[i]));
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
	
	public void looseTurn() {
		movingPlayer = true;
		Thread t = new Thread(()-> 
		{
			try {
				Thread.sleep(2000);
				popPlayersTurn();
				updateLabel();
				movingPlayer = false;
				if(autoPlaying) {
					synchronized(autoPlayThread) {
						autoPlayThread.notify();
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		t.start();
	}
	
	public void updateLabel() {
		curPlayerTurn.setText(currentPlayer.getName() + "'s Turn!");
		curPlayerTurn.setForeground(currentPlayer.getColor());
		deck.update();
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
	
	public void placeCompNotFill(Component comp, Container panel, int x, int y, int w, int h) {
		    GridBagConstraints cons = new GridBagConstraints();
		    cons.gridx = x;
		    cons.gridy = y;
		    cons.gridwidth = w;
		    cons.gridheight = h;
		    cons.weightx = 0.0f;
		    cons.weighty = 0.0f;
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
					j = -1;
				}
			}
			if(!slots[location - 1].hasObstacle() && !slots[location + 1].hasObstacle())
				slots[location].addObstacle(new Obstacle(rng.nextInt(4)+1));
			else {
				i--;
				continue;
			}
			takenLocations[i] = location;
			size++;
		}
	}
	
	public void addUtilities() {
		JPanel util = new JPanel();
		
		BorderLayout bl = new BorderLayout();
		
		util.setLayout(bl);
		
		curPlayerTurn = new JLabel(currentPlayer.getName() + "'s Turn!");
		curPlayerTurn.setHorizontalAlignment(SwingConstants.CENTER);
		curPlayerTurn.setVerticalAlignment(SwingConstants.BOTTOM);
		curPlayerTurn.setForeground(currentPlayer.getColor());
		curPlayerTurn.setFont(new Font(curPlayerTurn.getFont().getName(), Font.BOLD, getFontSize(60)));
		
		util.add(curPlayerTurn, BorderLayout.NORTH);
		
		deck.update();
		
		util.add(deck, BorderLayout.CENTER);	
		
		JPanel utilBts = new JPanel();
		
		FlowLayout fl = new FlowLayout();
		utilBts.setLayout(fl);
				
		play = new JButton("Play");
		
		
		
		play.addActionListener((e)->{
			if(!movingPlayer && !autoPlaying) {
				Thread draw = new Thread(()->{
					deck.drawCard().whenDrawn();
				});
				if(!drawing) {
					draw.start();
					drawing = true;
				} 
			}
		});
		play.setFocusable(false);
		
		utilBts.add(play);
		
		autoPlay = new JButton("Auto Play");
		autoPlay.setFocusable(false);
		autoPlay.addActionListener((e)->{
			if(!autoPlaying)
				autoPlaying = true;
			else 
				autoPlaying = false;
			autoPlayThread = new Thread(()-> {
				while(!gameWon && autoPlaying) {
					if(!movingPlayer) {
						deck.drawCard().whenDrawn();
					}
					if(!doNotWait) {
						synchronized(autoPlayThread) {
							try {
								autoPlayThread.wait();
								updateHighest();
							} catch(InterruptedException ex) {
								ex.printStackTrace();
							}
						}
					} else {
						doNotWait = false;
					}
				}
				}
			);
			autoPlayThread.start();
			});
		
		utilBts.add(autoPlay);
		
		util.add(utilBts, BorderLayout.SOUTH);
		
		placeCompNotFill(util, this, 1, 1, 6, 3);
	}
	
	public Player getWinner() {
		return winner;
	}
	
	public boolean isGameWon() {
		return gameWon;
	}
	
	public static int getFontSize(float factor) {
		return (int) ((Window.getHeight() + Window.getWidth()) / factor);
	}
	
	public boolean isAutoPlaying() {
		return autoPlaying;
	}
	
}
