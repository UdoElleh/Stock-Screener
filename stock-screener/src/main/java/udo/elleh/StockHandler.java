package udo.elleh;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StockHandler {
    DB connectDB = new DB();
    StockObj[] stockObjs = new StockObj[10];
    
    public StockHandler(){
        for(int i = 0; i < 10; i++){
            stockObjs[i] = new StockObj(fetchStockList()[i]);
        }
    }

    public String[] fetchStockList(){
        int p = 0;
        String outString[] = new String[getListCount()];
        try{ 
            ResultSet rs = connectDB.selectStatement("SELECT SYMBOL FROM STOCKLIST");
            while(rs.next()){
                outString[p] = rs.getString(1);
                p++;
            }
            return outString;
        }   catch(SQLException e){
            e.printStackTrace();
            return outString;
        }
    }
    public int getListCount(){
        int count = 0;
        try {
            ResultSet rs = connectDB.selectStatement("SELECT COUNT(ID) FROM STOCKLIST");
            while(rs.next()){
                count = rs.getInt(1);
        }
        return count;
        } catch (SQLException e) {
            e.printStackTrace();
            return count;    
        }
    }

    public double[] getMonthlyAvg(String symbolString ){
        double[] out = null;
        for(int i = 0; i < 10; i++){
            if(stockObjs[i].getSymbol() == symbolString){
                return stockObjs[i].getMonthlyAvg();
            }
        } 
        return out;   
    }
}
