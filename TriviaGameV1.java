package quizmaker;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import java.awt.Color;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.ImageIcon;

public class maker {

	private JLabel Score_lbl;
	public static ArrayList<String> names = new ArrayList();
	private JButton buttonEnable;
	private JTextField txt_1;
	public static String begin = null;
	private StringBuffer tableName = new StringBuffer();
	private int points;
	public static Question definition;
	private JButton testingbutton;
	private Integer [] fonts = {10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,35,40,50};
	private int tries;
	private JTextField field_txt;
	private JFrame frames_la;
	public static ArrayList<Quiz> quizzes = new ArrayList();
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(
		            UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException f9) {
			f9.printStackTrace();
		} catch (InstantiationException f9) {
			f9.printStackTrace();
		} catch (IllegalAccessException f9) {
			f9.printStackTrace();
		} catch (UnsupportedLookAndFeelException f9) {
			f9.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					maker wind = new maker();
					wind.frames_la.setVisible(true);
				} catch (Exception exc) {
					exc.printStackTrace();
				}
			}
		});
	}
	
	Connection connect = null;
	
	public void namesQuiz(){
		ResultSet rwQuer;
		int row = -1;
		ResultSet query_Name;
		
		try {
			String query = "SELECT Count(*) FROM QuizDB";
			PreparedStatement prepst = connect.prepareStatement(query);
			rwQuer = prepst.executeQuery();
			row = rwQuer.getInt(1);
			
			prepst.close();
		} catch (SQLException f9) {
			
			JOptionPane.showMessageDialog(null, f9); 
		}
		
		if (row > 0) { 
			try {
				String qry = "SELECT ALL QuizName FROM QuizDB";
				PreparedStatement pst = connect.prepareStatement(qry);
				query_Name = pst.executeQuery();
				for (int i = 0; i <= row; i++) {
					String gt_str = query_Name.getString(1);
					if (!names.contains(gt_str) &&
							gt_str != null) {
						names.add(gt_str);
						Quiz temp = new Quiz(gt_str); 
						quizzes.add(temp);
					}
					query_Name.next();
				}
				pst.close();
			} catch (SQLException f9) {
				
				JOptionPane.showMessageDialog(null, f9); 
			}
		}
		
	}
	
	
	public maker() {
		connect = SqliteConnection.dataConnect();
		namesQuiz();
		if (!quizzes.isEmpty()) {
			for (Quiz x: quizzes) {
				x.buildQuiz(x.getName());
			}
		}
		initQuiz();
	}
	
	public static Quiz refQuiz(String inpName) {
		for (Quiz x: quizzes) {
			if (x.getName().equals(inpName)) {
				return x;
			}
		}
		return null;
	}
	
	private Object Obj(final String prod)  {
	     return new Object() { public String toString() { return prod; } };
	   }

	private void initQuiz() {
		frames_la = new JFrame();
		frames_la.getContentPane().setBackground(new Color(51, 51, 51));
		frames_la.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 20));
		frames_la.setBounds(0, 0, 915, 600);
		frames_la.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frames_la.getContentPane().setLayout(null);
		
		JComboBox box = new JComboBox();
		box.setFont(new Font("Leelawadee UI", Font.PLAIN, 13));
		box.setBounds(199, 20, 178, 36);
		
		for (String x: names) {
			box.addItem(x);
		}
		frames_la.getContentPane().add(box);
		
		JLabel currlbl = new JLabel("Current Quiz: ");
		currlbl.setFont(new Font("Leelawadee UI", Font.BOLD, 13));
		currlbl.setForeground(new Color(255, 255, 255));
		currlbl.setBounds(36, 63, 308, 14);
		frames_la.getContentPane().add(currlbl);
		
		
		JButton buttonSet = new JButton("Set Current Quiz");
		buttonSet.setIcon(new ImageIcon("C:\\Users\\iinic\\OneDrive\\Documents\\Activity Feed_32px.png"));
		buttonSet.setFont(new Font("Leelawadee UI", Font.PLAIN, 13));
		buttonSet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				begin = box.getSelectedItem().toString();
				currlbl.setText("Current Quiz: " + begin);
			}
		});
		buttonSet.setBounds(26, 20, 163, 36);
		frames_la.getContentPane().add(buttonSet);
		
		txt_1 = new JTextField();
		txt_1.setFont(new Font("Leelawadee UI", Font.PLAIN, 13));
		txt_1.setBounds(590, 20, 162, 36);
		frames_la.getContentPane().add(txt_1);
		txt_1.setColumns(10);
		
	
		
		buttonEnable = new JButton("Create New Quiz");
		buttonEnable.setIcon(new ImageIcon("C:\\Users\\iinic\\OneDrive\\Documents\\New Document_32px.png"));
		buttonEnable.setFont(new Font("Leelawadee UI", Font.PLAIN, 13));
		buttonEnable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					box.addItem(Obj(txt_1.getText()));
					Quiz newQuiz = new Quiz(txt_1.getText());
					quizzes.add(newQuiz);
					txt_1.setText("");
				
			}
		});
		buttonEnable.setBounds(413, 20, 167, 36);
		frames_la.getContentPane().add(buttonEnable);
		
		testingbutton = new JButton("Add Question");
		testingbutton.setIcon(new ImageIcon("C:\\Users\\iinic\\OneDrive\\Documents\\quest.png"));
		testingbutton.setFont(new Font("Leelawadee UI", Font.PLAIN, 13));
		testingbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (begin == null) {
					JOptionPane.showMessageDialog(null, "select a quiz.");
				} else {
					AddQuestion aq = new AddQuestion();
					aq.NewScreen();
				}
			}
		});
		testingbutton.setBounds(188, 87, 162, 36);
		frames_la.getContentPane().add(testingbutton);
		
		JTextArea texta = new JTextArea("Quiz Maker");
		texta.setLineWrap(true);
		texta.setWrapStyleWord(true);
		texta.setFont(new Font("Leelawadee UI", Font.PLAIN, 30));
		texta.setBackground(new Color(51, 204, 51));
		texta.setBounds(164, 146, 543, 249);
		texta.setEditable(false);
		frames_la.getContentPane().add(texta);
		
		JComboBox boxext = new JComboBox(fonts);
		boxext.setFont(new Font("Tahoma", Font.PLAIN, 15));
		boxext.setBounds(768, 215, 81, 36);
		frames_la.getContentPane().add(boxext);
		
		JButton next_btn = new JButton("Set Font Size");
		next_btn.setFont(new Font("Tahoma", Font.PLAIN, 13));
		next_btn.setIcon(new ImageIcon("C:\\Users\\iinic\\OneDrive\\Documents\\lower.png"));
		next_btn.setBounds(734, 163, 145, 41);
		frames_la.getContentPane().add(next_btn);
		next_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int fSize = ((Integer) boxext.getSelectedItem()).intValue();
				texta.setFont(new Font("Leelawadee UI", Font.PLAIN, fSize));
			}
		});
		
		field_txt = new JTextField();
		field_txt.setFont(new Font("Leelawadee UI", Font.PLAIN, 13));
		field_txt.setBounds(249, 407, 377, 36);
		frames_la.getContentPane().add(field_txt);
		field_txt.setColumns(10);
		
		Score_lbl = new JLabel("Score: 0/0");
		Score_lbl.setFont(new Font("Leelawadee UI", Font.BOLD, 13));
		Score_lbl.setForeground(new Color(255, 255, 255));
		Score_lbl.setBackground(new Color(102, 255, 102));
		Score_lbl.setBounds(29, 163, 117, 14);
		frames_la.getContentPane().add(Score_lbl);
		
		JButton subButt = new JButton("Submit Answer");
		subButt.setIcon(new ImageIcon("C:\\Users\\iinic\\OneDrive\\Documents\\keys.png"));
		subButt.setFont(new Font("Leelawadee UI", Font.PLAIN, 13));
		subButt.setBackground(UIManager.getColor("Button.darkShadow"));
		subButt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (begin == null) {
					JOptionPane.showMessageDialog(null, "pick a quiz.");
				} else {
					if (tries < refQuiz(begin).length()) {
						if (field_txt.getText().equals("")) {
							JOptionPane.showMessageDialog(null, "Input an answer.");
						} else {
							if (field_txt.getText().toLowerCase().equals(definition.getAnswer())){
								points++;
								tries++;
								Score_lbl.setText("Score: " + points + "/" + tries);
							} else {
								tries++;
								Score_lbl.setText("Score: " + points + "/" + tries);
							}
							if (tries == refQuiz(begin).length()) {
								field_txt.setText(""); 
								texta.setText("Quiz End");
								JOptionPane.showMessageDialog(null, "Total Score: " + points + "/" + tries);
								tries = 0; 
							} else {
								field_txt.setText(""); 
								ArrayList<Question> qs = refQuiz(begin).getQuestions();
								Question newOne = qs.get(tries);
								texta.setText(newOne.getQuestion());
								definition = newOne;
							}
						}
					} else {
						JOptionPane.showMessageDialog(null, "Begin quiz.");
					}
				}
				
			}
		});
		subButt.setBounds(349, 454, 178, 41);
		frames_la.getContentPane().add(subButt);
		
		JButton strButt = new JButton("Begin Quiz");
		strButt.setIcon(new ImageIcon("C:\\Users\\iinic\\OneDrive\\Documents\\Circles.png"));
		strButt.setFont(new Font("Leelawadee UI", Font.ROMAN_BASELINE, 13));
		strButt.setBounds(26, 86, 135, 37);
		frames_la.getContentPane().add(strButt);
		strButt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (begin == null) {
					JOptionPane.showMessageDialog(null, "Select a quiz.");
				} else {
					tries = 0;
					points = 0;
					Score_lbl.setText("Score: " + points + "/" + tries);
					ArrayList<Question> qs = refQuiz(begin).getQuestions();
					Question q1 = qs.get(0);
					texta.setText(q1.getQuestion());
					definition = q1;
				}
			}
		});
		
	}
}
