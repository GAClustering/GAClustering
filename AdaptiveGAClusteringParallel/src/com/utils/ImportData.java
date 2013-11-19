/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utils;

import gaclustering.Const;
import gaclustering.AGAC;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import au.com.bytecode.opencsv.CSVReader;
import java.util.List;
/**
 *
 * @author PELE
 */
public class ImportData {
    
    private File FileName;
    public ImportData( File FileName) {
        this.FileName = FileName;
        
    }
    
    public int ProcessData() throws InstantiationException, IllegalAccessException, SQLException, IOException, BiffException {
        int NumberOfRecord = 0;

        File inputWorkbook = FileName;
        Workbook w;
        CSVReader csvReader = new CSVReader(new FileReader(inputWorkbook));
        List content = csvReader.readAll();
        String[] row = null;
        String[] header = null;
        boolean isheader = true;
        int NumberOfcolumn = 0;
        int currentColnumn = 0;
        for (Object object : content) {
            if (isheader) {
                header = (String[]) object;
                NumberOfRecord = Integer.valueOf(header[0]);
                Const.SimilarityMatrix = new Double[NumberOfRecord][NumberOfRecord];
                isheader = false;
            }
            else {
                row = (String[]) object;
                for (int i = 0; i< NumberOfRecord; i++){
                    Const.SimilarityMatrix[currentColnumn][i] = Double.parseDouble(row[i]) ;
                }
                
                currentColnumn++;
            }
            
        }
        
//        for (int i = 0; i<Const.SimilarityMatrix.length; i++) {
//            for (int j = 0; j< Const.SimilarityMatrix[i].length; j++){
//                System.out.print(Const.SimilarityMatrix[i][j] + "\t");
//            }
//            System.out.println();
//        }
//        
//        w = Workbook.getWorkbook(inputWorkbook);
//	Sheet sheet = w.getSheet(0);
//        NumberOfRecord = sheet.getRows();
//        //SAGPC.outputText.append("Total records: " + (NumberOfRecord) + "\n");
//        Const.SimilarityMatrix = new Double[NumberOfRecord][NumberOfRecord];
//        for (int i = 0; i < NumberOfRecord; i++) {
//            
//            for (int j = 0; j < NumberOfRecord; j++) {
//                Cell cell = sheet.getCell(j, i);
//                Const.SimilarityMatrix[i][j] = Double.parseDouble(cell.getContents());
//            }
//
//        }
  
        return NumberOfRecord; //file header
    }


}
