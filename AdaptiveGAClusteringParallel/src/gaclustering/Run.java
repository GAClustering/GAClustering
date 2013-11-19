
package gaclustering;

import com.utils.Purity;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author PELE
 */
public class Run extends Thread {

    public Run(){
    
    }

        @Override
    public void run() {

            AGAC.progessBar.setMinimum(0);
            AGAC.progessBar.setMaximum(Const.SLACK_GEN);
            Genetic aGenetic = new Genetic();
            Population bestPopulation = new Population();
            aGenetic.GenPop();
            aGenetic.printAllChromosomes();

            
            int current_slack =0;
            int total_slack = 0;
            int current_mode = 1;

            AGAC.outputText.append("Mode#: " + current_mode + "\n");
            AGAC.outputText.setCaretPosition(AGAC.outputText.getText().length()-1);
                    
            aGenetic.Execute();
            bestPopulation.setChromosome(aGenetic.bestPop().getChromosome());
            bestPopulation.setFitness(aGenetic.bestPop().getFitness());
            
            
            java.util.Date start_time= new java.util.Date();
            
            
            while ( total_slack < (Const.SLACK_GEN * 4) ) {
            //while ( bestPopulation.getFitness() <= 4020) {
                
                if (current_mode == 1) {
                    aGenetic.Reproduction();
                }
                else if (current_mode == 2) {
                    aGenetic.Reproduction2();
                }
                else {
                    aGenetic.Reproduction3();

                }
                
                
                aGenetic.Execute();
                
                
                if ( aGenetic.bestPop().getFitness() > bestPopulation.getFitness()) {
 
                    current_slack = 0;
                    
                    
                    bestPopulation.setChromosome(aGenetic.bestPop().getChromosome());
                    bestPopulation.setFitness(aGenetic.bestPop().getFitness());
                    bestPopulation.setClusterResult(aGenetic.bestPop().getClusterResult());
                    AGAC.outputText.append("Best Chrosome = " 
                            + bestPopulation.getFitness() + " = " 
                            + Arrays.toString(bestPopulation.getChromosome()) + "  (mode: " + current_mode + ")\n");
                    AGAC.outputText.setCaretPosition(AGAC.outputText.getText().length()-1);

                    System.out.println( "Best Chrosome = " + bestPopulation.getFitness() + " = " + Arrays.toString(bestPopulation.getChromosome()));


                }
                else {
                    current_slack++;
                    
                }

                if ( current_slack >= Const.SLACK_GEN ) {
                    
                    current_mode++;
                    if (current_mode > 3) {
                        current_mode = 1;
                    }
                    
                    total_slack += current_slack;
                    current_slack = 0;
                    //aGenetic.printAllChromosomes();
                    System.out.println("Move to mode# " + current_mode);
                    AGAC.outputText.append("Move to mode# " + current_mode + "\n");
                    AGAC.outputText.setCaretPosition(AGAC.outputText.getText().length()-1);
                }
                
                
                AGAC.progessBar.setValue(current_slack);
                AGAC.progessBar.repaint();
                
                
                
            }
            
            
          bestPopulation.printResult();
          
          
        // Calculate cluster purity
        Purity P = new Purity();
        try {        
            System.out.println("Cluster purity = " + P.callPurity(bestPopulation.getClusterResult()));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(clustering.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(clustering.class.getName()).log(Level.SEVERE, null, ex);
        }
          
          
          
          System.out.println( "Fitness " + bestPopulation.getFitness());
          
          
          //aGenetic.printAllChromosomes();
          
          //aGenetic.printAllChromosomes();
          AGAC.outputText.append("\n==== Finished====\n");
          AGAC.outputText.setCaretPosition(AGAC.outputText.getText().length()-1);
          
          java.util.Date end_time= new java.util.Date();
          
          System.out.println("Start: " + new Timestamp(start_time.getTime()));
          System.out.println("End: " + new Timestamp(end_time.getTime()));
          
          
          
          AGAC.outputText.append("Start: " + new Timestamp(start_time.getTime()) + "\n");
          AGAC.outputText.append("End: " + new Timestamp(end_time.getTime()) + "\n");
          AGAC.outputText.setCaretPosition(AGAC.outputText.getText().length()-1);
          Double secs = new Double((end_time.getTime() - start_time.getTime()) * 0.001);
          AGAC.outputText.append("Time processed: " + secs + " Sec.\n");
          AGAC.outputText.setCaretPosition(AGAC.outputText.getText().length()-1);
          
          
          
          
          JOptionPane.showMessageDialog(null, "Done" );
          
    }
}
