package udo.elleh;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JLabel;

public class LoginScreen {
    
    private static JFrame frame;
    private JPanel mainPanel = new JPanel();
    private JTextField usernameTextField = new JTextField();
    private JPasswordField passwordField = new JPasswordField();
    private JButton loginButton = new JButton("Login");
    private JButton registerUserButton = new JButton("Register New User");
    private JComponent[] allComponents = {new JLabel("Username"), usernameTextField, new JLabel("Password"), passwordField, loginButton, registerUserButton};

    public LoginScreen(){
        
        usernameTextField.setColumns(20);
        passwordField.setColumns(20);
        loginButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                String passwordString = new String(passwordField.getPassword());
                String usernameString = usernameTextField.getText();
                try{
                    User user = new User();
                    if(user.loginUserResult(usernameString, passwordString)){
                        openDashboard();
                    }
                }catch(SQLException s){
                    s.printStackTrace();
                }
            }
        });
        registerUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                openRegisterWindow();
            }
        });
        for(JComponent comp: allComponents){
            mainPanel.add(comp);
        }
        
    }

    public JComponent getMainComponent(){
        return mainPanel;
    }

    public static void createAndShowLogin(){
        LoginScreen loginScreen = new LoginScreen();
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(loginScreen.getMainComponent());
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }

    private void openRegisterWindow(){
        frame.setVisible(false);
        RegisterScreen.createAndShowRegister();
    }

    private void openDashboard(){
        frame.setVisible(false);
        Dashboard.createAndShowDashboard();
    }


}
