import java.awt.Color;

public class Player implements Moveable{
	
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
	
	public static int getNumOfPlayers() {
		return numOfPlayers;
	}

	@Override
	public void onObstacleHit(Obstacle obs) {
			
	}
	
	public String getName() {
		return playerName;
	}
	
	public Color getColor() {
		return playerColor;
	}
	
}