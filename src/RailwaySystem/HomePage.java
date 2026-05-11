package RailwaySystem;

import javax.swing.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.*;

public class HomePage extends JFrame {

    public HomePage() {
        setTitle("Railway Management System");
        setSize(1600,800);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        
        ImageIcon bgIcon = new ImageIcon(); 
        Image bgImage = bgIcon.getImage().getScaledInstance(1600, 800, Image.SCALE_SMOOTH);
        JLabel background = new JLabel(new ImageIcon(bgImage));
        background.setBounds(0, 0, 1500, 1000);
        setContentPane(background); 
       setVisible(true);
       
       JLabel title = new JLabel("...Railway reservation...");
       title.setBounds(400, 300, 1200, 150);
       title.setFont(new Font("French Script MT", Font.BOLD, 100));
       title.setForeground(Color.black);
       add(title);


        JMenuBar menuBar = new JMenuBar();
        JMenu userMenu = new JMenu("User");
        JMenu trainMenu = new JMenu("Train");
        JMenu bookingMenu = new JMenu("Booking");

        JMenuItem loginItem = new JMenuItem("Login");
        JMenuItem signupItem = new JMenuItem("Sign Up");
        JMenuItem addTrainItem = new JMenuItem("Add Train");
        JMenuItem deleteTrainItem = new JMenuItem("Delete Train");
        JMenuItem viewTrainItem = new JMenuItem("View Trains");
        JMenuItem bookItem = new JMenuItem("Book Ticket");
        JMenuItem viewBookingItem = new JMenuItem("View Booking");
        JMenuItem cancelTicketItem = new JMenuItem("Cancel Ticket");
        JMenuItem BookingHistoryItem = new JMenuItem("Booking history");

        userMenu.add(loginItem);
        userMenu.add(signupItem);
        trainMenu.add(addTrainItem);
        trainMenu.add(deleteTrainItem);
        trainMenu.add(viewTrainItem);
        bookingMenu.add(bookItem);
        bookingMenu.add(viewBookingItem);
        bookingMenu.add(cancelTicketItem);
        bookingMenu.add(BookingHistoryItem);

        menuBar.add(userMenu);
        menuBar.add(trainMenu);
        menuBar.add(bookingMenu);
        setJMenuBar(menuBar);

        
        loginItem.addActionListener(e -> new LoginPage().setVisible(true));
        signupItem.addActionListener(e -> new SignUpPage().setVisible(true));
        addTrainItem.addActionListener(e -> new AddTrainPage("Sindhu", "admin").setVisible(true));
        deleteTrainItem.addActionListener(e -> new DeleteTrainPage("Sindhu","admin").setVisible(true));
        viewTrainItem.addActionListener(e -> new ViewTrainPage().setVisible(true));
        bookItem.addActionListener(e -> new BookingPage().setVisible(true));
        viewBookingItem.addActionListener(e -> new ViewBookingPage().setVisible(true));
        cancelTicketItem.addActionListener(e -> new CancelTicketPage().setVisible(true));
        BookingHistoryItem.addActionListener(e -> new  BookingHistory().setVisible(true));
        

       
    }

    public static void main(String[] args) {
        new HomePage().setVisible(true);
    }
}