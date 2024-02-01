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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import net.proteanit.sql.DbUtils;

public class RecipeScreen extends JFrame {

	private JPanel contentPane;
	private JTable recipeTable;
	private JTable ingredientTable;
	private JTable pantryTable;
	private JComboBox<String> recipeCombo;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RecipeScreen frame = new RecipeScreen();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	Connection con = null;

	public void refreshTable() {
		con = Queries.dbconnect();
		try {
			String query = "select recipe_name from recipe";
			PreparedStatement pst = con.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			recipeTable.setModel(DbUtils.resultSetToTableModel(rs));
			pst.close();
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void fillComboBox() {
		con = Queries.dbconnect();
		try {
			String query = "select * from recipe order by id asc";
			PreparedStatement pst = con.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				recipeCombo.addItem(rs.getString("recipe_name"));
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
	public RecipeScreen() {
		con = Queries.dbconnect();
		setResizable(false);
		setTitle("PantryMate");
		setBackground(SystemColor.desktop);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 500);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(95, 141, 78));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 786, 96);
		panel.setBackground(new Color(229, 217, 182));
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("Recipe");
		lblNewLabel.setForeground(new Color(95, 141, 78));
		lblNewLabel.setBounds(63, 27, 211, 60);
		lblNewLabel.setLabelFor(panel);
		lblNewLabel.setFont(new Font("Arista 2.0", Font.BOLD, 50));
		lblNewLabel.setVerticalAlignment(SwingConstants.BOTTOM);
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(lblNewLabel);

		JButton addButton = new JButton("+ Add");
		addButton.setForeground(new Color(95, 141, 78));
		addButton.setIcon(null);
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				AddRecipe add = new AddRecipe();
				add.setVisible(true);
			}
		});
		addButton.setOpaque(false);
		addButton.setHorizontalTextPosition(SwingConstants.CENTER);
		addButton.setFont(new Font("Arista 2.0", Font.BOLD, 25));
		addButton.setBorder(null);
		addButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		addButton.setBackground(new Color(229, 217, 182));
		addButton.setBounds(690, 42, 74, 50);
		panel.add(addButton);

		JButton btnNewButton_1 = new JButton("<");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				new UnangScreen();
				UnangScreen.main(null);
			}
		});
		btnNewButton_1.setForeground(new Color(95, 141, 78));
		btnNewButton_1.setFont(new Font("Arista 2.0", Font.BOLD, 40));
		btnNewButton_1.setBorder(null);
		btnNewButton_1.setBackground(new Color(229, 217, 182));
		btnNewButton_1.setAlignmentX(0.5f);
		btnNewButton_1.setBounds(10, 34, 53, 50);
		panel.add(btnNewButton_1);

		recipeCombo = new JComboBox<>();
		recipeCombo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String query = "select food.name, link.amount, metric.metric_name from link JOIN food on food.id=link.food join metric on metric.id=link.metric where link.recipe=(select id from recipe where recipe_name='"
							+ recipeCombo.getSelectedItem() + "')";
					PreparedStatement pst = con.prepareStatement(query);
					ResultSet rs = pst.executeQuery();
					ingredientTable.setModel(DbUtils.resultSetToTableModel(rs));
					pst.close();
					rs.close();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				try {
					String query = "select food.name, pantry.food_amount, metric.metric_name, pantry.food_expiry from recipe join link on link.recipe=recipe.id join food on link.food=food.id join pantry on pantry.food_name=food.id join metric on metric.id=pantry.food_metric where recipe.id=(select id from recipe where recipe_name='"
							+ recipeCombo.getSelectedItem() + "') order by pantry.food_expiry asc";
					PreparedStatement pst = con.prepareStatement(query);
					ResultSet rs = pst.executeQuery();
					pantryTable.setModel(DbUtils.resultSetToTableModel(rs));
					pst.close();
					rs.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		recipeCombo.setBounds(229, 109, 242, 48);
		contentPane.add(recipeCombo);

		JScrollPane recipeScroll = new JScrollPane();
		recipeScroll.setBounds(10, 107, 209, 345);
		contentPane.add(recipeScroll);

		recipeTable = new JTable();
		recipeTable.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 14));
		recipeTable.setBackground(new Color(95, 141, 78));
		recipeTable.setForeground(new Color(229, 217, 182));
		recipeScroll.setViewportView(recipeTable);

		try {
			String query = "select * from recipe";
			PreparedStatement pst = con.prepareStatement(query);
			ResultSet rs = pst.executeQuery();
			recipeTable.setModel(DbUtils.resultSetToTableModel(rs));
			pst.close();
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		JScrollPane ingredientScroll = new JScrollPane();
		ingredientScroll.setBounds(229, 214, 242, 238);
		contentPane.add(ingredientScroll);

		ingredientTable = new JTable();
		ingredientTable.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 14));
		ingredientTable.setBackground(new Color(95, 141, 78));
		ingredientTable.setForeground(new Color(229, 217, 182));
		ingredientScroll.setViewportView(ingredientTable);

		JScrollPane pantryScroll = new JScrollPane();
		pantryScroll.setBounds(481, 214, 295, 238);
		contentPane.add(pantryScroll);

		pantryTable = new JTable();
		pantryTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		pantryTable.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 14));
		pantryTable.setBackground(new Color(95, 141, 78));
		pantryTable.setForeground(new Color(229, 217, 182));
		pantryScroll.setViewportView(pantryTable);

		JLabel ingredientLabel = new JLabel("Ingredients:");
		ingredientLabel.setForeground(new Color(229, 217, 182));
		ingredientLabel.setFont(new Font("Arista 2.0", Font.BOLD, 30));
		ingredientLabel.setBounds(229, 168, 242, 35);
		contentPane.add(ingredientLabel);

		JLabel pantryLabel = new JLabel("What you have:");
		pantryLabel.setForeground(new Color(229, 217, 182));
		pantryLabel.setFont(new Font("Arista 2.0", Font.BOLD, 30));
		pantryLabel.setBounds(481, 168, 242, 35);
		contentPane.add(pantryLabel);

		JButton deleteButton = new JButton("Delete");
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String query = "delete from link where recipe=(select id from recipe where recipe_name='"
							+ recipeCombo.getSelectedItem() + "')";
					PreparedStatement pst = con.prepareStatement(query);
					pst.execute();
					pst.close();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				try {
					String query = "delete from recipe where id=(select id from recipe where recipe_name='"
							+ recipeCombo.getSelectedItem() + "')";
					PreparedStatement pst = con.prepareStatement(query);
					pst.execute();
					pst.close();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				setVisible(false);
				RecipeScreen back = new RecipeScreen();
				back.setVisible(true);
			}
		});
		deleteButton.setForeground(new Color(229, 217, 182));
		deleteButton.setFont(new Font("Arista 2.0", Font.BOLD, 25));
		deleteButton.setBorder(null);
		deleteButton.setBackground(new Color(40, 84, 48));
		deleteButton.setBounds(670, 107, 106, 50);
		contentPane.add(deleteButton);

		fillComboBox();
		refreshTable();
	}
}
