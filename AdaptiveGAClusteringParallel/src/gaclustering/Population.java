package gaclustering;

import au.com.bytecode.opencsv.CSVWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Population {

    private Double Fitness;
    private int Chromosome[];
    private clustering c;
    ArrayList<Integer> dataNode;
    private ArrayList<ArrayList<Integer>> clusterResult;

    public Population() {

        Chromosome = new int[Const.CHROMOSOME_LENGTH];
        Fitness = 0.0;
        clusterResult = new ArrayList<>();
        c = new clustering();
    }

    public void setFitness(Double Value) {
        this.Fitness = Value;
    }

    public Double getFitness() {
        return Fitness;
    }

    private boolean isInvalid() {
        // Check duplicate
        boolean dup = false;
        for (int i = 0; i < Chromosome.length; i++) {
            for (int j = 0; j < Chromosome.length; j++) {
                if (i != j && Chromosome[i] == Chromosome[j]) {
                    dup = true;
                }
            }
        }

        return dup;
    }

    public Double calFitness() {

        if (isInvalid()) {
            //System.out.println("Invalid!!");

            this.setChromosome(Gencommand());

        }

        Double fitness = 0.0;

        ArrayList<ArrayList<Integer>> clusterMedroid = new ArrayList<>();
        int clusterNo = 0;
        ArrayList<Integer> d = new ArrayList<>();
        for (int i = 0; i < Chromosome.length; i++) {
            d.add(Chromosome[i]);

            if ((i + 1) % Const.MEDROID_LENGTH == 0) {
                clusterMedroid.add(clusterNo, d);

                clusterNo++;
                d = new ArrayList<>();
            }
        }

        fitness = c.findCluster(clusterMedroid);

        setClusterResult(clusterMedroid);
        return fitness;
    }

    public void printResult() throws IOException {

        CSVWriter writer = new CSVWriter(new FileWriter("output_" + this.getFitness() + ".csv"));
        for (int i = 0; i < getClusterResult().size(); i++) {
            System.out.println("\n Output for : " + (i + 1) + " th, n = " + getClusterResult().get(i).size());
            for (int j = 0; j < getClusterResult().get(i).size(); j++) {
                System.out.print(getClusterResult().get(i).get(j) + ", ");
            }
            writer.writeNext(getClusterResult().get(i).toString());
            System.out.println();

        }
        writer.close();

    }

    /**
     * @return the Chromosome
     */
    public int[] getChromosome() {
        return Chromosome;
    }

    /**
     * @param Chromosome the Chromosome to set
     */
    public void setChromosome(int[] Chromosome) {
        this.Chromosome = Chromosome;
    }

    private int[] Gencommand() {

        //System.out.println("INVALID CHROMOSOME IS REPLACED!!!");
        int tmpChromosome[] = new int[Const.CHROMOSOME_LENGTH];
        ArrayList<Integer> DataSet = new ArrayList<Integer>();
        for (int i = 1; i <= Const.TOTAL_RECORDS; i++) {
            DataSet.add(i);
        }
        Random rand = new Random();
        for (int i = 0; i < Const.CHROMOSOME_LENGTH; i++) {
            int nextRand = rand.nextInt(DataSet.size());
            tmpChromosome[i] = DataSet.remove(nextRand);
        }

        return tmpChromosome;

    }

    /**
     * @return the clusterResult
     */
    public ArrayList<ArrayList<Integer>> getClusterResult() {
        return clusterResult;
    }

    /**
     * @param clusterResult the clusterResult to set
     */
    public void setClusterResult(ArrayList<ArrayList<Integer>> clusterResult) {
        this.clusterResult = clusterResult;
    }

}
