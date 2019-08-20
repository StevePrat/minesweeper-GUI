package minesweeper;

import java.util.*;
import java.awt.Point;
import java.awt.event.*;

import javax.swing.*;

public class Box {
	
	private Board board;
	private int x;
	private int y;
	private boolean hasBomb;
	private int nearbyBombs;
	private boolean hasFlag;
	private JButton button;
	private ImageIcon bombIcon;
	private ImageIcon flagIcon;
	private BoxListener listener;
	
	public Box(Board board, int x, int y) {
		this.board = board;
		this.x = x;
		this.y = y;
		hasBomb = false;
		nearbyBombs = 0;
		hasFlag = false;
		button = new JButton();
		bombIcon = new ImageIcon("icons/Bomb.png","Bomb icon");
		flagIcon = new ImageIcon("icons/Flag.png","Flag icon");
		listener = new BoxListener();
		button.addMouseListener(listener);
	}
	
	public Point getPoint() {
		return new Point(x,y);
	}
	
	public void setButton(JButton button) {
		button.addMouseListener(listener);
		this.button = button;
	}
	
	public JButton getButton() {
		return button;
	}
	
	public boolean isClicked() {
		return button.isEnabled();
	}
	
	public void leftClick() {
		if (!hasFlag()) {
			button.setEnabled(false);
			if (hasBomb()) {
				board.gameOver();
			} else {
				board.check();
				if (nearbyBombs > 0) {
					button.setText(Integer.toString(nearbyBombs));
				} else {
					clickNearby();
				}
			}
		}
	}
	
	public void rightClick() {
		if (hasFlag()) {
			hasFlag = false;
			button.setIcon(null);
		} else {
			hasFlag = true;
			button.setIcon(flagIcon);
		}
	}
	
	public void clickNearby() {
		for (Box nb:getNearbyBoxes().values()) {
			if (!nb.isClicked()) {
				nb.leftClick();
			}
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
	
	public void displayBombIcon() {
		button.setIcon(bombIcon);
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
	
	class BoxListener implements MouseListener {
		
		public BoxListener() {}
		
		public void mouseClicked(MouseEvent evt) {
			if (!board.isGameOver()) {
				if (button == evt.getSource()) {
					switch (evt.getButton()) {
					case 1:
						leftClick();
					case 3:
						rightClick();
					}
				}	
			}
		}
		
		public void mouseEntered(MouseEvent evt) {}
		public void mouseExited(MouseEvent evt) {}
		public void mousePressed(MouseEvent evt) {}
		public void mouseReleased(MouseEvent evt) {}
		
	}
	
}

