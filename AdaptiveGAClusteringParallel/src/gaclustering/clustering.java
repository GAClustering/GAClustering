package gaclustering;

import com.utils.ImportData;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import jxl.read.biff.BiffException;

public class clustering {

    private Double[][] Dissimilarity;
    //private ArrayList<Integer> DataList;
    private Double intraDisimilarity;
    private Double interDisimilarity;
    private Element elements[];

    public static void main(String[] args) {
        try {
            File file = new File("D:\\SIT\\Thesis\\data\\HeartData\\DissimilarityMatrix.csv");
            ImportData preprocess_data = new ImportData(file);
            Const.TOTAL_RECORDS = preprocess_data.ProcessData();
            Const.MEDROID_LENGTH = 3;

            //&&(118)(126)&&&(58)(59)&(90)(4)&(43)(66)
            //int[] allmedroid = {64,62,98,99};
            //int[] allmedroid = {91,74,99,70,142,65,51,78,55,92};
            int[][] allmedroid = {{26, 30, 70, 75, 120, 125},
                {27, 34, 46, 10, 36, 25}};

            for (int[] k : allmedroid) {
                clustering c = new clustering();
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
//                System.out.println("ClusterNo: " + Const.NUM_OF_CLUSTER);
//
                System.out.println("K = " + (k.length / Const.MEDROID_LENGTH) + " == " + c.findCluster(clusterMedroid));

//                for (ArrayList<Integer> x : clusterMedroid) {
//                    System.out.println("result = " + x.size() + " - " + x);
//
//                    System.out.println();
//                }
//
//                System.out.println("===============================\n");
                //c.findCluster(clusterMedroid);
                //c.printResult(clusterMedroid);
            }

        } catch (InstantiationException | IllegalAccessException | SQLException | IOException | BiffException ex) {
            Logger.getLogger(clustering.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public clustering() {
        //DataList = new ArrayList<>();
        intraDisimilarity = 0.00;
        interDisimilarity = 0.00;
        //DataList = new ArrayList<>();
//        for (int i = 1; i <= Const.SimilarityMatrix.length; i++) {
//            DataList.add(i);
//        }

        elements = new Element[Const.TOTAL_RECORDS];

    }

    public Double findCluster(ArrayList<ArrayList<Integer>> clusterMedroid) {

        int minID = 0;
        Double MinAvg = 0.0;
        int minClusterNo = 0;
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
        while (totalMember <= Const.TOTAL_RECORDS) {
            if (!clusterMedroid.get(minClusterNo).contains(minID + 1)) {
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

    private Double callInterSim(ArrayList<ArrayList<Integer>> clusterMedroid) {

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

     private Double calIntraSim(ArrayList<ArrayList<Integer>> clusterMedroid){
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
