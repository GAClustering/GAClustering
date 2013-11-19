package gaclustering;
public class TreeMisc {
  private int  count;
   private String Data;
  public TreeMisc()  {}
  public int getSize(Tree _aTree)
  {
      count = 0;
      PutNode(_aTree);
      return count;
  }
  /*public String getStrTree(Tree _aTree)
  {
            Data = "";
            PutNodeToStr(_aTree);
            return Data;
  }*/
 public String getStrTreeNoParentheses(Tree _aTree)
  {
            Data = "";
            PutNodeToStrNoParentheses(_aTree);
            System.out.println("getStrTreeNoParentheses= " +Data);
            
            return Data;
  }
  private void  PutNode(Tree _aTree)
           {
              //System.out.println  ("Node no. "+count+" is"+_aTree.getValue());
                count++;
                String ch = _aTree.getValue();
                if ( ch.equals("&") )
                  {
                        PutNode(_aTree.LeftChild);
                        PutNode(_aTree.RightChild);
                   }

          }
/*
  private Tree  PutNodeToStr(Tree _aTree)
           {
                String ch = _aTree.getValue();
                if ( ch== "&" || ch == "|")
                  {
                                if(ch=="&")
                                {
                                    Data = Data+"(&";
                                }
                                else
                                {
                                    Data = Data+"(|";
                                }
                              PutNodeToStr(_aTree.LeftChild);
                              PutNodeToStr(_aTree.RightChild);
                              Data = Data+")";

                   } else if(ch =="!")
                            {
                                  Data = Data+"(!";
                                  PutNodeToStr(_aTree.LeftChild);
                                  Data = Data+")";
                            } else //if((ch >= 'A' && ch<= 'Z') || (ch >= 'a' && ch <= 'z'))
                                {
                                    Data = Data + ch;
                                }

            return  _aTree;
          }*/

        private void  PutNodeToStrNoParentheses(Tree _aTree)
           {
                String ch = _aTree.getValue();
                if(ch=="&")
                {
                   Data = Data+"&";                                    
                   PutNodeToStrNoParentheses(_aTree.LeftChild);
                   PutNodeToStrNoParentheses(_aTree.RightChild);
                }
                else 
                {
                   Data = Data + ch;
                }        

                   

            //return  _aTree;
          }
}