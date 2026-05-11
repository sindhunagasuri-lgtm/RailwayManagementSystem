package RailwaySystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class BookingHistory extends JFrame {

    JTextField usernameField;
    JTextArea resultArea;
    Connection conn;

    public BookingHistory() {
        setTitle("Booking History");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JLabel titleLabel = new JLabel("Booking History");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setBounds(200, 10, 200, 30);
        add(titleLabel);

        JLabel userLabel = new JLabel("Enter Username:");
        userLabel.setBounds(50, 60, 120, 30);
        add(userLabel);

        usernameField = new JTextField();
        usernameField.setBounds(180, 60, 150, 30);
        add(usernameField);

        JButton fetchButton = new JButton("Fetch History");
        fetchButton.setBounds(350, 60, 150, 30);
        add(fetchButton);

        resultArea = new JTextArea();
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setBounds(50, 110, 500, 300);
        add(scrollPane);

        JButton backBtn = new JButton("Back to Menu");
        backBtn.setBounds(200, 420, 150, 30);
        add(backBtn);

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/railway", "root", "sindhuindu");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "DB Connection Failed: " + e.getMessage());
        }

        fetchButton.addActionListener(e -> fetchHistory());

        backBtn.addActionListener(e -> {
            dispose();
            new HomePage().setVisible(true);
        });

        setVisible(true);
    }

    void fetchHistory() {
        String username = usernameField.getText().trim();
        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter a username.");
            return;
        }

        try {
            String sql = "SELECT passengername, trainname, source, destination, journeydate, pnr FROM bookings WHERE username = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            resultArea.setText("");
            boolean found = false;

            while (rs.next()) {
                found = true;
                resultArea.append(
                    "Passenger: " + rs.getString("passengername") + "\n" +
                    "Train: " + rs.getString("trainname") + "\n" +
                    "Source: " + rs.getString("source") + "\n" +
                    "Destination: " + rs.getString("destination") + "\n" +
                    "Date: " + rs.getString("journeydate") + "\n" +
                    "PNR: " + rs.getLong("pnr") + "\n\n"
                );
            }

            if (!found) {
                resultArea.setText("No booking history found for username: " + username);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error fetching history: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new BookingHistory();
    }
}