package RailwaySystem;

import javax.swing.*; 
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ViewTrainPage extends JFrame { JTable trainTable; DefaultTableModel tableModel;

public ViewTrainPage() {
    setTitle("View Trains");
    setSize(700, 400);
    setLocation(500,200);
    setLayout(new BorderLayout());
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    
    JButton backButton = new JButton("Back to Menu");
    backButton.setBounds(260, 280, 150, 30);
    add(backButton);
    

    backButton.addActionListener(e -> {
        dispose(); 
        new HomePage().setVisible(true); 
    });
    

    String[] columns = {"Train ID", "Train Name", "Source", "Destination", "Departure Time","Fare"};
    tableModel = new DefaultTableModel(columns, 0);
    trainTable = new JTable(tableModel);

    JScrollPane scrollPane = new JScrollPane(trainTable);
    add(scrollPane, BorderLayout.CENTER);

    loadTrains();
}

private void loadTrains() {
	
	
    
    
    try (Connection conn = DatabaseConnection.getConnection()) {
        String sql = "SELECT * FROM trains";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            String id = rs.getString("train_id");
            String name = rs.getString("train_name");
            String source = rs.getString("source");
            String dest = rs.getString("destination");
            String time = rs.getString("departure_time");
            String fare = rs.getString("fare");
            
            tableModel.addRow(new Object[]{id, name, source, dest, time,fare});
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Database error.");
    }
}

public static void main(String[] args) {
    new ViewTrainPage().setVisible(true);
}

}