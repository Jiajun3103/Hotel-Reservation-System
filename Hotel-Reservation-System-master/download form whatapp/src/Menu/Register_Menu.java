package Menu;
import Database.MySQL_db;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class Register_Menu implements ActionListener{
    MySQL_db db = new MySQL_db();
    private JFrame register_frame = new JFrame("Register");
    private JPanel p_input = new JPanel();
    private JPanel p_button = new JPanel();
    private JOptionPane op_login_success = new JOptionPane();
    private JLabel l_title = new JLabel("Register", SwingConstants.CENTER);
    private JTextField tf_username = new JTextField();
    private JTextField tf_password = new JTextField();
    private JButton b_register = new JButton("Register");
    private JButton b_back = new JButton("Back");

    public void show_Register_Menu(){
        register_frame.setLocationRelativeTo(null);
        register_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        register_frame.setVisible(true);
        register_frame.setLayout(new BorderLayout());
        register_frame.setSize(400,200);
        register_frame.add(l_title, BorderLayout.NORTH);
        register_frame.add(p_input, BorderLayout.CENTER);
        register_frame.add(p_button, BorderLayout.SOUTH);

        l_title.setFont(new Font("Poppins", Font.BOLD, 18));
        l_title.setForeground(Color.blue);

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
        p_button.add(b_register);

        b_register.addActionListener(this);
        b_register.setActionCommand("Register");
        b_back.addActionListener(this);
        b_back.setActionCommand("Back");
    }

    public void actionPerformed(ActionEvent event) {
        String command = event.getActionCommand();

        switch (command) {
            case "Register":
                try {
                    int new_user_id;
                    db.prepareStatement("SELECT count(*) AS total FROM user");
                    db.QueryResult();
                    db.result.next();
                    new_user_id = db.result.getInt("total") + 1;

                    db.prepareStatement("INSERT INTO user (user_id, username, password, role) VALUES (?,?,?,'customer')");
                    db.statement.setInt(1, new_user_id);
                    if(tf_username.getText().isEmpty()){throw new Exception("Username cannot be empty");}
                    else{db.statement.setString(2, tf_username.getText());}
                    if(tf_password.getText().isEmpty()){throw new Exception("Password cannot be empty");}
                    else{db.statement.setString(3, tf_password.getText());}
                    db.QueryUpdate();

                    JOptionPane.showMessageDialog(register_frame, "Register Successfully!", "Register"
                            , JOptionPane.INFORMATION_MESSAGE);

                    register_frame.dispose();
                    Main_Menu main_menu = new Main_Menu();
                    main_menu.show();

                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(register_frame, "Register Failed! Please make sure to fill all the column!"
                            , "Register"
                            , JOptionPane.ERROR_MESSAGE);
                } catch (Exception e){
                    JOptionPane.showMessageDialog(register_frame, e.getMessage()
                            , "Register"
                            , JOptionPane.ERROR_MESSAGE);
                }
                break;
            case "Back":
                register_frame.dispose();
                Main_Menu main_menu = new Main_Menu();
                main_menu.show();
                break;
        }
    }
}
