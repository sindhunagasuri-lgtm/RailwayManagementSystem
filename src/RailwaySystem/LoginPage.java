package RailwaySystem;

import javax.swing.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.*;
import java.sql.*;

public class LoginPage extends JFrame {
    JTextField usernameField;
    JPasswordField passwordField;

    public LoginPage() {
        setTitle("Login");
        setSize(400, 300);
        setLocation(500, 200);
        setLayout(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        ImageIcon bgIcon = new ImageIcon("C:\\Users\\sindh\\B-TECH 2023-27\\IV SEMISTER\\DBMS LAB\\main3 copy.png");
        Image bgImage = bgIcon.getImage().getScaledInstance(380, 250, Image.SCALE_SMOOTH);
        JLabel background = new JLabel(new ImageIcon(bgImage));
        background.setBounds(0, 0, 400, 300);
        setContentPane(background);
        setVisible(true);

        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(new Font("Lucida Calligraphy", Font.BOLD, 15));
        userLabel.setBounds(50, 50, 100, 30);
        userLabel.setForeground(Color.black);
        add(userLabel);

        usernameField = new JTextField();
        usernameField.setBounds(150, 50, 150, 30);
        add(usernameField);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(new Font("Lucida Calligraphy", Font.BOLD, 15));
        passLabel.setForeground(Color.black);
        passLabel.setBounds(50, 100, 100, 30);
      
        add(passLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(150, 100, 150, 30);
        add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(150, 160, 100, 30);
        loginButton.setBackground(Color.black);      
        loginButton.setForeground(Color.WHITE);
        add(loginButton);

        JButton backButton = new JButton("Back to Menu");
        backButton.setBounds(50, 200, 150, 30);
        add(backButton);

        JButton resetButton = new JButton("Reset");
        resetButton.setBounds(210, 200, 150, 30);
        add(resetButton);

        backButton.addActionListener(e -> {
            dispose();
            new HomePage().setVisible(true);
        });

        resetButton.addActionListener(e -> {
            usernameField.setText("");
            passwordField.setText("");
        });

        loginButton.addActionListener(e -> login());
    }

    private void login() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        
        if (username.equalsIgnoreCase("Sindhu") && password.equals("admin")) {
            UserSession.setUsername(username);
            JOptionPane.showMessageDialog(this, "Admin login successful!");
        
            new HomePage().setVisible(true);
            return;
        }

        try (Connection conn = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM users WHERE username=? AND password=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                UserSession.setUsername(username);
                JOptionPane.showMessageDialog(this, "Login successful!");
                dispose();
                new HomePage().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error");
        }
    }

    public static void main(String[] args) {
        new LoginPage().setVisible(true);
    }
}