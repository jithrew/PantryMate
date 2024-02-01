package appUI;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;

public class AddSolution extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddSolution frame = new AddSolution();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	Connection con=null;
	private JTextField headerText;
	private JTextField bodyText;
	

	/**
	 * Create the frame.
	 */
	public AddSolution() {
		con=Queries.dbconnect();
		setResizable(false);
		setTitle("PantryMate");
		setBackground(SystemColor.desktop);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 500);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(160, 190, 123));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(new Color(229, 217, 182));
		panel.setBounds(0, 0, 786, 96);
		contentPane.add(panel);
		
		JLabel titleLabel = new JLabel("Add Memo");
		titleLabel.setVerticalAlignment(SwingConstants.BOTTOM);
		titleLabel.setHorizontalAlignment(SwingConstants.LEFT);
		titleLabel.setForeground(new Color(40, 84, 48));
		titleLabel.setFont(new Font("Arista 2.0", Font.BOLD, 50));
		titleLabel.setBounds(63, 27, 420, 60);
		panel.add(titleLabel);
		
		JButton backButton = new JButton("<");
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				ExpiredFoodSolutionScreen back = new ExpiredFoodSolutionScreen();
				back.setVisible(true);
			}
		});
		backButton.setForeground(new Color(40, 84, 48));
		backButton.setFont(new Font("Arista 2.0", Font.BOLD, 40));
		backButton.setBorder(null);
		backButton.setBackground(new Color(229, 217, 182));
		backButton.setAlignmentX(0.5f);
		backButton.setBounds(10, 34, 53, 50);
		panel.add(backButton);
		
		JPanel addPanel = new JPanel();
		addPanel.setBackground(new Color(40, 84, 48));
		addPanel.setBounds(31, 124, 717, 306);
		contentPane.add(addPanel);
		addPanel.setLayout(null);
		
		JLabel headerLabel = new JLabel("Header:");
		headerLabel.setForeground(new Color(229, 217, 182));
		headerLabel.setFont(new Font("Arista 2.0", Font.BOLD, 25));
		headerLabel.setBounds(20, 17, 110, 37);
		addPanel.add(headerLabel);
		
		JLabel bodyLabel = new JLabel("Body:");
		bodyLabel.setForeground(new Color(229, 217, 182));
		bodyLabel.setFont(new Font("Arista 2.0", Font.BOLD, 25));
		bodyLabel.setBounds(20, 59, 133, 37);
		addPanel.add(bodyLabel);
		
		headerText = new JTextField();
		headerText.setBounds(130, 11, 577, 43);
		addPanel.add(headerText);
		headerText.setColumns(10);
		
		bodyText = new JTextField();
		bodyText.setColumns(10);
		bodyText.setBounds(130, 59, 577, 182);
		addPanel.add(bodyText);
		
		JButton addButton = new JButton("+ Add");
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String query ="insert into solution(header, memo) values(?, ?)";
					PreparedStatement pst = con.prepareStatement(query);
					pst.setString(1, headerText.getText());
					pst.setString(2, bodyText.getText());
					pst.execute();				
					pst.close();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				headerText.setText("");
				bodyText.setText("");
				setVisible(false);
				ExpiredFoodSolutionScreen back = new ExpiredFoodSolutionScreen();
				back.setVisible(true);
			}
		});
		addButton.setForeground(new Color(229, 217, 182));
		addButton.setBackground(new Color(40, 84, 48));
		addButton.setBorder(null);
		addButton.setFont(new Font("Arista 2.0", Font.BOLD, 25));
		addButton.setBounds(597, 252, 110, 43);
		addPanel.add(addButton);
		
		JButton clearButton = new JButton("x Clear");
		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				headerText.setText("");
				bodyText.setText("");
			}
		});
		clearButton.setForeground(new Color(229, 217, 182));
		clearButton.setFont(new Font("Arista 2.0", Font.BOLD, 25));
		clearButton.setBorder(null);
		clearButton.setBackground(new Color(40, 84, 48));
		clearButton.setBounds(20, 252, 110, 43);
		addPanel.add(clearButton);
	}
}
