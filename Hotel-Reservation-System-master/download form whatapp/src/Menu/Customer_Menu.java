package Menu;

import Database.MySQL_db;
import Hotel.*;
import Payment.Bank_Payment;
import Payment.Cash_Payment;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Customer_Menu implements ActionListener {
    MySQL_db db = new MySQL_db();
    User global_customer;
    double price = 0;

    private JFrame f_customer_menu = new JFrame("Customer Menu");
    private JFrame f_hotel_reserve_menu = new JFrame("Hotel Reservation");
    private JFrame f_cancel_reserve_menu = new JFrame("Cancel Reservation");

    public void Show_CustomerMenu(User customer){
        // Initiate
        global_customer = customer;
        JPanel p_user_selection = new JPanel();
        JLabel l_title = new JLabel("Customer Menu", SwingConstants.CENTER);
        JButton b_reservation = new JButton("Reserve Room");
        JButton b_reservation_cancel = new JButton("Cancel Reservation");
        JButton b_do_payment = new JButton("Payment");
        JButton b_log_out = new JButton("Log Out");

        // Font setting
        l_title.setFont(new Font("Poppins", Font.BOLD, 18));
        b_reservation.setFont(new Font("Poppins", Font.BOLD, 14));
        b_reservation_cancel.setFont(new Font("Poppins", Font.BOLD, 14));
        b_log_out.setFont(new Font("Poppins", Font.BOLD, 14));

        // Customer menu frame
        f_customer_menu.setLocationRelativeTo(null);
        f_customer_menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f_customer_menu.setVisible(true);
        f_customer_menu.setSize(400,300);
        f_customer_menu.add(l_title, BorderLayout.NORTH);
        f_customer_menu.add(p_user_selection, BorderLayout.CENTER);

        // Panel setting
        p_user_selection.setLayout(new GridLayout(6, 1));
        p_user_selection.setBorder(BorderFactory.createEmptyBorder(15, 50, 0, 50));
        p_user_selection.setBackground(Color.lightGray);
        p_user_selection.add(b_reservation);
        p_user_selection.add(new JLabel(""));
        p_user_selection.add(b_reservation_cancel);
        p_user_selection.add(new JLabel(""));
        p_user_selection.add(b_log_out);
        p_user_selection.add(new JLabel(""));

        // Button event setting
        b_reservation.addActionListener(this);
        b_reservation.setActionCommand("Reserve Hotel Menu");
        b_reservation_cancel.addActionListener(this);
        b_reservation_cancel.setActionCommand("Cancel Reserve Menu");
        b_log_out.addActionListener(this);
        b_log_out.setActionCommand("Log Out");
    }

    public void Show_hotel_menu(){
        // Get Data from MySQL
        Hotel hotel = new Hotel();

        // Get Hotel Details
        try{
            db.prepareStatement("SELECT * FROM hotel");
            db.QueryResult();

            while(db.result.next()){
                hotel.AddHotelId(db.result.getInt("hotel_id"));
                hotel.AddHotel(db.result.getString("hotel_name"));
            }
        } catch (SQLException e){
            System.out.println("There's no hotel data in the Database!");
        }

        // Initiate
        String[] room_type = {"--Select Room Type--", "Standard Room", "Deluxe Room"};
        String[] payment_method = {"--Select Payment Method--", "Cash", "Bank"};
        JPanel p_title = new JPanel();
        JPanel p_user_selection = new JPanel();
        JPanel p_button = new JPanel();
        JPanel p_receipt = new JPanel();
        JPanel p_receipt_info = new JPanel();
        JLabel l_title = new JLabel("Hotel Reservation", SwingConstants.CENTER);
        JLabel l_user_info = new JLabel("Username: " + global_customer.name);
        JLabel l_select_hotel = new JLabel("Select hotel:");
        JLabel l_select_room_type = new JLabel("Select room type:");
        JLabel l_select_room_number = new JLabel("Select room number:");
        JLabel l_select_checkInDate = new JLabel("Check In from (YYYY-MM-DD):");
        JLabel l_select_checkOutDate = new JLabel("Check Out at (YYYY-MM-DD):");
        JLabel l_select_paymentMethod = new JLabel("Payment Method:");
        JLabel l_receipt_tittle = new JLabel("------Receipt------", SwingConstants.CENTER);
        JTextField tf_checkInDate = new JTextField(10);
        JTextField tf_checkOutDate = new JTextField(10);
        JComboBox<String> hotel_list = new JComboBox<>(hotel.GetAvailableHotels().toArray(new String[0]));
        JComboBox<String> room_type_list = new JComboBox<>(room_type);
        JComboBox<Room> room_number_list = new JComboBox<>(hotel.GetAvailableRooms().toArray(new Room[0]));
        JComboBox<String> payment_method_list = new JComboBox<>(payment_method);
        JButton b_back = new JButton("Back");
        JButton b_confirm = new JButton("Confirm");
        tf_checkInDate.setEnabled(false);
        tf_checkOutDate.setEnabled(false);
        room_type_list.setEnabled(false);
        room_number_list.setEnabled(false);
        payment_method_list.setEnabled(false);
        b_confirm.setEnabled(false);

        // Font setting
        l_title.setFont(new Font("Poppins", Font.BOLD, 18));
        l_user_info.setFont(new Font("Poppins", Font.BOLD, 18));
        l_receipt_tittle.setFont(new Font("Poppins", Font.BOLD, 18));

        // Frame setting
        f_hotel_reserve_menu.setLocationRelativeTo(null);
        f_hotel_reserve_menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f_hotel_reserve_menu.setVisible(true);
        f_hotel_reserve_menu.setSize(600,400);
        f_hotel_reserve_menu.add(p_title, BorderLayout.NORTH);
        f_hotel_reserve_menu.add(p_user_selection, BorderLayout.CENTER);
        f_hotel_reserve_menu.add(p_button, BorderLayout.SOUTH);
        f_hotel_reserve_menu.add(p_receipt, BorderLayout.EAST);

        // Panel setting
        p_title.setLayout(new GridLayout(2, 1));
        p_title.add(l_title);
        p_title.add(l_user_info);

        p_user_selection.setBorder(BorderFactory.createEmptyBorder(15,25,15,25));
        p_user_selection.setLayout(new GridLayout(12,2));
        p_user_selection.add(l_select_hotel);
        p_user_selection.add(hotel_list);
        p_user_selection.add(new JLabel(""));
        p_user_selection.add(new JLabel(""));
        p_user_selection.add(l_select_room_type);
        p_user_selection.add(room_type_list);
        p_user_selection.add(new JLabel(""));
        p_user_selection.add(new JLabel(""));
        p_user_selection.add(l_select_room_number);
        p_user_selection.add(room_number_list);
        p_user_selection.add(new JLabel(""));
        p_user_selection.add(new JLabel(""));
        p_user_selection.add(l_select_checkInDate);
        p_user_selection.add(tf_checkInDate);
        p_user_selection.add(new JLabel(""));
        p_user_selection.add(new JLabel(""));
        p_user_selection.add(l_select_checkOutDate);
        p_user_selection.add(tf_checkOutDate);
        p_user_selection.add(new JLabel(""));
        p_user_selection.add(new JLabel(""));
        p_user_selection.add(l_select_paymentMethod);
        p_user_selection.add(payment_method_list);

        p_button.setBorder(BorderFactory.createEmptyBorder(15,50,15,50));
        p_button.add(b_back);
        p_button.add(b_confirm);

        p_receipt.setBorder(BorderFactory.createEmptyBorder(0,0,0,25));
        p_receipt.setLayout(new BorderLayout());
        p_receipt.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        p_receipt.add(l_receipt_tittle, BorderLayout.NORTH);
        p_receipt.add(p_receipt_info, BorderLayout.CENTER);

        p_receipt_info.setBorder(BorderFactory.createEmptyBorder(15,10,15,10));
        p_receipt_info.setLayout(new GridLayout(6,1));
        p_receipt_info.add(new JLabel("Room Number: "));
        p_receipt_info.add(new JLabel(room_number_list.getSelectedItem().toString()));
        p_receipt_info.add(new JLabel("Price: "));
        p_receipt_info.add(new JLabel(String.valueOf(price)));

        // Button Event
        b_back.addActionListener(this);
        b_back.setActionCommand("Back from Hotel Reserve");
        b_confirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(actionEvent.getActionCommand().equals("Confirm")){
                    Reservation reservation = new Reservation(global_customer
                    , hotel.GetRoom(room_number_list.getSelectedIndex()));

                    if(!reservation.check_valid_date(tf_checkInDate.getText(), tf_checkOutDate.getText())){
                        JOptionPane.showMessageDialog(f_hotel_reserve_menu, "Please key in Correct Date Format!"
                        , "Wrong Date Format",JOptionPane.ERROR_MESSAGE);
                    } else {
                        if(payment_method_list.getSelectedItem().equals("Cash")){
                            Cash_Payment cash_payment = new Cash_Payment(
                                    global_customer.user_id,
                                    hotel.GetRoom(room_number_list.getSelectedIndex()).GetRoomId(),
                                    hotel.GetRoom(room_number_list.getSelectedIndex()).GetPrice());

                            if(reservation.Make_Reservation(cash_payment)){
                                JOptionPane.showMessageDialog(f_hotel_reserve_menu, "Reserved Successfully!"
                                        , "Reserve Success", JOptionPane.INFORMATION_MESSAGE);

                                f_hotel_reserve_menu.dispose();
                                Customer_Menu customer_menu = new Customer_Menu();
                                customer_menu.Show_CustomerMenu(global_customer);
                            }else {
                                JOptionPane.showMessageDialog(f_hotel_reserve_menu, "Error occured!"
                                        , "Unknown Error",JOptionPane.ERROR_MESSAGE);
                            }
                        } else if (payment_method_list.getSelectedItem().equals("Bank")){
                            Bank_Payment back_payment = new Bank_Payment(
                                    global_customer.user_id,
                                    hotel.GetRoom(room_number_list.getSelectedIndex()).GetRoomId(),
                                    hotel.GetRoom(room_number_list.getSelectedIndex()).GetPrice());

                            if(reservation.Make_Reservation(back_payment)){
                                JOptionPane.showMessageDialog(f_hotel_reserve_menu, "Reserved Successfully!"
                                        , "Reserve Success", JOptionPane.INFORMATION_MESSAGE);

                                f_hotel_reserve_menu.dispose();
                                Customer_Menu customer_menu = new Customer_Menu();
                                customer_menu.Show_CustomerMenu(global_customer);
                            }else {
                                JOptionPane.showMessageDialog(f_hotel_reserve_menu, "Error occured!"
                                        , "Unknown Error",JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                }
            }
        });

        // Item Event
        hotel_list.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent itemEvent) {
                if (itemEvent.getStateChange() == ItemEvent.SELECTED) {
                    room_type_list.setEnabled(hotel_list.getSelectedIndex() != 0);
                    room_type_list.setSelectedIndex(0);
                    room_number_list.setSelectedIndex(0);
                    payment_method_list.setSelectedIndex(0);
                }
            }
        });

        room_type_list.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent itemEvent) {
                if (itemEvent.getStateChange() == ItemEvent.SELECTED) {
                    room_number_list.setEnabled(room_type_list.getSelectedIndex() != 0);

                    if(room_type_list.getSelectedIndex() != 0){
                        try{
                            room_number_list.removeAllItems();
                            room_number_list.addItem(hotel.GetRoom(0));
                            hotel.ClearRoom();
                            if(room_type_list.getSelectedItem() == "Standard Room"){
                                db.prepareStatement("SELECT * FROM room WHERE hotel_id = ? " +
                                        "AND room_type = 'Standard' AND status = 'Vacant'");
                                db.statement.setInt(1, hotel.GetHotelId(hotel_list.getSelectedIndex()));
                                db.QueryResult();

                                while(db.result.next()){
                                    Room standard_room = new StandardRoom(db.result.getInt("room_id")
                                            , db.result.getString("room_number"), db.result.getDouble("price"));
                                    hotel.AddRoom(standard_room);
                                    room_number_list.addItem(standard_room);
                                }
                            } else if (room_type_list.getSelectedItem() == "Deluxe Room") {
                                db.prepareStatement("SELECT * FROM room WHERE hotel_id = ? " +
                                        "AND room_type = 'Deluxe' AND status = 'Vacant'");
                                db.statement.setInt(1, hotel.GetHotelId(hotel_list.getSelectedIndex()));
                                db.QueryResult();

                                while(db.result.next()){
                                    Room deluxe_room = new DeluxeRoom(db.result.getInt("room_id")
                                            , db.result.getString("room_number"), db.result.getDouble("price"));
                                    hotel.AddRoom(deluxe_room);
                                    room_number_list.addItem(deluxe_room);
                                }
                            }
                        } catch (SQLException e){
                            System.out.println("There's no room data in the Hotel!");
                        }
                    }else {
                        price = 0;
                        hotel.ClearRoom();
                        room_number_list.removeAllItems();
                        room_number_list.addItem(hotel.GetRoom(0));
                        room_number_list.setSelectedIndex(0);
                        p_receipt_info.removeAll();
                        p_receipt_info.add(new JLabel("Room Number: "));
                        p_receipt_info.add(new JLabel(room_number_list.getSelectedItem().toString()));
                        p_receipt_info.add(new JLabel("Price: "));
                        p_receipt_info.add(new JLabel(String.valueOf(price)));
                    }
                }
            }
        });

        room_number_list.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent itemEvent) {
                if (itemEvent.getStateChange() == ItemEvent.SELECTED) {
                    tf_checkInDate.setEnabled(room_number_list.getSelectedIndex() != 0);
                    tf_checkOutDate.setEnabled(room_number_list.getSelectedIndex() != 0);
                    payment_method_list.setEnabled(room_number_list.getSelectedIndex() != 0);

                    if(room_number_list.getSelectedIndex()!= 0){
                        price = hotel.GetRoom(room_number_list.getSelectedIndex()).GetPrice();
                        p_receipt_info.removeAll();
                        p_receipt_info.add(new JLabel("Room Number: "));
                        p_receipt_info.add(new JLabel(room_number_list.getSelectedItem().toString()));
                        p_receipt_info.add(new JLabel("Price: "));
                        p_receipt_info.add(new JLabel(String.valueOf(price)));
                        p_receipt_info.revalidate();
                        p_receipt_info.repaint();
                    }
                    else{
                        payment_method_list.setSelectedIndex(0);
                        price = 0;
                        p_receipt_info.removeAll();
                        p_receipt_info.add(new JLabel("Room Number: "));
                        p_receipt_info.add(new JLabel(room_number_list.getSelectedItem().toString()));
                        p_receipt_info.add(new JLabel("Price: "));
                        p_receipt_info.add(new JLabel(String.valueOf(price)));
                        p_receipt_info.revalidate();
                        p_receipt_info.repaint();
                    }
                }
            }
        });

        payment_method_list.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent itemEvent) {
                if(itemEvent.getStateChange() == ItemEvent.SELECTED){
                    b_confirm.setEnabled(payment_method_list.getSelectedIndex() != 0);
                }
            }
        });
    }

    public void Show_cancel_menu(){
        Hotel hotel = new Hotel();
        List<Integer> room_id = new ArrayList<>();

        JPanel p_title = new JPanel();
        JPanel p_user_selection = new JPanel();
        JPanel p_button = new JPanel();
        JLabel l_title = new JLabel("Cancel Reservation");
        JLabel l_select_reserved_room_number = new JLabel("Select reserved room number:");
        JLabel l_amount = new JLabel("Amount:");
        JLabel l_amount_info = new JLabel();
        JLabel l_checkInDate = new JLabel("Check In from (YYYY-MM-DD):");
        JLabel l_checkInDate_info = new JLabel("");
        JLabel l_checkOutDate = new JLabel("Check Out at (YYYY-MM-DD):");
        JLabel l_checkOutDate_info = new JLabel("");
        JButton b_back = new JButton("Back");
        JButton b_confirm = new JButton("Confirm");
        JComboBox<Room> room_number_list = new JComboBox<>(hotel.GetAvailableRooms().toArray(new Room[0]));
        b_confirm.setEnabled(false);

        // Get data from MySQL
        try{
            db.prepareStatement("SELECT * FROM reservation WHERE user_id = ?");
            db.statement.setInt(1, global_customer.user_id);
            db.QueryResult();

            while(db.result.next()){
                room_id.add(db.result.getInt("room_id"));
            }

            room_number_list.removeAllItems();
            room_number_list.addItem(hotel.GetRoom(0));
            hotel.ClearRoom();

            for(int i = 0; i < room_id.size(); i++) {
                db.prepareStatement("SELECT * FROM room WHERE room_id = ?");
                db.statement.setInt(1, room_id.get(i));
                db.QueryResult();
                db.result.next();
                Room room = new StandardRoom(room_id.get(i), db.result.getString("room_number")
                        , db.result.getDouble("price"));
                hotel.AddRoom(room);
                room_number_list.addItem(room);
            }
        }catch (SQLException e){
            System.out.println("There's something error while retrieving data from room table!");
        }

        // Font Setting
        l_title.setFont(new Font("Poppins", Font.BOLD, 18));

        // Frame setting
        f_cancel_reserve_menu.setLocationRelativeTo(null);
        f_cancel_reserve_menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f_cancel_reserve_menu.setVisible(true);
        f_cancel_reserve_menu.setSize(600, 400);
        f_cancel_reserve_menu.add(p_title, BorderLayout.NORTH);
        f_cancel_reserve_menu.add(p_user_selection, BorderLayout.CENTER);
        f_cancel_reserve_menu.add(p_button, BorderLayout.SOUTH);

        // Panel Setting
        p_title.add(l_title);

        p_user_selection.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK),
                BorderFactory.createEmptyBorder(20, 100, 20 ,100)
        ));
        p_user_selection.setLayout(new GridLayout(7, 2));
        p_user_selection.add(l_select_reserved_room_number);
        p_user_selection.add(room_number_list);
        p_user_selection.add(new JLabel(""));
        p_user_selection.add(new JLabel(""));
        p_user_selection.add(l_amount);
        p_user_selection.add(l_amount_info);
        p_user_selection.add(new JLabel(""));
        p_user_selection.add(new JLabel(""));
        p_user_selection.add(l_checkInDate);
        p_user_selection.add(l_checkInDate_info);
        p_user_selection.add(new JLabel(""));
        p_user_selection.add(new JLabel(""));
        p_user_selection.add(l_checkOutDate);
        p_user_selection.add(l_checkOutDate_info);

        p_button.setBorder(BorderFactory.createEmptyBorder(30,0,30,0));
        p_button.add(b_back);
        p_button.add(b_confirm);

        // Button Event
        b_back.addActionListener(this);
        b_back.setActionCommand("Back from Cancel Reserve");
        b_confirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(actionEvent.getActionCommand().equals("Confirm")){
                    try {
                        db.prepareStatement("SELECT * FROM reservation WHERE user_id = ? AND room_id = ?");
                        db.statement.setInt(1, global_customer.user_id);
                        db.statement.setInt(2, hotel.GetRoom(room_number_list.getSelectedIndex()).GetRoomId());
                        db.QueryResult();
                        db.result.next();
                        Reservation reservation = new Reservation(global_customer,
                                hotel.GetRoom(room_number_list.getSelectedIndex()),
                                db.result.getString("checkIn_date"), db.result.getString("checkOut_date"));

                        reservation.Cancel_Reservation();

                        JOptionPane.showMessageDialog(f_cancel_reserve_menu, "Cancel Reservation Successfully!",
                                "Cancel Reservation Success", JOptionPane.INFORMATION_MESSAGE);

                        // Back to Customer Menu
                        f_cancel_reserve_menu.dispose();
                        f_cancel_reserve_menu = new JFrame("Cancel Reservation");
                        price = 0;
                        Show_CustomerMenu(global_customer);
                    } catch (SQLException e){
                        JOptionPane.showMessageDialog(f_cancel_reserve_menu, "Failed to Cancel Reservation!",
                                "Failed to Cancel Reservation Success", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        // ItemList Event
        room_number_list.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent itemEvent) {
                if(itemEvent.getStateChange() == ItemEvent.SELECTED){
                    b_confirm.setEnabled(room_number_list.getSelectedIndex() != 0);

                    if(room_number_list.getSelectedIndex()!= 0){
                        // Get date data
                        try {
                            l_amount_info.setText(
                                    Double.toString(hotel.GetRoom(room_number_list.getSelectedIndex()).GetPrice()));

                            db.prepareStatement("SELECT * FROM reservation WHERE user_id = ? AND room_id = ?");
                            db.statement.setInt(1, global_customer.user_id);
                            db.statement.setInt(2, hotel.GetRoom(room_number_list.getSelectedIndex()).GetRoomId());
                            db.QueryResult();
                            db.result.next();
                            l_checkInDate_info.setText(db.result.getString("checkIn_date"));
                            l_checkOutDate_info.setText(db.result.getString("checkOut_date"));
                        } catch (SQLException e){
                            System.out.println("Something wrong when retrieving data from reservation table!");
                        }
                    }
                    else {
                        l_amount_info.setText("");
                        l_checkInDate_info.setText("");
                        l_checkOutDate_info.setText("");
                    }
                }
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent event){
        String command = event.getActionCommand();

        switch (command){
            case "Reserve Hotel Menu":
                f_customer_menu.dispose();
                Show_hotel_menu();
                break;

            case "Cancel Reserve Menu":
                f_customer_menu.dispose();
                Show_cancel_menu();
                break;

            case "Log Out":
                f_customer_menu.dispose();
                Main_Menu main_menu = new Main_Menu();
                main_menu.show();
                break;

            case "Back from Hotel Reserve":
                f_hotel_reserve_menu.dispose();
                f_hotel_reserve_menu = new JFrame("Hotel Reservation");
                price = 0;
                Show_CustomerMenu(global_customer);
                break;

            case "Back from Cancel Reserve":
                f_cancel_reserve_menu.dispose();
                f_cancel_reserve_menu = new JFrame("Cancel Reservation");
                price = 0;
                Show_CustomerMenu(global_customer);
                break;
        }
    }
}
