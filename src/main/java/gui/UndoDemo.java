import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.undo.*;
import javax.swing.text.*;
import javax.swing.text.DefaultEditorKit.CopyAction;

/** Simple GUI demo of UndoManager and friends.
 * @author Ian Darwin http://www.darwinsys.com/
 */
public class UndoDemo extends JFrame {

	JTextArea ta;
	UndoManager um;

	/** Simple main program that just constructs and shows the GUI */
	public static void main(String[] files) {
		new UndoDemo().setVisible(true);
	}

	/** Construct a GUI that demonstrates use of UndoManager */
	public UndoDemo() {

		Container cp = getContentPane();
		cp.add(ta = new JTextArea(20, 60), BorderLayout.CENTER);
		JPanel bp;
		cp.add(bp = new JPanel(), BorderLayout.SOUTH);

		// Create a javax.swing.undo.UndoManager; this is an amazing class that
		// keeps a Stack of UndoableEdits and lets you invoke them;
		// by registering it as a Listener on the TextComponent.Document,
		// the Document will create the UndoableEdit objects and send them
		// to the UndoManager. Between them they do ALL the work!
		um = new UndoManager();
		ta.getDocument().addUndoableEditListener(um);

		// Create the buttons
		JButton cutButton, copyButton, pasteButton, undoButton, redoButton;
		bp.add(cutButton = new JButton("Cut"));
		cutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ta.cut();
			}
		});

		bp.add(copyButton = new JButton("Copy"));
		copyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ta.copy();
			}
		});

		bp.add(pasteButton = new JButton("Paste"));
		pasteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ta.paste();
			}
		});
		bp.add(undoButton = new JButton("UnDo"));
		undoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (um.canUndo()) {
					um.undo();
				} else {
					warn("Can't undo");
				}
			}
		});
		bp.add(redoButton = new JButton("ReDo"));
		redoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (um.canRedo()) {
					um.redo();
				} else {
					warn("Can't redo");
				}
			}
		});
		pack();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	void warn(String msg) {
		JOptionPane.showMessageDialog(this,
			"Warning: " + msg, "Warning",
			JOptionPane.WARNING_MESSAGE);
	}
}