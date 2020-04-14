import java.awt.Font;
import java.util.Random;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class Deck extends JLabel{

	private static final long serialVersionUID = -6622823178073375320L;

	public Board brd;
	
	private Thread anim;
	
	private static final int TIMESTEP = 10;
	private static final int NUMOFFRAMES = 100;
	
	private String defaultTxt = "Hit play to play turn.";
	
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
	
	public synchronized Card drawCard() {
		playAnim();
		synchronized(this) {
			try {
				this.wait();
				brd.setDrawing(false);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
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
			this.setText("Switch");
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
	
	private synchronized void playAnim() {
		anim = new Thread(
			()-> {
				for(int i = 0; i < NUMOFFRAMES; i++) {
					Random rng = new Random();
					int rand = rng.nextInt(10);
					switch(rand) {
					case 0:
					case 1:
						this.setText("Moving:\n 1");
						break;
					case 2:
					case 3:
						this.setText("Moving:\n 2");
						break;
					case 4:
					case 5:
						this.setText("Moving:\n 3");
						break;
					case 6:
					case 7:
						this.setText("Moving:\n 4");
						break;
					case 8:
						this.setText("Loose Turn.");
						break;
					case 9:
						this.setText("Switch");
						break;
					default:
						System.err.println("Invalid RN.");
					}
					this.updateUI();
					try {
						Thread.sleep(TIMESTEP);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				synchronized(this) {
					this.notify();
				}
			}
		);
		anim.start();
	}
	
}