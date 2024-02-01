package appUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.proteanit.sql.DbUtils;

import javax.swing.JTable;
import javax.swing.JLabel;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;



public class PantryNotif extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTable expiringTable;
	private JTable expiredTable;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			PantryNotif dialog = new PantryNotif();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	
	Connection con=null;
	
	public PantryNotif() {
		con=Queries.dbconnect();
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JScrollPane expiringScroll = new JScrollPane();
		expiringScroll.setBounds(31, 84, 183, 116);
		contentPanel.add(expiringScroll);
		{
			expiringTable = new JTable();
			expiringTable.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 15));
			expiringTable.setBackground(new Color(229, 217, 182));
			expiringTable.setForeground(new Color(255, 69, 0));
			expiringTable.setTableHeader(null);
			expiringScroll.setViewportView(expiringTable);
			
			try {
				String query ="select food.name, pantry.food_expiry from pantry join food on food.id=pantry.food_name where pantry.food_expiry<\'" + java.time.LocalDate.now().plusDays(3) + "\' and "
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
		
		JScrollPane expiredScroll = new JScrollPane();
		expiredScroll.setBounds(224, 84, 178, 116);
		contentPanel.add(expiredScroll);
		{
			expiredTable = new JTable();
			expiredTable.setBackground(new Color(229, 217, 182));
			expiredTable.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 15));
			expiredTable.setForeground(new Color(178, 34, 34));
			expiredTable.setTableHeader(null);
			expiredScroll.setViewportView(expiredTable);
			
			try {
				String query ="select food.name, pantry.food_expiry from pantry join food on food.id=pantry.food_name where pantry.food_expiry<\'" + java.time.LocalDate.now() + "\'"
						+ " order by pantry.food_expiry asc";
				PreparedStatement pst = con.prepareStatement(query);
				ResultSet rs=pst.executeQuery();
				expiredTable.setModel(DbUtils.resultSetToTableModel(rs));
				pst.close();
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		setTitle("PantryMate");
		JLabel titleLabel = new JLabel("Caution!");
		titleLabel.setForeground(new Color(255, 0, 0));
		titleLabel.setFont(new Font("Arista 2.0", Font.BOLD, 18));
		titleLabel.setBounds(31, 11, 371, 43);
		contentPanel.add(titleLabel);
		
		JLabel subtitleLabel = new JLabel("Expiring:                                Expired:");
		subtitleLabel.setFont(new Font("Arista 2.0", Font.BOLD, 15));
		subtitleLabel.setBounds(31, 53, 371, 43);
		contentPanel.add(subtitleLabel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
