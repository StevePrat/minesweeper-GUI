package minesweeper;

import java.util.*;
import java.awt.Point;
import javax.swing.*;

public class Board {
	
	private GameInterface gameInterface;
	private Map<Point,Box> board;
	private int hSize;
	private int vSize;
	private Set<Point> bombs;
	private Set<Point> flags;
	private boolean gameOver;
	
	public Board(GameInterface gameInterface, int hSize, int vSize) {
		this.gameInterface = gameInterface;
		this.board = new HashMap<Point,Box>();
		this.bombs = new HashSet<Point>();
		this.hSize = hSize;
		this.vSize = vSize;
		for (int x=0; x<hSize; x++) {
			for (int y=0; y<vSize; y++) {
				board.put(new Point(x,y), new Box(this,x,y));
			}
		}
		this.gameOver = false;
	}

	public Box getBox(int x, int y) {
		return board.get(new Point(x,y));
	}
	
	private void setBox(int x, int y, Box box) {
		board.put(new Point(x,y), box);
	}
	
	public int getWidth() {
		return hSize;
	}
	
	public int getHeight() {
		return vSize;
	}
	
	public void addBomb(int x, int y) {
		bombs.add(new Point(x,y));
	}
	
	public Set getBombs() {
		return bombs;
	}
	
	public void putBombs(int n) {
		/**
		 * Put n bombs randomly. Does not remove previously placed bombs. Updates the nearbyBombs on neighboring boxes
		 */
		Random rnd = new Random();
		int x;
		int y;
		Box box;
		for (int i=0; i<n; i++) {
			x = rnd.nextInt(hSize);
			y = rnd.nextInt(vSize);
			box = getBox(x,y);
			while (box.hasBomb()) {
				x = rnd.nextInt(hSize);
				y = rnd.nextInt(vSize);
				box = getBox(x,y);
			}
			box.putBomb();
		}
	}
	
	public void countNearbyBombs() {
		for (Box box:board.values()) {
			box.countNearbyBombs();
		}
	}
	
	public void addFlag(int x, int y) {
		flags.add(new Point(x,y));
	}
	
	public void removeFlag(int x, int y) {
		flags.remove(new Point(x,y));
	}
	
	public int countDeployedFlags() {
		return flags.size();
	}
	
	public int countRemainingFlags() {
		return bombs.size() - flags.size();
	}
	
	public boolean isGameOver() {
		return gameOver;
	}
	
	public void stopGame() {
		gameOver = true;
		Box box;
		for (Point p:bombs) {
			box = getBox(p.x,p.y);
			box.displayBombIcon();
		}
	}
	
	public void successful() {
		
	}
	
	public void printBoard() {
		/**
		 * For console debugging purposes
		 */
		for (int i=0; i<getHeight(); i++) {
			for (int j=0; j<getWidth(); j++) {
				Box box = getBox(i,j);
				String valStr = Integer.toString(box.displayValue());
				while (valStr.length() < 2) {
					valStr = "0" + valStr;
				}
				System.out.print(valStr + " ");
			}
			System.out.println();
		}
	}
}
