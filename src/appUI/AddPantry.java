package appUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class AddPantry extends JFrame {

    private JPanel contentPane;
    private JTextField foodAmountText;
    private JTextField expiryText;
    private JComboBox<String> foodNameCombo, foodMetricCombo;

    Connection con = null;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    AddPantry frame = new AddPantry();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void fillComboBox() {
        con = Queries.dbconnect();
        try {
            String query = "select * from food order by name asc";
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                foodNameCombo.addItem(rs.getString("name"));
            }
            pst.close();
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        foodNameCombo.addItem("None");
    }

    public void fillComboBox1() {
        con = Queries.dbconnect();
        try {
            String query = "select * from metric order by metric_name asc";
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                foodMetricCombo.addItem(rs.getString("metric_name"));
            }
            pst.close();
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public AddPantry() {
        con = Queries.dbconnect();
        setResizable(false);
        setTitle("PantryMate");
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

        JLabel titleLabel = new JLabel("Add Here");
        titleLabel.setForeground(new Color(40, 84, 48));
        titleLabel.setBounds(63, 27, 310, 60);
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

        JPanel addPanel = new JPanel();
        addPanel.setBackground(new Color(160, 190, 123));
        addPanel.setBounds(74, 125, 606, 313);
        contentPane.add(addPanel);
        addPanel.setLayout(null);

        JTextField foodNameText = new JTextField();
        foodNameText.setBounds(364, 62, 212, 39);
        foodNameText.setColumns(10);
        foodNameText.setEditable(false);
        addPanel.add(foodNameText);

        JLabel foodNameLabel = new JLabel("Food:");
        foodNameLabel.setBounds(14, 70, 120, 31);
        foodNameLabel.setForeground(new Color(40, 84, 48));
        foodNameLabel.setFont(new Font("Arista 2.0", Font.BOLD, 25));
        addPanel.add(foodNameLabel);

        JLabel foodAmountLabel = new JLabel("Amount:");
        foodAmountLabel.setBounds(14, 126, 147, 31);
        foodAmountLabel.setForeground(new Color(40, 84, 48));
        foodAmountLabel.setFont(new Font("Arista 2.0", Font.BOLD, 25));
        addPanel.add(foodAmountLabel);

        JLabel foodExpiryLabel = new JLabel("Expiry:");
        foodExpiryLabel.setBounds(14, 182, 130, 31);
        foodExpiryLabel.setForeground(new Color(40, 84, 48));
        foodExpiryLabel.setFont(new Font("Arista 2.0", Font.BOLD, 25));
        addPanel.add(foodExpiryLabel);

        JLabel dateGuideLabel = new JLabel("YYYY-MM-DD");
        dateGuideLabel.setHorizontalAlignment(SwingConstants.CENTER);
        dateGuideLabel.setBounds(258, 223, 203, 25);
        dateGuideLabel.setForeground(new Color(229, 217, 182));
        dateGuideLabel.setFont(new Font("Arista 2.0", Font.BOLD, 20));
        addPanel.add(dateGuideLabel);

        foodMetricCombo = new JComboBox<>();
        foodMetricCombo.setBounds(393, 118, 185, 39);
        addPanel.add(foodMetricCombo);

        JButton addButton = new JButton("+ Add");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String foodName = foodNameCombo.getSelectedItem().toString();
                String foodAmount = foodAmountText.getText();
                String foodMetric = foodMetricCombo.getSelectedItem().toString();
                String expiryDate = expiryText.getText();

                if (foodName.isEmpty() || foodAmount.isEmpty() || foodMetric.isEmpty() || expiryDate.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill in all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    String query = "INSERT INTO pantry (food_name, food_amount, food_metric, food_expiry) VALUES (?, ?, ?, ?)";
                    PreparedStatement pst = con.prepareStatement(query);
                    pst.setString(1, foodName);
                    pst.setDouble(2, Double.parseDouble(foodAmount));
                    pst.setString(3, foodMetric);
                    pst.setString(4, expiryDate);
                    int rowsAffected = pst.executeUpdate();

                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(null, "Item added to pantry successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                        foodNameCombo.setSelectedIndex(0);
                        foodAmountText.setText("");
                        expiryText.setText("");
                    } else {
                        JOptionPane.showMessageDialog(null, "Failed to add item to pantry.", "Error", JOptionPane.ERROR_MESSAGE);
                    }

                    pst.close();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid value for food amount.", "Input Error", JOptionPane.ERROR_MESSAGE);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error while adding item to pantry.", "Database Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        addButton.setBounds(502, 259, 76, 43);
        addButton.setForeground(new Color(229, 217, 182));
        addButton.setFont(new Font("Arista 2.0", Font.BOLD, 25));
        addButton.setBorder(null);
        addButton.setBackground(new Color(40, 84, 48));
        addPanel.add(addButton);

        JButton clearButton = new JButton("x Clear");
        clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                foodNameText.setText("");
                expiryText.setText("");
                foodAmountText.setText("");
            }
        });
        clearButton.setForeground(new Color(229, 217, 182));
        clearButton.setFont(new Font("Arista 2.0", Font.BOLD, 25));
        clearButton.setBorder(null);
        clearButton.setBackground(new Color(40, 84, 48));
        clearButton.setBounds(20, 252, 110, 43);
        addPanel.add(clearButton);

        foodAmountText = new JTextField();
        foodAmountText.setColumns(10);
        foodAmountText.setBounds(171, 118, 212, 38);
        addPanel.add(foodAmountText);

        expiryText = new JTextField();
        expiryText.setColumns(10);
        expiryText.setBounds(171, 174, 407, 38);
        addPanel.add(expiryText);

        foodNameCombo = new JComboBox<>();
        foodNameCombo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if ("None".equals(foodNameCombo.getSelectedItem().toString())) {
                    foodNameText.setEditable(true);
                } else {
                    foodNameText.setEditable(false);
                }
            }
        });
        foodNameCombo.setBounds(171, 62, 185, 39);
        addPanel.add(foodNameCombo);

        fillComboBox();
        fillComboBox1();
    }
}
