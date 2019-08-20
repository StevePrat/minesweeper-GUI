package minesweeper;

import java.io.File;
import java.util.*;
import javax.swing.*;
import java.awt.Point;

public class ForDebug {

	public static void main(String[] args) {
		Board board = new Board(new GameInterface(),10,10);
		Box box = board.getBox(0,0);
		Map<Point,Box> nbMap = box.getNearbyBoxes();
		for (Box nb:nbMap.values()) {
			System.out.println(nb.getPoint());
		}
	}
	
}
