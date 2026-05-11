package RailwaySystem;

import javax.swing.*;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.*
; import java.sql.*;

public class DeleteTrainPage extends JFrame { JTextField trainIdField;

public DeleteTrainPage(String username, String password) {
	 if (!"Sindhu".equalsIgnoreCase(UserSession.getUsername())) {
         JOptionPane.showMessageDialog(this, "Access denied. Admins only.");
         
         new HomePage().setVisible(true);
         return;
     }

    setTitle("Delete Train");
    setSize(400, 250);
    setLayout(null);
    setLocation(500,200);
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    
    ImageIcon bgIcon = new ImageIcon("C:\\Users\\sindh\\B-TECH 2023-27\\IV SEMISTER\\DBMS LAB\\main3.png"); 
    Image bgImage = bgIcon.getImage().getScaledInstance(400,250, Image.SCALE_SMOOTH);
    JLabel background = new JLabel(new ImageIcon(bgImage));
    background.setBounds(0, 0,400,250);
    setContentPane(background); 
   setVisible(true);

    JLabel idLabel = new JLabel("Train ID:");
    idLabel.setBounds(50, 40, 100, 30);
    add(idLabel);

    trainIdField = new JTextField();
    trainIdField.setBounds(150, 40, 150, 30);
    add(trainIdField);

    JButton deleteButton = new JButton("Delete Train");
    deleteButton.setBounds(130, 90, 120, 30);
    deleteButton.setBackground(Color.black);      
    deleteButton.setForeground(Color.WHITE);
    add(deleteButton);
    JButton backButton = new JButton("Back to Menu");
    backButton.setBounds(50, 130, 120, 30);
    add(backButton);
   
    JButton resetButton = new JButton("Reset");
    resetButton.setBounds(200, 130, 120, 30);
    add(resetButton);

    
    backButton.addActionListener(e -> {
        dispose(); 
        new HomePage().setVisible(true); 
    });
    
    resetButton.addActionListener(e -> {
        trainIdField.setText("");
    });

    deleteButton.addActionListener(e -> deleteTrain());
}

private void deleteTrain() {
    String trainId = trainIdField.getText();

    try (Connection conn = DatabaseConnection.getConnection()) {
        String sql = "DELETE FROM trains WHERE train_id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, trainId);

        int result = ps.executeUpdate();
        if (result > 0) {
            JOptionPane.showMessageDialog(this, "Train deleted successfully!");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Train ID not found.");
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Database error.");
    }
}

public static void main(String[] args) {
    new DeleteTrainPage("Sindhu","admin").setVisible(true);
}

}