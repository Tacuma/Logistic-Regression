/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logisticregression;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author TaKuma
 */
public class LogisticRegression {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException {
        // TODO code application logic here
        
        File file = new File("fieldgoal.txt");
        Scanner input = new Scanner(file);
        String set;
        double[] weights = {0,0};
        double g = 0;
        //ArrayList<Double> xVector = new ArrayList<Double>();
        ArrayList<String[]> dataTable = new ArrayList<String[]>();
        
        while(input.hasNext()){     // Fills a Table of x1, x2 and y values
            set = input.nextLine();
            String values[] = set.split(" ");
            dataTable.add(values);
        }
        
        for (int t =0; t < 10000; t++){ 
            g = gradient(weights, dataTable); // returns the gradient
            double v = -g;
            for (int j = 0; j<weights.length; j++)
                weights[j] =  (weights[j] + 0.01*v);
        }
        
        System.out.println(String.format("Ein = " + -g));
        for(double w:weights){
            System.out.println(String.format("%.4f", w));
        }
        
        System.out.println(oSampleError(dataTable,weights));
    }
    
    
    public static double gradient(double[] w, ArrayList<String[]> dataTable){
         double gradientSum = 0;
         for (int i = 0; i < dataTable.size() - 100; i++){
             double x1 = Double.parseDouble(dataTable.get(i)[0]);
             double x2 = Double.parseDouble(dataTable.get(i)[2]);
             double y =  Double.parseDouble(dataTable.get(i)[1]);
             
             double ynxn = (y * x1);// + (y * x2);
             double ynwxn = y *((w[0]*x1));// + (w[1]*x2));
             
             double singleGradient = ((double)ynxn) /(1 + Math.exp(ynwxn));
             
             gradientSum = (gradientSum + singleGradient);
         }
         
          double gradient = - ((double) gradientSum / (dataTable.size() - 100));
          return gradient;   
    }
    
    public static double oSampleError(ArrayList<String[]> dataTable, double[] w){  
        int goodorNot;
        int errorCount = 0;
         for (int i = (dataTable.size() - 100); i < dataTable.size(); i++){
             
             double x1 = Double.parseDouble(dataTable.get(i)[0]);
             double x2 = Double.parseDouble(dataTable.get(i)[2]);
             double y = Double.parseDouble(dataTable.get(i)[1]);
             
             double wx = (w[0]* x1) + (w[0] * x2);
             double result = (double)(Math.exp(wx))/(1 + Math.exp(wx));
             
             if (result >= 0.5)
                 goodorNot = 1;
             else
                 goodorNot = 0;
             
             if (goodorNot != y)
                 errorCount++;             
         } 
         return ((double)errorCount/100);
    }
   
}
