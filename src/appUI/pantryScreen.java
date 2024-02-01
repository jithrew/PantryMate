package appUI;

import java.awt.Color;
import java.awt.SystemColor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import net.proteanit.sql.DbUtils;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;

public class pantryScreen extends JFrame {

    private JPanel contentPane;
    private JTable pantryTable;
    private JComboBox<String> searchCombo;
    private JTable searchTable;
    private JTable expiredTable;
    private JTable expiringTable;

    Connection con = null;

	public void refreshTable() {
		try {
			String query ="select food.name, pantry.food_amount, metric.metric_name, pantry.food_expiry"
					+ " from pantry"
					+ " join metric on metric.id=pantry.food_metric"
					+ " join food on food.id=pantry.food_name"
					+ " where pantry.food_expiry>\'" + java.time.LocalDate.now().plusDays(3) + "\'"
					+ " order by pantry.food_expiry asc";
			PreparedStatement pst = con.prepareStatement(query);
			ResultSet rs=pst.executeQuery();
			pantryTable.setModel(DbUtils.resultSetToTableModel(rs));
			pst.close();
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			String query ="select food.name, pantry.food_amount, metric.metric_name, pantry.food_expiry"
					+ " from pantry"
					+ " join metric on metric.id=pantry.food_metric"
					+ " join food on food.id=pantry.food_name"
					+ " where pantry.food_expiry<\'" + java.time.LocalDate.now() + "\'"
					+ " order by pantry.food_expiry asc";
			PreparedStatement pst = con.prepareStatement(query);
			ResultSet rs=pst.executeQuery();
			expiredTable.setModel(DbUtils.resultSetToTableModel(rs));
			pst.close();
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			String query ="select food.name, pantry.food_amount, metric.metric_name, pantry.food_expiry"
					+ " from pantry"
					+ " join metric on metric.id=pantry.food_metric"
					+ " join food on food.id=pantry.food_name"
					+ " where pantry.food_expiry<\'" + java.time.LocalDate.now().plusDays(3) + "\' and "
					+ " pantry.food_expiry>\'" + java.time.LocalDate.now() + "\'"
					+ " order by pantry.food_expiry asc";
			PreparedStatement pst = con.prepareStatement(query);
			ResultSet rs=pst.executeQuery();
			expiringTable.setModel(DbUtils.resultSetToTableModel(rs));
			pst.close();
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    

    public void fillComboBox() {
        con = Queries.dbconnect();
        try {
            String query = "select * from pantry join food on food.id=pantry.food_name order by food.name asc";
            PreparedStatement pst = con.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                searchCombo.addItem(rs.getString("name"));
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
    public pantryScreen() {

        PantryNotif notif = new PantryNotif();
        notif.setVisible(true);
        notif.setAlwaysOnTop(true);

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

        JLabel titleLabel = new JLabel("Pantry");
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
                new UnangScreen();
                UnangScreen.main(null);
            }
        });
        backButton.setForeground(new Color(40, 84, 48));
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setFont(new Font("Arista 2.0", Font.BOLD, 40));
        backButton.setBorder(null);
        backButton.setBackground(new Color(229, 217, 182));
        backButton.setBounds(10, 34, 53, 50);
        panel.add(backButton);

        JButton editButton = new JButton("x Edit");
        editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                EditPantry edit = new EditPantry();
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
        editButton.setBounds(568, 42, 99, 50);
        panel.add(editButton);

        JButton addButton = new JButton("+ Add");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                AddPantry add = new AddPantry();
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
        addButton.setBounds(677, 42, 99, 50);
        panel.add(addButton);

        JScrollPane pantryScroll = new JScrollPane();
        pantryScroll.setBounds(26, 300, 346, 152);
        contentPane.add(pantryScroll);

        pantryTable = new JTable();
        pantryTable.setBackground(new Color(229, 217, 182));
        pantryTable.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 15));
        pantryTable.setForeground(new Color(40, 84, 48));
        pantryTable.setTableHeader(null);
        pantryScroll.setViewportView(pantryTable);

        JScrollPane expiredScroll = new JScrollPane();
        expiredScroll.setBounds(26, 127, 346, 87);
        contentPane.add(expiredScroll);

        expiredTable = new JTable();
        expiredTable.setBackground(new Color(229, 217, 182));
        expiredTable.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 15));
        expiredTable.setForeground(new Color(178, 34, 34));
        expiredScroll.setViewportView(expiredTable);

        JScrollPane expiringScroll = new JScrollPane();
        expiringScroll.setBounds(26, 214, 346, 87);
        contentPane.add(expiringScroll);

        expiringTable = new JTable();
        expiringScroll.setViewportView(expiringTable);
        expiringTable.setForeground(new Color(255, 69, 0));
        expiringTable.setTableHeader(null);
        expiringTable.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 15));
        expiringTable.setBackground(new Color(229, 217, 182));

        searchCombo = new JComboBox<String>();
        searchCombo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String query = "select food.name, pantry.food_amount, metric.metric_name, pantry.food_expiry"
                            + " from pantry"
                            + " join metric on metric.id=pantry.food_metric"
                            + " join food on food.id=pantry.food_name"
                            + " where food.name like ? order by pantry.food_expiry asc";
                    PreparedStatement pst = con.prepareStatement(query);
                    pst.setString(1, "%" + searchCombo.getSelectedItem() + "%");
                    ResultSet rs = pst.executeQuery();
                    searchTable.setModel(DbUtils.resultSetToTableModel(rs));
                    pst.close();
                    rs.close();
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        });
        searchCombo.setBounds(411, 191, 346, 30);
        contentPane.add(searchCombo);

        JScrollPane searchScroll = new JScrollPane();
        searchScroll.setBounds(411, 232, 346, 200);
        contentPane.add(searchScroll);

        searchTable = new JTable();
        searchTable.setBackground(new Color(229, 217, 182));
        searchTable.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 15));
        searchTable.setForeground(new Color(40, 84, 48));
        searchScroll.setViewportView(searchTable);

        JPanel searchPanel = new JPanel();
        searchPanel.setBackground(new Color(164, 190, 123));
        searchPanel.setBounds(397, 127, 379, 325);
        contentPane.add(searchPanel);
        searchPanel.setLayout(null);

        JLabel searchLabel = new JLabel("Search here:");
        searchLabel.setBounds(10, 21, 189, 31);
        searchPanel.add(searchLabel);
        searchLabel.setForeground(new Color(40, 84, 48));
        searchLabel.setFont(new Font("Arista 2.0", Font.BOLD, 30));

        fillComboBox();
        refreshTable();
    }
}
