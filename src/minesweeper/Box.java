package minesweeper;

import java.util.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class Box {
	
	private static ImageIcon masterBombIcon = new ImageIcon("icons/Bomb.png","Bomb icon");;
	private static ImageIcon masterFlagIcon = new ImageIcon("icons/Flag.png","Flag icon");
	
	private Board board;
	private int x;
	private int y;
	private boolean hasBomb;
	private int nearbyBombs;
	private boolean hasFlag;
	private boolean isClicked;
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
		isClicked = false;
		button = new JButton();
		bombIcon = masterBombIcon;
		flagIcon = masterFlagIcon;
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
		return isClicked;
	}
	
	public void leftClick() {
		if (!hasFlag()) {
			isClicked = true;
			button.setEnabled(false);
			System.out.println("Button left clicked: " + Integer.toString(x) + "," + Integer.toString(y));
			if (hasBomb()) {
				board.gameOver();
			} else {
				board.check();
				if (nearbyBombs > 0) {
					button.setText(Integer.toString(nearbyBombs));
					System.out.println("Nearby bombs: " + Integer.toString(nearbyBombs));
				} else {
					 clickNearby();
				}
			}
		}
	}
	
	public void rightClick() {
		System.out.println("Button right clicked: " + Integer.toString(x) + "," + Integer.toString(y));
		if (hasFlag()) {
			removeFlag();
		} else {
			putFlag();
		}
		board.check();
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
	
	public void displayFlagIcon() {
		button.setIcon(flagIcon);
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
		if (board.countRemainingFlags() > 0) {
			hasFlag = true;
			board.addFlag(x,y);
			button.setIcon(flagIcon);
		}
	}
	
	public void removeFlag() {
		hasFlag = false;
		board.removeFlag(x,y);
		button.setIcon(null);
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
	
	public void resizeBombImage() {
		Image masterBombImg = masterBombIcon.getImage();
        Image scaledBombImg = masterBombImg.getScaledInstance(button.getWidth(), button.getHeight(), java.awt.Image.SCALE_SMOOTH);
        bombIcon = new ImageIcon(scaledBombImg);
	}
	
	public void resizeFlagImage() {
		Image masterFlagImg = masterFlagIcon.getImage();
		Image scaledFlagImg = masterFlagImg.getScaledInstance(button.getWidth(), button.getHeight(), java.awt.Image.SCALE_SMOOTH);
		flagIcon = new ImageIcon(scaledFlagImg);
	}
	
	class BoxListener implements MouseListener, ComponentListener {
		
		public BoxListener() {}
		
		@Override
		public void mouseClicked(MouseEvent evt) {
			if (!board.isGameOver()) {
				if (button == evt.getSource()) {
					switch (evt.getButton()) {
					case 1:
						leftClick();
						break;
					case 3:
						rightClick();
					}
				}	
			}
		}
		
		@Override public void mouseEntered(MouseEvent evt) {}
		@Override public void mouseExited(MouseEvent evt) {}
		@Override public void mousePressed(MouseEvent evt) {}
		@Override public void mouseReleased(MouseEvent evt) {}
		
		@Override
		public void componentResized(ComponentEvent e) {
			resizeBombImage();
			resizeFlagImage();
//			Dimension size = button.getSize();
//			Insets insets = button.getInsets();
//			size.width -= insets.left + insets.right;
//            size.height -= insets.top + insets.bottom;
//            if (size.width > size.height) {
//                size.width = -1;
//            } else {
//                size.height = -1;
//            }
//            
//            Image masterBombImg = masterBombIcon.getImage();
//            Image scaledBombImg = masterBombImg.getScaledInstance(size.width, size.height, java.awt.Image.SCALE_SMOOTH);
//            bombIcon = new ImageIcon(scaledBombImg);
//            
//			Image masterFlagImg = masterFlagIcon.getImage();
//			Image scaledFlagImg = masterFlagImg.getScaledInstance(size.width, size.height, java.awt.Image.SCALE_SMOOTH);
//			flagIcon = new ImageIcon(scaledFlagImg);
		}

		@Override public void componentMoved(ComponentEvent e) {}
		@Override public void componentShown(ComponentEvent e) {}
		@Override public void componentHidden(ComponentEvent e) {}

	}
	
}

