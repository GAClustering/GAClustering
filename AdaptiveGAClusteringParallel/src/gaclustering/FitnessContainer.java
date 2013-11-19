package gaclustering;

public class FitnessContainer {
  private boolean[] aBool;
  private float[] aNum;
  public FitnessContainer()
   {
        aBool = new boolean[Const.POPULATION];
        aNum = new float[Const.POPULATION];
   }
  public void init()
    {
        for(int i = 0; i < Const.POPULATION ; i++)
          {
              aBool[i] = false;
              aNum[i] = 0;
          }

    }
  public void setBool(boolean value,int position) {aBool[position] = value;}
  public boolean getBool(int position){ return aBool[position]; }
  public void setNum(float value,int position){aNum[position] = value; }
  public float getNum(int position){   return aNum[position];}

  }