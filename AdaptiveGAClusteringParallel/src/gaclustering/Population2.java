package gaclustering;
import com.beans.data_converted;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Population2{
  private Double Fitness;
  private Tree aTree;
  
  ArrayList<Integer> dataNode;

  public Population2(){
            aTree = new Tree();
            Fitness = 0.0;
  }
         

  public void setFitness(Double Value){
          this.Fitness = Value;
      }
  
  public Double getFitness(){
          return Fitness;
      }
  
  public void setTree(Tree Value){
	
          this.aTree = Value;
		  
      }
  public Tree getTree(){
      return this.aTree;
  }


  public Double calFitness(){
        Double fitness = 0.0;
        if(aTree.isValid()){
            dataNode = new ArrayList<Integer>();
            GetClusterPrototype(aTree);

            clustering c = new clustering();
            c.setDissimilarity(Const.SimilarityMatrix);

            ArrayList<ArrayList<Integer>> clusterMedroid = new ArrayList<>();
            int clusterNo=0;
            ArrayList<Integer> d = new ArrayList<>();
            for (int i = 1; i <= dataNode.size(); i++) {
                d.add(dataNode.get(i-1));
                if (i%2 == 0){
                    clusterMedroid.add(clusterNo, d);
                    clusterNo++;
                    d = new ArrayList<>();

                }
            }

            fitness = c.findCluster(clusterMedroid);

         }
        else{
            //Const.countInvalid++;
            //System.out.println("Invalid Tree# " + Const.countInvalid);
        }
         return fitness;
    }
  
  private void GetClusterPrototype(Tree aTree){
    
      if (aTree.getNodeType() == 0){
          GetClusterPrototype(aTree.LeftChild);
          GetClusterPrototype(aTree.RightChild);
      }
      else {
          int value = Integer.parseInt(aTree.getValue().replaceAll("\\(", "").replaceAll("\\)", ""));
          dataNode.add(value); 
      }
      
  }
       
 
  public void printResult(){
        dataNode = new ArrayList<Integer>();
        GetClusterPrototype(aTree);

        clustering c = new clustering();

        c.setDissimilarity(Const.SimilarityMatrix);
        ArrayList<ArrayList<Integer>> clusterMedroid = new ArrayList<>();
        int clusterNo=0;
        ArrayList<Integer> d = new ArrayList<>();
        for (int i = 1; i <= dataNode.size(); i++) {
            d.add(dataNode.get(i-1));
            if (i%2 == 0){
                clusterMedroid.add(clusterNo, d);
                clusterNo++;
                d = new ArrayList<>();

            }
        }

        c.printResult(clusterMedroid);
    
      
  }
  
}