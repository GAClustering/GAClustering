/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gaclustering;

import java.util.ArrayList;

/**
 *
 * @author PELE
 */
public class Element {

    private ArrayList<Double> distance;
    private int ID;
    private Double minAvg;
    private int minClusterPosition;
    private boolean is_moved;

    public Element(int ID) {
        distance = new ArrayList<Double>();
        this.ID = ID;
        is_moved = false;
    }

    public void intitial(ArrayList<ArrayList<Integer>> clusterMedroid) {
        setMinAvg((Double) 0.0);
        setMinClusterPosition(0);
        for (int k = 0; k < clusterMedroid.size(); k++) {

            if (!clusterMedroid.get(k).contains(ID+1)) {
                Double sum = 0.0;
                Double Avg = 0.0;
                int clusterSize = clusterMedroid.get(k).size();
                for (int i = 0; i < clusterSize; i++) {
                    sum += Const.SimilarityMatrix[getID()][clusterMedroid.get(k).get(i) - 1];
                }
                Avg = sum / clusterSize;

                if (getMinAvg() == 0.0 || (Avg < getMinAvg())) {
                    setMinAvg(Avg);
                    setMinClusterPosition(k);
                }
                getDistance().add(Avg);
            }
            else {
                is_moved = true;
            }

        }

    }

    public void updateDistance(int moveElementID, int clusterNo, int clusterSize) {
        Double old = distance.get(clusterNo) * (clusterSize - 1);
        Double New = (old + Const.SimilarityMatrix[getID()][moveElementID]) / (clusterSize);

        distance.set(clusterNo, New);
        if (New < getMinAvg()) {
            setMinAvg(New);
            setMinClusterPosition(clusterNo);
        }
    }

    /**
     * @return the distance
     */
    public ArrayList<Double> getDistance() {
        return distance;
    }

    /**
     * @param distance the distance to set
     */
    public void setDistance(ArrayList<Double> distance) {
        this.distance = distance;
    }

    /**
     * @return the ID
     */
    public int getID() {
        return ID;
    }

    /**
     * @param ID the ID to set
     */
    public void setID(int ID) {
        this.ID = ID;
    }

    /**
     * @return the minAvg
     */
    public Double getMinAvg() {
        return minAvg;
    }

    /**
     * @param minAvg the minAvg to set
     */
    public void setMinAvg(Double minAvg) {
        this.minAvg = minAvg;
    }

    /**
     * @return the minClusterPosition
     */
    public int getMinClusterPosition() {
        return minClusterPosition;
    }

    /**
     * @param minClusterPosition the minClusterPosition to set
     */
    public void setMinClusterPosition(int minClusterPosition) {
        this.minClusterPosition = minClusterPosition;
    }

    /**
     * @return the is_moved
     */
    public boolean isIs_moved() {
        return is_moved;
    }

    /**
     * @param is_moved the is_moved to set
     */
    public void setIs_moved(boolean is_moved) {
        this.is_moved = is_moved;
    }
}
