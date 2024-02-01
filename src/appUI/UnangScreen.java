package appUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class UnangScreen {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UnangScreen window = new UnangScreen();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	

	/**
	 * Create the application.
	 */
	public UnangScreen() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
    private void initialize() {
        frame = new JFrame();
        frame.getContentPane().setBackground(new Color(229, 217, 182));
        frame.setTitle("PantryMate");
        frame.setBounds(100, 100, 800, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel fridgeImage = new JLabel("");
        try {
            // Load the original image
            ImageIcon originalIcon = new ImageIcon("resources/fridge.png");
            // Get the original image from the ImageIcon
            Image originalImage = originalIcon.getImage();
            // Resize the image to the desired dimensions (e.g., 60x100)
            Image resizedImage = originalImage.getScaledInstance(60, 100, Image.SCALE_SMOOTH);
            // Create a new ImageIcon from the resized image
            ImageIcon resizedIcon = new ImageIcon(resizedImage);
            // Set the resized ImageIcon to the label
            fridgeImage.setIcon(resizedIcon);
        } catch (Exception e) {
            e.printStackTrace();
        }

        fridgeImage.setBounds(372, 60, 60, 100);
        frame.getContentPane().add(fridgeImage);
		
		JLabel title = new JLabel("Welcome to PantryMate!");
		title.setHorizontalTextPosition(SwingConstants.CENTER);
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setForeground(new Color(40, 84, 48));
		title.setFont(new Font("Arista 2.0", Font.BOLD, 40));
		title.setAlignmentX(0.5f);
		title.setBounds(145, 163, 468, 90);
		frame.getContentPane().add(title);
		
		JLabel lblYourDigitalPantry = new JLabel("Your Pantry Assistant!");
		lblYourDigitalPantry.setHorizontalTextPosition(SwingConstants.CENTER);
		lblYourDigitalPantry.setHorizontalAlignment(SwingConstants.CENTER);
		lblYourDigitalPantry.setForeground(new Color(40, 84, 48));
		lblYourDigitalPantry.setFont(new Font("Arista 2.0", Font.PLAIN, 25));
		lblYourDigitalPantry.setAlignmentX(0.5f);
		lblYourDigitalPantry.setBounds(145, 214, 468, 60);
		frame.getContentPane().add(lblYourDigitalPantry);
		
		JButton pantryButton = new JButton("Pantry");
		pantryButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				pantryScreen pantry = new pantryScreen();
				pantry.setVisible(true);
			}
		});
		pantryButton.setForeground(Color.WHITE);
		pantryButton.setFont(new Font("Arista 2.0", Font.BOLD, 25));
		pantryButton.setFocusable(false);
		pantryButton.setBorder(null);
		pantryButton.setBackground(new Color(40, 84, 48));
		pantryButton.setAlignmentX(0.5f);
		pantryButton.setBounds(237, 285, 287, 40);
		frame.getContentPane().add(pantryButton);
		
		JButton recipeButton = new JButton("Recipes");
		recipeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				RecipeScreen recipe = new RecipeScreen();
				recipe.setVisible(true);
			}
		});
		recipeButton.setForeground(Color.WHITE);
		recipeButton.setFont(new Font("Arista 2.0", Font.BOLD, 25));
		recipeButton.setFocusable(false);
		recipeButton.setBorder(null);
		recipeButton.setBackground(new Color(95, 141, 78));
		recipeButton.setAlignmentX(0.5f);
		recipeButton.setBounds(237, 336, 287, 40);
		frame.getContentPane().add(recipeButton);
		
		JButton solutionsButton = new JButton("Expired Food Solutions");
		solutionsButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				ExpiredFoodSolutionScreen memo = new ExpiredFoodSolutionScreen();
				memo.setVisible(true);
			}
		});
		solutionsButton.setForeground(Color.WHITE);
		solutionsButton.setFont(new Font("Arista 2.0", Font.BOLD, 25));
		solutionsButton.setFocusable(false);
		solutionsButton.setBorder(null);
		solutionsButton.setBackground(new Color(164, 190, 123));
		solutionsButton.setAlignmentX(0.5f);
		solutionsButton.setBounds(237, 387, 287, 40);
		frame.getContentPane().add(solutionsButton);
	}
}