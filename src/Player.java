import java.awt.Color;

public class Player {
	
	private static int numOfPlayers = 0;
	
	public static final int MAX_NUM_OF_PLAYERS = 4;
	
	private final String playerName;
	private final Color playerColor;
	private BoardSlot location;
	
	public Player(String playerName, Color playerColor, BoardSlot location) {
		this.playerName = playerName;
		this.playerColor = playerColor;
		this.location = location;
	}
	
	public BoardSlot getLocation() {
		return location;
	}
	
	public void updateLocation(BoardSlot newLoc) {
		location = newLoc;
	}
	
	public void getCurPlayer() {
		
	}
	
	public static int getNumOfPlayers() {
		return numOfPlayers;
	}
	
	public String getName() {
		return playerName;
	}
	
	public Color getColor() {
		return playerColor;
	}
	
}