package trial1;

import java.awt.*;
import java.awt.event.*;

public class AnotherGUIProgram extends Frame implements ActionListener, WindowListener {
	
	private Frame frame = new Frame();
	private Panel[][] board;
	
	public AnotherGUIProgram(int hSize, int vSize) {
		frame.setName("Adjacent Buttons");
		frame.setLayout(new GridLayout(vSize,hSize));
		frame.setVisible(true);
		
		board = new Panel[hSize][vSize];
		
	}
	
	@Override
	public void actionPerformed(ActionEvent evt) {
		// TODO
	}
	
	@Override
	public void windowClosing(WindowEvent evt) {
		System.out.println("Window Closed");
		System.exit(0);
	}
	
	// Not Used, BUT need to provide an empty body to compile.
	@Override public void windowOpened(WindowEvent evt) { }
	@Override public void windowClosed(WindowEvent evt) { }
	@Override public void windowIconified(WindowEvent evt) { System.out.println("Window Iconified"); }
	@Override public void windowDeiconified(WindowEvent evt) { System.out.println("Window Deiconified"); }
	@Override public void windowActivated(WindowEvent evt) { System.out.println("Window Activated"); }
	@Override public void windowDeactivated(WindowEvent evt) { System.out.println("Window Deactivated"); }
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
