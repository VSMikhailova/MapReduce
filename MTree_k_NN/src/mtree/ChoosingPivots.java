package mtree;

import java.util.Random;
import java.io.*;
import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;
/**
 *
 * @author vasilisa
 */
public class ChoosingPivots {
    
    public void processing(){
        Writer writer = null;
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(new 
                    FileInputStream("tree/Iris.data")));
            writer = new BufferedWriter(new OutputStreamWriter(new
                    FileOutputStream("tree/level-0/pivots")));
            String line;
            int i = 0;
            while((line = reader.readLine()) != null){
                String[] ar = line.split(",");
                line = "";
                double x =  Double.valueOf(ar[0]);
                x = (x - 4.3)*100/3.6;//normalization, min 4.3, max 7.9
                line = line + x + " ";
                x = Double.valueOf(ar[1]);
                x = (x - 2.0)*100/2.4;//min 2.0, max 4.4
                line = line + x + " ";
                x = Double.valueOf(ar[2]);
                x = (x - 1.0)*100/5.9;//min 1.0, max 6.9
                line = line + x + " ";
                x = Double.valueOf(ar[3]);
                x = (x - 0.1)*100/2.4;//min 0.1, max 2.5
                line = line + x + " 0 " +i+ "\n";
                i++;
                writer.write(line);
            }    
        }catch (IOException e){}
            finally {try{writer.close();} catch(IOException e){}};
    }

    static public void WriteFile (String point, String file){
        Writer writer = null;
        try{
            writer = new BufferedWriter(new OutputStreamWriter(new
                    FileOutputStream(file, true)));
                writer.write(point + "\n");
        }catch (IOException e){}
            finally {try{writer.close();} catch(IOException e){}};
        
    }
    
    static public String ReadFile (int k, String file){
        BufferedReader reader = null;
        try{
            reader = new BufferedReader(new InputStreamReader(new
                    FileInputStream(file)));
                String line;
                int i = 0;
                while((line = reader.readLine()) != null){
                    if (i == k){
                        return line;
                    }
                    i++;   
                }
        }catch (IOException e){};
        return null;
    }
    
    void CreateFile(int CountOfPoints){
        Writer writer = null;
        try{
            writer = new BufferedWriter(new OutputStreamWriter(new
                    FileOutputStream("tree/level-0/pivots")));
            Random rand = new Random();
            for(int i = 0; i < CountOfPoints; i++){
                for (int j = 0; j < Point.dem; j++){
                    writer.write(100*rand.nextDouble() + " ");
                }
                writer.write("0 " + i);
                writer.write("\n");
            }
        }catch (IOException e){}
            finally {try{writer.close();} catch(IOException e){}};
    }   
    
    Point[] Choose(int CountOfPoints, int CountOfPivots, String file){            
        Set set = new HashSet();
        Random rand = new Random();
        Point[] pivots = new Point[CountOfPivots];
        for(int i = 0; i < CountOfPivots; i++){
            while(! set.add(rand.nextInt(CountOfPoints))){}
        }

        try{
            BufferedReader reader = new BufferedReader(new 
                InputStreamReader(new FileInputStream(file)));
            String line;
            int i = 0;
            int k = -1;
            while((line = reader.readLine()) != null){
                k++;
                if(!set.contains(k)){continue;}
                Point a = new Point(line, i);
                pivots[i++] = a;
            }
        } catch(IOException e){}  
        return pivots;
    }
    
    public static ArrayList<Point> Children(int level, int k){
        ArrayList<Point> children = new ArrayList<Point>(0);
        BufferedReader reader = null;
        try{
            reader = new BufferedReader(new InputStreamReader(new
                    FileInputStream("tree/level-"+level+"/part-00000")));
                String line;
                while((line = reader.readLine()) != null){
                    String[] ar = line.split("\t");
                   if(Integer.valueOf(ar[0]) == k){
                        for(int i = 0; i < Integer.valueOf(ar[1]); i++){
                            line = reader.readLine();
                            Point temp = new Point(line);
                            children.add(temp);
                            
                        }
                        return children;
                    }
                    else{
                        for (int i = 0; i < Integer.valueOf(ar[1]); i++){
                            reader.readLine();
                        }
                    }
                }
        }catch (IOException e){};
        return null;     
    }
    
}
 