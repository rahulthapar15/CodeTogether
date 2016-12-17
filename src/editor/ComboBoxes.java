package editor;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ComboBoxes extends JApplet {

	//private static final long serialVersionUID = 1L;
	private String[] description = { "C", "C++", "Java",
			"Python" };

	private static JComboBox<String> c = new JComboBox<String>();

	private int count = 0;

	public static int getC () {
		return c.getSelectedIndex();
	}

	public void init() {
		for (int i = 0; i < description.length; i++)
			c.addItem(description[count++]);

		c.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent e) {
				  TextEditor.setSyntaxKit();
		      }
		});

		Container cp = getContentPane();
		cp.setLayout(new FlowLayout());
		cp.add(c);
	}

	public static void main(String[] args) {
	    run(new ComboBoxes(), 200, 125);
	  }

	public static void run(JApplet applet, int width, int height) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(applet);
		frame.setSize(width, height);
		applet.init();
		applet.start();
		frame.setVisible(true);
		TextEditor.setSyntaxKit();
	}
}
