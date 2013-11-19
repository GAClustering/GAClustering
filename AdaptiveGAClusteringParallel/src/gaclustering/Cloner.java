package   gaclustering;

public class Cloner {
  private TreeMisc aMisc;
  public Cloner()
  {
    aMisc = new TreeMisc();
  }
  public Tree Clone(Tree inTree)
  {
      GetTreeFromStr aTreeFromStr = new  GetTreeFromStr();
      
      //Tree newTree = aTreeFromStr.Get_Tree_From_Str(aMisc.getStrTreeNoParentheses(inTree));
      Tree newTree = inTree;
      
      return newTree;
  }
}