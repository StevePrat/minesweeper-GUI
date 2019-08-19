package minesweeper;

import java.util.*;
import java.awt.Point;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

public class GameInterface {
	
	private Board board;
	private JFrame mainFrame;
	private JFrame newGameWindow;
	private JMenuBar menuBar;
	private JLabel statusLabel;
	private JTextField widthField;
	private JTextField heightField;
	private JTextField nField;
	private JButton startButton;
	private JButton newGameButton;
	private StartListener startListener;
	private NewGameListener newGameListener;
	private JPanel statusPanel;
	private JPanel paramsPanel;
	private JPanel startPanel;
	private JPanel boardPanel;
	private JPanel newGamePanel;
	
	public GameInterface() {
		mainFrame = new JFrame("Minesweeper");
		newGameWindow = new JFrame("New Game");
		statusPanel = new JPanel();
		paramsPanel = new JPanel();
		startPanel = new JPanel();
		boardPanel = new JPanel();
		newGamePanel = new JPanel();
		newGameWindow = new JFrame("New Game");
		statusLabel = new JLabel();
		widthField = new JTextField();
		heightField = new JTextField();
		nField = new JTextField();
		startButton = new JButton("Start Game");
		newGameButton = new JButton("Start New Game");
		startListener = new StartListener();
		newGameListener = new NewGameListener();
	}
	
	public void preStart() {
		System.out.println("Game PreStart");
		
		newGameWindow.setVisible(true);
		newGameWindow.setLayout(new BoxLayout(newGameWindow.getContentPane(), BoxLayout.PAGE_AXIS));
		
		/* Params @ New Game Window */
		paramsPanel.setLayout(new SpringLayout());
		paramsPanel.add(new JLabel("Board Width"));
		paramsPanel.add(widthField);
		paramsPanel.add(new JLabel("Board Height"));
		paramsPanel.add(heightField);
		paramsPanel.add(new JLabel("Number of Bombs"));
		paramsPanel.add(nField);
		SpringUtilities.makeCompactGrid(paramsPanel,3,2,3,3,3,3);
		newGameWindow.add(paramsPanel, BorderLayout.PAGE_START);
		
		/* Start Button @ New Game Window */
		startButton.addMouseListener(startListener);
		startPanel.add(startButton);
		newGameWindow.add(startPanel);
		
		/* Status Bar @ Main Frame */
		statusLabel.setText("Status will be shown here");
		statusPanel.add(statusLabel);
		mainFrame.add(statusPanel, BorderLayout.PAGE_START);
		
		/* Board Panel @ Main Frame */
		mainFrame.add(boardPanel);
		
		/* Start New Game @ Main Frame */
		newGameButton.addMouseListener(newGameListener);
		newGamePanel.add(newGameButton);
		mainFrame.add(newGamePanel, BorderLayout.PAGE_END);
	}
	
	public void start() {
		System.out.println("Game Started");
		mainFrame.setVisible(true);
		newGameWindow.setVisible(false);
		
		/* Initialize Board */
		int width = Integer.parseInt(widthField.getText());
		int height = Integer.parseInt(heightField.getText());
		board = new Board(width,height);
		
		/* Grid View for Board Panel */
		boardPanel.removeAll();
		boardPanel.setLayout(new GridLayout(height,width));
		
		JButton btn;
		Box box;
		for (int y=0; y<height; y++) {
			for (int x=0; x<width; x++) {
//				System.out.println("(" + Integer.toString(x) + "," + Integer.toString(y) + ")");
				btn = new JButton();
				box = new Box(board,x,y);
				box.setButton(btn);
				boardPanel.add(btn);
			}
		}
		
		/* Refresh Main Frame */
		mainFrame.revalidate();
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
		
		public void mouseEntered(MouseEvent evt) {}
		public void mouseExited(MouseEvent evt) {}
		public void mousePressed(MouseEvent evt) {}
		public void mouseReleased(MouseEvent evt) {}
	}
	
	class NewGameListener implements MouseListener {
		
		public NewGameListener() {}
		
		public void mouseClicked(MouseEvent evt) {
			if (newGameButton == evt.getSource()) {
				if (evt.getButton() == 1) {
					newGameWindow.setVisible(true);
				}
			}
		}
		
		public void mouseEntered(MouseEvent evt) {}
		public void mouseExited(MouseEvent evt) {}
		public void mousePressed(MouseEvent evt) {}
		public void mouseReleased(MouseEvent evt) {}
	}
	
}
