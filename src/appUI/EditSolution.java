package appUI;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class EditSolution extends JFrame {

	private JPanel contentPane;
	private JComboBox<String> memoCombo;
	private JTextField headerText;
	private JTextField bodyText;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EditSolution frame = new EditSolution();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	Connection con = null;

	public void fillComboBox() {
		con = Queries.dbconnect();
		try {
			String query = "select * from solution order by id asc";
			PreparedStatement pst = con.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				memoCombo.addItem(rs.getString("id"));
			}
			pst.close();
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the frame.
	 */
	public EditSolution() {
		con = Queries.dbconnect();
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

		JLabel titleLabel = new JLabel("Edit Memo");
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

		JPanel memoPanel = new JPanel();
		memoPanel.setBackground(new Color(40, 84, 48));
		memoPanel.setBounds(31, 124, 717, 306);
		contentPane.add(memoPanel);
		memoPanel.setLayout(null);

		JLabel headerLabel = new JLabel("Header:");
		headerLabel.setForeground(new Color(229, 217, 182));
		headerLabel.setFont(new Font("Arista 2.0", Font.BOLD, 25));
		headerLabel.setBounds(20, 73, 110, 37);
		memoPanel.add(headerLabel);

		JLabel bodyLabel = new JLabel("Body:");
		bodyLabel.setForeground(new Color(229, 217, 182));
		bodyLabel.setFont(new Font("Arista 2.0", Font.BOLD, 25));
		bodyLabel.setBounds(20, 121, 133, 37);
		memoPanel.add(bodyLabel);

		headerText = new JTextField();
		headerText.setBounds(130, 67, 577, 43);
		memoPanel.add(headerText);
		headerText.setColumns(10);

		bodyText = new JTextField();
		bodyText.setColumns(10);
		bodyText.setBounds(130, 121, 577, 120);
		memoPanel.add(bodyText);

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
		memoPanel.add(clearButton);

		memoCombo = new JComboBox<String>();
		memoCombo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String query = "select header, memo from solution where id=?";
					PreparedStatement pst = con.prepareStatement(query);
					pst.setString(1, memoCombo.getSelectedItem().toString());
					ResultSet rs = pst.executeQuery();
					if (rs.next()) {
						String header = rs.getString("header");
						String memo = rs.getString("memo");
						headerText.setText(header);
						bodyText.setText(memo);
					}
					pst.close();
					rs.close();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		memoCombo.setBounds(20, 11, 289, 43);
		memoPanel.add(memoCombo);

		JButton editButton = new JButton("+ Edit");
		editButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String query = "update solution set header=?, memo=? where id=?";
					PreparedStatement pst = con.prepareStatement(query);
					pst.setString(1, headerText.getText());
					pst.setString(2, bodyText.getText());
					pst.setString(3, memoCombo.getSelectedItem().toString());
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
		editButton.setForeground(new Color(229, 217, 182));
		editButton.setBackground(new Color(40, 84, 48));
		editButton.setBorder(null);
		editButton.setFont(new Font("Arista 2.0", Font.BOLD, 25));
		editButton.setBounds(597, 252, 110, 43);
		memoPanel.add(editButton);

		JButton deleteButton = new JButton("x Delete");
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String query = "delete from solution where id=?";
					PreparedStatement pst = con.prepareStatement(query);
					pst.setString(1, memoCombo.getSelectedItem().toString());
					pst.execute();
					pst.close();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				setVisible(false);
				ExpiredFoodSolutionScreen back = new ExpiredFoodSolutionScreen();
				back.setVisible(true);
			}
		});
		deleteButton.setForeground(new Color(229, 217, 182));
		deleteButton.setFont(new Font("Arista 2.0", Font.BOLD, 25));
		deleteButton.setBorder(null);
		deleteButton.setBackground(new Color(40, 84, 48));
		deleteButton.setBounds(597, 13, 110, 43);
		memoPanel.add(deleteButton);

		fillComboBox();
	}
}
