/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gaclustering;

import au.com.bytecode.opencsv.CSVReader;
import com.utils.ImportData;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jxl.read.biff.BiffException;

/**
 *
 * @author PELE
 */
public class HeartData {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            File file = new File("D:\\SIT\\Thesis\\data\\HeartData\\DissimilarityMatrix.csv");
            ImportData preprocess_data = new ImportData(file);
            Const.TOTAL_RECORDS = preprocess_data.ProcessData();
            
            ArrayList<ArrayList<Integer>> clusterMedroid = new ArrayList<>();
            ArrayList<Integer> d = new ArrayList<>();
            
            
            //File inputWorkbook = new File("D:\\SIT\\Thesis\\data\\HeartData\\ResultFromDivisiveBest.csv");;
            //File inputWorkbook = new File("D:\\SIT\\Thesis\\data\\HeartData\\ResultFromDivisive.csv");
            File inputWorkbook = new File("D:\\SIT\\Thesis\\data\\HeartData\\ResultFromK-Means.csv");
            CSVReader csvReader = new CSVReader(new FileReader(inputWorkbook));
            List content = csvReader.readAll();
            String[] row = null;
            
            for (Object object : content) {
                row = (String[]) object;
                for (String s : row){
                    
                    if ( !s.equals("")) {
                        //System.out.print(s + " ");
                        int value = Integer.parseInt(s);
                        d.add(value);
                        
                    }
                    
                }
                //System.out.println();
                clusterMedroid.add(d);
                d = new ArrayList<>();
            }
            
            File inputWorkbookClass = new File("D:\\SIT\\Thesis\\data\\HeartData\\HeartDataClassified.csv");
            csvReader = new CSVReader(new FileReader(inputWorkbookClass));
            content = csvReader.readAll();
            String[] row2 = null;
            

            ArrayList<ArrayList<Integer>> classResult = new ArrayList<>();
            ArrayList<Integer> temp = new ArrayList<>();
            for (Object object : content) {
                row2 = (String[]) object;
                for (String s : row2){
                    
                    if ( !s.equals("")) {
                        System.out.print(s + " ");
                        int value = Integer.parseInt(s);
                        temp.add(value);
                        
                    }
                    
                }
                System.out.println();
                classResult.add(temp);
                temp = new ArrayList<>();
                
                
            }
           
            
            //Check Accuracy
           //Check Accuracy
            int count =0;
            for (int j : clusterMedroid.get(1)) {
                for (int k : classResult.get(0)) {
                    if ( j == k){
                        count++;
                    }
                }

            }
                
            for (int j : clusterMedroid.get(0)) {
                for (int k : classResult.get(1)) {
                    if ( j == k){
                        count++;
                    }
                }

            }
            
            System.out.println("Accuracy: " + count);
            
            count =0;
            for (int j : clusterMedroid.get(0)) {
                for (int k : classResult.get(0)) {
                    if ( j == k){
                        count++;
                    }
                }

            }
                
            for (int j : clusterMedroid.get(1)) {
                for (int k : classResult.get(1)) {
                    if ( j == k){
                        count++;
                    }
                }

            }
            
            System.out.println("Or Accuracy: " + count);

        } catch (InstantiationException | IllegalAccessException | SQLException | IOException | BiffException ex) {
            Logger.getLogger(HeartData.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
  
 
    
}
