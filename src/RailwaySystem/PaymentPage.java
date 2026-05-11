package RailwaySystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Random;
import java.awt.print.*;

public class PaymentPage extends JFrame {
    private JTextField cardNumberField, cardHolderField, upiIdField;
    private JLabel trainDetailsLabel, passengerDetailsLabel, amountLabel;
    private JComboBox<String> paymentModeComboBox;
    private JButton payButton, printButton;
    
    private String trainName, source, destination,journey;
    private ArrayList<String> passengerNames, genders, nationalities, ages, seatNumbers;
    private double fare;
    private long currentPNR;
    private String receiptMessage = "";

    public PaymentPage(long currentPNR, String trainName, String source, String destination,String journey,
                       ArrayList<String> passengerNames, ArrayList<String> genders,
                       ArrayList<String> nationalities, ArrayList<String> ages,
                       ArrayList<String> seatNumbers, double fare) {

        this.currentPNR = currentPNR;
        this.trainName = trainName;
        this.source = source;
        this.destination = destination;
        this.journey = journey;
        this.passengerNames = passengerNames;
        this.genders = genders;
        this.nationalities = nationalities;
        this.ages = ages;
        this.seatNumbers = seatNumbers;
        this.fare = fare;

        setTitle("Make Payment");
        setSize(600, 600);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        ImageIcon bgIcon = new ImageIcon("C:\\Users\\sindh\\B-TECH 2023-27\\IV SEMISTER\\DBMS LAB\\main3 copy1.png"); 
        Image bgImage = bgIcon.getImage().getScaledInstance(600,600, Image.SCALE_SMOOTH);
        JLabel background = new JLabel(new ImageIcon(bgImage));
        background.setBounds(0, 0, 600, 600);
        setContentPane(background);


        trainDetailsLabel = new JLabel("<html><b>Train:</b> " + trainName +"<br><b>From:</b> " + source + " <b>--->:</b> " + destination+ "</html>");
        trainDetailsLabel.setBounds(50, 85, 500, 30);
        trainDetailsLabel.setFont(new Font("Copperplate Gothic Light", Font.BOLD, 15));
        add(trainDetailsLabel);

        passengerDetailsLabel = new JLabel("<html><b>Passenger:</b> " + passengerNames.toString() + " <b>gender:</b>" + genders.toString() + 
                                           " \nages:" + ages.toString() + "<br><b>Nationality:</b> " + nationalities.toString() +
                                           "<br><b>Seat No:</b> " + seatNumbers.toString()+"<b>Journey date:</b>"+journey + "</html>");
        passengerDetailsLabel.setBounds(50, 120, 500, 70);
        passengerDetailsLabel.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 15));
        add(passengerDetailsLabel);

        amountLabel = new JLabel("<html><b>Fare Amount:</b> ₹ " + fare + "</html>");
        amountLabel.setBounds(50, 210, 300, 30);
        amountLabel.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 15));
        add(amountLabel);

        JLabel paymentModeLabel = new JLabel("Select Payment Mode:");
        paymentModeLabel.setBounds(50, 240, 200, 30);
        paymentModeLabel.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 15));
        add(paymentModeLabel);

        String[] paymentModes = {"Card", "UPI", "Cash"};
        paymentModeComboBox = new JComboBox<>(paymentModes);
        paymentModeComboBox.setBounds(250, 240, 200, 30);
        add(paymentModeComboBox);

        JLabel cardNumberLabel = new JLabel("Card Number:");
        cardNumberLabel.setBounds(50, 290, 150, 30);
        cardNumberLabel.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 15));
        add(cardNumberLabel);

        cardNumberField = new JTextField();
        cardNumberField.setBounds(250, 290, 200, 30);
        add(cardNumberField);

        JLabel cardHolderLabel = new JLabel("Card Holder Name:");
        cardHolderLabel.setBounds(50, 340, 150, 30);
       cardHolderLabel.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 15));
        add(cardHolderLabel);

        cardHolderField = new JTextField();
        cardHolderField.setBounds(250, 340, 200, 30);
        add(cardHolderField);

        JLabel upiIdLabel = new JLabel("UPI ID:");
        upiIdLabel.setBounds(50, 390, 150, 30);
        upiIdLabel.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 15));
        add(upiIdLabel);

        upiIdField = new JTextField();
        upiIdField.setBounds(250, 390, 200, 30);
        add(upiIdField);

        payButton = new JButton("Pay Now");
        payButton.setBounds(150, 460, 120, 30);
        add(payButton);

        printButton = new JButton("Print");
        printButton.setBounds(300, 460, 120, 30);
        printButton.setEnabled(false); 
        add(printButton);

        updatePaymentFields();

        paymentModeComboBox.addActionListener(e -> updatePaymentFields());
        payButton.addActionListener(e -> makePayment());
        printButton.addActionListener(e -> printReceipt());

        setVisible(true);
    }

    private void updatePaymentFields() {
        String mode = (String) paymentModeComboBox.getSelectedItem();
        boolean isCard = mode.equals("Card");
        boolean isUPI = mode.equals("UPI");

        cardNumberField.setVisible(isCard);
        cardHolderField.setVisible(isCard);
        upiIdField.setVisible(isUPI);
    }

    private void makePayment() {
        String paymentMode = (String) paymentModeComboBox.getSelectedItem();
        String paymentInfo = "";

        if (paymentMode.equals("Card")) {
            String cardNum = cardNumberField.getText().trim();
            String cardHolder = cardHolderField.getText().trim();
            if (cardNum.isEmpty() || cardHolder.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter Card Number and Card Holder Name!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            paymentInfo = "Card (**** " + cardNum.substring(cardNum.length() - 4) + ")";
        } else if (paymentMode.equals("UPI")) {
            String upiId = upiIdField.getText().trim();
            if (upiId.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter UPI ID!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            paymentInfo = "UPI (" + upiId + ")";
        } else {
            paymentInfo = "Cash";
        }

        String paymentId = "PAY" + (new Random().nextInt(9000) + 1000);

        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/railway", "root", "sindhuindu");
            for (int i = 0; i < passengerNames.size(); i++) {
                String query = "INSERT INTO payments (pnr, train_name, source, destination, passenger_name, gender, nationality, age, seat_no, amount, payment_mode, payment_id,journey) VALUES (?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement pst = con.prepareStatement(query);
                pst.setLong(1, currentPNR);
                pst.setString(2, trainName);
                pst.setString(3, source);
                pst.setString(4, destination);
                pst.setString(5, passengerNames.get(i));
                pst.setString(6, genders.get(i));
                pst.setString(7, nationalities.get(i));
                pst.setString(8, ages.get(i));
                pst.setString(9, seatNumbers.get(i));
                pst.setDouble(10, fare / passengerNames.size());
                pst.setString(11, paymentInfo);
                pst.setString(12, paymentId);
                pst.setString(13, journey);
                pst.executeUpdate();
            }
            con.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving payment to database", "DB Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Store receipt for print
        receiptMessage = "\n\t TRAIN TICKECT\t\n"+
                "✅ Payment Successful!\n\n" +
                "🎟Payment ID: " + paymentId + "\n" +
                "🚆Train: " + trainName + "\n" +
                "📍From: " + source + " ➡ To: " + destination + "\n" +
                "🧑Passenger: " + passengerNames + " \n"+
                "🧔🏻‍♀🧔🏻Gender:"+ genders + "\n" +
                "🪪Nationality: " + nationalities + "\n" +
                "🧑Age: " + ages + "\n" +
                "💺Seat No: " + seatNumbers + "\n" +
                "🗓Journey Date:"+journey+"\n"+
                "💳 Payment Mode: " + paymentInfo + "\n" +
                "💵 Paid Amount: ₹ " + fare + "\n\n" +
                "\t🎉🎉 Thank you for booking with us! ✨\n";

        JOptionPane.showMessageDialog(this, receiptMessage, "Payment Success", JOptionPane.INFORMATION_MESSAGE);
        printButton.setEnabled(true);
    }

    private void printReceipt() {
        JTextArea printArea = new JTextArea(receiptMessage);
        try {
            printArea.print();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Printing failed: " + e.getMessage(), "Print Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}