/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.utils;

/*** Start JDBC Connection (Local) ***/

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionDB {

    private Connection conn;

    public ConnectionDB() {
    }

    public boolean createConnection() throws InstantiationException, IllegalAccessException {

        PropertiesUtil.loadDBProperties();

        try {

            Class.forName(ConstantDB.DRIVER_CLASS);
            conn = DriverManager.getConnection(ConstantDB.CONNECTION_STR, ConstantDB.USER_CONNECTION, ConstantDB.PASSWORD_CONNECTION);
            

        } catch (ClassNotFoundException ex) {
            System.out.println(ex);

            Logger.getLogger(ConnectionDB.class.getName()).log(Level.SEVERE, null, ex);
            return false;

        } catch (SQLException ex) {
            System.out.println(ex);
            Logger.getLogger(ConnectionDB.class.getName()).log(Level.SEVERE, null, ex);
            return false;

        }
        return true;
    }

    public Connection getConnection() {
        return conn;
    }

    public void closeConnection() {
        try {

            conn.close();

        } catch (SQLException ex) {

            Logger.getLogger(ConnectionDB.class.getName()).log(Level.SEVERE, null, ex);

        }
    }
    
    public static void main (String [] args) throws SQLException, InstantiationException, IllegalAccessException{
        ConnectionDB db = new ConnectionDB();
        if(db.createConnection()){
            System.out.println("PASS");
        }
    }
}

/**** End JDBC Connection (Local) ****/

