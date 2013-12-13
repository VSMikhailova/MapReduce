package mtree;

import java.io.*;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
/**
 *
 * @author vasilisa
 */
public class MTree {
    public static int CountOfPivots = 150;    
    public static Point[] pivots; 
    
    public static void main(String[] args) throws IOException{
        int UMax = 20;
        int UMin = 10;
        
        int CountOfPoints = CountOfPivots;
        ChoosingPivots test = new ChoosingPivots();
        //test.processing();
        //test.CreateFile(CountOfPoints);

//MapReduceConfig
        if(args.length != 2){
            System.err.println("Usage: mtree.Mtree <input path> <output path>");
            System.exit(-1);
        }
        
        String path_in = args[0];
        String path_out = args[1];
        int i = 0;
        while(CountOfPivots > 1){ 
            CountOfPoints = CountOfPivots;
            CountOfPivots = 2*CountOfPivots/(3*UMin) + 1;
            
            pivots = new Point[CountOfPoints];
            pivots = test.Choose(CountOfPoints, CountOfPivots, path_in + i + "/pivots");
            
            JobConf conf = new JobConf(MTree.class);
            conf.setJobName("MTree");
       
            conf.setMapperClass(MTreeMapper.class);
            conf.setReducerClass(MTreeReducer.class);
        
            conf.setOutputKeyClass(IntWritable.class);//number of pivot
            conf.setOutputValueClass(Text.class);//points 
            
            FileInputFormat.addInputPath(conf, new Path(path_in + i + "/pivots"));
            i++;
            FileOutputFormat.setOutputPath(conf, new Path(path_out + i));
            Point.level = i;

            JobClient.runJob(conf);
                      
        }

        String line = ChoosingPivots.ReadFile(0, "in");
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
        line = line + x + " 0 ";
        Point in_point = new Point(line, 0);
        
        
        int k = 6;
        k_NN.k_NN_Search(in_point, k);
        
      
    }
}