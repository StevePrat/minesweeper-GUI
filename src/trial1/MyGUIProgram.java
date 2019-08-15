package trial1;

import java.awt.*;
import java.awt.event.*;

public class MyGUIProgram extends Frame implements ActionListener, WindowListener {
	
	private Frame frame = new Frame();
	private TextArea txtIn = new TextArea();
	private TextArea txtOut = new TextArea();
	
	public MyGUIProgram() {
		frame.setName("Test Frame");
		frame.setVisible(true);
		
		Panel[] panels = new Panel[3];
		for (int i=0; i<panels.length; i++) {
			panels[i] = new Panel();
		}
		
		Label lblIn = new Label("Input");
		txtIn.setText("Type your input here");
		panels[0].add(lblIn);
		panels[0].add(txtIn);
		
		Label lblOut = new Label("Output");
		txtOut.setEditable(false);
		
		panels[1].add(lblOut);
		panels[1].add(txtOut);
		
		Button btn = new Button("Generate Output");
		panels[2].add(btn);
		
		frame.setLayout(new GridLayout(panels.length,1));
		
		for (Panel pnl:panels) {
			frame.add(pnl);
		}
		
		btn.addActionListener(this);
		frame.addWindowListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent evt) {
		txtOut.setText(txtIn.getText());
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
		new MyGUIProgram();
	}
}
