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
        aPopulation.setChromosome(p.getChromosome());
        
    }
    
    public Object call() {
        aPopulation.calFitness();
        return aPopulation;
    }
}
