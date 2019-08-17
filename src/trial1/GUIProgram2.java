package trial1;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GUIProgram2 extends JFrame implements /*ActionListener,*/ MouseListener {
	
	private Map<Point,JButton> buttons;
	
	public GUIProgram2(int vSize, int hSize) {
		JPanel gridPane = new JPanel();
		gridPane.setLayout(new GridLayout(vSize,hSize));
		
		buttons = new HashMap<Point,JButton>();
		JButton btn;
		for (int y=0; y<vSize; y++) {
			for (int x=0; x<hSize; x++) {
				btn = new JButton();
				btn.setText(Integer.toString(x) + "," + Integer.toString(y));
				btn.addMouseListener(this);
				buttons.put(new Point(x,y), btn);
				gridPane.add(btn);
			}
		}
		this.add(gridPane);
	}
	
	@Override
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
	
	@Override public void mouseEntered(MouseEvent evt) { }
	@Override public void mouseExited(MouseEvent evt) { }
	@Override public void mousePressed(MouseEvent evt) { }
	@Override public void mouseReleased(MouseEvent evt) { }

	public static void main(String[] args) {
		JFrame prg = new GUIProgram2(5,3);
		prg.setVisible(true);
	}
}
