package trial1;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GUIProgram2 extends JFrame implements ActionListener {
	
	private Map<Point,JButton> buttons;
	
	public GUIProgram2(int hSize, int vSize) {
		JPanel gridPane = new JPanel();
		gridPane.setLayout(new GridLayout(hSize,vSize));
		
		buttons = new HashMap<Point,JButton>();
		JButton btn;
		for (int x=0; x<hSize; x++) {
			for (int y=0; y<vSize; y++) {
				btn = new JButton();
				btn.setText("Before");
				btn.addActionListener(this);
				buttons.put(new Point(x,y), btn);
				gridPane.add(btn);
			}
		}
		this.add(gridPane);
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		
		for (JButton btn:buttons.values()) {
			if (btn == evt.getSource()) {
				btn.setEnabled(false);
				btn.setText("After");
			}
		}
	}

	public static void main(String[] args) {
		JFrame prg = new GUIProgram2(3,3);
		prg.setVisible(true);
	}

}
