package editor;
import jsyntaxpane.syntaxkits.CSyntaxKit;
import jsyntaxpane.syntaxkits.CppSyntaxKit;
import jsyntaxpane.syntaxkits.JavaSyntaxKit;
import jsyntaxpane.syntaxkits.PythonSyntaxKit;

import javax.swing.*;
import javax.swing.text.DefaultEditorKit;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import static java.awt.Color.WHITE;

class TextEditor extends JFrame {

	private static final long serialVersionUID = 1L;
	private static final Action New = null;
	private final static JEditorPane area = new JEditorPane();
	private JFileChooser dialog = new JFileChooser(System.getProperty("user.dir"));
	private String currentFile = "Untitled";
	private boolean changed = false;

	public TextEditor() {
		setLocation(20, 120);
		setFont(new Font("Monospaced", Font.PLAIN, 12));
		JScrollPane scroll = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		add(scroll,BorderLayout.CENTER);

		ComboBoxes.run(new ComboBoxes(), 200, 125);

		JMenuBar JMB = new JMenuBar();
		setJMenuBar(JMB);
		JMenu file = new JMenu("File");
		JMenu edit = new JMenu("Edit");
		JMB.add(file); JMB.add(edit);

		file.add(New);file.add(Open);file.add(Save);
		file.add(Quit);file.add(SaveAs);
		file.addSeparator();

		for(int i=0; i<4; i++)
			file.getItem(i).setIcon(null);

		edit.add(Cut);edit.add(Copy);edit.add(Paste);

		edit.getItem(0).setText("Cut out");
		edit.getItem(1).setText("Copy");
		edit.getItem(2).setText("Paste");

		JToolBar tool = new JToolBar();
		add(tool,BorderLayout.NORTH);
		tool.add(New);tool.add(Open);tool.add(Save);
		tool.addSeparator();

		JButton cut = tool.add(Cut), cop = tool.add(Copy),pas = tool.add(Paste);

		cut.setText(null);
		cop.setText(null);
		pas.setText(null);

		Save.setEnabled(false);
		SaveAs.setEnabled(false);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		addKeyListener(k1);
		setTitle(currentFile);

		CSyntaxKit.initKit();
		getArea().setContentType("text/c");

		setVisible(true);

	}

	public static void setSyntaxKit() {

		switch (ComboBoxes.getC()) {

		case 0: CSyntaxKit.initKit(); getArea().setContentType("text/c"); break;

		case 1: CppSyntaxKit.initKit(); getArea().setContentType("text/cpp"); break;

		case 2: JavaSyntaxKit.initKit(); getArea().setContentType("text/java"); break;

		case 3: PythonSyntaxKit.initKit(); getArea().setContentType("text/python"); break;

		default: break;

		}

	}


	private KeyListener k1 = new KeyAdapter() {
		public void keyPressed(KeyEvent e) {
			changed = true;
			Save.setEnabled(true);
			SaveAs.setEnabled(true);
		}
	};

	Action Open = new AbstractAction("Open") {
		/**
		 *
		 */
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
			saveOld();
			if(dialog.showOpenDialog(null)==JFileChooser.APPROVE_OPTION) {
				readInFile(dialog.getSelectedFile().getAbsolutePath());
			}
			SaveAs.setEnabled(true);
		}
	};

	Action Save = new AbstractAction("Save") {
		/**
		 *
		 */
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
			if(!currentFile.equals("Untitled"))
				saveFile(currentFile);
			else
				saveFileAs();
		}
	};

	Action SaveAs = new AbstractAction("Save as...") {
		/**
		 *
		 */
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
			saveFileAs();
		}
	};

	Action Quit = new AbstractAction("Quit") {
		/**
		 *
		 */
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
			saveOld();
			System.exit(0);
		}
	};

	ActionMap m = getArea().getActionMap();
	Action Cut = m.get(DefaultEditorKit.cutAction);
	Action Copy = m.get(DefaultEditorKit.copyAction);
	Action Paste = m.get(DefaultEditorKit.pasteAction);

	private void saveFileAs() {
		if(dialog.showSaveDialog(null)==JFileChooser.APPROVE_OPTION)
			saveFile(dialog.getSelectedFile().getAbsolutePath());
	}

	private void saveOld() {
		if(changed) {
			if(JOptionPane.showConfirmDialog(this, "Would you like to save "+ currentFile +" ?","Save",JOptionPane.YES_NO_OPTION)== JOptionPane.YES_OPTION)
				saveFile(currentFile);
		}
	}

	private void readInFile(String fileName) {
		try {
			FileReader r = new FileReader(fileName);
			getArea().read(r,null);
			r.close();
			currentFile = fileName;
			setTitle(currentFile);
			changed = false;
		}
		catch(IOException e) {
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(this,"Editor can't find the file called "+fileName);
		}

	}

	private void saveFile(String fileName) {
		try {
			FileWriter w = new FileWriter(fileName);
			getArea().write(w);
			w.close();
			currentFile = fileName;
			setTitle(currentFile);
			changed = false;
			Save.setEnabled(false);
		}
		catch(IOException e) {
		}
	}

	public static JEditorPane getArea() {
		return area;
	}

}

public class editor {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		new TextEditor();

	}

}
