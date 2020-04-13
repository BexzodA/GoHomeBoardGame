import java.util.Random;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class Deck extends JLabel{

	private static final long serialVersionUID = -6622823178073375320L;

	public Board brd;
	
	String defaultTxt = "Hit play to play turn.";
	
	public Deck(Board brd) {
		super();
		this.brd = brd;
		this.setText(defaultTxt);
		this.setHorizontalAlignment(SwingConstants.CENTER);
	}
	
	public void update() {
		this.setForeground(brd.getCurPlayer().getColor());
		this.setText(defaultTxt);
	}
	
	public Card drawCard() {
		Random rng = new Random();
		int rand = rng.nextInt(10);
		switch(rand) {
		case 0:
		case 1:
			this.setText("Moving:\n 1");
			return new Card() {
				@Override
				public void whenDrawn() {
					brd.moveCurrentPlayer(1);
				}
			};
		case 2:
		case 3:
			this.setText("Moving:\n 2");
			return new Card() {
				@Override
				public void whenDrawn() {
					brd.moveCurrentPlayer(2);
				}
			};
		case 4:
		case 5:
			this.setText("Moving:\n 3");
			return new Card() {
				@Override
				public void whenDrawn() {
					brd.moveCurrentPlayer(3);
				}
			};
		case 6:
		case 7:
			this.setText("Moving:\n 4");
			return new Card() {
				@Override
				public void whenDrawn() {
					brd.moveCurrentPlayer(4);
				}
			};
		case 8:
			this.setText("Loose\nTurn.");
			return new Card() {
				@Override
				public void whenDrawn() {
					brd.looseTurn();;
				}
			};
		case 9:
			this.setText("Switch with another player.");
			return new Card() {
				@Override
				public void whenDrawn() {
					brd.switchPlayer();
				}
			};
		default:
			return new Card() {
				@Override
				public void whenDrawn() {
					System.err.println("Invalid RN.");
				}
			};
		}
	}
}