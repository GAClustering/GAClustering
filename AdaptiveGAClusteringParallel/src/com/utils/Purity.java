/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utils;

import au.com.bytecode.opencsv.CSVReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author PELE
 */
public class Purity {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        try {


            // Read clustering result from file
            ArrayList<ArrayList<Integer>> clusterResult = new ArrayList<>();
            ArrayList<Integer> clusterElement = new ArrayList<>();



            File inputWorkbook = new File("D:\\Google\\Thesis\\Data\\Hepatitis\\PAM\\hep.output01.csv");
            File inputWorkbookClass = new File("D:\\Google\\Thesis\\Data\\Hepatitis\\Purity\\hepatitis.classes.csv");
            
 
            CSVReader csvReader = new CSVReader(new FileReader(inputWorkbook));
            List content = csvReader.readAll();
            String[] row = null;
            for (Object object : content) {
                row = (String[]) object;
                for (String s : row) {

                    if (!s.equals("")) {
                        System.out.print(s + " ");
                        int value = Integer.parseInt(s);
                        clusterElement.add(value);
                    }

                }
                System.out.println();
                clusterResult.add(clusterElement);
                clusterElement = new ArrayList<>();
            }


            // Read class 
            
            csvReader = new CSVReader(new FileReader(inputWorkbookClass));
            content = csvReader.readAll();
            String[] row2 = null;

            ArrayList<Integer> classes = new ArrayList<>();
            for (Object object : content) {
                row2 = (String[]) object;
                int value = Integer.parseInt(row2[0]);
                classes.add(value);
            }


            Purity P = new Purity();
            double purity = 0.0;

            purity = P.callPurity(clusterResult, classes);



        } catch (FileNotFoundException ex) {
            Logger.getLogger(Purity.class.getName()).log(Level.SEVERE, null, ex);
        }




    }

    public Purity() {
    }

    public double callPurity(ArrayList<ArrayList<Integer>> clusterResult, ArrayList<Integer> classes) {

        double sum = 0;
        int countMajorClass[] = new int[clusterResult.size()];
        for (ArrayList<Integer> k : clusterResult) {
            for (int i = 0; i < countMajorClass.length; i++) {
                countMajorClass[i] = 0;
            }
            
            for (Integer data : k) {
                countMajorClass[classes.get(data - 1) - 1]++;

            }
            int majorclass = 0;
            for (int count : countMajorClass) {
                System.out.println("count: " + count);
                if (count > majorclass) {
                    majorclass = count;
                }
            }

            System.out.println("MajorityClass: " + majorclass);
            sum += majorclass;

        }

        System.out.println("Sum: " + sum);

        double purity = sum / classes.size();
        System.out.println("Cluster purity = " + purity);
        return purity;
    }

    public double callPurity(ArrayList<ArrayList<Integer>> clusterResult) throws FileNotFoundException, IOException {

        // Read class 
        File inputWorkbookClass = new File("D:\\SIT\\Thesis\\data\\purity\\HeartDataClasses.csv");
        CSVReader csvReader = new CSVReader(new FileReader(inputWorkbookClass));
        List content = csvReader.readAll();
        String[] row2 = null;

        ArrayList<Integer> classes = new ArrayList<>();
        for (Object object : content) {
            row2 = (String[]) object;
            int value = Integer.parseInt(row2[0]);
            classes.add(value);
        }

        double sum = 0;
        for (ArrayList<Integer> k : clusterResult) {
            int class1 = 0;
            int class2 = 0;
            for (Integer data : k) {
                //System.out.println("data: " + data);
                if (classes.get(data - 1) == 1) {
                    class1++;
                } else {
                    class2++;
                }
            }
            int majorclass = (class1 > class2) ? class1 : class2;
            System.out.println("MajorityClass: " + majorclass);
            sum += majorclass;

        }

        double purity = sum / classes.size();
        return purity;
    }
}
