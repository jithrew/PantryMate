package appUI;

import java.awt.Color;
import java.awt.SystemColor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.proteanit.sql.DbUtils;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;

public class ExpiredFoodSolutionScreen extends JFrame {

	private JPanel contentPane;
	private JTable memoTable;
	private JComboBox<Integer> memoCombo;
	private Connection con = null;

	public void refreshTable() {
		try {
			String query = "select id, header from solution";
			PreparedStatement pst = con.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			memoTable.setModel(DbUtils.resultSetToTableModel(rs));
			pst.close();
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void fillComboBox() {
		con = Queries.dbconnect();
		try {
			String query = "select * from solution order by id asc";
			PreparedStatement pst = con.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				memoCombo.addItem(rs.getInt("id"));
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
	public ExpiredFoodSolutionScreen() {
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

		JLabel titleLabel = new JLabel("Solutions");
		titleLabel.setVerticalAlignment(SwingConstants.BOTTOM);
		titleLabel.setHorizontalAlignment(SwingConstants.LEFT);
		titleLabel.setForeground(new Color(40, 84, 48));
		titleLabel.setFont(new Font("Arista 2.0", Font.BOLD, 50));
		titleLabel.setBounds(63, 27, 420, 60);
		panel.add(titleLabel);

		JButton addButton = new JButton("+ Add");
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				AddSolution add = new AddSolution();
				add.setVisible(true);
			}
		});
		addButton.setOpaque(false);
		addButton.setHorizontalTextPosition(SwingConstants.CENTER);
		addButton.setForeground(new Color(40, 84, 48));
		addButton.setFont(new Font("Arista 2.0", Font.BOLD, 25));
		addButton.setBorder(null);
		addButton.setBackground(new Color(229, 217, 182));
		addButton.setAlignmentX(0.5f);
		addButton.setBounds(690, 42, 74, 50);
		panel.add(addButton);

		JButton editButton = new JButton("x Edit");
		editButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				EditSolution edit = new EditSolution();
				edit.setVisible(true);
			}
		});
		editButton.setOpaque(false);
		editButton.setHorizontalTextPosition(SwingConstants.CENTER);
		editButton.setForeground(new Color(40, 84, 48));
		editButton.setFont(new Font("Arista 2.0", Font.BOLD, 25));
		editButton.setBorder(null);
		editButton.setBackground(new Color(229, 217, 182));
		editButton.setAlignmentX(0.5f);
		editButton.setBounds(591, 42, 99, 50);
		panel.add(editButton);

		JButton backButton = new JButton("<");
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				new UnangScreen();
				UnangScreen.main(null);
			}
		});
		backButton.setForeground(new Color(40, 84, 48));
		backButton.setFont(new Font("Arista 2.0", Font.BOLD, 40));
		backButton.setBorder(null);
		backButton.setBackground(new Color(229, 217, 182));
		backButton.setAlignmentX(0.5f);
		backButton.setBounds(10, 34, 53, 50);
		panel.add(backButton);

		JScrollPane memoScroll = new JScrollPane();
		memoScroll.setBounds(10, 107, 321, 345);
		contentPane.add(memoScroll);

		memoTable = new JTable();
		memoTable.setBackground(new Color(229, 217, 182));
		memoTable.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 15));
		memoTable.setForeground(new Color(40, 84, 48));
		memoScroll.setViewportView(memoTable);

		try {
			String query = "select id, header from solution";
			PreparedStatement pst = con.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			memoTable.setModel(DbUtils.resultSetToTableModel(rs));
			pst.close();
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		JLabel headerLabel = new JLabel(" ");
		headerLabel.setBackground(new Color(229, 217, 182));
		headerLabel.setForeground(new Color(40, 84, 48));
		headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
		headerLabel.setFont(new Font("Arista 2.0", Font.BOLD, 20));
		headerLabel.setBounds(341, 161, 435, 49);
		contentPane.add(headerLabel);

		JLabel bodyLabel = new JLabel(" ");
		bodyLabel.setForeground(new Color(40, 84, 48));
		bodyLabel.setBackground(new Color(95, 141, 78));
		bodyLabel.setVerticalAlignment(SwingConstants.TOP);
		bodyLabel.setFont(new Font("Arista 2.0", Font.BOLD, 20));
		bodyLabel.setBounds(359, 221, 400, 231);
		contentPane.add(bodyLabel);

		memoCombo = new JComboBox<Integer>();
		memoCombo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int selectedId = (int) memoCombo.getSelectedItem(); // Cast the selected item to integer
					String query = "select header, memo from solution where id=?";
					PreparedStatement pst = con.prepareStatement(query);
					pst.setInt(1, selectedId); // Set the integer value as parameter
					ResultSet rs = pst.executeQuery();
					if (rs.next()) {
						String header = rs.getString("header");
						String memo = rs.getString("memo");
						headerLabel.setText(header);
						bodyLabel.setText("<html>" + memo + "</html>");
					}
					pst.close();
					rs.close();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		memoCombo.setBounds(341, 107, 435, 43);
		contentPane.add(memoCombo);
		
		JPanel memoPanel = new JPanel();
		memoPanel.setBackground(new Color(229, 217, 182));
		memoPanel.setBounds(341, 161, 435, 291);
		contentPane.add(memoPanel);
		
		fillComboBox();
		refreshTable();
	}
}
