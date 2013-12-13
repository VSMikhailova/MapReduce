package mtree;

/**
 *
 * @author vasilisa
 */

import java.io.IOException;
import java.io.DataInput;
import java.io.DataOutput;
import java.util.Random;

import org.apache.hadoop.io.*;

public class Point implements Writable {
    public static int dem = 4;
    private double coord[] = new double[dem];//0 - number in list
    private int number = 0; 
    private int count_of_childrens = 0;
    private double radius = 0;
    public static int level = 0;
    
    public void readFields(DataInput in) throws IOException{
        number = in.readInt();
        for (int i = 0; i<dem; i++){
            coord[i] = in.readDouble();
        }
    }
    
    public void write(DataOutput out) throws IOException{
        out.writeInt(number);
        for (int i = 0; i<dem; i++){
            out.writeFloat(i);
        }
    }
    
    Point(int k){
        Random rand  =new Random();
        for(int i = 0; i < dem; i++){
            coord[i] = 100*rand.nextDouble();
        }
        number = k;
    };
    Point(String temp_str, int k){ 
        number = k;
        String[] str = temp_str.split(" ");
        for (int i = 0; i<dem; i++){
            coord[i] = Double.valueOf(str[i]);
        }
        radius = Double.valueOf(str[dem]);
    };
    Point(String temp_str){ 
        String[] str = temp_str.split(" ");
        for (int i = 0; i<dem; i++){
            coord[i] = Double.valueOf(str[i]);
        }
        radius = Double.valueOf(str[dem]);
        number = Integer.valueOf(str[dem+1]);
    };
    Point(double [] array, int k){
        number = k;
        for(int i = 0; i<dem; i++){
            coord[i] = array[i];
        }
    };
    
    public int get_number (){
        return number;
    }
    
    public double get_distance(Point b){
        double result = 0;
        double[] coord_a = coord;
        double[] coord_b = b.get_coord();
        for(int i = 0; i<dem; i++){
            result += (coord_a[i] - coord_b[i])*(coord_a[i] - coord_b[i]);
        }
        return Math.sqrt(result);
    }
    
    public double[] get_coord(){
        return coord;
    }
      
    public void add_coord(int i, double x){
        coord[i] += x;
    }
    
    public void set_coord(int i, double x){
        coord[i] = x;
    }
    
    public void set_coord(double x[]){
        for(int i = 0; i < dem; i++){
            coord[i] = x[i];
        }
    }
    
    public void set_number(int i){
        number = i;
    }
    
    public void inc_count(int i){
        count_of_childrens += i;
    }
    
    public void set_count(int i){
        count_of_childrens = i;        
    }
    
    public int get_count(){
        return count_of_childrens;
    }
    
    public double get_radius(){
        return radius;
    }
    
    public void set_radius(double x){
        radius = x;
    }
    
    public String toString(){
        String res = "";
        for(int i = 0; i < dem; i++){
            res = res + coord[i] + " ";
        }
        res = res + radius;
        return res;
    }
}
