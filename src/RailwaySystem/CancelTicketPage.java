package RailwaySystem;

import javax.swing.*;
import java.sql.*;

public class CancelTicketPage extends JFrame { JTextField pnrField, passengerNameField; JButton cancelButton;

public CancelTicketPage() {
    setTitle("Cancel Ticket");
    setSize(400, 300);
    setLocation(500,200);
    setLayout(null);
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);

    JLabel pnrLabel = new JLabel("Enter PNR Number:");
    pnrLabel.setBounds(30, 30, 150, 30);
    add(pnrLabel);
    pnrField = new JTextField();
    pnrField.setBounds(180, 30, 150, 30);
    add(pnrField);

    JLabel nameLabel = new JLabel("Passenger Name:");
    nameLabel.setBounds(30, 70, 150, 30);
    add(nameLabel);
    passengerNameField = new JTextField();
    passengerNameField.setBounds(180, 70, 150, 30);
    add(passengerNameField);

    cancelButton = new JButton("Cancel Ticket");
    cancelButton.setBounds(120, 120, 150, 30);
    add(cancelButton);
       
    JButton backButton = new JButton("Back to Menu");
    backButton.setBounds(50, 180, 120, 30);
    add(backButton);
   
    JButton resetButton = new JButton("Reset");
    resetButton.setBounds(200, 180, 120, 30);
    add(resetButton);

    
    backButton.addActionListener(e -> {
        dispose(); 
        new HomePage().setVisible(true); 
    });
    
    resetButton.addActionListener(e -> {
        pnrField.setText("");
        passengerNameField.setText("");
    });


    cancelButton.addActionListener(e -> cancelTicket());
}

private void cancelTicket() {
    String pnr = pnrField.getText();
    String passengerName = passengerNameField.getText();
    Connection conn;

    try {
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/railway", "root", "sindhuindu");
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Database Connection Failed!");
        return;
    }
    
    try {
        String sql = "DELETE FROM bookings WHERE pnr = ? AND passengername = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, pnr);
        ps.setString(2, passengerName);

        int result = ps.executeUpdate();
        if (result > 0) {
        	
            JOptionPane.showMessageDialog(this, "Your ticket cancelled successfully!\n\nAmount will be refunded into your account....\n");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Ticket cancellation failed.");
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Database error.");
    }
}

public static void main(String[] args) {
    new CancelTicketPage().setVisible(true);
}

}