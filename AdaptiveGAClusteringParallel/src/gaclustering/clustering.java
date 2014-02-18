package gaclustering;

import com.utils.ImportData;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import jxl.read.biff.BiffException;

public class Clustering {

    private Double[][] Dissimilarity;
    //private ArrayList<Integer> DataList;
    private Double intraDisimilarity;
    private Double interDisimilarity;

    public static void main(String[] args) {
        try {
            File file = new File("C:\\Pele\\GoogleDrive\\Thesis\\Data\\Student\\clustering\\Dissimilarity_SanitizedV1.5.csv");
            ImportData preprocess_data = new ImportData(file);
            Const.TOTAL_RECORDS = preprocess_data.ProcessData();
            Const.MEDROID_LENGTH = 20;

            //&&(118)(126)&&&(58)(59)&(90)(4)&(43)(66)
            //int[] allmedroid = {64,62,98,99};
            //int[] allmedroid = {91,74,99,70,142,65,51,78,55,92};
            int[][] allmedroid = {{1444, 438, 463, 953, 359, 224, 559, 1153, 894, 363, 1079, 1592, 976, 1233, 942, 1151, 1511, 1496, 1266, 958, 650, 25, 1911, 176, 862, 1206, 832, 697, 1096, 663, 981, 1549, 1992, 1697, 63, 1644, 1999, 1359, 631, 1225, 2030, 1131, 752, 1417, 974, 1946, 670, 1873, 1902, 1665, 278, 1560, 16, 1683, 1306, 604, 1499, 1922, 1504, 773, 35, 1345, 1652, 1168, 418, 1132, 1100, 1523, 1389, 850, 1407, 918, 235, 1381, 1157, 991, 487, 1912, 351, 998}};

            for (int[] k : allmedroid) {
                Clustering c = new Clustering();
                ArrayList<ArrayList<Integer>> clusterMedroid = new ArrayList<>();
                int clusterNo = 0;
                ArrayList<Integer> d = new ArrayList<>();
                for (int i = 0; i < k.length; i++) {
                    d.add(k[i]);

                    if ((i + 1) % Const.MEDROID_LENGTH == 0) {
                        clusterMedroid.add(clusterNo, d);

                        clusterNo++;
                        d = new ArrayList<>();
                    }
                }
                Const.NUM_OF_CLUSTER = clusterNo;

                //System.out.println("K = " + (k.length / Const.MEDROID_LENGTH) + " == " + c.findCluster2(clusterMedroid));
                
        long startTime = System.currentTimeMillis();
        Double Fitness = c.findCluster(clusterMedroid);
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
System.out.println("cal fitness time: " + duration);
        

                System.out.println("Fitness: " + Fitness);
                c.printResult(clusterMedroid, Fitness);
                

            }

        } catch (InstantiationException | IllegalAccessException | SQLException | IOException | BiffException ex) {
            Logger.getLogger(Clustering.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    public void printResult(ArrayList<ArrayList<Integer>> clusterMedroid, Double Fitness) throws IOException {

        File file = new File("test_output_" + Fitness + ".csv");
        if (!file.exists()) {
            file.createNewFile();
        }
        
        FileWriter fw = new FileWriter(file.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);
        for (int i = 0; i < clusterMedroid.size(); i++) {
            String content = "";
            System.out.println("\n Output for : " + (i + 1) + " th, n = " +clusterMedroid.get(i).size());
            for (int j = 0; j < clusterMedroid.get(i).size(); j++) {
                content += clusterMedroid.get(i).get(j) + ",";
            }
            content += "\n";
            bw.write(content);

        }

        bw.close();

    }
    
    public Clustering() {

    }

    public Double findCluster(ArrayList<ArrayList<Integer>> clusterMedroid) {

        int minID = 0;
        Double MinAvg = 0.0;
        int minClusterNo = 0;

        //Initial data elements
        Element elements[];
        elements = new Element[Const.TOTAL_RECORDS];
        for (int i = 0; i < Const.TOTAL_RECORDS; i++) {
            elements[i] = new Element(i);
            elements[i].intitial(clusterMedroid);
            
            
            if (i == 0 || MinAvg > elements[i].getMinAvg()) {
                MinAvg = elements[i].getMinAvg();
                minClusterNo = elements[i].getMinClusterPosition();
                minID = i;
            }
        }
        
        int totalMember = Const.MEDROID_LENGTH * Const.NUM_OF_CLUSTER;
        while (totalMember < Const.TOTAL_RECORDS) {
            if ( !elements[minID].isIs_moved()) {
                clusterMedroid.get(minClusterNo).add(minID + 1);
                elements[minID].setIs_moved(true);
            }

            boolean reset = true;
            int tmpMinID = minID;
            int tmpMinClusterNo = minClusterNo;
            for (Element e : elements) {
                if (!e.isIs_moved()) {
                    e.updateDistance(tmpMinID, tmpMinClusterNo, clusterMedroid.get(tmpMinClusterNo).size());
                    if (reset || MinAvg > e.getMinAvg()) {
                        MinAvg = e.getMinAvg();
                        minClusterNo = e.getMinClusterPosition();
                        minID = e.getID();
                        reset = false;
                    }
                }
            }

            totalMember++;

        }

        return callInterSim(clusterMedroid) - calIntraSim(clusterMedroid);
    }

    public Double callInterSim(ArrayList<ArrayList<Integer>> clusterMedroid) {

        Double sum = 0.0;
        for (int i = 0; i < clusterMedroid.size() - 1; i++) {
            for (int j = i + 1; j < clusterMedroid.size(); j++) {
                for (int value1 : clusterMedroid.get(i)) {
                    for (int value2 : clusterMedroid.get(j)) {
                        sum+= (Const.SimilarityMatrix[value1 - 1][value2 - 1] * 2);
                    }
                }
            }
        }

        return sum;
    }

    public Double calIntraSim(ArrayList<ArrayList<Integer>> clusterMedroid) {
        Double sum = 0.0;

        for (ArrayList<Integer> d : clusterMedroid) {
             for(int i=0; i< d.size() - 1;i++){
                 for(int j=i+1; j < d.size(); j++){
                     sum +=  (Const.SimilarityMatrix[d.get(i) - 1][d.get(j) - 1] * 2);
                }
            }
        }

        return sum;
    }

    public Double[][] getDissimilarity() {
        return Dissimilarity;
    }

    public Double getIntraDisimilarity() {
        return intraDisimilarity;
    }

    /**
     * @param intraDisimilarity the intraDisimilarity to set
     */
    public void setIntraDisimilarity(Double intraDisimilarity) {
        this.intraDisimilarity = intraDisimilarity;
    }

    /**
     * @return the interDisimilarity
     */
    public Double getInterDisimilarity() {
        return interDisimilarity;
    }

    /**
     * @param interDisimilarity the interDisimilarity to set
     */
    public void setInterDisimilarity(Double interDisimilarity) {
        this.interDisimilarity = interDisimilarity;
    }
}
