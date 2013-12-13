package gaclustering;

import com.utils.ImportData;
import com.utils.Purity;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import jxl.read.biff.BiffException;

public class clustering {

    private Double[][] Dissimilarity;
    private ArrayList<Integer> DataList;
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
                c.setDissimilarity(Const.SimilarityMatrix);
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
                System.out.println("ClusterNo: " + Const.NUM_OF_CLUSTER);

                System.out.println("K = " + (k.length / Const.MEDROID_LENGTH) + " == " + c.findCluster(clusterMedroid));

                for (ArrayList<Integer> x : clusterMedroid) {
                    System.out.println("result = " + x.size() + " - " + x);

                    System.out.println();
                }

                System.out.println("===============================\n");
                //c.findCluster(clusterMedroid);
                //c.printResult(clusterMedroid);
            }

        } catch (InstantiationException | IllegalAccessException | SQLException | IOException | BiffException ex) {
            Logger.getLogger(clustering.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public clustering() {
        DataList = new ArrayList<>();
        intraDisimilarity = 0.00;
        interDisimilarity = 0.00;
        DataList = new ArrayList<>();
        for (int i = 1; i <= Const.SimilarityMatrix.length; i++) {
            DataList.add(i);
        }

        elements = new Element[Const.TOTAL_RECORDS];

    }

    private void updateDataList(ArrayList<ArrayList<Integer>> clusterMedroid) {
        //System.out.println("data ");
        for (ArrayList<Integer> k : clusterMedroid) {
            for (int j = 0; j < k.size(); j++) {
                //System.out.println("Clean: " + k.get(j));
                DataList.remove(k.get(j));
            }
        }

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

        Double fitness = 0.0;
        for (int i = 0; i < clusterMedroid.size(); i++) {
            Double inter = calInterSim(clusterMedroid, i);
            Double intra = calIntraSim(clusterMedroid, i);
            fitness += inter - intra;
            //fitness +=  inter;
            //fitness +=  Math.pow(inter / intra, 2) ;
            //fitness += inter /  Math.pow(intra,2);
            //fitness += (inter) - (intra * Math.pow(clusterMedroid.size(),2) );
            //fitness += (inter /(clusterMedroid.size())) * Const.Weight  - intra;

        }


        //return fitness;
        return fitness;
    }

    public Double findCluster_(ArrayList<ArrayList<Integer>> clusterMedroid) {

        updateDataList(clusterMedroid);

        Double minAvg = 0.00;
        int minDataPosition = 0;
        int minClusterPosition = 0;
        while (!DataList.isEmpty()) {
            minAvg = 0.00;
            minDataPosition = 0;
            minClusterPosition = 0;
            for (int data : DataList) {
                for (int k = 0; k < clusterMedroid.size(); k++) {
                    Double sum = 0.0;
                    Double Avg = 0.0;
                    int clusterSize = clusterMedroid.get(k).size();
                    for (int i = 0; i < clusterSize; i++) {
                        sum += Const.SimilarityMatrix[data - 1][clusterMedroid.get(k).get(i) - 1];

                    }
                    Avg = sum / clusterSize;
                    if (minAvg == 0.0 || (Avg < minAvg)) {
                        minAvg = Avg;
                        minDataPosition = data;
                        minClusterPosition = k;

                    }
                }
            }


            // Update DataList
            for (int i = 0; i < DataList.size(); i++) {
                if (DataList.get(i) == minDataPosition || DataList.size() == 1) {
                    DataList.remove(i);
                }

            }
            //Assign to cluster

            if (!clusterMedroid.get(minClusterPosition).contains(minDataPosition)) {
                clusterMedroid.get(minClusterPosition).add(minDataPosition);
            }


        }



        boolean valid = true;
        for (ArrayList<Integer> k : clusterMedroid) {
            if (k.size() == 2) {
                valid = false;
            }
        }
        Double fitness = 0.0;
        if (valid) {
            for (int i = 0; i < clusterMedroid.size(); i++) {


                Double inter = calInterSim(clusterMedroid, i);
                Double intra = calIntraSim(clusterMedroid, i);
                fitness += inter - intra;
                //fitness +=  inter;
                //fitness +=  Math.pow(inter / intra, 2) ;
                //fitness += inter /  Math.pow(intra,2);
                //fitness += (inter) - (intra * Math.pow(clusterMedroid.size(),2) );
                //fitness += (inter /(clusterMedroid.size())) * Const.Weight  - intra;

            }
        }

        //return fitness;
        return fitness;
    }

    private Double calInterSim(ArrayList<ArrayList<Integer>> clusterMedroid, int i) {
        Double sum = 0.0;

        for (int k : clusterMedroid.get(i)) {
            for (int j = 0; j < clusterMedroid.size(); j++) {
                if (i != j) {
                    for (int k2 : clusterMedroid.get(j)) {
                        sum += Const.SimilarityMatrix[k - 1][k2 - 1];
                    }
                }
            }
        }
        //sum = sum /clusterMedroid.size();
        //System.out.println("Inter: " + sum);
        return sum;

    }

    private Double calIntraSim(ArrayList<ArrayList<Integer>> clusterMedroid, int i) {
        Double sum = 0.0;
        for (int k = 0; k < clusterMedroid.get(i).size(); k++) {
            for (int k2 = 0; k2 < clusterMedroid.get(i).size(); k2++) {
                if (k != k2) {
                    sum += Const.SimilarityMatrix[clusterMedroid.get(i).get(k) - 1][clusterMedroid.get(i).get(k2) - 1];
                }
            }
        }
        //sum =  clusterMedroid.get(i).size() / sum ;
        //System.out.println("Intra: " + sum);
        return sum;
    }

    public void printResult(ArrayList<ArrayList<Integer>> clusterMedroid) {
        setDissimilarity(Const.SimilarityMatrix);
        updateDataList(clusterMedroid);

        Double minAvg = 0.00;
        int minDataPosition = 0;
        int minClusterPosition = 0;
        while (!DataList.isEmpty()) {
            minAvg = 0.00;
            minDataPosition = 0;
            minClusterPosition = 0;
            for (int data : DataList) {
                for (int k = 0; k < clusterMedroid.size(); k++) {
                    Double sum = 0.0;
                    Double Avg = 0.0;
                    int clusterSize = clusterMedroid.get(k).size();
                    for (int i = 0; i < clusterSize; i++) {
                        sum += Const.SimilarityMatrix[data - 1][clusterMedroid.get(k).get(i) - 1];

                    }
                    Avg = sum / clusterSize;
                    if (minAvg == 0.0 || (Avg < minAvg)) {
                        minAvg = Avg;
                        minDataPosition = data;
                        minClusterPosition = k;

                    }
                }
            }

            // Update DataList
            for (int i = 0; i < DataList.size(); i++) {
                if (DataList.get(i) == minDataPosition || DataList.size() == 1) {
                    DataList.remove(i);
                }

            }
            //Assign to cluster
            if (!clusterMedroid.get(minClusterPosition).contains(minDataPosition)) {
                clusterMedroid.get(minClusterPosition).add(minDataPosition);
            }

        }


        for (int i = 0; i < clusterMedroid.size(); i++) {
            System.out.println("\n Output for : " + (i + 1) + " th, n = " + clusterMedroid.get(i).size());
            for (int j = 0; j < clusterMedroid.get(i).size(); j++) {
                System.out.print(clusterMedroid.get(i).get(j) + ", ");
            }
            System.out.println();

        }

        // Calculate cluster purity
        Purity P = new Purity();
        try {
            System.out.println("Cluster purity = " + P.callPurity(clusterMedroid));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(clustering.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(clustering.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public Double[][] getDissimilarity() {
        return Dissimilarity;
    }

    public void setDissimilarity(Double[][] Dissimilarity) {
        this.Dissimilarity = Dissimilarity;
        DataList = new ArrayList<>();
        for (int i = 1; i <= Const.SimilarityMatrix.length; i++) {
            DataList.add(i);
        }
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
