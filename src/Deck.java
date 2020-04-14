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
		int rand = rng.nextInt(10);
		int curve = 0;
		switch(rand) {
		case 0:
		case 1:
			this.setText("Moving:\n 1");
			return new Card() {
				@Override
				public void whenDrawn() {
					brd.move(1 + curve);
				}
			};
		case 2:
		case 3:
			this.setText("Moving:\n 2");
			return new Card() {
				@Override
				public void whenDrawn() {
					brd.move(2 + curve);
				}
			};
		case 4:
		case 5:
			this.setText("Moving:\n 3");
			return new Card() {
				@Override
				public void whenDrawn() {
					brd.move(3 + curve);
				}
			};
		case 6:
		case 7:
			this.setText("Moving:\n 4");
			return new Card() {
				@Override
				public void whenDrawn() {
					brd.move(4 + curve);
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
					brd.dontWait();
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
	
//	public Card drawCard() {
//		Random rng = new Random();
//		int max = 10;
//		int rand = rng.nextInt(max);
//		int curve = 0;
//		switch(rand) {
//		case 0:
//		case 1:
//		case 2:
//		case 3:
//		case 4:
//			this.setText("Moving:\n 4");
//			return new Card() {
//				@Override
//				public void whenDrawn() {
//					brd.move(4 + curve);
//				}
//			};
//		case 5:
//		case 6:
//		case 7:
//		case 8:
//		case 9:
//			this.setText("Switch with another player.");
//			return new Card() {
//				@Override
//				public void whenDrawn() {
//					brd.dontWait();
//					brd.switchPlayer();
//				}
//			};
//		default:
//			return new Card() {
//				@Override
//				public void whenDrawn() {
//					System.err.println("Invalid RN.");
//				}
//			};
//		}
//	}
	
}