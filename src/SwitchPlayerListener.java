import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SwitchPlayerListener implements ActionListener{
	
	Board board;
	BoardSlot slot;
	
	public SwitchPlayerListener(Board board, BoardSlot slot) {
		this.board = board;
		this.slot = slot;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(board.isSwitchtingPlayer()) {
			if(slot.playerIsPresent()) {
				BoardSlot source = board.getCurPlayer().getLocation();
				if(source == slot) {
					board.popPlayersTurn();
					board.updateLabel();
					board.endSwitching();
					return;
				}
				Player playerSwitching = source.removePlayer(board.getCurPlayer());
				Player playerBeingSwitched = slot.removeFirstPlayer();
				slot.addPlayer(playerSwitching);
				playerSwitching.updateLocation(slot);
				source.addPlayer(playerBeingSwitched);
				playerBeingSwitched.updateLocation(source);
				source.repaint();
				slot.repaint();
				board.popPlayersTurn();
				board.updateLabel();
				board.endSwitching();
			} else {
				board.popPlayersTurn();
				board.updateLabel();
				board.endSwitching();
			}
		}
	}

}
