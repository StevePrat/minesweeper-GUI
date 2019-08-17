package minesweeper;

import java.util.*;
import java.awt.Point;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

public class GameInterface implements MouseListener {
	
	private Board board;
	private JFrame frame;
	private JLabel statusLabel;
	private JTextField widthField;
	private JTextField heightField;
	private JTextField nField;
	private JButton startButton;
	private Map<Point,JButton> buttons;
	
	public GameInterface() { 
		statusLabel = new JLabel();
		widthField = new JTextField();
		heightField = new JTextField();
		nField = new JTextField();
		startButton = new JButton("Start Game");
		startButton.addMouseListener(this);
	}
	
	public void preStart() {
		frame = new JFrame("Minesweeper");
		frame.setVisible(true);
		frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.PAGE_AXIS));
		
		/* status bar */
		JPanel panel1 = new JPanel();
		statusLabel.setText("Status will be displayed here");
		panel1.add(statusLabel);
		frame.add(panel1);
		
		/* parameters and start button */
		JPanel panel2 = new JPanel();
		panel2.setLayout(new BoxLayout(panel2, BoxLayout.PAGE_AXIS));
		
		JPanel panel2sub1 = new JPanel();
		panel2sub1.add(new JLabel("Board Width"));
		panel2sub1.add(widthField);
		panel2sub1.add(new JLabel("Board Height"));
		panel2sub1.add(heightField);
		panel2.add(panel2sub1);
		
		JPanel panel2sub2 = new JPanel();
		panel2sub2.add(new JLabel("Number of Bombs"));
		panel2sub2.add(nField);
		panel2.add(panel2sub2);
		
		JPanel panel2sub3 = new JPanel();
		panel2sub3.add(startButton);
		
		frame.add(panel2);
	}
	
	public void start() {
		/* Board */
		int width = Integer.parseInt(widthField.getText());
		int height = Integer.parseInt(heightField.getText());
		board = new Board(width,height);
		
		/* Grid View */
		JPanel panel3 = new JPanel();
		panel3.setLayout(new GridLayout(height,width));
		
		JButton btn;
		for (int y=0; y<height; y++) {
			for (int x=0; x<width; x++) {
				btn = new JButton();
				btn.setText(Integer.toString(x) + "," + Integer.toString(y));
				buttons.put(new Point(x,y), btn);
				panel3.add(btn);
			}
		}
	}
	
	public void mouseClicked(MouseEvent evt) {
		for (JButton btn:buttons.values()) {
			if (btn == evt.getSource()) {
				switch (evt.getButton()) {
				case 1:
					btn.setEnabled(false);
					btn.setText("Left click");
					break;
				case 2:
					btn.setEnabled(false);
					btn.setText("Middle click");
					break;
				case 3:
					btn.setEnabled(false);
					btn.setText("Right click");
					break;
				}
			}
		}
	}
	
	public void mouseEntered(MouseEvent evt) { }
	public void mouseExited(MouseEvent evt) { }
	public void mousePressed(MouseEvent evt) { }
	public void mouseReleased(MouseEvent evt) { }
	
	public static void main(String[] args) {
		GameInterface game = new GameInterface();
		game.preStart();
	}

}
