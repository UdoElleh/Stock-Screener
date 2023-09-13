package udo.elleh;

import java.sql.*;

public class DB {
    
    protected Connection conn;

    public DB(){
        String databaseURL = "jdbc:ucanaccess://src/StockTable.accdb";
        try{
            conn = DriverManager.getConnection(databaseURL);
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    public void insertStatement(String prepStatement){
        try {
            PreparedStatement p = conn.prepareStatement(prepStatement);
            p.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateStatement(String prepStatement){
        try {
            PreparedStatement p = conn.prepareStatement(prepStatement);
            p.executeUpdate();

        } catch (SQLException e) {
            System.out.println("sqlexception");
            e.printStackTrace();
        }
    }

    public ResultSet selectStatement(String prepStatement){
        try {
            PreparedStatement p = conn.prepareStatement(prepStatement);
            ResultSet rs = p.executeQuery();
            return rs;
        } catch (SQLException e){
            e.printStackTrace();
            ResultSet rs = null;
            return rs;
        }
    }

    public PreparedStatement createPreparedStatement(String prepStatement){
        try {
            PreparedStatement p = conn.prepareStatement(prepStatement);
            return p;
        } catch (Exception e) {
           PreparedStatement p = null;
           return p;
        }
    }

    public void closeConnection(){
        try {        
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

   

}

class PrepStatement extends DB{
    private PreparedStatement p;
    
    public PrepStatement(String inPrepStatement){
        try {
            p = conn.prepareStatement(inPrepStatement);           
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public PreparedStatement getPrepStatement(){
        return p;
    }
}