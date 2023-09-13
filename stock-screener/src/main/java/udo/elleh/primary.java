package udo.elleh;

import java.io.IOException;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class primary {
    public static void main(String[] args) throws SQLException, IOException {
        login();
    }

    private static void login(){
        LoginScreen.createAndShowLogin();
    }

    private static void dashboard(){
        Dashboard.createAndShowDashboard();
    }

    
}
