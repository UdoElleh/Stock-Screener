package udo.elleh;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

import javax.swing.JTextField;

public class Dashboard {
    private static JFrame frame;
    private JPanel mainPanel = new JPanel();
    private JTextField stockTextField = new JTextField(20);
    private JButton stockSearchButton = new JButton("Search");
    private JLabel stockInfoLabel = new JLabel("stock data:");
    private StockHandler stockHandlerObj = new StockHandler();

    private JComponent[] allComponents = { new JLabel("Stock"), stockTextField, stockSearchButton, stockInfoLabel };


    public Dashboard() {

        stockSearchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){

            }
        });
        for (JComponent comp : allComponents) {
            mainPanel.add(comp);
        }
    }

    public JComponent getMainComponent(){
        return mainPanel;
    }

    public static void createAndShowDashboard() {
        Dashboard dashboard = new Dashboard();
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(dashboard.getMainComponent());
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }

    public void searchStock() {
        System.out.println(stockHandlerObj.getMonthlyAvg(stockTextField.getText()));

    }

    
}
