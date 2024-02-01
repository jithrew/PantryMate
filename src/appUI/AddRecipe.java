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

import net.proteanit.sql.DbUtils;

import javax.swing.JScrollPane;
import javax.swing.JTable;

public class AddRecipe extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddRecipe frame = new AddRecipe();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	Connection con = null;
	
	JTextField ingredientAmountText;
	private JComboBox<String> foodCombo, ingredientMetricCombo, ingredientCombo;
	private JTextField editAmountText;
	private JTextField recipeNameText;
	private JTable ingredientTable;
	private JTextField editMetricText;
	JButton deleteButton, addButton, editButton, createButton;
	
	public void fillComboBox() {
		con=Queries.dbconnect();
		foodCombo.removeAllItems();
		ingredientMetricCombo.removeAllItems();
		try {
			String query ="select * from food order by name asc";
			PreparedStatement pst = con.prepareStatement(query);
			ResultSet rs=pst.executeQuery();
			while(rs.next()) {
				foodCombo.addItem(rs.getString("name"));
			}
			pst.close();
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		foodCombo.addItem("None");
		try {
			String query ="select * from metric order by metric_name asc";
			PreparedStatement pst = con.prepareStatement(query);
			ResultSet rs=pst.executeQuery();
			while(rs.next()) {
				ingredientMetricCombo.addItem(rs.getString("metric_name"));
			}
			pst.close();
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void fillComboBox1() {
		con=Queries.dbconnect();
		ingredientCombo.removeAllItems();
		try {
			String query ="select * from food join link on link.food=food.id join recipe on recipe.id=link.recipe where recipe.id=(select max(id) from recipe)";
			PreparedStatement pst = con.prepareStatement(query);
			ResultSet rs=pst.executeQuery();
			while(rs.next()) {
				ingredientCombo.addItem(rs.getString("name"));
			}
			pst.close();
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void refreshTable() {
		try {
			String query ="select food.name, link.amount, metric.metric_name from link join recipe on link.recipe=recipe.id join food on food.id=link.food join metric on metric.id=link.metric where link.recipe=(select max(id) from recipe)";
			PreparedStatement pst = con.prepareStatement(query);
			ResultSet rs=pst.executeQuery();
			ingredientTable.setModel(DbUtils.resultSetToTableModel(rs));
			pst.close();
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * Create the frame.
	 */
	public AddRecipe() {
		con=Queries.dbconnect();		
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
		
		JLabel titleLabel = new JLabel("Recipe");
		titleLabel.setForeground(new Color(40, 84, 48));
		titleLabel.setBounds(63, 27, 211, 60);
		titleLabel.setLabelFor(panel);
		titleLabel.setFont(new Font("Arista 2.0", Font.BOLD, 50));
		titleLabel.setVerticalAlignment(SwingConstants.BOTTOM);
		titleLabel.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(titleLabel);
		
		JButton backButton = new JButton("<");
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				RecipeScreen back = new RecipeScreen();
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
		
		JPanel recipePanel = new JPanel();
		recipePanel.setBackground(new Color(160, 190, 123));
		recipePanel.setBounds(10, 107, 511, 166);
		contentPane.add(recipePanel);
		recipePanel.setLayout(null);
		
		JTextField ingredientText = new JTextField();
		ingredientText.setEditable(false);
		ingredientText.setBounds(292, 60, 106, 39);
		ingredientText.setColumns(10);
		ingredientText.setEditable(false);
		recipePanel.add(ingredientText);
		
		JLabel ingredientLabel = new JLabel("Ingredient:");
		ingredientLabel.setBounds(14, 68, 120, 31);
		ingredientLabel.setForeground(new Color(40, 84, 48));
		ingredientLabel.setFont(new Font("Arista 2.0", Font.BOLD, 18));
		recipePanel.add(ingredientLabel);
		
		JLabel ingredientAmountLabel = new JLabel("Amount:");
		ingredientAmountLabel.setBounds(14, 122, 147, 31);
		ingredientAmountLabel.setForeground(new Color(40, 84, 48));
		ingredientAmountLabel.setFont(new Font("Arista 2.0", Font.BOLD, 18));
		recipePanel.add(ingredientAmountLabel);
		
		ingredientMetricCombo = new JComboBox<String>();
		ingredientMetricCombo.setBounds(260, 110, 138, 39);
		recipePanel.add(ingredientMetricCombo);
		

		addButton = new JButton("Add");
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {					
				if (foodCombo.getSelectedItem()=="None") {
					try {
						String query ="insert into food (name) values (\'" + ingredientText.getText() + "\')";
						PreparedStatement pst = con.prepareStatement(query);
						pst.execute();
						pst.close();
						
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					try {
						String query ="insert into link (recipe, food, amount, metric) values((select max(id)from recipe), (select max(id) from food), \'" + ingredientAmountText.getText() + "\', (select id from metric where metric_name=\'" + ingredientMetricCombo.getSelectedItem() + "\'))";
						PreparedStatement pst = con.prepareStatement(query);
						pst.execute();				
						pst.close();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				} else {
					try {
						String query ="insert into link (recipe, food, amount, metric) values((select max(id)from recipe), (select id from food where name=\'" + foodCombo.getSelectedItem() + "\'), \'" + ingredientAmountText.getText() + "\', (select id from metric where metric_name=\'" + ingredientMetricCombo.getSelectedItem() + "\'))";
						PreparedStatement pst = con.prepareStatement(query);
						pst.execute();				
						pst.close();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
				ingredientText.setText("");
				ingredientAmountText.setText("");
				refreshTable();
				fillComboBox();
				fillComboBox1();
			}
		});
		addButton.setBounds(409, 110, 93, 43);
		addButton.setForeground(new Color(229, 217, 182));
		addButton.setFont(new Font("Arista 2.0", Font.BOLD, 25));
		addButton.setBorder(null);
		addButton.setEnabled(false);
		addButton.setBackground(new Color(40, 84, 48));
		recipePanel.add(addButton);
		
		createButton = new JButton("Create");
		createButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String query ="insert into recipe(recipe_name) values(\'" + recipeNameText.getText() + "\')";
					PreparedStatement pst = con.prepareStatement(query);
					pst.execute();				
					pst.close();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				recipeNameText.setEditable(false);
				createButton.setEnabled(false);
				addButton.setEnabled(true);
				editButton.setEnabled(true);
				deleteButton.setEnabled(true);
				ingredientAmountText.setEditable(true);
				editAmountText.setEditable(true);
				refreshTable();
			}
		});
		createButton.setForeground(new Color(229, 217, 182));
		createButton.setFont(new Font("Arista 2.0", Font.BOLD, 25));
		createButton.setBorder(null);
		createButton.setBackground(new Color(40, 84, 48));
		createButton.setBounds(408, 11, 94, 43);
		recipePanel.add(createButton);
		
		ingredientAmountText = new JTextField();
		ingredientAmountText.setEditable(false);
		ingredientAmountText.setColumns(10);
		ingredientAmountText.setBounds(171, 116, 79, 38);
		recipePanel.add(ingredientAmountText);
		
		foodCombo = new JComboBox<String>();
		foodCombo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if ("None".equals(foodCombo.getSelectedItem())) {
					ingredientText.setEditable(true);
				} else {
					ingredientText.setEditable(false);
				}
			}
		});
		foodCombo.setBounds(171, 60, 111, 39);
		recipePanel.add(foodCombo);
		
		JLabel recipeNameLabel = new JLabel("Recipe Name:");
		recipeNameLabel.setForeground(new Color(40, 84, 48));
		recipeNameLabel.setFont(new Font("Arista 2.0", Font.BOLD, 18));
		recipeNameLabel.setBounds(14, 17, 147, 31);
		recipePanel.add(recipeNameLabel);
		
		recipeNameText = new JTextField();
		recipeNameText.setColumns(10);
		recipeNameText.setBounds(171, 11, 227, 38);
		recipePanel.add(recipeNameText);
		
		JPanel editPanel = new JPanel();
		editPanel.setBackground(new Color(160, 190, 123));
		editPanel.setBounds(10, 284, 511, 168);
		contentPane.add(editPanel);
		editPanel.setLayout(null);
		
		JLabel editLabel = new JLabel("Edit Ingredient:");
		editLabel.setBounds(10, 19, 172, 31);
		editPanel.add(editLabel);
		editLabel.setForeground(new Color(40, 84, 48));
		editLabel.setFont(new Font("Arista 2.0", Font.BOLD, 18));
		
		ingredientCombo = new JComboBox<String>();
		ingredientCombo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String query ="select link.amount from food join link on link.food=food.id join recipe on recipe.id=link.recipe where (food.name=\'" + ingredientCombo.getSelectedItem() + "\' and recipe.id=(select max(id) from recipe))";
					PreparedStatement pst = con.prepareStatement(query);
					ResultSet rs=pst.executeQuery();
					String amount = "";
					if (rs.next()) {
						amount = rs.getString("amount");
					}
					editAmountText.setText(amount);
					pst.close();
					rs.close();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				try {
					String query ="select metric.metric_name from metric join pantry on pantry.food_metric=metric.id join food on food.id=pantry.food_name where food.name=\'" + ingredientCombo.getSelectedItem() + "\'";
					PreparedStatement pst = con.prepareStatement(query);
					ResultSet rs=pst.executeQuery();
					String metric = "";
					if (rs.next()) {
						metric = rs.getString("metric_name");
					}
					editMetricText.setText(metric);
					pst.execute();				
					pst.close();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		ingredientCombo.setBounds(180, 11, 217, 39);
		editPanel.add(ingredientCombo);
		
		JLabel editAmountLabel = new JLabel("Amount:");
		editAmountLabel.setBounds(10, 66, 147, 31);
		editPanel.add(editAmountLabel);
		editAmountLabel.setForeground(new Color(40, 84, 48));
		editAmountLabel.setFont(new Font("Arista 2.0", Font.BOLD, 18));
		
		editAmountText = new JTextField();
		editAmountText.setEditable(false);
		editAmountText.setBounds(180, 59, 85, 38);
		editPanel.add(editAmountText);
		editAmountText.setColumns(10);
		
		deleteButton = new JButton("Delete");
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String query ="delete from link where food=(select id from food where name=\'" + ingredientCombo.getSelectedItem() + "\') and recipe=(select max(id) from recipe)";
					PreparedStatement pst = con.prepareStatement(query);
					pst.execute();				
					pst.close();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				editAmountText.setText("");
				setVisible(false);
				AddRecipe recipe = new AddRecipe();
				recipe.setVisible(true);
			}
		});
		deleteButton.setBounds(10, 114, 94, 43);
		editPanel.add(deleteButton);
		deleteButton.setForeground(new Color(229, 217, 182));
		deleteButton.setFont(new Font("Arista 2.0", Font.BOLD, 25));
		deleteButton.setEnabled(false);
		deleteButton.setBorder(null);
		deleteButton.setBackground(new Color(40, 84, 48));
		
		editButton = new JButton("Edit");
		editButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String query ="update link set amount=\'" + editAmountText.getText() + "\' where food=(select id from food where name=\'" + ingredientCombo.getSelectedItem() + "\') and recipe=(select max(id) from recipe)";
					PreparedStatement pst = con.prepareStatement(query);
					pst.execute();				
					pst.close();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				refreshTable();
			}
		});
		editButton.setBounds(407, 114, 94, 43);
		editPanel.add(editButton);
		editButton.setForeground(new Color(229, 217, 182));
		editButton.setFont(new Font("Arista 2.0", Font.BOLD, 25));
		editButton.setEnabled(false);
		editButton.setBorder(null);
		editButton.setBackground(new Color(40, 84, 48));
		
		editMetricText = new JTextField();
		editMetricText.setEditable(false);
		editMetricText.setColumns(10);
		editMetricText.setBounds(275, 61, 122, 38);
		editPanel.add(editMetricText);
		
		JScrollPane recipeScroll = new JScrollPane();
		recipeScroll.setBounds(531, 107, 245, 345);
		contentPane.add(recipeScroll);
		
		ingredientTable = new JTable();
		ingredientTable.setBackground(new Color(229, 217, 182));
		ingredientTable.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 15));
		ingredientTable.setForeground(new Color(40, 84, 48));
		recipeScroll.setViewportView(ingredientTable);
		
		fillComboBox();
		refreshTable();
	}
}
