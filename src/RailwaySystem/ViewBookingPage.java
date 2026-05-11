package RailwaySystem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ViewBookingPage extends JFrame { 
	
	JTable bookingTable;
	DefaultTableModel tableModel;

public ViewBookingPage() {
    setTitle("View Bookings");
    setSize(800, 500);
    setLocation(450,150);
    setLayout(new BorderLayout());
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    
    
    JButton backButton = new JButton("Back to Menu");
    backButton.setBounds(260, 280, 150, 30);
    add(backButton);
    

    backButton.addActionListener(e -> {
        dispose(); 
        new HomePage().setVisible(true); 
    });

    String[] columns = {
    		"Booking ID", "Passenger Name", "Gender", "Nationality", "Preference", "Train Id", "Source", "Destination",
    		"Journey date", "PNR"};
    tableModel = new DefaultTableModel(columns, 0);
    bookingTable = new JTable(tableModel);

    JScrollPane scrollPane = new JScrollPane(bookingTable);
    add(scrollPane, BorderLayout.CENTER);

    loadBookings();
}

private void loadBookings() {
    try (Connection conn = DatabaseConnection.getConnection()) {
        String sql = "SELECT * FROM bookings";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            String id = rs.getString("id");
            String name = rs.getString("passengername");
            String gender = rs.getString("gender");
            String nationality = rs.getString("nationality");
            String preference = rs.getString("preference");
            String train = rs.getString("trainname");
            String source = rs.getString("source");
            String dest = rs.getString("destination");
            String date = rs.getString("journeydate");
            String pnr = rs.getString("pnr");


            tableModel.addRow(new Object[]{id, name, gender, nationality, preference, train, source, dest, date,pnr});
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Database error.");
    }
}

public static void main(String[] args) {
    new ViewBookingPage().setVisible(true);
}

}