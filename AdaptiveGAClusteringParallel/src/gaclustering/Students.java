/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gaclustering;

import au.com.bytecode.opencsv.CSVReader;
import com.utils.ImportData;
import java.io.File;
import java.io.FileNotFoundException;
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
public class Students {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {


            File file = new File("D:\\Google\\Thesis\\Data\\Student\\clustering\\Dissimilarity_SanitizedV1.5.csv");
            ImportData preprocess_data = new ImportData(file);
            Const.TOTAL_RECORDS = preprocess_data.ProcessData();

            for (int i = 1; i <= 5; i++) {

                ArrayList<ArrayList<Integer>> clusterMedroid = new ArrayList<>();
                ArrayList<Integer> d = new ArrayList<>();
                
                
                File inputWorkbook = new File("D:\\Google\\Thesis\\Data\\Student\\clustering\\SOMs\\k4_output0"+ i +".csv");

                CSVReader csvReader = new CSVReader(new FileReader(inputWorkbook));
                List content = csvReader.readAll();
                String[] row = null;

                for (Object object : content) {
                    row = (String[]) object;
                    for (String s : row) {

                        if (!s.equals("")) {
                            //System.out.print(s + " ");
                            int value = Integer.parseInt(s);
                            d.add(value);

                        }

                    }
                    //System.out.println();
                    clusterMedroid.add(d);
                    d = new ArrayList<>();
                }
                clustering c = new clustering();


                Double fitness = c.callInterSim(clusterMedroid) - c.calIntraSim(clusterMedroid);

                System.out.println("Fitness for k=" + i + ": " + fitness);
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Students.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Students.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException | IllegalAccessException | SQLException | BiffException ex) {
            Logger.getLogger(Students.class.getName()).log(Level.SEVERE, null, ex);
        }




    }
}
