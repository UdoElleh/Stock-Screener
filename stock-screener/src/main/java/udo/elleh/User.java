package udo.elleh;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class User {
    private String username, password, userID;
    DB connectDB = new DB();

    public User() {

    }

    public boolean loginUserResult(String inUsername, String inPassword) throws SQLException {
        boolean[] loginResultBoolean = loginUser(inUsername, inPassword);
        boolean outResult = false;
        if (loginResultBoolean[0] == true) {
            System.out.println("Login Successful");
            outResult = true;
        } else {
            if (loginResultBoolean[1] == true & loginResultBoolean[0] == false) {
                System.out.println("incorrect password");
            }
            if (loginResultBoolean[1] == false & loginResultBoolean[0] == false) {

                System.out.println("No user with this username");
            }
        }
        return outResult;
    }

    public boolean registerUser(String inUsername, String inPassword) {
        String uniqueID = UUID.randomUUID().toString();
        String prepStatement = "INSERT INTO USERS (UUID, Username, Password) VALUES (\"" + uniqueID + "\", \""
                + inUsername + "\",\" " + inPassword + "\");";
        username = inUsername;
        password = inPassword;
        userID = uniqueID;
        connectDB.insertStatement(prepStatement);
        return true;
    }

    public boolean[] loginUser(String inUsername, String inPassword) throws SQLException {
        boolean[] loginStateBoolean = new boolean[2];
        String prepStatement = "SELECT * FROM USERS WHERE Username = \"" + inUsername + "\";";
        ResultSet rs = connectDB.selectStatement(prepStatement);
        if (rs.next()) {
            String passwordResulString = rs.getString("Password");
            if (passwordResulString.compareTo(" " + inPassword) == 0) {

                username = inUsername;
                password = inPassword;
                userID = rs.getString("UUID");
                loginStateBoolean[0] = true;
                loginStateBoolean[1] = true;
            } else {
                loginStateBoolean[0] = false;
                loginStateBoolean[1] = true;
            }
        } else {
            loginStateBoolean[0] = false;
            loginStateBoolean[1] = false;
        }
        return loginStateBoolean;

    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getUserID() {
        return userID;
    }
}
