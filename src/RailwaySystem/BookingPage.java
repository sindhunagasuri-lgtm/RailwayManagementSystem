package RailwaySystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;

public class BookingPage extends JFrame {
    Connection conn;
    JTextField usernameField, passengerNameField, nationalityField, ageField,sourceField, destinationField, journeyDateField, amountField, trainIdField;
    JComboBox<String> genderBox, preferenceBox, trainBox;
    int farePerPassenger = 0;
    long currentPNR;
    ArrayList<String> passengers = new ArrayList<>();
    ArrayList<String> genders = new ArrayList<>();
    ArrayList<String> nationalities = new ArrayList<>();
    ArrayList<String> ages = new ArrayList<>();
    ArrayList<String> seatNumbers = new ArrayList<>();

    public BookingPage() {
        setTitle("Booking Page");
        setSize(900, 500);
        setLocation(500, 150);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/railway", "root", "sindhuindu");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Database Connection Failed!");
            return;
        }

        ImageIcon bgIcon = new ImageIcon("C:\\Users\\sindh\\B-TECH 2023-27\\IV SEMISTER\\DBMS LAB\\main3 copy1.png"); 
        Image bgImage = bgIcon.getImage().getScaledInstance(900,500, Image.SCALE_SMOOTH);
        JLabel background = new JLabel(new ImageIcon(bgImage));
        background.setBounds(0, 0, 900, 500);
        setContentPane(background);

        setVisible(true);

        // Labels
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(50, 30, 120, 30);
        usernameLabel.setFont(new Font("Lucida Calligraphy", Font.BOLD, 15));
        usernameLabel.setForeground(Color.black);
        add(usernameLabel);

        JLabel passengerLabel = new JLabel("Name:");
        passengerLabel.setBounds(50, 70, 150, 30);
        passengerLabel.setFont(new Font("Lucida Calligraphy", Font.BOLD, 15));
        passengerLabel.setForeground(Color.black);
        add(passengerLabel);

        JLabel genderLabel = new JLabel("Gender:");
        genderLabel.setBounds(50, 110, 120, 30);
        genderLabel.setFont(new Font("Lucida Calligraphy", Font.BOLD, 15));
        genderLabel.setForeground(Color.black);
        add(genderLabel);

        JLabel ageLabel = new JLabel("Age:");
        ageLabel.setBounds(50, 150, 120, 30);
        ageLabel.setFont(new Font("Lucida Calligraphy", Font.BOLD, 15));
        ageLabel.setForeground(Color.black);
        add(ageLabel);
        
        JLabel nationalityLabel = new JLabel("Nationality:");
        nationalityLabel.setBounds(50, 190, 120, 30);
        nationalityLabel.setFont(new Font("Lucida Calligraphy", Font.BOLD, 15));
        nationalityLabel.setForeground(Color.black);
        add(nationalityLabel);

        JLabel preferenceLabel = new JLabel("Preference:");
        preferenceLabel.setBounds(50, 230, 120, 30);
        preferenceLabel.setFont(new Font("Lucida Calligraphy", Font.BOLD, 15));
        preferenceLabel.setForeground(Color.black);
        add(preferenceLabel);

        // Fields Left
        usernameField = new JTextField();
        usernameField.setBounds(180, 30, 150, 30);
        add(usernameField);

        passengerNameField = new JTextField();
        passengerNameField.setBounds(180, 70, 150, 30);
        add(passengerNameField);

        genderBox = new JComboBox<>(new String[]{"Male", "Female"});
        genderBox.setBounds(180, 110, 150, 30);
        add(genderBox);

        ageField = new JTextField();
        ageField.setBounds(180, 150, 150, 30);
        add(ageField);

        nationalityField = new JTextField();
        nationalityField.setBounds(180, 190, 150, 30);
        add(nationalityField);

        preferenceBox = new JComboBox<>(new String[]{"Upper", "Lower", "Middle", "Side"});
        preferenceBox.setBounds(180, 230, 150, 30);
        add(preferenceBox);

        // Labels Right
        JLabel trainLabel = new JLabel("Train Name:");
        trainLabel.setBounds(550, 30, 120, 30);
        trainLabel.setFont(new Font("Bookman Old Style", Font.BOLD, 15));
        trainLabel.setForeground(Color.black);
        add(trainLabel);

        JLabel trainIdLabel = new JLabel("Train ID:");
        trainIdLabel.setBounds(550, 70, 120, 30);
        trainIdLabel.setFont(new Font("Bookman Old Style", Font.BOLD, 15));
        trainIdLabel.setForeground(Color.black);
        add(trainIdLabel);

        JLabel sourceLabel = new JLabel("Source:");
        sourceLabel.setBounds(550, 110, 120, 30);
        sourceLabel.setFont(new Font("Bookman Old Style", Font.BOLD, 15));
        sourceLabel.setForeground(Color.black);
        add(sourceLabel);

        
        JLabel destLabel = new JLabel("Destination:");
        destLabel.setBounds(550, 150, 120, 30);
        destLabel.setFont(new Font("Bookman Old Style", Font.BOLD, 15));
        destLabel.setForeground(Color.black);
        add(destLabel);

        JLabel dateLabel = new JLabel("Journey Date:");
        dateLabel.setBounds(550, 190, 120, 30);
        dateLabel.setFont(new Font("Lucida Calligraphy", Font.BOLD, 15));
        dateLabel.setForeground(Color.BLACK);
        add(dateLabel);

        JLabel amountLabel = new JLabel("Amount:");
        amountLabel.setBounds(550, 230, 120, 30);
        amountLabel.setFont(new Font("Bookman Old Style", Font.BOLD, 15));
        amountLabel.setForeground(Color.black);
        add(amountLabel);

        // Fields Right
        trainBox = new JComboBox<>();
        trainBox.setBounds(680, 30, 150, 30);
        add(trainBox);
        loadTrainNames();

        trainIdField = new JTextField();
        trainIdField.setBounds(680, 70, 150, 30);
        trainIdField.setEditable(false);
        add(trainIdField);

        sourceField = new JTextField();
        sourceField.setBounds(680, 110, 150, 30);
        add(sourceField);
        
        JButton swapBtn = new JButton("⬆⬇");
        swapBtn.setBounds(450, 130, 50, 20);
        add(swapBtn);

        destinationField = new JTextField();
        destinationField.setBounds(680, 150, 150, 30);
        add(destinationField);

        journeyDateField = new JTextField();
        journeyDateField.setBounds(680, 190, 150, 30);
        add(journeyDateField);

        amountField = new JTextField();
        amountField.setBounds(680, 230, 150, 30);
        amountField.setEditable(false);
        add(amountField);

        trainBox.addActionListener(e -> fetchTrainDetails());

        JButton addPassengerBtn = new JButton("ADD PASSENGER");
        addPassengerBtn.setBounds(50, 300, 200, 30);
        add(addPassengerBtn);

        JButton bookBtn = new JButton("PROCEED TO PAYMENT");
        bookBtn.setBounds(300, 300, 200, 30);
        add(bookBtn);
        
        JButton viewPassengerBtn = new JButton("VIEW PASSENGERS");
        viewPassengerBtn.setBounds(540, 300, 200, 30);
        add(viewPassengerBtn);
        
        


        JButton backBtn = new JButton("Back to Menu");
        backBtn.setBounds(200, 350, 150, 30);
        backBtn.setBackground(Color.white);      
        backBtn.setForeground(Color.black);
        add(backBtn);

        JButton resetBtn = new JButton("Reset");
        resetBtn.setBounds(430, 350, 150, 30);
        resetBtn.setBackground(Color.white);      
        resetBtn.setForeground(Color.black);
        add(resetBtn);

        addPassengerBtn.addActionListener(e -> addPassenger());
        bookBtn.addActionListener(e -> proceedToPayment());
        backBtn.addActionListener(e -> {
            dispose();
            new HomePage().setVisible(true);
        });
        resetBtn.addActionListener(e -> clearFields());

        setVisible(true);
        viewPassengerBtn.addActionListener(e -> showPassengerTable());
        swapBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String temp = sourceField.getText();
                sourceField.setText(destinationField.getText());
                destinationField.setText(temp);
            }
        });

        setVisible(true);
    
    }
   
    
void showPassengerTable() {
    String[] columnNames = {"Name", "Gender", "Age", "Nationality"};
    Object[][] data = new Object[passengers.size()][4];

    for (int i = 0; i < passengers.size(); i++) {
        data[i][0] = passengers.get(i);
        data[i][1] = genders.get(i);
        data[i][2] = ages.get(i);
        data[i][3] = nationalities.get(i);
    }

    JTable table = new JTable(data, columnNames);
    table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

    JScrollPane scrollPane = new JScrollPane(table);
    scrollPane.setPreferredSize(new Dimension(600, 200));

    int option = JOptionPane.showConfirmDialog(
        this, scrollPane, "View Passenger Details", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE
    );

    if (option == JOptionPane.OK_OPTION) {
        // Save back the modified table data
        passengers.clear();
        genders.clear();
        ages.clear();
        nationalities.clear();
        for (int i = 0; i < table.getRowCount(); i++) {
            passengers.add(table.getValueAt(i, 0).toString());
            genders.add(table.getValueAt(i, 1).toString());
            ages.add(table.getValueAt(i, 2).toString());
            nationalities.add(table.getValueAt(i, 3).toString());
        }
        JOptionPane.showMessageDialog(this, "Passenger details viwed!!!");
    }
}

    void loadTrainNames() {
        try {
            PreparedStatement pst = conn.prepareStatement("SELECT train_name FROM trains");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                trainBox.addItem(rs.getString("train_name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void fetchTrainDetails() {
        try {
            String selectedTrain = (String) trainBox.getSelectedItem();
            PreparedStatement pst = conn.prepareStatement("SELECT train_id,source,destination, fare FROM trains WHERE train_name = ?");
            pst.setString(1, selectedTrain);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                trainIdField.setText(rs.getString("train_id"));
                sourceField.setText(rs.getString("source"));
                destinationField.setText(rs.getString("destination"));
                farePerPassenger = rs.getInt("fare");
                amountField.setText(String.valueOf(farePerPassenger));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
   
    void addPassenger() {
    	if (usernameField.getText().trim().isEmpty() || passengerNameField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill Username and Passenger Name.");
            return;
        }

        if (passengers.size() == 0) {
            currentPNR = (long) (Math.random() * 1_000_000_000L);
        }
        String seatNo = generateSeatNumber();

        passengers.add(passengerNameField.getText().trim());
        genders.add((String) genderBox.getSelectedItem());
        nationalities.add(nationalityField.getText().trim());
        ages.add(ageField.getText().trim());
        seatNumbers.add(seatNo);
        savePassenger();
        clearPassengerFields();
        JOptionPane.showMessageDialog(this, "Passenger Added Successfully!");
    }

    String generateSeatNumber() {
        int row = 1 + passengers.size();
        char seat = (char) ('A' + (passengers.size() % 6));
        return row + "" + seat;
    }

    void savePassenger() {
        try {
            String sql = "INSERT INTO bookings (username, passengername, gender, nationality, preference, trainname, train_id, source, destination, journeydate, fare, pnr, seatnumber,age) VALUES (?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, usernameField.getText().trim());
            pst.setString(2, passengerNameField.getText().trim());
            pst.setString(3, (String) genderBox.getSelectedItem());
            pst.setString(4, nationalityField.getText().trim());
            pst.setString(5, (String) preferenceBox.getSelectedItem());
            pst.setString(6, (String) trainBox.getSelectedItem());
            pst.setString(7, trainIdField.getText().trim());
            pst.setString(8, sourceField.getText().trim());
            pst.setString(9, destinationField.getText().trim());
            pst.setString(10, journeyDateField.getText().trim());
            pst.setInt(11, farePerPassenger);
            pst.setLong(12, currentPNR);
            pst.setString(13, generateSeatNumber());
            pst.setString(14, ageField.getText().trim());
            pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void proceedToPayment() {
        if (passengers.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please add at least one passenger!");
            return;
        }

        int totalAmount = passengers.size() * farePerPassenger;
        new PaymentPage(currentPNR, (String) trainBox.getSelectedItem(), sourceField.getText(), destinationField.getText(),journeyDateField.getText(), passengers, genders, nationalities,ages, seatNumbers, totalAmount).setVisible(true);
        dispose();
    }

    void clearFields() {
        passengerNameField.setText("");
        usernameField.setText("");
        trainIdField.setText("");
        genderBox.setSelectedIndex(0);
        nationalityField.setText("");
        ageField.setText("");
        preferenceBox.setSelectedIndex(0);
        sourceField.setText("");
        destinationField.setText("");
        journeyDateField.setText("YYYY-MM-DD");
        passengers.clear();
        seatNumbers.clear();
    }

    void clearPassengerFields() {
        passengerNameField.setText("");
        genderBox.setSelectedIndex(0);
        ageField.setText("");
        preferenceBox.setSelectedIndex(0);
    }

    public static void main(String[] args) {
        new BookingPage();
    }
}
