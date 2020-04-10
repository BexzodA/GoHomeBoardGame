import java.awt.Color;

public class Player implements Moveable{
	
	private static int numOfPlayers = 0;
	
	public static final int MAX_NUM_OF_PLAYERS = 4;
	
	private final String playerName;
	private final Color playerColor;
	
	public Player(String playerName, Color playerColor) {
		this.playerName = playerName;
		this.playerColor = playerColor;
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