package minesweeper;

import java.util.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

public class GameInterface {
	
	private Board board;
	private JFrame mainFrame;
	private JFrame newGameWindow;
	private JFrame gameOverWindow;
	private JFrame gameSuccessWindow;
	private JLabel statusLabel;
	private JTextField widthField;
	private JTextField heightField;
	private JTextField nField;
	private JButton startButton;
	private JButton newGameButton;
	private GameListener gameListener;
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
		gameListener = new GameListener();
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
		startButton.addMouseListener(gameListener);
		startPanel.add(startButton);
		newGameWindow.add(startPanel);
		
		newGameWindow.setMinimumSize(new Dimension(200,150));
		
		/* Status Bar @ Main Frame */
		statusLabel.setText("Status will be shown here");
		statusPanel.add(statusLabel);
		mainFrame.add(statusPanel, BorderLayout.PAGE_START);
		
		/* Board Panel @ Main Frame */
		mainFrame.add(boardPanel);
		
		/* Start New Game @ Main Frame */
		newGameButton.addMouseListener(gameListener);
		newGamePanel.add(newGameButton);
		mainFrame.add(newGamePanel, BorderLayout.PAGE_END);
		mainFrame.setMinimumSize(new Dimension(600,400));
	}
	
	public void start() {
		System.out.println("Game Started");
		mainFrame.setEnabled(true);
		mainFrame.setVisible(true);
		newGameWindow.setVisible(false);
		
		/* Initialize Board */
		int width = Integer.parseInt(widthField.getText());
		int height = Integer.parseInt(heightField.getText());
		board = new Board(this,width,height);
		
		/* Put Bombs */
		int n = Integer.parseInt(nField.getText());
		board.putBombs(n);
		
		/* Grid View for Board Panel */
		boardPanel.removeAll();
		boardPanel.setLayout(new GridLayout(height,width));
		
		JButton btn;
		Box box;
		for (int y=0; y<height; y++) {
			for (int x=0; x<width; x++) {
//				System.out.println("(" + Integer.toString(x) + "," + Integer.toString(y) + ")");
				box = board.getBox(x,y);
				btn = box.getButton();
				boardPanel.add(btn);
			}
		}
		
		/* Refresh Main Frame */
		mainFrame.revalidate();
	}
	
	public void gameOver() {
		gameOverWindow = new JFrame("Game Over");
		gameOverWindow.setMinimumSize(new Dimension(400,300));
		gameOverWindow.setVisible(true);
		mainFrame.setEnabled(false);
		
		Container pane = gameOverWindow.getContentPane();
		pane.setLayout(new BoxLayout(pane,BoxLayout.PAGE_AXIS));
		JLabel msgLabel = new JLabel("Don't give up! Click the button to start a new game");
		pane.add(msgLabel);
		pane.add(newGameButton);
	}
	
	public void gameSuccess() {
		gameSuccessWindow = new JFrame("Congratulations!");
		gameSuccessWindow.setMinimumSize(new Dimension(400,300));
		gameSuccessWindow.setVisible(true);
		mainFrame.setEnabled(false);
		
		Container pane = gameSuccessWindow.getContentPane();
		pane.setLayout(new BoxLayout(pane,BoxLayout.PAGE_AXIS));
		JLabel msgLabel = new JLabel("You have just successfully finished the game! Click the button to start a new game");
		pane.add(msgLabel);
		pane.add(newGameButton);
	}
	
	public static void main(String[] args) {
		GameInterface game = new GameInterface();
		game.preStart();
	}
	
	class GameListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent evt) {
			if (startButton == evt.getSource()) {
				if (evt.getButton() == 1) {
					start();
				}
			}
			if (newGameButton == evt.getSource()) {
				if (evt.getButton() == 1) {
					newGameWindow.setVisible(true);
					mainFrame.setVisible(false);
					try {
						gameOverWindow.dispose();
						gameSuccessWindow.dispose();
					} catch (Exception e) {
						if (!(e instanceof java.lang.NullPointerException)) {
							e.printStackTrace();
						}
					}
				}
			}	
		}

		@Override public void mousePressed(MouseEvent e) {}
		@Override public void mouseReleased(MouseEvent e) {}
		@Override public void mouseEntered(MouseEvent e) {}
		@Override public void mouseExited(MouseEvent e) {}		
	}
	
}
