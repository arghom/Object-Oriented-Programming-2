package funGUI;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.regex.Pattern;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import searchTree.SearchTreeMap;

public class WordCountGUI extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	JTextField fileTextField;
	JTextArea textArea;

	public WordCountGUI() {

		/* Name label and textfield */
		JLabel urlLabel = new JLabel("File: ");
		int nameFieldLength = 40;
		fileTextField = new JTextField(nameFieldLength);
		fileTextField.setText("shakespeare.txt");
		fileTextField.addActionListener(this);

		/* Adding Post button */
		JButton refreshButton = new JButton("Count!");
		refreshButton.addActionListener(this);

		/* Text Area to type info */
		textArea = new JTextArea(20, 60);
		textArea.setEditable(false);

		JScrollPane scrollPane = new JScrollPane(textArea,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		/* Adding the scrollPane to the panel */

		// Lay out the GUI.
		BorderLayout layout = new BorderLayout();

		Container contentPane = getContentPane();
		contentPane.setLayout(layout);

		JPanel top = new JPanel(new FlowLayout());
		top.add(urlLabel);
		top.add(fileTextField);
		top.add(refreshButton);
		contentPane.add(top, BorderLayout.NORTH);
		contentPane.add(scrollPane, BorderLayout.CENTER);

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
		String fileName = fileTextField.getText();
		textArea.selectAll();

		SearchTreeMap<String, Integer> tree = new SearchTreeMap<String, Integer>();
		try {
			Path path = Paths.get(fileName);
			Scanner sc = new Scanner(path);
			Pattern p = Pattern.compile("[a-zA-Z]+");
			String s;
			while (sc.hasNextLine()) {
				// find all words in line
				while ((s = sc.findInLine(p)) != null) {
					Integer i = tree.get(s);
					if (i == null)
						tree.put(s, 1);
					else
						tree.put(s, i + 1);
				}
				sc.nextLine(); // go to next line
			}
		} catch (Exception exc) {
			
		}

		String curStr = fileName + "\nWords Counted = " + tree.size()
		+ "\n<Word #>\n";
		for (String str : tree.keyList()) {
			curStr = curStr + str + " " + tree.get(str) + "\n";
		}
		textArea.setText(curStr);

	}

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new WordCountGUI();
			}
		});
	}

	/* Static block for feel and look */
	/* Comment this block out and see how the feel and look changes */
	/* You can also try this experiment in a Mac */

	static {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}