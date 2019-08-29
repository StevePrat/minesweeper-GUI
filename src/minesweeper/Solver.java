package minesweeper;

import java.util.*;

public class Solver extends Thread {
	
	private Board board;
	
	public Solver(Board board) {
		this.board = board;
	}
	
	private Set<Box> getClickedBoxes() {
		Set<Box> clickedBoxes = new HashSet<Box>();
		Set<Box> allBoxes = new HashSet<Box>(board.getAllBoxes()); 
		for (Box box:allBoxes) {
			if (box.isClicked()) {
				clickedBoxes.add(box);
			}
		}
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
	
	private Set<Box> getUnResolvedBoxes() {
		/**
		 * UnResolved boxes are defined as numbered boxes which still have unflagged neighbours
		 */
		Set<Box> unResolvedBoxes = new HashSet<Box>();
		for (Box box:getNumberedBoxes()) {
			if (!getClickableNeighbours(box).isEmpty()) {
				unResolvedBoxes.add(box);
			}
		}
		return unResolvedBoxes;
	}

	private Set<Box> getClickableNeighbours(Box box) {
		Set<Box> clickableNeighbours = new HashSet<Box>();
		for (Box nb:box.getNearbyBoxes().values()) {
			if (!nb.hasFlag() && !nb.isClicked()) {
				clickableNeighbours.add(nb);
			}
		}
		return clickableNeighbours;
	}

	private Set<Box> getFlaggedNeighbours(Box box) {
		Set<Box> flaggedNeighbours = new HashSet<Box>();
		for (Box nb:box.getNearbyBoxes().values()) {
			if (nb.hasFlag()) {
				flaggedNeighbours.add(nb);
			}
		}
		return flaggedNeighbours;
	}
	
	private Box getRandomClickableBox() {
		Random rnd = new Random();
		int x = rnd.nextInt(board.getWidth());
		int y = rnd.nextInt(board.getHeight());
		Box box = board.getBox(x, y);
		if (box.isClicked() || box.hasFlag()) {
			box = getRandomClickableBox();
		}
		return box;
	}
	
	private void pause(int ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {}
	}
	
	@Override
	public void run() {
		solve();
	}
	
	public void solve() {
		board.solverOn();
		System.out.println("Solver started");
		
		Set<Box> unResolvedBoxes = getUnResolvedBoxes();
		/* While game has not ended */
		while (!(board.isGameOver() || board.isSuccessful())) {
			/* Randomly click a box as a start, or when stuck */
			if (unResolvedBoxes.equals(getUnResolvedBoxes())) {
				Box box = getRandomClickableBox();
				System.out.println("Solver picked a random box " + box.getPoint().toString());
				box.leftClick();
				/* Check for termination condition */
				if (board.isGameOver() || board.isSuccessful()) {
					return;
				}
			}
			
			pause(500);
			
			/* Check unresolved boxes (boxes at the edge) */
			unResolvedBoxes = getUnResolvedBoxes();
			System.out.println("Number of unresolved boxes: " + Integer.toString(unResolvedBoxes.size()));
			for (Box urb:unResolvedBoxes) {
				System.out.println("Solver evaluating box " + urb.getPoint().toString());
				/* Left click neighbours if possible */
				if (urb.getNearbyBombCount() == getFlaggedNeighbours(urb).size()) {
					for (Box clickable:getClickableNeighbours(urb)) {
						clickable.leftClick();
					}
				}
				/* Flag neighbours if possible (pigeonhole principle) */
				if ((urb.getNearbyBombCount() - getFlaggedNeighbours(urb).size()) == getClickableNeighbours(urb).size()) {
					for (Box nb:getClickableNeighbours(urb)) {
						nb.rightClick();
					}
				}
				
				pause(500);
			}
		}
	}
	
}
