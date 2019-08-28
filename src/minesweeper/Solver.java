package minesweeper;

import java.awt.Point;
import java.util.*;

public class Solver {
	
	private Board board;
	private Set<Box> clickedBoxes;
	private Set<Box> unClickedBoxes;
	private Set<Box> checkedBoxes;
	
	public Solver(Board board) {
		this.board = board;
		clickedBoxes = new HashSet<Box>();
		unClickedBoxes = new HashSet<Box>();
		checkedBoxes = new HashSet<Box>();
	}
	
	private void update() {
		Set<Box> allBoxes = new HashSet<Box>(board.getAllBoxes()); 
		for (Box box:allBoxes) {
			if (box.isClicked()) {
				clickedBoxes.add(box);
			} else {
				unClickedBoxes.add(box);
			}
		}
	}
	
	private Set<Box> getClickedBoxes() {
		return clickedBoxes;
	}
	
	private Set<Box> getNumberedBoxes() {
		/**
		 * returns clicked boxes with nearbyBombs >= 1
		 */
		Set<Box> numberedBoxes = new HashSet<Box>();
		for (Box box:getClickedBoxes()) {
			if (box.getNearbyBombCount() > 0) {
				numberedBoxes.add(box);
			}
		}
		return numberedBoxes;
	}
	
	private Set<Box> getUnClickedBoxes() {
		return unClickedBoxes;
	}
	
	private Set<Box> getClickedNeighbours(Box box) {
		Set<Box> clicked = new HashSet<Box>();
		Collection<Box> nearBoxes = box.getNearbyBoxes().values();
		for (Box nb:nearBoxes) {
			if (nb.isClicked()) {
				clicked.add(nb);
			}
		}
		return clicked;
	}
	
	private Set<Box> getUnClickedNeighbours(Box box) {
		Set<Box> unClicked = new HashSet<Box>();
		Collection<Box> nearBoxes = box.getNearbyBoxes().values();
		for (Box nb:nearBoxes) {
			if (!nb.isClicked()) {
				unClicked.add(nb);
			}
		}
		return unClicked;
	}
	
	private int getNearbyFlagsCount(Box box) {
		int nFlags = 0;
		for (Box nb:box.getNearbyBoxes().values()) {
			if (nb.hasFlag()) {
				nFlags++;
			}
		}
		return nFlags;
	}
	
	public void solve() {
		board.solverOn();
		update();
		
		Random rnd = new Random();
		int x = rnd.nextInt(board.getWidth());
		int y = rnd.nextInt(board.getHeight());
		Box box = board.getBox(x, y);		
		box.leftClick();
		update();
		
		if (board.isGameOver() || board.isSuccessful()) {
			return;
		}
		
		Set<Box> boxesToCheck;
		int nFlags;
		
		/* While game has not ended */
		while (!(board.isGameOver() || board.isSuccessful())) {
			boxesToCheck = getNumberedBoxes();
			box = boxesToCheck.iterator().next();
			nFlags = getNearbyFlagsCount(box);
			Set<Box> unClickedNeighbours = getUnClickedNeighbours(box);
			
			/* If there's some box to click nearby */
			if (!unClickedNeighbours.isEmpty()) {
				/* Flag all unclicked boxes nearby 
				 * if the number of bomb count matches the number of unclicked boxes */
				if (unClickedNeighbours.size() == box.getNearbyBombCount()) {
					for (Box nb:unClickedNeighbours) {
						if (!nb.hasFlag()) {
							nb.rightClick();
						}
					}
				} else if (nFlags == box.getNearbyBombCount()) {
				/* Else click the unflagged boxes
				 * if the number of flags matches the number of unclicked boxes */
					for (Box nb:unClickedNeighbours) {
						if (!nb.hasFlag()) {
							nb.leftClick();	
						}
					}
					update();
				} else {
					// Else do nothing
				}
			}
		}
	}
	
}
