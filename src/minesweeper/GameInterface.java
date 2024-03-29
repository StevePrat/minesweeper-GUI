package minesweeper;

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
	private JLabel warningLabel;
	private JTextField widthField;
	private JTextField heightField;
	private JTextField nField;
	private JButton startButton;
	private JButton newGameButton;
	private JButton solverButton;
	private GameListener listener;
	private JPanel statusPanel;
	private JPanel paramsPanel;
	private JPanel startPanel;
	private JPanel boardPanel;
	private JPanel bottomPanel;
	private JPanel warningPanel;
	
	public GameInterface() {
		mainFrame = new JFrame("Minesweeper");
		newGameWindow = new JFrame("New Game");
		statusPanel = new JPanel();
		paramsPanel = new JPanel();
		startPanel = new JPanel();
		boardPanel = new JPanel();
		bottomPanel = new JPanel();
		newGameWindow = new JFrame("New Game");
		statusLabel = new JLabel();
		warningLabel = new JLabel();
		warningPanel = new JPanel();
		widthField = new JTextField();
		heightField = new JTextField();
		nField = new JTextField();
		startButton = new JButton("Start Game");
		newGameButton = new JButton("Start New Game");
		listener = new GameListener();
		solverButton = new JButton("Solver");
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
		startButton.addMouseListener(listener);
		startPanel.add(startButton);
		newGameWindow.add(startPanel);
		
		/* Warning Panel @ New Game Window */
		warningPanel.add(warningLabel);
		newGameWindow.add(warningPanel);
		
		newGameWindow.setMinimumSize(new Dimension(200,175));
		
		/* Status Bar @ Main Frame */
		statusLabel.setText("Status will be shown here");
		statusPanel.add(statusLabel);
		mainFrame.add(statusPanel, BorderLayout.PAGE_START);
		
		/* Board Panel @ Main Frame */
		mainFrame.add(boardPanel);
		
		/* Solver Panel @ Main Frame */
		solverButton.addMouseListener(listener);
		bottomPanel.add(solverButton);
		mainFrame.add(bottomPanel, BorderLayout.PAGE_END);
		
		/* Add Listeners to All Frames */
		mainFrame.addWindowListener(listener);
		newGameWindow.addWindowListener(listener);
	}
	
	public boolean isParamsValid() {
		int width;
		int height;
		int bombs;
		
		/* Input must be of the right type (integers) */
		try {
			width = Integer.parseInt(widthField.getText().strip());
			height = Integer.parseInt(heightField.getText().strip());
			bombs = Integer.parseInt(nField.getText().strip());
		} catch (NumberFormatException e) {
			showWarning("Please enter positive integers only");
			e.printStackTrace();
			return false;
		}
		
		/* Integers must be valid */
		boolean isValid = true;
		String msg = "";
		
		if (width <= 0) {
			msg += "Board width must be a positive integer<br>";
			isValid = false;
		}
		if (height <= 0) {
			msg += "Board height must be a positive integer<br>";
			isValid = false;
		}
		if (bombs <= 0) {
			msg += "Number of Bombs must be a positive integer<br>";
			isValid = false;
		}
		
		int boxes = width * height;
		if (bombs >= boxes) {
			msg += "Too many bombs";
			isValid = false;
		}
		
		/* Show warning message */
		if (!msg.isEmpty()) {
			showWarning("<html>" + msg + "</html>");
		} else {
			showWarning(null);
		}
		
		return isValid;
	}
	
	public void showWarning(String msg) {
		warningLabel.setText(msg);
		warningPanel.revalidate();
		newGameWindow.revalidate();
	}
	
	public void start() {
		System.out.println("Game Started");
		
		/* Start New Game @ Main Frame */
		newGameButton.addMouseListener(listener);
		bottomPanel.add(newGameButton);
		// mainFrame.add(bottomPanel, BorderLayout.PAGE_END);
		
		mainFrame.setMinimumSize(new Dimension(600,400));
		mainFrame.setEnabled(true);
		mainFrame.setVisible(true);
		newGameWindow.setVisible(false);
		
		/* Initialize Board */
		int width = Integer.parseInt(widthField.getText().strip());
		int height = Integer.parseInt(heightField.getText().strip());
		board = new Board(this,width,height);
		
		/* Put Bombs */
		int n = Integer.parseInt(nField.getText().strip());
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
		
		/* Refresh */
		mainFrame.revalidate();
		for (int y=0; y<height; y++) {
			for (int x=0; x<width; x++) {
				box = board.getBox(x,y);
				box.resizeBombImage();
				box.resizeFlagImage();
			}
		}
	}
	
	public void solve() {
		board.solve();
	}
	
	public void updateStatus() {
		statusLabel.setText(board.getStatusMsg());
		System.out.println("Status label updated: " + board.getStatusMsg());
		statusPanel.revalidate();
	}
	
	public void gameOver() {
		gameOverWindow = new JFrame("Game Over");
		gameOverWindow.setMinimumSize(new Dimension(400,300));
		gameOverWindow.setVisible(true);
		boardPanel.setEnabled(false);
		
		Container pane = gameOverWindow.getContentPane();
		pane.setLayout(new BoxLayout(pane,BoxLayout.PAGE_AXIS));
		JPanel msgPanel = new JPanel();
		msgPanel.setAlignmentX(JPanel.CENTER_ALIGNMENT);
		JLabel msgLabel = new JLabel("Don't give up! Try again!");
		msgLabel.setHorizontalAlignment(SwingConstants.CENTER);
		msgPanel.add(msgLabel);
		pane.add(msgPanel);
		JPanel btnPanel = new JPanel();
		btnPanel.setAlignmentX(JPanel.CENTER_ALIGNMENT);
		newGameButton.setHorizontalAlignment(SwingConstants.CENTER);
		btnPanel.add(newGameButton);
		pane.add(btnPanel);
	}
	
	public void gameSuccess() {
		gameSuccessWindow = new JFrame("Congratulations!");
		gameSuccessWindow.setMinimumSize(new Dimension(400,300));
		gameSuccessWindow.setVisible(true);
		boardPanel.setEnabled(false);
		
		Container pane = gameSuccessWindow.getContentPane();
		pane.setLayout(new BoxLayout(pane,BoxLayout.PAGE_AXIS));
		JPanel msgPanel = new JPanel();
		msgPanel.setAlignmentX(JPanel.CENTER_ALIGNMENT);
		JLabel msgLabel = new JLabel("You have just successfully finished the game!");
		msgLabel.setHorizontalAlignment(SwingConstants.CENTER);
		msgPanel.add(msgLabel);
		pane.add(msgPanel);
		JPanel btnPanel = new JPanel();
		btnPanel.setAlignmentX(JPanel.CENTER_ALIGNMENT);
		newGameButton.setHorizontalAlignment(SwingConstants.CENTER);
		btnPanel.add(newGameButton);
		pane.add(btnPanel);
	}
	
	public static void main(String[] args) {
		GameInterface game = new GameInterface();
		game.preStart();
	}
	
	class GameListener implements MouseListener, WindowListener {

		@Override
		public void mouseClicked(MouseEvent evt) {
			if (startButton == evt.getSource()) {
				if (evt.getButton() == 1) {
					if (isParamsValid()) {
						start();
					}
				}
			}
			if (newGameButton == evt.getSource()) {
				if (evt.getButton() == 1) {
					newGameWindow.setVisible(true);
					mainFrame.setVisible(false);
					try {
						board.stopTimer();
						gameOverWindow.dispose();
						gameSuccessWindow.dispose();
					} catch (Exception e) {
						if (!(e instanceof java.lang.NullPointerException)) {
							e.printStackTrace();
						}
					}
				}
			}
			if (solverButton == evt.getSource()) {
				if (evt.getButton() == 1) {
					solve();
				}
			}
		}

		@Override public void mousePressed(MouseEvent e) {}
		@Override public void mouseReleased(MouseEvent e) {}
		@Override public void mouseEntered(MouseEvent e) {}
		@Override public void mouseExited(MouseEvent e) {}

		@Override 
		public void windowClosing(WindowEvent e) {
			System.out.println("Window closing");
			newGameWindow.dispose();
			mainFrame.dispose();
			System.exit(0);
		}
		
		@Override public void windowClosed(WindowEvent e) {}
		@Override public void windowOpened(WindowEvent e) {}
		@Override public void windowIconified(WindowEvent e) {}
		@Override public void windowDeiconified(WindowEvent e) {}
		@Override public void windowActivated(WindowEvent e) {}
		@Override public void windowDeactivated(WindowEvent e) {}
		
	}
	
}
