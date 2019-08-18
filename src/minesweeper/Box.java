package minesweeper;

import java.util.*;
import java.awt.Point;
import javax.swing.*;

public class Box {
	
	private Board board;
	private int x;
	private int y;
	private boolean hasBomb;
	private boolean hasDetonated;
	private int nearbyBombs;
	private boolean hasFlag;
	private JButton button;
	
	public Box(Board board, int x, int y) {
		this.board = board;
		this.x = x;
		this.y = y;
		this.hasBomb = false;
		this.nearbyBombs = 0;
		this.hasFlag = false;
	}
	
	public void setButton(JButton button) {
		this.button = button;
	}
	
	public JButton getButton() {
		return button;
	}
	
	public void leftClick() {
		if (hasFlag()) {
			// TODO cannot click
		} else {
			if (hasBomb()) {
				// TODO detonate bomb
			} else {
				// TODO display nearby bombs count
			}
		}
	}
	
	public void rightClick() {
		if (!hasFlag()) {
			// TODO flag the box 
		}
	}
	
	public void putBomb() {
		hasBomb = true;
		board.addBomb(x,y);
		for (Box nearBox:getNearbyBoxes().values()) {
			nearBox.addNearbyBombCount();
		}
	}
	
	public boolean hasBomb() {
		return hasBomb;
	}
	
	public void addNearbyBombCount() {
		nearbyBombs++;
	}
	
	public int countNearbyBombs() {
		int nearbyBombs = 0;
		Map<Point,Box> nearbyBoxes = getNearbyBoxes();
		for (Box nb:nearbyBoxes.values()) {
			if (nb.hasBomb()) {
				nearbyBombs++;
			}
		}
		this.nearbyBombs = nearbyBombs;
		return nearbyBombs;
	}
	
	public Map<Point,Box> getNearbyBoxes() {
		Map<Point,Box> nearbyBoxes = new HashMap<Point,Box>();
		int leftLimit = Integer.max(0,x-1);
		int rightLimit = Integer.min(board.getWidth()-1,x+1);
		int bottomLimit = Integer.max(0,y-1);
		int topLimit = Integer.min(board.getHeight()-1,y+1);
		for (int xn=leftLimit; xn<=rightLimit; xn++) {
			for (int yn=bottomLimit; yn<=topLimit; yn++) {
				nearbyBoxes.put(new Point(xn,yn), board.getBox(xn,yn));
			}
		}
		return nearbyBoxes;
	}
	
	public boolean hasFlag() {
		return hasFlag;
	}
	
	public void putFlag() {
		hasFlag = true;
		board.addFlag(x,y);
	}
	
	public void removeFlag() {
		hasFlag = false;
		board.removeFlag(x,y);
	}
	
	public int displayValue() {
		/**
		 *  For console debugging purposes
		 */
		if (hasFlag()) {
			return -2;
		} else {
			if (hasBomb()) {
				return -1;
			} else {
				return nearbyBombs;
			}
		}
	}
	
	
}

