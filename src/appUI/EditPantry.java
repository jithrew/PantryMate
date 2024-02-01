package appUI;

import java.awt.Color;
import java.awt.Component;
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

public class EditPantry extends JFrame {

	private JPanel contentPane;
	private JComboBox<String> productCombo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EditPantry frame = new EditPantry();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	Connection con = null;
	private JTextField foodAmtText;
	private JTextField expiryText;
	private JTextField foodAmtMetricText;

	public void fillComboBox() {
		con = Queries.dbconnect();
		try {
			String query = "select * from pantry join food on pantry.food_name=food.id order by food.name asc";
			PreparedStatement pst = con.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				productCombo.addItem(rs.getString("name"));
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
	public EditPantry() {
		con = Queries.dbconnect();
		setResizable(false);
		setTitle("PantryMate");
		setBackground(SystemColor.desktop);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 500);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(40, 84, 48));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 786, 96);
		panel.setBackground(new Color(229, 217, 182));
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel titleLabel = new JLabel("Edit Here");
		titleLabel.setForeground(new Color(40, 84, 48));
		titleLabel.setBounds(63, 27, 322, 60);
		titleLabel.setLabelFor(panel);
		titleLabel.setFont(new Font("Arista 2.0", Font.BOLD, 50));
		titleLabel.setVerticalAlignment(SwingConstants.BOTTOM);
		titleLabel.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(titleLabel);

		JButton backButton = new JButton("<");
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				pantryScreen back = new pantryScreen();
				back.setVisible(true);
			}
		});
		backButton.setForeground(new Color(40, 84, 48));
		backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		backButton.setFont(new Font("Arista 2.0", Font.BOLD, 40));
		backButton.setBorder(null);
		backButton.setBackground(new Color(229, 217, 182));
		backButton.setBounds(10, 34, 53, 50);
		panel.add(backButton);

		JPanel editPanel = new JPanel();
		editPanel.setBackground(new Color(160, 190, 123));
		editPanel.setBounds(123, 123, 572, 306);
		contentPane.add(editPanel);
		editPanel.setLayout(null);

		JLabel foodAmtLabel = new JLabel("Amount:");
		foodAmtLabel.setBounds(24, 83, 147, 31);
		foodAmtLabel.setForeground(new Color(40, 84, 48));
		foodAmtLabel.setFont(new Font("Arista 2.0", Font.BOLD, 25));
		editPanel.add(foodAmtLabel);

		JLabel expiryLabel = new JLabel("Expiry:");
		expiryLabel.setBounds(24, 135, 130, 31);
		expiryLabel.setForeground(new Color(40, 84, 48));
		expiryLabel.setFont(new Font("Arista 2.0", Font.BOLD, 25));
		editPanel.add(expiryLabel);

		JLabel dateGuideLabel = new JLabel("YYYY-MM-DD");
		dateGuideLabel.setHorizontalAlignment(SwingConstants.CENTER);
		dateGuideLabel.setBounds(262, 184, 203, 25);
		dateGuideLabel.setForeground(new Color(229, 217, 182));
		dateGuideLabel.setFont(new Font("Arista 2.0", Font.BOLD, 20));
		editPanel.add(dateGuideLabel);

		JButton clearButton = new JButton("x Clear");
		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				foodAmtText.setText("");
				expiryText.setText("");
			}
		});
		clearButton.setForeground(new Color(229, 217, 182));
		clearButton.setFont(new Font("Arista 2.0", Font.BOLD, 25));
		clearButton.setBorder(null);
		clearButton.setBackground(new Color(40, 84, 48));
		clearButton.setBounds(20, 252, 110, 43);
		editPanel.add(clearButton);

		productCombo = new JComboBox<String>();
		productCombo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String query = "select pantry.food_amount from pantry join food on pantry.food_name=food.id where (pantry.food_name=food.id and food.name=\'"
							+ productCombo.getSelectedItem() + "\')";
					PreparedStatement pst = con.prepareStatement(query);
					ResultSet rs = pst.executeQuery();
					if (rs.next()) {
						String amount = rs.getString("food_amount");
						foodAmtText.setText(amount);
					}
					pst.close();
					rs.close();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				try {
					String query = "select pantry.food_expiry from pantry join food on pantry.food_name=food.id where pantry.food_name=food.id and food.name=\'"
							+ productCombo.getSelectedItem() + "\'";
					PreparedStatement pst = con.prepareStatement(query);
					ResultSet rs = pst.executeQuery();
					if (rs.next()) {
						String memo = rs.getString("food_expiry");
						expiryText.setText(memo);
					}
					pst.close();
					rs.close();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				try {
					String query = "select metric.metric_name from metric join pantry on pantry.food_metric=metric.id join food on food.id=pantry.food_name where food.name=\'"
							+ productCombo.getSelectedItem() + "\'";
					PreparedStatement pst = con.prepareStatement(query);
					ResultSet rs = pst.executeQuery();
					if (rs.next()) {
						String metric = rs.getString("metric_name");
						foodAmtMetricText.setText(metric);
					}
					pst.execute();
					pst.close();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		productCombo.setBounds(24, 18, 230, 43);
		editPanel.add(productCombo);

		JButton editButton = new JButton("+ Edit");
		editButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String query = "update pantry set food_amount=\'" + foodAmtText.getText() + "\', food_expiry=\'"
							+ expiryText.getText() + "\' where food_name=(select id from food where name=\'"
							+ productCombo.getSelectedItem() + "\')";
					PreparedStatement pst = con.prepareStatement(query);
					pst.execute();
					pst.close();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				foodAmtText.setText("");
				expiryText.setText("");
				setVisible(false);
				pantryScreen back = new pantryScreen();
				back.setVisible(true);
			}
		});
		editButton.setForeground(new Color(229, 217, 182));
		editButton.setBackground(new Color(40, 84, 48));
		editButton.setBorder(null);
		editButton.setFont(new Font("Arista 2.0", Font.BOLD, 25));
		editButton.setBounds(438, 252, 110, 43);
		editPanel.add(editButton);

		JButton deleteButton = new JButton("x Delete");
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String query = "delete from pantry where food_name in (select id from food where name=\'"
							+ productCombo.getSelectedItem() + "\')";
					PreparedStatement pst = con.prepareStatement(query);
					pst.execute();
					pst.close();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				setVisible(false);
				pantryScreen back = new pantryScreen();
				back.setVisible(true);
			}
		});
		deleteButton.setForeground(new Color(229, 217, 182));
		deleteButton.setFont(new Font("Arista 2.0", Font.BOLD, 25));
		deleteButton.setBorder(null);
		deleteButton.setBackground(new Color(40, 84, 48));
		deleteButton.setBounds(438, 13, 110, 43);
		editPanel.add(deleteButton);

		foodAmtText = new JTextField();
		foodAmtText.setColumns(10);
		foodAmtText.setBounds(181, 86, 230, 35);
		editPanel.add(foodAmtText);

		expiryText = new JTextField();
		expiryText.setColumns(10);
		expiryText.setBounds(181, 138, 367, 35);
		editPanel.add(expiryText);

		foodAmtMetricText = new JTextField();
		foodAmtMetricText.setEditable(false);
		foodAmtMetricText.setColumns(10);
		foodAmtMetricText.setBounds(421, 86, 127, 35);
		editPanel.add(foodAmtMetricText);

		fillComboBox();
	}
}
