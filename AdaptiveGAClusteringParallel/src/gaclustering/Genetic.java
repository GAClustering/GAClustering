package gaclustering;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Genetic {

    private Population aPopulation[];
    private Random rand;
    private ArrayList<Integer> DataSet;
    private ArrayList<Integer> DataSetPool;
    private String Data;
    private Integer size = 0;
    private Integer bestPos;
    private ExecutorService eservice;

    public Genetic() {
        rand = new Random();
        DataSet = new ArrayList<Integer>();
        for (int i = 1; i <= Const.TOTAL_RECORDS; i++) {
            DataSet.add(i);
        }
        aPopulation = new Population[Const.POPULATION];
        bestPos = 0;

        int nrOfProcessors = Runtime.getRuntime().availableProcessors();
        eservice = Executors.newFixedThreadPool(nrOfProcessors);
    }

    public void GenPop() {

        for (int i = 0; i < Const.POPULATION; i++) {
            DataSetPool = new ArrayList<Integer>(DataSet);
            aPopulation[i] = new Population();
            aPopulation[i].setChromosome(Gencommand());


        }
    }

    private int[] Gencommand() {

        int Chromosome[] = new int[Const.CHROMOSOME_LENGTH];

        for (int i = 0; i < Const.CHROMOSOME_LENGTH; i++) {
            int nextRand = rand.nextInt(DataSetPool.size());
            Chromosome[i] = DataSetPool.remove(nextRand);
        }


        return Chromosome;

    }

    public Double getFitness() {
        return this.aPopulation[0].getFitness();
    }

    public Population bestPop() {

        return this.aPopulation[bestPos];
    }

    public void Execute() {


        List< Future> futuresList = new ArrayList< Future>();
        for (int index = 0; index < Const.POPULATION; index++) {
            futuresList.add(eservice.submit(new Task(this.aPopulation[index])));
        }

        Object taskResult;
        Double bestFitness = 0.0;
        for (int i = 0; i < futuresList.size(); i++) {

            try {

                //Population aPop = new Population();
                taskResult = futuresList.get(i).get();
                Population aPop = (Population) taskResult;
                this.aPopulation[i].setFitness(aPop.getFitness());
                this.aPopulation[i].setChromosome(aPop.getChromosome());
                this.aPopulation[i].setClusterResult(aPop.getClusterResult());
                if (aPop.getFitness() > bestFitness) {
                    bestFitness = aPop.getFitness();
                    bestPos = i;
                }
            } catch (InterruptedException | ExecutionException ex) {
                Logger.getLogger(Genetic.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        /*
         * Enable for parallel processing
         /*  
         Double fitness = 0.0;
         for(int i = 0 ; i < Const.POPULATION ; i++) {
         fitness = this.aPopulation[i].calFitness();
         this.aPopulation[i].setFitness(fitness);
         }
         */
    }

    public void printAllChromosomes() {

        for (Population p : aPopulation) {
            System.out.println(p.getFitness() + " = " + Arrays.toString(p.getChromosome()));
        }

    }

    public void Sort() {
        int i, j;
        Population tmpSortPopulation = new Population();
        //****************** Bubble Sort *********************
        for (i = 0; i < Const.POPULATION - 1; i++) {
            for (j = i + 1; j < Const.POPULATION; j++) {
                if (aPopulation[i].getFitness() < aPopulation[j].getFitness()) {

                    tmpSortPopulation.setChromosome(this.aPopulation[i].getChromosome());
                    tmpSortPopulation.setFitness(this.aPopulation[i].getFitness());

                    this.aPopulation[i].setChromosome(this.aPopulation[j].getChromosome());
                    this.aPopulation[i].setFitness(this.aPopulation[j].getFitness());
                    this.aPopulation[j].setChromosome(tmpSortPopulation.getChromosome());
                    this.aPopulation[j].setFitness(tmpSortPopulation.getFitness());
                }//if
            } //for
        } //for

    }

// Mode1
    public void Reproduction() {


        this.Sort();

        Population newPopulation[];
        newPopulation = new Population[Const.POPULATION];

        int i = 0;
        int tmpval1, tmpval2;
        tmpval1 = tmpval2 = 0;
        // Single
        tmpval1 = (Const.PERCENT_SINGLE * Const.POPULATION) / 100;

        // Single, copy the best chromosomes
        while (i < tmpval1) {
            newPopulation[i] = new Population();
            newPopulation[i].setChromosome(aPopulation[i].getChromosome());
            i++;
        }



        tmpval1 = tmpval1 + ((Const.PERCENT_CROSSOVER * Const.POPULATION) / 100);

        int j = 0;
        while (i < tmpval1) {
            newPopulation[i] = new Population();
            newPopulation[i + 1] = new Population();



            //System.out.println("Selection: " + parent1 + " <::> " + parent2);

            newPopulation[i].setChromosome(aPopulation[j].getChromosome());
            newPopulation[i + 1].setChromosome(aPopulation[j + 1].getChromosome());

            //System.out.println("Crossover parent 1 = " +  Arrays.toString(newPopulation[i].getChromosome()) );
            //System.out.println("Crossover parent 2 = " +  Arrays.toString(newPopulation[i+1].getChromosome()) );


            CossoverRand(newPopulation[i], newPopulation[i + 1]);

            // System.out.println("Crossover offspring 1 = " +  Arrays.toString(newPopulation[i].getChromosome()) );
            //System.out.println("Crossover offspring 2 = " +  Arrays.toString(newPopulation[i+1].getChromosome()) );
            // System.out.println();


            i = i + 2;
            j = j + 2;
        }


        // Reproduce
        while (i < Const.POPULATION) {
            DataSetPool = new ArrayList<Integer>(DataSet);
            newPopulation[i] = new Population();
            newPopulation[i].setChromosome(Gencommand());
            i++;
        }



        // Random selection and mutate
        i = 0;
        int mutate_percentage = (Const.PERCENT_MUTATION * Const.POPULATION) / 100;
        while (i < mutate_percentage) {
            Mutate(newPopulation[rand.nextInt(Const.POPULATION)]);
            i++;
        }


        // Copy to new generation  
        for (i = 0; i < Const.POPULATION; i++) {
            this.aPopulation[i].setChromosome(newPopulation[i].getChromosome());
        }

    }

    // Mode2
    public void Reproduction2() {

        Population newPopulation[];
        newPopulation = new Population[Const.POPULATION];


        //System.out.println("Parents");
        //printAllChromosomes();

        double sumFitness = SumFitness();


        int tmpval1 = Const.POPULATION - (Const.PERCENT_REPRODUCTION * Const.POPULATION) / 100;
        //System.out.println("sumFitness: " + sumFitness);
        int i = 0;

        // RouletteWheel selection and crossover
        while (i < tmpval1) {
            newPopulation[i] = new Population();
            newPopulation[i + 1] = new Population();

            int parent1 = RouletteWheel(rand.nextDouble() * sumFitness);
            int parent2 = RouletteWheel(rand.nextDouble() * sumFitness);
            while (parent1 == parent2) {
                parent2 = RouletteWheel(rand.nextDouble() * sumFitness);
            }

            //System.out.println("Selection: " + parent1 + " <::> " + parent2);

            newPopulation[i].setChromosome(aPopulation[parent1].getChromosome());
            newPopulation[i + 1].setChromosome(aPopulation[parent2].getChromosome());

            //System.out.println("Crossover parent 1 = " +  Arrays.toString(newPopulation[i].getChromosome()) );
            // System.out.println("Crossover parent 2 = " +  Arrays.toString(newPopulation[i+1].getChromosome()) );


            CossoverRand(newPopulation[i], newPopulation[i + 1]);

            //System.out.println("Crossover offspring 1 = " +  Arrays.toString(newPopulation[i].getChromosome()) );
            //System.out.println("Crossover offspring 2 = " +  Arrays.toString(newPopulation[i+1].getChromosome()) );
            //System.out.println();


            i = i + 2;
        }

        // Reproduce
        for (i = tmpval1; i < Const.POPULATION; i++) {
            DataSetPool = new ArrayList<Integer>(DataSet);
            newPopulation[i] = new Population();
            newPopulation[i].setChromosome(Gencommand());
        }



        // Random selection and mutate
        i = 0;
        int mutate_percentage = (Const.PERCENT_MUTATION * Const.POPULATION) / 100;
        while (i < mutate_percentage) {
            Mutate(newPopulation[rand.nextInt(Const.POPULATION)]);
            i++;
        }




        // Copy to new generation  
        for (i = 0; i < Const.POPULATION; i++) {
            this.aPopulation[i].setChromosome(newPopulation[i].getChromosome());
        }

        //System.out.println("Offspring");
        //printAllChromosomes();



    }

    // Mode3
    public void Reproduction3() {

        Population newPopulation[];
        newPopulation = new Population[Const.POPULATION];


        //System.out.println("Parents");
        //printAllChromosomes();


        int tmpval1 = Const.POPULATION - (Const.PERCENT_REPRODUCTION * Const.POPULATION) / 100;
        //System.out.println("sumFitness: " + sumFitness);
        int i = 0;

        // Random selection and crossover
        while (i < tmpval1) {
            newPopulation[i] = new Population();
            newPopulation[i + 1] = new Population();

            int parent1 = rand.nextInt(Const.POPULATION);
            int parent2 = rand.nextInt(Const.POPULATION);
            while (parent1 == parent2) {
                parent2 = rand.nextInt(Const.POPULATION);
            }
            // System.out.println("Selection: " + parent1 + " :: " + parent2);

            newPopulation[i].setChromosome(aPopulation[parent1].getChromosome());
            newPopulation[i + 1].setChromosome(aPopulation[parent2].getChromosome());

            //System.out.println("Crossover parent 1 = " +  Arrays.toString(newPopulation[i].getChromosome()) );
            //System.out.println("Crossover parent 2 = " +  Arrays.toString(newPopulation[i+1].getChromosome()) );


            Cossover2Point(newPopulation[i], newPopulation[i + 1]);

            // System.out.println("Crossover offspring 1 = " +  Arrays.toString(newPopulation[i].getChromosome()) );
            // System.out.println("Crossover offspring 2 = " +  Arrays.toString(newPopulation[i+1].getChromosome()) );
            // System.out.println();


            i = i + 2;
        }
        // Reproduce
        for (i = tmpval1; i < Const.POPULATION; i++) {
            DataSetPool = new ArrayList<Integer>(DataSet);
            newPopulation[i] = new Population();
            newPopulation[i].setChromosome(Gencommand());
        }

        // Random selection and mutate
        i = 0;
        int mutate_percentage = (Const.PERCENT_MUTATION * Const.POPULATION) / 100;
        while (i < mutate_percentage) {
            Mutate(newPopulation[rand.nextInt(Const.POPULATION)]);
            i++;
        }




        // Copy to new generation  
        for (i = 0; i < Const.POPULATION; i++) {
            this.aPopulation[i].setChromosome(newPopulation[i].getChromosome());
        }

        //System.out.println("Offspring");
        //printAllChromosomes();



    }

    private void Cossover(Population p1, Population p2) {

        int parent1[] = p1.getChromosome();
        int parent2[] = p2.getChromosome();

        int OffSpring1[] = new int[parent1.length];
        int OffSpring2[] = new int[parent1.length];


        //System.out.println("Parent1: " + Arrays.toString(parent1));
        //System.out.println("Parent2: " + Arrays.toString(parent2));

        int onePointPos;
        onePointPos = parent1.length / 2;


        System.arraycopy(parent1, 0, OffSpring1, 0, onePointPos);
        System.arraycopy(parent2, onePointPos, OffSpring1, onePointPos, parent2.length - onePointPos);

        System.arraycopy(parent2, 0, OffSpring2, 0, onePointPos);
        System.arraycopy(parent1, onePointPos, OffSpring2, onePointPos, parent1.length - onePointPos);

        //System.out.println("Offspring1: " + Arrays.toString(OffSpring1));
        //System.out.println("Offspring2: " + Arrays.toString(OffSpring2));

        p1.setChromosome(OffSpring1);
        p2.setChromosome(OffSpring2);


    }

    private void CossoverRand(Population p1, Population p2) {

        int parent1[] = p1.getChromosome();
        int parent2[] = p2.getChromosome();

        int OffSpring1[] = new int[parent1.length];
        int OffSpring2[] = new int[parent1.length];


        //System.out.println("Parent1: " + Arrays.toString(parent1));
        //System.out.println("Parent2: " + Arrays.toString(parent2));

        int onePointPos = rand.nextInt(parent1.length - 1) + 1;

        System.arraycopy(parent1, 0, OffSpring1, 0, onePointPos);
        System.arraycopy(parent2, onePointPos, OffSpring1, onePointPos, parent2.length - onePointPos);

        System.arraycopy(parent2, 0, OffSpring2, 0, onePointPos);
        System.arraycopy(parent1, onePointPos, OffSpring2, onePointPos, parent1.length - onePointPos);

        //System.out.println("Offspring1: " + Arrays.toString(OffSpring1));
        //System.out.println("Offspring2: " + Arrays.toString(OffSpring2));

        p1.setChromosome(OffSpring1);
        p2.setChromosome(OffSpring2);


    }

    private void Cossover2Point(Population p1, Population p2) {

        int parent1[] = p1.getChromosome();
        int parent2[] = p2.getChromosome();

        int OffSpring1[] = new int[parent1.length];
        int OffSpring2[] = new int[parent1.length];


        //System.out.println("Parent1: " + Arrays.toString(parent1));
        //System.out.println("Parent2: " + Arrays.toString(parent2));

        int crossoverPoint1 = rand.nextInt(parent1.length - 1) + 1;
        int crossoverPoint2 = rand.nextInt(parent1.length - 1) + 1;

        while (Math.abs(crossoverPoint1 - crossoverPoint2) < 1) {
            crossoverPoint2 = rand.nextInt(parent1.length - 1) + 1;
        }

        if (crossoverPoint1 < crossoverPoint2) {

            System.arraycopy(parent1, crossoverPoint1, OffSpring1, crossoverPoint1, (crossoverPoint2 - crossoverPoint1));
            System.arraycopy(parent2, 0, OffSpring1, 0, crossoverPoint1);
            System.arraycopy(parent2, crossoverPoint2, OffSpring1, crossoverPoint2, parent1.length - crossoverPoint2);

            System.arraycopy(parent2, crossoverPoint1, OffSpring2, crossoverPoint1, (crossoverPoint2 - crossoverPoint1));
            System.arraycopy(parent1, 0, OffSpring2, 0, crossoverPoint1);
            System.arraycopy(parent1, crossoverPoint2, OffSpring2, crossoverPoint2, parent2.length - crossoverPoint2);

        } else {
            System.arraycopy(parent1, crossoverPoint2, OffSpring1, crossoverPoint2, (crossoverPoint1 - crossoverPoint2));
            System.arraycopy(parent2, 0, OffSpring1, 0, crossoverPoint2);
            System.arraycopy(parent2, crossoverPoint1, OffSpring1, crossoverPoint1, parent1.length - crossoverPoint1);

            System.arraycopy(parent2, crossoverPoint2, OffSpring2, crossoverPoint2, (crossoverPoint1 - crossoverPoint2));
            System.arraycopy(parent1, 0, OffSpring2, 0, crossoverPoint2);
            System.arraycopy(parent1, crossoverPoint1, OffSpring2, crossoverPoint1, parent2.length - crossoverPoint1);
        }

        p1.setChromosome(OffSpring1);
        p2.setChromosome(OffSpring2);


    }

    private void Mutate(Population p1) {

        int parent1[] = p1.getChromosome();

        int MutatePos = rand.nextInt(parent1.length);

        parent1[MutatePos] = DataSet.get(rand.nextInt(DataSet.size()));


        p1.setChromosome(parent1);
    }

    private double SumFitness() {

        double sum = 0.0;

        for (Population p : aPopulation) {
            if (p.getFitness() > 0) {
                sum += p.getFitness();
            }

        }

        return sum;

    }

    public static void main(String[] args) {
        Const.TOTAL_RECORDS = 500;
        Const.NUM_OF_CLUSTER = 2;


        int popNo, length;

        popNo = (int) (0.1 * Const.TOTAL_RECORDS);

        if (popNo < 50) {
            Const.POPULATION = 50;
        } else if (popNo > 200) {
            Const.POPULATION = 200;
        } else {
            Const.POPULATION = popNo;
        }

        length = (Const.NUM_OF_CLUSTER * Const.TOTAL_RECORDS) / 100;


        if (length > (2 * Const.NUM_OF_CLUSTER)) {
            Const.CHROMOSOME_LENGTH = length;
        } else {
            Const.CHROMOSOME_LENGTH = (2 * Const.NUM_OF_CLUSTER);
        }

        Genetic aGenetic = new Genetic();
        aGenetic.GenPop();


    }

    private int RouletteWheel(double Rand) {

        //System.out.println("Random Sum: " + Rand);
        double sum = 0.0;

        int i = 0;


        while (true) {



            if (aPopulation[i].getFitness() > 0) {
                sum += aPopulation[i].getFitness();
            }


            if (sum >= Rand) {
                return i;
            }

            i++;
            if ((i == aPopulation.length) && (sum < Rand)) {
                //reset i
                i = 0;
                System.out.println("RESET i Rand = " + Rand + "sum = " + sum);
            }


        }


//        while ( sum < Rand) {
//            
//            
//            sum += aPopulation[i].getFitness();
//            i++;
//            if ( (i == aPopulation.length) && (sum < Rand) ){
//                //reset i
//                i=0;
//                System.out.println("RESET i Rand = " + Rand + "sum = " + sum);
//            }
//            
//
//        }



//        for (int i=0; i<aPopulation.length;i++ ){
//            sum += aPopulation[i].getFitness();
//            if (sum >= Rand) {
//                return i;
//            }
//        }
//        


    }
}