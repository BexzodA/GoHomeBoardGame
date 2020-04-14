import java.awt.Font;
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
		this.setFont(new Font(this.getFont().getName(), Font.BOLD, 20));
	}
	
	public void update() {
		this.setForeground(brd.getCurPlayer().getColor());
		this.setText(defaultTxt);
	}
	
	public Card drawCard() {
		Random rng = new Random();
		int max = brd.isAutoPlaying() ? 9 : 10;
		int rand = rng.nextInt(max);
		switch(rand) {
		case 0:
		case 1:
			this.setText("Moving:\n 1");
			return new Card() {
				@Override
				public void whenDrawn() {
					brd.move(1);
				}
			};
		case 2:
		case 3:
			this.setText("Moving:\n 2");
			return new Card() {
				@Override
				public void whenDrawn() {
					brd.move(2);
				}
			};
		case 4:
		case 5:
			this.setText("Moving:\n 3");
			return new Card() {
				@Override
				public void whenDrawn() {
					brd.move(3);
				}
			};
		case 6:
		case 7:
			this.setText("Moving:\n 4");
			return new Card() {
				@Override
				public void whenDrawn() {
					brd.move(4);
				}
			};
		case 8:
			this.setText("Loose Turn.");
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