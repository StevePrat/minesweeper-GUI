package minesweeper;

import java.util.*;
import java.awt.Point;

public class MainCall {

	public static void main(String[] args) {
		Board board = new Board(10,10);
		board.putBombs(5);
		board.printBoard();
	}

}
