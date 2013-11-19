package gaclustering;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import com.utils.ConnectionDB;
import java.io.FileInputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;

public class GetTreeFromDB {
  private String Name,Data,CH1;
  private FileInputStream InputFile;
  private int Count;
  private char CH;
  TreeMisc Misc=new TreeMisc();
  private Tree[] aTree;
  
  public GetTreeFromDB() throws InstantiationException, IllegalAccessException, SQLException {
    
      aTree = new Tree[Const.POPULATION];
      Statement stmt;
      Connection conn;
      ResultSet rs;
      ConnectionDB db = new ConnectionDB();
      db.createConnection();
      conn = (Connection) db.getConnection();
      stmt = (Statement) conn.createStatement();
      PreparedStatement statement = null;

      statement = conn.prepareStatement("SELECT rule_string FROM populations ");
      rs = statement.executeQuery();
      int i=0;
      while ( rs.next() ){
          Data = rs.getString("rule_string");
          aTree[i] = GenCommand();
          Count = 0;
          System.out.println("atree from gent command inGetTreeFromFile ");
          System.out.println(Misc.getStrTreeNoParentheses(aTree[i]));
          i++;
      }
  }

  public Tree getTree(int TreeNo) {
      
      return this.aTree[TreeNo];
  }

    private Tree GenCommand()
    {
          CH = Data.charAt(Count);
          
          String tmp="";
          Count++;
          Tree aTree = new Tree();
          if (CH == '(')
              {
                String value = String.valueOf(CH);
                while( Data.charAt(Count) != ')') {
                    value = value + Data.charAt(Count);
                    Count++;
                }
                value = value + ")";
                Count++;
                aTree.setValue(value);
              } //if
          else if (CH == '&') {
                aTree.setValue("&");
                aTree.setLeftChild(GenCommand());
                aTree.setRightChild(GenCommand());
            } //else
            
            return aTree;
    }
    
/**
    public static void main (String [] args) throws SQLException, InstantiationException, IllegalAccessException{
        GetTreeFromDB aGet = new GetTreeFromDB(0);
        Population[] aPopulation = new Population[Const.POPULATION];  
        
        for(int i = 0 ; i < Const.POPULATION ; i++) {
                  Tree atree = aGet.getTree(i);
                 // System.out.println("Tr{"+i+"}="+Misc.getStrTreeNoParentheses(atree));
                  aPopulation[i] = new Population();
                  aPopulation[i].setTree(atree);
                  TreeMisc tm = new TreeMisc();
                  System.out.println("Tr{"+i+"}="+tm.getStrTreeNoParentheses(aPopulation[i].getTree ()));

              }
    }
*/
  }