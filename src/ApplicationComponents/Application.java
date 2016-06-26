package ApplicationComponents;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import Commands.InvalidArgumentNumberException;
import Commands.InvalidCommandArgumentException;
import Commands.InvalidCommandException;
import Commands.InvalidCommandNameException;

public class Application {
	private JFrame window = new JFrame("Homemade Assembler");
	private JTextArea assCode = new JTextArea(30, 30);
	private JTextArea mem = new JTextArea(30, 54);
	private ArrayList<JTextField> regTextFields;
	private JScrollPane memScroll;
	private Processor p;
	private JCheckBoxMenuItem cb;
	private JPanel rightPane;
	private TreeMap<String, Register> regs;

	public static final int SAVING_RUNNING = 1;
	public static final int SAVING_ENDED = 2;
	public static final int LOADING_RUNNING = 3;
	public static final int LOADING_ENDED = 4;

	public class CodeLoadButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			JFileChooser chooser = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Assembly sourcecode(*.asc)", "asc");
			chooser.setFileFilter(filter);
			File dirF = new File(".");
			chooser.setCurrentDirectory(dirF);
			int loadingState = LOADING_RUNNING;
			for (; loadingState != LOADING_ENDED;) {
				int returnVal = chooser.showOpenDialog(window);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File f = chooser.getSelectedFile();
					if (!(f.exists())) {
						JOptionPane.showMessageDialog(window, "\"" + f.getName() + "\" doesn't exists.", "Missing file",
								JOptionPane.ERROR_MESSAGE);
						loadingState = LOADING_RUNNING;
					} else {
						try {
							ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
							String loadedText = (String) ois.readObject();
							assCode.setText(loadedText);
							loadingState = LOADING_ENDED;
							ois.close();
						} catch (FileNotFoundException e) {
							JOptionPane.showMessageDialog(window,
									"Error accessing file! File  \"" + f.getName() + "\".", "can't be opened!",
									JOptionPane.ERROR_MESSAGE);
							loadingState = LOADING_RUNNING;
						} catch (IOException e) {
							JOptionPane.showMessageDialog(window, "Error accessing file! File \"" + f.getName() + "\".",
									"can't be written!", JOptionPane.ERROR_MESSAGE);
							loadingState = LOADING_RUNNING;
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
						}
					}
				} else if ((returnVal == JFileChooser.CANCEL_OPTION) || returnVal == JFileChooser.ERROR_OPTION) {
					loadingState = LOADING_ENDED;
				}
			}
		}
	}

	public class StateLoadButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			JFileChooser chooser = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Processor statefile (*.prc)", "prc");
			chooser.setFileFilter(filter);
			File dirF = new File(".");
			chooser.setCurrentDirectory(dirF);
			int loadingState = LOADING_RUNNING;
			for (; loadingState != LOADING_ENDED;) {
				int returnVal = chooser.showOpenDialog(window);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File f = chooser.getSelectedFile();
					if (!(f.exists())) {
						JOptionPane.showMessageDialog(window, "\"" + f.getName() + "\" doesn't exists.", "Missing file",
								JOptionPane.ERROR_MESSAGE);
						loadingState = LOADING_RUNNING;
					} else {
						try {
							ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
							Processor loadedProcessor = (Processor) ois.readObject();
							String loadedText = (String) ois.readObject();
							p = loadedProcessor;
							mem.setText(p.getMemory().toString(16, 3));
							assCode.setText(loadedText);
							ois.close();
							loadingState = LOADING_ENDED;
						} catch (FileNotFoundException e) {
							JOptionPane.showMessageDialog(window,
									"Error accessing file! File  \"" + f.getName() + "\".", "can't be opened!",
									JOptionPane.ERROR_MESSAGE);
							loadingState = LOADING_RUNNING;
						} catch (IOException e) {
							JOptionPane.showMessageDialog(window, "Error accessing file! File \"" + f.getName() + "\".",
									"can't be written!", JOptionPane.ERROR_MESSAGE);
							loadingState = LOADING_RUNNING;
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
						}
					}
				} else if ((returnVal == JFileChooser.CANCEL_OPTION) || returnVal == JFileChooser.ERROR_OPTION) {
					loadingState = LOADING_ENDED;
				}
			}
		}
	}

	public class RunButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (cb.getState())
				(new CompileButtonActionListener()).actionPerformed(arg0);
			p.executeAll();
			mem.setText(p.getMemory().toString(6, 3));
			int i = 0;
			for (Iterator<Map.Entry<String, Register>> it = regs.entrySet().iterator(); it.hasNext(); i++) {
				Map.Entry<String, Register> pair = it.next();
				regTextFields.get(i).setText(String.format("0x%08X", pair.getValue().getValue()));
			}
		}
	}

	public class StateSaveButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			JFileChooser chooser = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Processor statefile (*.prc)", "prc");
			chooser.setFileFilter(filter);
			File dirF = new File(".");
			chooser.setCurrentDirectory(dirF);
			int savingState = SAVING_RUNNING;
			int questionAnswer = 0;
			for (; savingState != SAVING_ENDED;) {
				int returnVal = chooser.showSaveDialog(window);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File f = chooser.getSelectedFile();
					if (f.exists())
						questionAnswer = JOptionPane.showConfirmDialog(window,
								"\"" + f.getName() + "\" is already exists. Would you overwrite it?",
								"File already exists", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if (questionAnswer != JOptionPane.NO_OPTION)
						try {
							ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
							oos.writeObject(p);
							oos.writeObject(assCode.getText());
							oos.close();
							savingState = SAVING_ENDED;
						} catch (FileNotFoundException e) {
							JOptionPane.showMessageDialog(window,
									"Error accessing file! File  \"" + f.getName() + "\".", "can't be opened!",
									JOptionPane.ERROR_MESSAGE);
							savingState = LOADING_RUNNING;
						} catch (IOException e) {
							JOptionPane.showMessageDialog(window, "Error accessing file! File \"" + f.getName() + "\".",
									"can't be written!", JOptionPane.ERROR_MESSAGE);
							savingState = LOADING_RUNNING;
						}
				} else if ((returnVal == JFileChooser.CANCEL_OPTION) || returnVal == JFileChooser.ERROR_OPTION) {
					savingState = SAVING_ENDED;
				}
			}
		}
	}

	public class CodeSaveButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			JFileChooser chooser = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Assembly sourcecode(*.asc)", "asc");
			chooser.setFileFilter(filter);
			File dirF = new File(".");
			chooser.setCurrentDirectory(dirF);
			int savingState = SAVING_RUNNING;
			int questionAnswer = 0;
			for (; savingState != SAVING_ENDED;) {
				int returnVal = chooser.showSaveDialog(window);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File f = chooser.getSelectedFile();
					if (f.exists())
						questionAnswer = JOptionPane.showConfirmDialog(window,
								"\"" + f.getName() + "\" is already exists. Would you overwrite it?",
								"File already exists", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
					if (questionAnswer != JOptionPane.NO_OPTION)
						try {
							ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
							oos.writeObject(assCode.getText());
							oos.close();
							savingState = SAVING_ENDED;
						} catch (FileNotFoundException e) {
							JOptionPane.showMessageDialog(window,
									"Error accessing file! File  \"" + f.getName() + "\".", "can't be opened!",
									JOptionPane.ERROR_MESSAGE);
							savingState = LOADING_RUNNING;
						} catch (IOException e) {
							JOptionPane.showMessageDialog(window, "Error accessing file! File \"" + f.getName() + "\".",
									"can't be written!", JOptionPane.ERROR_MESSAGE);
							savingState = LOADING_RUNNING;
						}
				} else if ((returnVal == JFileChooser.CANCEL_OPTION) || returnVal == JFileChooser.ERROR_OPTION) {
					savingState = SAVING_ENDED;
				}
			}
		}
	}

	public class CompileButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			p.commandsClear();
			p.constansClear();
			String cmdStrOriginal = "";
			String cmdStr = "";
			String[] commands = (assCode.getText().toUpperCase()).split("\n");
			for (int i = 0; i < commands.length; i++)
				try {
					cmdStrOriginal = commands[i].split(";")[0];
					cmdStr = cmdStrOriginal.replaceAll("( )+", " ");
					cmdStr = cmdStr.replaceAll("^ +", "");
					cmdStr = cmdStr.replaceAll(" +$", "");
					if (cmdStr.length() > 0) {
						p.addCommand(cmdStr);
					}
				} catch (InvalidCommandNameException icne) {
					JOptionPane.showMessageDialog(window, "Unknown command: \"" + icne.getMessage() + "\"",
							"Syntax error", JOptionPane.ERROR_MESSAGE);
					p.commandsClear();
				} catch (InvalidCommandException ice) {
					JOptionPane.showMessageDialog(window, "Error in line \"" + cmdStrOriginal + "\"!", "Bad command!",
							JOptionPane.ERROR_MESSAGE);
					p.commandsClear();
				} catch (InvalidCommandArgumentException icae) {
					JOptionPane
							.showMessageDialog(window,
									"Invalid operand in line \"" + cmdStrOriginal + "\"! The invalid operand is \""
											+ icae.getMessage() + "\"",
									"Invalid opearand", JOptionPane.ERROR_MESSAGE);
					p.commandsClear();
				} catch (InvalidArgumentNumberException iane) {
					JOptionPane.showMessageDialog(window,
							"Invalid operand number in line \"" + cmdStrOriginal + "\"",
							"Invalid operand number", JOptionPane.ERROR_MESSAGE);
					p.commandsClear();
				}
		}
	}

	public Application() {

		p = new Processor();

		window.setResizable(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setLayout(new BorderLayout());

		JScrollPane codeScroll = new JScrollPane(assCode);
		codeScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		codeScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		assCode.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
		window.add(codeScroll, BorderLayout.LINE_START);

		List<JButton> button_array = new ArrayList<JButton>();
		button_array.add(new JButton("Compile"));
		button_array.add(new JButton("Run"));
		button_array.add(new JButton("Save code"));
		button_array.add(new JButton("Load code"));
		button_array.add(new JButton("Save state"));
		button_array.add(new JButton("Load state"));

		button_array.get(0).addActionListener(new CompileButtonActionListener());
		button_array.get(1).addActionListener(new RunButtonActionListener());
		button_array.get(2).addActionListener(new CodeSaveButtonActionListener());
		button_array.get(3).addActionListener(new CodeLoadButtonActionListener());
		button_array.get(4).addActionListener(new StateSaveButtonActionListener());
		button_array.get(5).addActionListener(new StateLoadButtonActionListener());

		rightPane = new JPanel(new GridBagLayout());
		rightPane.setSize(new Dimension(700,600));
		GridBagConstraints gbc = new GridBagConstraints();
		for (int i = 0; i < button_array.size(); i++) {
			button_array.get(i).setMargin(new Insets(2, 5, 2, 5));
			gbc.gridx = (i % 2);
			gbc.gridy = i / 2;
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.insets = new Insets(3, 3, 3, 3);
			gbc.anchor = GridBagConstraints.PAGE_START;
			rightPane.add(button_array.get(i), gbc);
		}
		window.add(rightPane, BorderLayout.LINE_END);
		JLabel memLabel = new JLabel("Memory:");
		gbc.gridx = 0;
		gbc.gridy = 3;
		rightPane.add(memLabel, gbc);

		regs = new TreeMap<String, Register>(p.getRegisters());
		regTextFields = new ArrayList<JTextField>();
		int i = 0;
		for (Iterator<Map.Entry<String, Register>> it = regs.entrySet().iterator(); it.hasNext(); i++) {
			Map.Entry<String, Register> pair = it.next();
			gbc.gridx = (i % 2) * 2 + 2;
			gbc.gridy = (i / 2);
			rightPane.add(new JLabel(pair.getKey() + ":"), gbc);
			gbc.gridx++;
			JTextField rf = new JTextField(String.format("0x%08X", pair.getValue().getValue()), 10);
			rf.setMargin(new Insets(1, 1, 1, 1));
			rf.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
			regTextFields.add(rf);
			rightPane.add(rf, gbc);
		}

		memScroll = new JScrollPane(mem);
		memScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		memScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		mem.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 11));
		mem.setMargin(new Insets(1, 5, 1, 5));
		gbc.gridy = 4;
		gbc.gridx = 0;
		gbc.gridwidth = 8;
		mem.setEditable(false);
		rightPane.add(memScroll, gbc);
		mem.setText(p.getMemory().toString(6, 3));

		JMenuBar mb = new JMenuBar();
		JMenuItem codeSave = new JMenuItem("Save code");
		codeSave.addActionListener(new CodeSaveButtonActionListener());
		JMenuItem codeLoad = new JMenuItem("Load code");
		codeLoad.addActionListener(new CodeLoadButtonActionListener());
		JMenuItem stateSave = new JMenuItem("Save state");
		stateSave.addActionListener(new StateSaveButtonActionListener());
		JMenuItem stateLoad = new JMenuItem("Load state");
		stateLoad.addActionListener(new StateLoadButtonActionListener());
		JMenu m = new JMenu("File");
		m.add(codeSave);
		m.add(codeLoad);
		m.addSeparator();
		m.add(stateSave);
		m.add(stateLoad);
		mb.add(m);

		JMenu compileAndRun = new JMenu("Compile and run");
		mb.add(compileAndRun);
		JMenuItem compile = new JMenuItem("Compile");
		stateSave.addActionListener(new CompileButtonActionListener());
		JMenuItem run = new JMenuItem("Run");
		stateLoad.addActionListener(new StateLoadButtonActionListener());
		cb = new JCheckBoxMenuItem("Compile before run");
		compileAndRun.add(compile);
		compileAndRun.add(run);
		compileAndRun.add(cb);

		window.setJMenuBar(mb);

		window.pack();
	}

	public void start() {
		window.setVisible(true);
	}

}