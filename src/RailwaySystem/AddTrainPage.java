package RailwaySystem;

import javax.swing.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.*;
import java.sql.*;

public class AddTrainPage extends JFrame {
    Connection conn;
    JTextField trainNameField, sourceField, destinationField, fareField,trainidField,departureField;

    public AddTrainPage(String username, String password) {
        if (!"Sindhu".equalsIgnoreCase(UserSession.getUsername())) {
            JOptionPane.showMessageDialog(this, "Access denied. Admins only.");
            
            new HomePage().setVisible(true);
            return;
        }



        setTitle("ADD TRAIN");
        setSize(520, 480);
        setLocation(500,200);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        ImageIcon bgIcon = new ImageIcon("C:\\Users\\sindh\\B-TECH 2023-27\\IV SEMISTER\\DBMS LAB\\main3 copy1.png"); 
        Image bgImage = bgIcon.getImage().getScaledInstance(520, 480, Image.SCALE_SMOOTH);
        JLabel background = new JLabel(new ImageIcon(bgImage));
        background.setBounds(0, 20,520,480);
        setContentPane(background); 
       setVisible(true);
       
      

        JLabel titleLabel = new JLabel("Add New Train");
        titleLabel.setBounds(160, 0, 1500, 100);
        titleLabel.setFont(new Font("Bookman Old Style",Font.BOLD,20));
        titleLabel.setForeground(Color.black);
        add(titleLabel);
        
        JLabel idLabel = new JLabel("Train Id:");
        idLabel.setBounds(50, 70, 100, 60);
        idLabel.setFont(new Font("Lucida Calligraphy",Font.BOLD,15));
        add(idLabel);
        trainidField = new JTextField();
        trainidField.setBounds(180, 70, 200, 30);
        add(trainidField);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(50, 120, 100, 50);
        nameLabel.setFont(new Font("Lucida Calligraphy",Font.BOLD,15));
        add(nameLabel);
        trainNameField = new JTextField();
        trainNameField.setBounds(180, 120, 200, 30);
        add(trainNameField);

        JLabel sourceLabel = new JLabel("Source:");
        sourceLabel.setBounds(50, 170, 100, 50);
        sourceLabel.setFont(new Font("Lucida Calligraphy",Font.BOLD,15));
        add(sourceLabel);
        sourceField = new JTextField();
        sourceField.setBounds(180, 170, 200, 30);
        add(sourceField);

        JLabel destLabel = new JLabel("Destination:");
        destLabel.setBounds(20, 220, 1000, 50);
        destLabel.setFont(new Font("Lucida Calligraphy",Font.BOLD,15));
        add(destLabel);
        destinationField = new JTextField();
        destinationField.setBounds(180, 220, 200, 30);
        add(destinationField);
        
        JLabel departureLabel = new JLabel("Departure time:");
        departureLabel.setBounds(20, 270, 1000, 50);
        departureLabel.setFont(new Font("Lucida Calligraphy",Font.BOLD,15));
        add(departureLabel);
        departureField = new JTextField();
        departureField.setBounds(180, 270, 200, 30);
        add(departureField);

        JLabel fareLabel = new JLabel("Fare:");
        fareLabel.setBounds(50, 320, 100, 50);
        fareLabel.setFont(new Font("Lucida Calligraphy",Font.BOLD,15));
        add(fareLabel);
        fareField = new JTextField();
        fareField.setBounds(180, 320, 200, 30);
        add(fareField);

        JButton addButton = new JButton("Add Train");
        addButton.setBounds(70, 390, 120, 30);
        add(addButton);

        JButton resetButton = new JButton("Reset");
        resetButton.setBounds(200, 390, 100, 30);
        add(resetButton);

        JButton backButton = new JButton("Back to Menu");
        backButton.setBounds(310, 390, 140, 30);
        add(backButton);

        // Action: Add Train
        addButton.addActionListener(e -> insertTrain());

        // Action: Reset fields
        resetButton.addActionListener(e -> resetFields());

        // Action: Back to home page
        backButton.addActionListener(e -> {
         
            new HomePage().setVisible(true);
        });

        setVisible(true);
    }

    void insertTrain() {
        try (Connection conn = DatabaseConnection.getConnection()){
            String sql = "INSERT INTO trains (train_id,train_name, source, destination,departure_time, fare) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1,trainidField.getText().trim());   
            stmt.setString(2, trainNameField.getText().trim());
            stmt.setString(3, sourceField.getText().trim());
            stmt.setString(4, destinationField.getText().trim());
            stmt.setString(5, departureField.getText().trim());
            stmt.setInt(6, Integer.parseInt(fareField.getText().trim()));
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Train added successfully!");
            resetFields();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    void resetFields() {
    	trainidField.setText("");
        trainNameField.setText("");
        sourceField.setText("");
        destinationField.setText("");
        departureField.setText("");
        fareField.setText("");
    }

    public static void main(String[] args) {
        new AddTrainPage("Sindhu","admin");
    }
}