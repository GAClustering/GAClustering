/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utils;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import gaclustering.Const;
import gaclustering.AGAC;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.lang.Math;
/**
 *
 * @author PELE
 */
public class UpdateCluster {
    public void AvgSim(){
        
    }
    
    public int updateClusterDB(int k,int currentCulsterIndex, Double cohesion , List<Integer> Cluster1, List<Integer> Cluster2) throws InstantiationException, IllegalAccessException, SQLException {
        Double avgsim1 = callAvgSim(Cluster1);
        Double avgsim2 = callAvgSim(Cluster2);
    
        int min_cluster = k;
        Double min_val;
        Connection conn;
        ResultSet rs;

        ConnectionDB db = new ConnectionDB();
        db.createConnection();
        conn = (Connection) db.getConnection();
        PreparedStatement statement = null;
        if ( avgsim1 > avgsim2 ) {
            min_val = avgsim2;
            //update cluster data in cluster2 to k
            for (int i : Cluster2) {
                statement = conn.prepareStatement("UPDATE data_converted SET clusterNo=? WHERE id=?");
                statement.setInt(1, k);
                statement.setInt(2, i);
                statement.executeUpdate();
            }
        }
        else {
            min_val = avgsim1;
            //update cluster data in cluster1 to k
            for (int i : Cluster1) {
                statement = conn.prepareStatement("UPDATE data_converted SET clusterNo=? WHERE id=?");
                statement.setInt(1, k);
                statement.setInt(2, i);
                statement.executeUpdate();
            }
        }
        
        
        // Find cluster id for next split
        ArrayList<Integer> list = new ArrayList<Integer>(); 
        statement = conn.prepareStatement("SELECT Distinct(clusterNo) FROM data_converted WHERE clusterNo not in(?, ?)");
        statement.setInt(1, currentCulsterIndex);
        statement.setInt(2, k);
        rs = statement.executeQuery();
        while( rs.next() ){
            int clusterNo;
            clusterNo = rs.getInt("clusterNo");
            list.add(clusterNo);
        }

        if( !list.isEmpty()){
            for(int i : list) {
                ArrayList<Integer> ClusterDataID = new ArrayList<Integer>(); 
                statement = conn.prepareStatement("SELECT id FROM data_converted WHERE clusterNo=?");
                statement.setInt(1, i);
                rs = statement.executeQuery();
                while(rs.next()){
                    ClusterDataID.add(rs.getInt("id"));
                }
                Double clusterAvgSim = callAvgSim(ClusterDataID);
                if ( clusterAvgSim < min_val ) {
                    min_cluster = i;
                }
            }
        }
            
        
        statement.close();
        conn.close();
        
        return min_cluster;
        
    }
    
    private Double callAvgSim(List<Integer> ClusterDataID){
        Double avgsim = 0.00;
        Double sum = 0.00;
        for (int i : ClusterDataID) {
            for (int j : ClusterDataID) {
                if ( i != j){
                    sum += Const.SimilarityMatrix[i-1][j-1];
                }
            }
        }
        
        sum = sum / 2;
        //avgsim = sum / ClusterDataID.size();
        avgsim = sum / (ClusterDataID.size() * ClusterDataID.size());
        //avgsim = sum / ClusterDataID.size();
        
        return avgsim;
    }
    
    private Double callSimilarityCohesion(Double AvgSim, Double Cohesion ){
        Double SimilarityCohesion;
        
        SimilarityCohesion = Math.pow(AvgSim,0.5) * Math.pow(Cohesion, 0.5);
        
        return SimilarityCohesion;
    }
}
