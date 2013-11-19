package gaclustering;

import java.util.ArrayList;


public class Tree extends java.lang.Object {
  protected  String Value;
  protected  String Data="";
  
  private int NodePosition;
  private int count;
  protected Tree LeftChild;
  protected Tree RightChild;
  protected int nodeType;
  private Tree aTree;
  ArrayList<Integer> dataNode;
    public Tree()
  {
    NodePosition = 0;
    Value = "?";
    LeftChild = RightChild = null;
    dataNode = new ArrayList<>();
    
  }
    
    public Tree(Tree bTree){

        this.LeftChild = bTree.LeftChild;
        this.RightChild= bTree.RightChild;
        this.Value = bTree.Value;
        this.nodeType = bTree.nodeType;
    
    }

  public Tree(char Value1)
  {
    Value = String.valueOf(Value1);
    LeftChild = RightChild = null;
  }


    public void setValue(String _Value)
        {
        Value = _Value;
        }
    public void setLeftChild(Tree LNode)
        {
          LeftChild = LNode;
        }
    public void setRightChild(Tree RNode)
        {
          RightChild = RNode;
        }
    public void setNodeType(int nodetype) {
        this.nodeType = nodetype;
    }
    
    public int getNodeType() {
        return this.nodeType;
    }
    
    public String getValue()
        {
          return Value;
        }


    public void setNode(int node, Tree newTree) {
        NodePosition = node;
        count = 0;
        aTree = new Tree();
        this.aTree = newTree;
        setNodeByPosition(this);
     }
    
    private void setNodeByPosition(Tree t1){
        count++;
        if (count == NodePosition){
            t1.setLeftChild(aTree.LeftChild);
            t1.setRightChild(aTree.RightChild);
            t1.setValue(aTree.getValue());
            t1.setNodeType(aTree.getNodeType());
         }
        if (t1.nodeType == 0) {
            setNodeByPosition(t1.LeftChild);
            setNodeByPosition(t1.RightChild);
        }
    }
    
     public Tree getNode(int node) {
        NodePosition = node;
        count = 0;
        aTree = new Tree();
        getNodeByPosition(this);
        return aTree;
     }
     
     private void getNodeByPosition(Tree t1){
        count++;
         
         if (count == NodePosition){
            aTree = t1;
         }
         
         if (t1.nodeType == 0){
             getNodeByPosition(t1.LeftChild);
             getNodeByPosition(t1.RightChild);
         }

     }
     
     public int getSize(){
    	 int leftnode_size = 0;
    	 int rightnode_size = 0;
    	 if (this.LeftChild != null || this.RightChild != null){
    		 leftnode_size = this.LeftChild.getSize();
    		 rightnode_size = this.RightChild.getSize();
    	 }
    	 
    	 return 1 + leftnode_size + rightnode_size;
    	 
    	 
     }
     
    public boolean isValid() {
        dataNode = new ArrayList<>();
        return checkValid(this);
    }
     
     public boolean checkValid(Tree t) {
        boolean valid = true; 
        if (t.LeftChild != null || t.RightChild != null){
            valid = checkValid(t.LeftChild ) && checkValid(t.RightChild );
        }
        else {
          int value = Integer.parseInt(t.getValue().replaceAll("\\(", "").replaceAll("\\)", ""));
          if (dataNode.contains(value)) {
              valid = false;
          }
          else {
              dataNode.add(value);
          }
           
        }
         return valid;
     }
             
             
}
