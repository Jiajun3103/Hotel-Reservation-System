package Menu;
import Database.MySQL_db;
import Hotel.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class Login_Menu implements ActionListener{
    MySQL_db db = new MySQL_db();
    private JFrame login_frame = new JFrame("Login");
    private JPanel p_input = new JPanel();
    private JPanel p_button = new JPanel();
    private JOptionPane op_login_success = new JOptionPane();
    private JLabel l_title = new JLabel("Login", SwingConstants.CENTER);
    private JTextField tf_username = new JTextField();
    private JTextField tf_password = new JTextField();
    private JButton b_login = new JButton("Login");
    private JButton b_back = new JButton("Back");

    public void show_Login_Menu(){
        login_frame.setLocationRelativeTo(null);
        login_frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        login_frame.setVisible(true);
        login_frame.setLayout(new BorderLayout());
        login_frame.setSize(400,200);
        login_frame.add(l_title, BorderLayout.NORTH);
        login_frame.add(p_input, BorderLayout.CENTER);
        login_frame.add(p_button, BorderLayout.SOUTH);

        l_title.setFont(new Font("Poppins", Font.BOLD, 18));
        l_title.setForeground(Color.red);

        p_input.setLayout(new GridLayout(4,2));
        p_input.setBorder(BorderFactory.createEmptyBorder(15, 50, 0, 50));
        p_input.setBackground(Color.LIGHT_GRAY);
        p_input.add(new JLabel("Username"));
        p_input.add(tf_username);
        p_input.add(new JLabel(""));
        p_input.add(new JLabel(""));
        p_input.add(new JLabel("Password"));
        p_input.add(tf_password);

        p_button.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 50));
        p_button.add(b_back);
        p_button.add(b_login);

        b_login.addActionListener(this);
        b_login.setActionCommand("Login");
        b_back.addActionListener(this);
        b_back.setActionCommand("Back");
    }


    public void actionPerformed(ActionEvent event){
        String command = event.getActionCommand();

        switch (command){
            case "Login":
                try {
                    db.prepareStatement("SELECT count(*) AS total FROM user WHERE username = ? AND password = ?");
                    db.statement.setString(1, tf_username.getText());
                    db.statement.setString(2, tf_password.getText());
                    db.QueryResult();
                    db.result.next();

                    if(db.result.getInt("total") == 1){
                        db.prepareStatement("SELECT * FROM user WHERE username = ? AND password = ?");
                        db.statement.setString(1, tf_username.getText());
                        db.statement.setString(2, tf_password.getText());
                        db.QueryResult();
                        db.result.next();
                        User user = new User(db.result.getInt("user_id"), db.result.getString("username")
                                ,db.result.getString("role"));
                        JOptionPane.showMessageDialog(login_frame, "Login Successfully!", "Login"
                                , JOptionPane.INFORMATION_MESSAGE);

                        if(db.result.getString("role").equals("customer")){
                            login_frame.dispose();
                            Customer_Menu customer_menu = new Customer_Menu();
                            customer_menu.Show_CustomerMenu(user);
                        } else if (db.result.getString("role").equals("admin")){

                        }
                    }
                    else{
                        JOptionPane.showMessageDialog(login_frame, "Login Failed! Please check your username and password!"
                                , "Login"
                                , JOptionPane.ERROR_MESSAGE);
                    }

                } catch (SQLException e){
                    System.out.println("Broke");
                }
                break;
            case "Back":
                System.out.println("Register");
                login_frame.dispose();
                Main_Menu main_menu = new Main_Menu();
                main_menu.show();
                break;
        }
    }
}
