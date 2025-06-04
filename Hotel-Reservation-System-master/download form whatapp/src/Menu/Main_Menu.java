package Menu;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Main_Menu implements ActionListener {
    private JFrame main_frame = new JFrame("Hotel Reservation System");
    private JPanel p_selection = new JPanel();
    private JLabel l_welcome = new JLabel("Welcome to Hotel Reservation System", SwingConstants.CENTER);
    private JButton b_login = new JButton("Login");
    private JButton b_register = new JButton("Register");
    private JButton b_exit = new JButton("Exit");

    public void show(){
        // Main Frame
        main_frame.setLocationRelativeTo(null);
        main_frame.setVisible(true);
        main_frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        main_frame.setSize(400,400);
        main_frame.setLayout(new BorderLayout());
        main_frame.setBackground(Color.BLACK);
        main_frame.add(l_welcome, BorderLayout.NORTH);
        main_frame.add(p_selection, BorderLayout.CENTER);

        // Text font configuration
        l_welcome.setFont(new Font("Poppins", Font.BOLD, 18));
        b_login.setFont(new Font("Poppins", Font.BOLD, 14));
        b_register.setFont(new Font("Poppins", Font.BOLD, 14));
        b_exit.setFont(new Font("Poppins", Font.BOLD, 14));

        // Selection Panel
        p_selection.setLayout(new GridLayout(6, 1));
        p_selection.setBackground(Color.DARK_GRAY);
        p_selection.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));
        p_selection.add(b_login);
        p_selection.add(new Label(""));
        p_selection.add(b_register);
        p_selection.add(new Label(""));
        p_selection.add(b_exit);
        p_selection.add(new Label(""));

        // Button Event
        b_login.addActionListener(this);
        b_login.setActionCommand("Login");
        b_register.addActionListener(this);
        b_register.setActionCommand("Register");
        b_exit.addActionListener(this);
        b_exit.setActionCommand("Exit");
    }

    public void actionPerformed(ActionEvent event){
        String command = event.getActionCommand();

        switch (command){
            case "Login":
                main_frame.dispose();
                Login_Menu login_menu = new Login_Menu();
                login_menu.show_Login_Menu();
                break;
            case "Register":
                main_frame.dispose();
                Register_Menu register_Menu = new Register_Menu();
                register_Menu.show_Register_Menu();
                break;
            case "Exit":
                System.out.println("Exit");
                main_frame.dispose();
                break;
        }
    }
}
