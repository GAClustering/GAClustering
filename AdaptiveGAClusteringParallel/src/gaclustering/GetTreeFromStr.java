package gaclustering;
import javax.swing.*;
public class GetTreeFromStr {
  private String Name,Data;
  private int Count;
  private char CH;
  public GetTreeFromStr() {}
  public Tree Get_Tree_From_Str(String  InData)
  {
        Count = 0;
        Data = InData;
        //System.out.println("Data = " + Data);
        Tree aTree = GenCommand();
          return aTree;
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
                //Count++;
                System.out.println(value);
                aTree.setValue(value);
              } //if
          else if (CH == '&') {
                aTree.setValue("&");
                aTree.setLeftChild(GenCommand());
                aTree.setRightChild(GenCommand());
            } //else
            
            return aTree;
    }
  }