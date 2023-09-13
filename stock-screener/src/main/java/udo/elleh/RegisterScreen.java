package udo.elleh;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class RegisterScreen {
    private static JFrame frame;
    private JPanel mainPanel = new JPanel();
    private JTextField usernameTextField = new JTextField();
    private JPasswordField initialPasswordField = new JPasswordField();
    private JPasswordField confirmPasswordField = new JPasswordField();
    private JButton registerButton = new JButton("Register");
    private JButton loginButton = new JButton("Go Back to Login");
    private JComponent[] allComponents = { new JLabel("Username"), usernameTextField, new JLabel("Enter Password"),
            initialPasswordField, new JLabel("Confirm Password"), confirmPasswordField, registerButton };

    public RegisterScreen() {
        usernameTextField.setColumns(20);
        initialPasswordField.setColumns(20);
        confirmPasswordField.setColumns(20);
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                String initialPasswordString = new String(initialPasswordField.getPassword());
                String confirmPasswordString = new String(confirmPasswordField.getPassword());
                String usernameString = usernameTextField.getText();
                if (initialPasswordString.compareTo(confirmPasswordString) == 0) {

                    User newUser = new User();
                    newUser.registerUser(usernameString, confirmPasswordString);

                }
            }

        });

        loginButton.addActionListener((new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event){
                openLoginWindow();
            }
        }));

        for (JComponent comp : allComponents) {
            mainPanel.add(comp);
        }
    }

    public JComponent getMainComponent() {
        return mainPanel;
    }

    public static void createAndShowRegister() {
        RegisterScreen registerScreen = new RegisterScreen();
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(registerScreen.getMainComponent());
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }

    private void openLoginWindow(){
        LoginScreen.createAndShowLogin();
    }


}