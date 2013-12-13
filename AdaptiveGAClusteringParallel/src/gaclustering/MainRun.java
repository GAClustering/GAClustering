/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gaclustering;

import com.utils.ImportData;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import jxl.read.biff.BiffException;

/**
 *
 * @author rato
 */
public class MainRun {

    public static void main(String[] args) {

        final ResourceBundle res = ResourceBundle.getBundle("com.config.SystemConfiguration");
        String FileName = res.getString("file.name");
        File file = new File(FileName);
        ImportData preprocess_data = new ImportData(file);
        try {
            Const.TOTAL_RECORDS = preprocess_data.ProcessData();
            System.out.println(Const.TOTAL_RECORDS);
        } catch (InstantiationException | IllegalAccessException | SQLException | IOException | BiffException ex) {
            Logger.getLogger(MainRun.class.getName()).log(Level.SEVERE, null, ex);
        }

        int popNo, length;
        popNo = (int) (0.1 * Const.TOTAL_RECORDS);

        if (popNo < 50) {
            Const.POPULATION = 50;
        } else if (popNo > 200) {
            Const.POPULATION = 200;
        } else {
            Const.POPULATION = popNo;
        }

        Const.SLACK_GEN = Integer.valueOf(res.getString("ga.SLACK_GEN"));
        //*********** percent of operation *************
        Const.PERCENT_SINGLE = Integer.valueOf(res.getString("ga.PERCENT_SINGLE"));
        Const.PERCENT_CROSSOVER = Integer.valueOf(res.getString("ga.PERCENT_CROSSOVER"));
        Const.PERCENT_REPRODUCTION = Integer.valueOf(res.getString("ga.PERCENT_REPRODUCTION"));
        Const.PERCENT_MUTATION = Integer.valueOf(res.getString("ga.PERCENT_MUTATION"));

        length = Const.NUM_OF_CLUSTER * (Const.TOTAL_RECORDS / 100);
        if (length < (2 * Const.NUM_OF_CLUSTER)) {
            Const.CHROMOSOME_LENGTH = length;
        } else {
            Const.CHROMOSOME_LENGTH = length;
        }

        Const.MEDROID_LENGTH = Const.CHROMOSOME_LENGTH / Const.NUM_OF_CLUSTER;

        MainRun m = new MainRun();
        m.run();
    }

    public void run() {

        Genetic aGenetic = new Genetic();
        Population bestPopulation = new Population();
        aGenetic.GenPop();
        aGenetic.printAllChromosomes();

        int current_slack = 0;
        int total_slack = 0;
        int current_mode = 1;

        System.out.println("Mode#: " + current_mode);
        aGenetic.Execute();
        bestPopulation.setChromosome(aGenetic.bestPop().getChromosome());
        bestPopulation.setFitness(aGenetic.bestPop().getFitness());

        java.util.Date start_time = new java.util.Date();

        while (total_slack < (Const.SLACK_GEN * 4)) {
            //while ( bestPopulation.getFitness() <= 4020) {

            if (current_mode == 1) {
                aGenetic.Reproduction();
            } else if (current_mode == 2) {
                aGenetic.Reproduction2();
            } else {
                aGenetic.Reproduction3();

            }

            aGenetic.Execute();

            if (aGenetic.bestPop().getFitness() > bestPopulation.getFitness()) {

                current_slack = 0;

                bestPopulation.setChromosome(aGenetic.bestPop().getChromosome());
                bestPopulation.setFitness(aGenetic.bestPop().getFitness());
                bestPopulation.setClusterResult(aGenetic.bestPop().getClusterResult());
                System.out.println("\n Best Chrosome = " + bestPopulation.getFitness() + " = " + Arrays.toString(bestPopulation.getChromosome()));

            } else {
                current_slack++;
                System.out.print("|");

            }

            if (current_slack >= Const.SLACK_GEN) {

                current_mode++;
                if (current_mode > 3) {
                    current_mode = 1;
                }

                total_slack += current_slack;
                current_slack = 0;
                //aGenetic.printAllChromosomes();
                System.out.println("\n Move to mode# " + current_mode);
            }

        }

        try {
            bestPopulation.printResult();

        } catch (IOException ex) {
            Logger.getLogger(Run.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("Fitness " + bestPopulation.getFitness());
        java.util.Date end_time = new java.util.Date();

        System.out.println("Start: " + new Timestamp(start_time.getTime()));
        System.out.println("End: " + new Timestamp(end_time.getTime()));
        Double secs = new Double((end_time.getTime() - start_time.getTime()) * 0.001);
        System.out.println("Time processed: " + secs);
    }

}
