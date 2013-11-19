package com.utils;


import java.util.ArrayList;
import java.util.Random;

public class test {
    private Random rand;
    private String Data;
    private Integer size=0;
    private ArrayList<Integer> DataSet;
    private ArrayList<Integer> DataSetPool;
    private int Count;
    public test(){
        rand = new Random();
        DataSet = new ArrayList<Integer>();
        for (int i = 1; i<=150;i++){
            DataSet.add(i);
        }
    }
    
    public static void main(String[] args) {
        test p = new test();
        p.GenPop();
        //p.tree();
        

    }
    
    public void tree(){
        Count = 0;
        Data = "&&(51)(128)&&(47)(11)&(94)(87)";
        showCommand();
    }
    
    public void GenPop(){

        
        for(int i = 0; i < 40; i++){
            DataSetPool = new ArrayList<Integer>(DataSet) ;
            Data = "";
            size= 0;
            GenCommand(1);
            System.out.println(Data);
            Count = 0;
            showCommand();
            System.out.println();
            
        }
    }
    
    private void GenCommand(int COM) {
        
        if (size >= 15 && COM == 1) {
            Data = Data + "&";
            GenCommand(0);
            GenCommand(0);
        }
        else {
            
            if (size >= (15)) {
            COM =0;
            }
            
            size++;
            if ( COM == 0 ) {
                int nextRand = rand.nextInt(DataSetPool.size());
                Data = Data + "(" + String.valueOf(DataSetPool.remove(nextRand))  + ")" ;
            }
            else {
                
                Data = Data + "&";
                int nextRand = 1;
                if (size > 1) {
                    nextRand = rand.nextInt(2);
                }
                size+=2;
                GenCommand(nextRand);
                GenCommand(nextRand);
            }
            
        }
    }
    
    private void showCommand() {
        
        char CH = Data.charAt(Count);
        String tmp="";
        Count++;

        if ( CH == '(') {
            String value = "";
            while( Data.charAt(Count) != ')') {
                value = value + Data.charAt(Count);
                Count++;
            }
            Count++;
            System.out.println(value);
        }
        else {
            System.out.println(CH);
            showCommand();
            showCommand();
        }
        
    }
    
}
