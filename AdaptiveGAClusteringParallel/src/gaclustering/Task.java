package gaclustering;

import java.util.concurrent.Callable;

/**
 *
 * @author PELE
 */
public class Task implements Callable {

    private Population aPopulation;
    private int seq;

    public Task() {
    }

    public Task(Population p) {
        aPopulation = new Population();
        aPopulation = p;

    }

    public Object call() {
        Double fitness = 0.0;

        fitness = aPopulation.calFitness();

        return fitness;
    }
}
