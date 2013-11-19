/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utils;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import gaclustering.AGAC;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author PELE
 */
public class ConvertData {

    private ArrayList<String> alphaPool;
    
    public ConvertData() {
        alphaPool = new ArrayList<String>();
    }
    
    public void convert() throws InstantiationException, IllegalAccessException, SQLException{
        Statement stmt;
        Connection conn;
        ResultSet rs;

        ConnectionDB db = new ConnectionDB();
        db.createConnection();
        conn = (Connection) db.getConnection();
        stmt = (Statement) conn.createStatement();
        PreparedStatement statement = null;
        rs = stmt.executeQuery("SELECT id, gender, age, weight, gpa FROM data_raw");
        while ( rs.next() ) {
            
            //Attribute 'gender'
            String Data = "";
            String tmp = "a";
            String gender = rs.getString("gender");
            if ( "Male".equals(gender) ) {
                tmp += "A";
            }
            else {
                tmp += "B";
            }
            addAlpaPool(tmp);
            Data += tmp;

            //Attribute 'age'
            // 22-40
            tmp = "b";
            int age = rs.getInt("age");
            
            if ( age <= 23 ){
                tmp += "C";
            }
            else if( age <= 25 ){
                tmp += "D";
            }
            else if( age <= 27 ){
                tmp += "E";
            }
            else if( age <= 29 ){
                tmp += "F";
            }
            else if( age <= 31 ){
                tmp += "G";
            }
            else if( age <= 33 ){
                tmp += "H";
            }
            else if( age <= 35 ){
                tmp += "I";
            }
            else if( age <= 37 ){
                tmp += "J";
            }
            else if( age <= 39 ){
                tmp += "K";
            }
            else {
                tmp += "L";
            }
            
            addAlpaPool(tmp);
            Data += tmp;
            
            //Weight
            tmp = "c";
            int weight = rs.getInt("weight");
            
            if ( weight <=45 ){
                tmp += "M";
            }
            else if ( weight <=55 ){
                tmp += "N";
            }
            else if ( weight <=65 ){
                tmp += "O";
            }
            else if ( weight <=75 ){
                tmp += "P";
            }
            else if ( weight <=85 ){
                tmp += "Q";
            }
            else if ( weight <=95 ){
                tmp += "R";
            }
            else if ( weight <= 105 ){
                tmp += "S";
            }
            else if ( weight <= 110 ){
                tmp += "T";
            }
            else {
                tmp += "U";
            }

            addAlpaPool(tmp);
            Data += tmp;
            
            //GPA
            tmp = "d";
            Double gpa = rs.getDouble("gpa");
            if ( gpa >= 3.5){
                tmp += "V"; 
            }
            else if ( gpa >= 3.0) {
                tmp += "W";
            }
            else if ( gpa >= 2.5) {
                tmp += "X";
            } 
            
            addAlpaPool(tmp);
            Data += tmp;
            
            //Save converted data into db table
            statement = conn.prepareStatement("Insert into data_converted (id, data_string, clusterNo) Values (?,?,?)");
            statement.setInt(1, rs.getInt("id") );
            statement.setString(2, Data);
            statement.setInt(3, 1);
            statement.executeUpdate(); 

            AGAC.outputText.append(Data+"\n");
            System.out.println(Data);
        }
        
        stmt.close();
        conn.close();
        AGAC.outputText.append("**** Data has been Converted successfully ****\n");
        
    }

    /**
     * @return the alphaPool
     */
    public ArrayList<String> getAlphaPool() {
        return alphaPool;
    }

    public void addAlpaPool(String tmp) { 
        if( !alphaPool.contains(tmp)) {
            alphaPool.add(tmp);
        } 
    }
}
