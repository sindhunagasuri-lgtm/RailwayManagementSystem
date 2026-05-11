package RailwaySystem;

import javax.swing.*;

import java.awt.Image;
import java.awt.event.*;
import java.sql.*;

public class SignUpPage extends JFrame { 
	JTextField usernameField, nameField, nationalityField, mobileField; JPasswordField passwordField; JComboBox<String> genderBox;

public SignUpPage() {
    setTitle("Sign Up");
    setSize(450, 400);
    setLocation(500,200);
    setLayout(null);
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    
    ImageIcon bgIcon = new ImageIcon("C:\\Users\\sindh\\B-TECH 2023-27\\IV SEMISTER\\DBMS LAB\\passenger.png"); 
    Image bgImage = bgIcon.getImage().getScaledInstance(450,400, Image.SCALE_SMOOTH);
    JLabel background = new JLabel(new ImageIcon(bgImage));
    background.setBounds(0, 0,450,400);
    setContentPane(background); 
   setVisible(true);
    
    JLabel userLabel = new JLabel("Username:");
    userLabel.setBounds(50, 30, 100, 30);
    add(userLabel);
    usernameField = new JTextField();
    usernameField.setBounds(160, 30, 200, 30);
    add(usernameField);

    JLabel passLabel = new JLabel("Password:");
    passLabel.setBounds(50, 70, 100, 30);
    add(passLabel);
    passwordField = new JPasswordField();
    passwordField.setBounds(160, 70, 200, 30);
    add(passwordField);

    JLabel nameLabel = new JLabel("Name:");
    nameLabel.setBounds(50, 110, 100, 30);
    add(nameLabel);
    nameField = new JTextField();
    nameField.setBounds(160, 110, 200, 30);
    add(nameField);

    JLabel genderLabel = new JLabel("Gender:");
    genderLabel.setBounds(50, 150, 100, 30);
    add(genderLabel);
    genderBox = new JComboBox<>(new String[]{"Male", "Female"});
    genderBox.setBounds(160, 150, 200, 30);
    add(genderBox);

    JLabel nationalityLabel = new JLabel("Nationality:");
    nationalityLabel.setBounds(50, 190, 100, 30);
    add(nationalityLabel);
    nationalityField = new JTextField();
    nationalityField.setBounds(160, 190, 200, 30);
    add(nationalityField);

    JLabel mobileLabel = new JLabel("Mobile:");
    mobileLabel.setBounds(50, 230, 100, 30);
    add(mobileLabel);
    mobileField = new JTextField();
    mobileField.setBounds(160, 230, 200, 30);
    add(mobileField);

    JButton signUpButton = new JButton("Sign Up");
    signUpButton.setBounds(160, 280, 100, 30);
    add(signUpButton);
    
 
    JButton backButton = new JButton("Back to Menu");
    backButton.setBounds(50, 320, 150, 30);
    add(backButton);

    
    JButton resetButton = new JButton("Reset");
    resetButton.setBounds(250, 320, 150, 30);
    add(resetButton);

    
    backButton.addActionListener(e -> {
        dispose(); 
        new HomePage().setVisible(true); 
    });
    
    resetButton.addActionListener(e -> {
        usernameField.setText("");
        passwordField.setText("");
        genderBox.setSelectedIndex(0);
        nationalityField.setText("");
        nameField.setText("");
        mobileField.setText("");
    });

    

    signUpButton.addActionListener(e -> registerUser());
}

private void registerUser() {
    String username = usernameField.getText();
    String password = new String(passwordField.getPassword());
    String name = nameField.getText();
    String gender = (String) genderBox.getSelectedItem();
    String nationality = nationalityField.getText();
    String mobile = mobileField.getText();

    try (Connection conn = DatabaseConnection.getConnection()) {
        String sql = "INSERT INTO users (username, password, name, gender, nationality, mobile) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, username);
        ps.setString(2, password);
        ps.setString(3, name);
        ps.setString(4, gender);
        ps.setString(5, nationality);
        ps.setString(6, mobile);

        int result = ps.executeUpdate();
        if (result > 0) {
            JOptionPane.showMessageDialog(this, "Registration Successful!");
        } else {
            JOptionPane.showMessageDialog(this, "Registration Failed");
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Database Error");
    }
}

public static void main(String[] args) {
    new SignUpPage().setVisible(true);
}

}