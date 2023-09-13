package udo.elleh;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class StockObj {

    private static final String APIKEY = "ZCV3SWV7ET3N4H4T";
    private DB connectDB = new DB();
    private String stockSymbol;

    public StockObj(String inSymbol) {
        stockSymbol = inSymbol;
        insertStock();
    }

    public void insertStock(){
        download();
        makeTable();
        insertStockData();
        updateStockList();
        deleteFile();
    }

    private void downloadStream(String urlString, String symbolFile) throws IOException {
        URL url = new URL(urlString);
        BufferedInputStream inputStream = new BufferedInputStream(url.openStream());
        FileOutputStream outputStream = new FileOutputStream(symbolFile);
        byte[] buffer = new byte[1024];
        int bytesRead = 0;
        while ((bytesRead = inputStream.read(buffer, 0, 1024)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        outputStream.close();
        inputStream.close();
    }

    private void download() {
       
        try {
            downloadStream("https://www.alphavantage.co/query?function=TIME_SERIES_MONTHLY_ADJUSTED&symbol=IBM&apikey="
                    + APIKEY + "&datatype=csv&" + stockSymbol + ".csv", "src/" + stockSymbol + ".csv");
        } catch (IOException e) {
            e.printStackTrace();
            
        }
        
    }

    private void deleteFile() {
        try {
            File f = new File("src/" + stockSymbol + ".csv");
            f.delete();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String path() {
        return "src/" + stockSymbol + ".csv";
    }

    public void makeTable() {
        String inPrepStatement = "CREATE TABLE " + stockSymbol
                + " (Timestamp DATETIME,Open DOUBLE,High DOUBLE,Low DOUBLE,Close DOUBLE,Adjusted_Close DOUBLE,Volume INTEGER,Dividend_Amount DOUBLE);";
        connectDB.updateStatement(inPrepStatement);
    }

    public void dropTable(String inSymbol) {
        String prepStatement = "DROP TABLE " + inSymbol;
        connectDB.updateStatement(prepStatement);
    }

    public void dropAllTables(){
        for(int i = 0; i<getListCount(); i++){
            String prepStatement = "DROP TABLE " + fetchStockList()[i];
            connectDB.updateStatement(prepStatement);
        }
    }

    public Date getStartDate() {
        try {
            ResultSet rs = connectDB.selectStatement("SELECT TOP 1 TIMESTAMP FROM " + stockSymbol + " ORDER BY TIMESTAMP");
            rs.next();
            return rs.getDate("Timestamp");
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Date getLastDate() {
        try {
            ResultSet rs = connectDB.selectStatement("SELECT TOP 1 TIMESTAMP FROM " + stockSymbol + " ORDER BY TIMESTAMP DESC");
            rs.next();
            return rs.getDate("Timestamp");
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getNumberMonths(){
        try{
        ResultSet rs = connectDB.selectStatement("SELECT COUNT(TIMESTAMP) AS NUMMONTHS FROM " + stockSymbol);
        rs.next();
        return rs.getInt("NUMMONTHS");
        }catch(SQLException e){
            e.printStackTrace();
            return 0;
        }
    }

    public double[] getMonthlyAvg() {
        double rt[] = new double[getNumberMonths()]; 
        int i = 0;
        try {
            ResultSet rs = connectDB.selectStatement("SELECT MONTH(Timestamp), AVG(CLOSE) FROM " + stockSymbol +  " GROUP BY MONTH(Timestamp) ORDER BY MONTH(Timestamp);");
            while(rs.next()){
                rt[i] = rs.getDouble(2);
                i++;
            }
            return rt;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void insertStockData() {
        try {
            String prepStatement = "INSERT INTO " + stockSymbol
                    + " (Timestamp, Open, High, Low, CLose, Adjusted_Close, Volume, Dividend_Amount) VALUES (?,?,?,?,?,?,?,?);";
            PrepStatement p = new PrepStatement(prepStatement);
            java.sql.Date sqlTimestamp;
            int vol;
            Float open, high, low, close, adjCLose, divAmt;
            try {
                Scanner sc = new Scanner(new File("src/" + stockSymbol + ".csv"));
                sc.nextLine();
                while (sc.hasNextLine()) {
                    Scanner lineSc = new Scanner(sc.nextLine()).useDelimiter(",");
                    try{

                        sqlTimestamp = Date.valueOf(lineSc.next());
                        open = lineSc.nextFloat();
                        high = lineSc.nextFloat();
                        low = lineSc.nextFloat();
                        close = lineSc.nextFloat();
                        adjCLose = lineSc.nextFloat();
                        vol = lineSc.nextInt();
                        divAmt = lineSc.nextFloat();
                        p.getPrepStatement().setDate(1, sqlTimestamp);
                        p.getPrepStatement().setFloat(2, open);
                        p.getPrepStatement().setFloat(3, high);
                        p.getPrepStatement().setFloat(4, low);
                        p.getPrepStatement().setFloat(5, close);
                        p.getPrepStatement().setFloat(6, adjCLose);
                        p.getPrepStatement().setInt(7, vol);
                        p.getPrepStatement().setFloat(8, divAmt);
                        p.getPrepStatement().execute();
                    }catch(NoSuchElementException e){
                        e.printStackTrace();
                    }
                    lineSc.close();
                }
                sc.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateStockList(){
        connectDB.insertStatement("INSERT INTO STOCKLIST(SYMBOL) VALUES(\"" + stockSymbol + "\");");
    }

    public String[] fetchStockList(){
        int i = 0;
        String outString[] = new String[getListCount()];
        try{ 
            ResultSet rs = connectDB.selectStatement("SELECT SYMBOL FROM STOCKLIST");
            while(rs.next()){
                outString[i] = rs.getString(1);
                i++;
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

    public boolean checkStockPresence(String inSymbol){
        boolean outBool = false;
        for(int i = 0; i < getListCount(); i++){
            if(inSymbol.equals(fetchStockList()[i])){
                outBool = true;
            }
        }
        return outBool;
        
    }

    public String getSymbol(){
        return stockSymbol;
    }

}
