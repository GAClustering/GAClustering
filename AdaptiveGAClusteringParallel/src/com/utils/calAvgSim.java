/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utils;

import gaclustering.Const;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

/**
 *
 * @author PELE
 */
public class calAvgSim {
    Double[][] simMatrix;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, BiffException {
        File inputWorkbook = new File("D:\\SIT\\Thesis\\data\\SimilarityMatric150.xls") ;
        calAvgSim c = new calAvgSim();
        c.importSimilarityMatrix(inputWorkbook);
        
        
        inputWorkbook = new File("D:\\SIT\\Thesis\\R-Project\\N150\\cluster150_1.xls");
        ArrayList<Integer> cluster1 = new ArrayList<Integer>();
        cluster1 = c.importCluster(inputWorkbook);
        Double avg1 = c.callAvgSim(cluster1);
        System.out.println("Cluster 1: " + avg1);
        
        inputWorkbook = new File("D:\\SIT\\Thesis\\R-Project\\N150\\cluster150_2.xls");
        ArrayList<Integer> cluster2 = new ArrayList<Integer>();
        cluster2 = c.importCluster(inputWorkbook);
        Double avg2 = c.callAvgSim(cluster2);
        System.out.println("Cluster 2: " + avg2);
        
        
        inputWorkbook = new File("D:\\SIT\\Thesis\\R-Project\\N150\\cluster150_3.xls");
        ArrayList<Integer> cluster3 = new ArrayList<Integer>();
        cluster3 = c.importCluster(inputWorkbook);
        Double avg3 = c.callAvgSim(cluster3);
        System.out.println("Cluster 3: " + avg3);

        
        System.out.println("Sum: " + (avg1 + avg2 + avg3) );
        
    }

    private Double callAvgSim(ArrayList<Integer> data){
        Double avgsim = 0.00;
        Double sum = 0.00;
        
        
        for (int i : data) {
            for (int j : data) {
                if ( i != j){
                    
                    Double sim = simMatrix[i-1][j-1];
                    sum += sim;
                }
            }
        }
        sum = sum / 2;
        //avgsim = sum / ClusterDataID.size();
        avgsim = sum / (data.size() * data.size());
        
        return avgsim;
    }

    private ArrayList<Integer> importCluster(File filename) throws IOException, BiffException{
        
        ArrayList<Integer> ClusterDataID = new ArrayList<Integer>(); 
        Workbook w;
        w = Workbook.getWorkbook(filename);
        Sheet sheet = w.getSheet(0);
        int NumberOfRecord = sheet.getRows();
        for (int i = 1; i < NumberOfRecord; i++) {
            Cell cell = sheet.getCell(0, i);
            ClusterDataID.add(Integer.parseInt(cell.getContents()));
        }
        
        w.close();
        return ClusterDataID;
        
    }
    
    private void  importSimilarityMatrix(File filename){
        try {

            Workbook w;
            
            w = Workbook.getWorkbook(filename);
            Sheet sheet = w.getSheet(0);
            int row = sheet.getRows();
            int col = sheet.getColumns();
            
            System.out.println("ROW: " + row);
            System.out.println("col: " + col);
            
            this.simMatrix = new Double[row][col];
            
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    Cell cell = sheet.getCell(j, i);
                    this.simMatrix[i][j] = Double.parseDouble(cell.getContents());
                    System.out.print(String.format("%.5f \t", simMatrix[i][j]));
                }
                System.out.println();
            }
            
            w.close();
        } catch (IOException | BiffException ex) {
            Logger.getLogger(calAvgSim.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
