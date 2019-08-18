package minesweeper;

import java.util.*;
import java.awt.Point;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

public class GameInterface {
	
	private Board board;
	private JFrame frame;
	private JLabel statusLabel;
	private JTextField widthField;
	private JTextField heightField;
	private JTextField nField;
	private JButton startButton;
	private StartListener startListener;
	private JPanel statusPanel;
	private JPanel paramsPanel;
	private JPanel startPanel;
	private JPanel boardPanel;
	
	public GameInterface() {
		frame = new JFrame("Minesweeper");
		statusPanel = new JPanel();
		paramsPanel = new JPanel();
		startPanel = new JPanel();
		boardPanel = new JPanel();
		frame.add(statusPanel);
		frame.add(paramsPanel);
		frame.add(startPanel);
		frame.add(boardPanel);		
		statusLabel = new JLabel();
		widthField = new JTextField();
		heightField = new JTextField();
		nField = new JTextField();
		startButton = new JButton("Start Game");
		startListener = new StartListener();
		startButton.addMouseListener(startListener);
		startPanel.add(startButton);
	}
	
	public void preStart() {
		System.out.println("Game PreStart");
		
		frame.setVisible(true);
		frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.PAGE_AXIS));
		
		/* status bar */
		
		statusLabel.setText("Status will be displayed here");
		statusPanel.add(statusLabel);
		
		/* parameters */
		paramsPanel.setLayout(new SpringLayout());
		paramsPanel.add(new JLabel("Board Width"));
		paramsPanel.add(widthField);
		paramsPanel.add(new JLabel("Board Height"));
		paramsPanel.add(heightField);
		paramsPanel.add(new JLabel("Number of Bombs"));
		paramsPanel.add(nField);
		SpringUtilities.makeCompactGrid(paramsPanel,3,2,3,3,3,3);
	}
	
	public void start() {
		System.out.println("Game Started");
		
		/* Board */
		int width = Integer.parseInt(widthField.getText());
		int height = Integer.parseInt(heightField.getText());
		board = new Board(width,height);
		
		/* Grid View */
		boardPanel.setLayout(new GridLayout(height,width));
		
		JButton btn;
		Box box;
		for (int y=0; y<height; y++) {
			for (int x=0; x<width; x++) {
				System.out.println("(" + Integer.toString(x) + "," + Integer.toString(y) + ")");
				btn = new JButton();
				box = new Box(board,x,y);
				box.setButton(btn);
				boardPanel.add(btn);
			}
		}
		boardPanel.revalidate();
	}
	
	public static void main(String[] args) {
		GameInterface game = new GameInterface();
		game.preStart();
	}
	
	class StartListener implements MouseListener {
		
		public StartListener() {}
		
		public void mouseClicked(MouseEvent evt) {
			if (startButton == evt.getSource()) {
				if (evt.getButton() == 1) {
					start();
				}
			}
			
		}
		
		public void mouseEntered(MouseEvent evt) { }
		public void mouseExited(MouseEvent evt) { }
		public void mousePressed(MouseEvent evt) { }
		public void mouseReleased(MouseEvent evt) { }
	}
	
}
