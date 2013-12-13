package mtree;
import java.io.IOException;

import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapred.Mapper;
/**
 *
 * @author vasilisa
 */
public class MTreeMapper extends MapReduceBase 
  implements Mapper <LongWritable, Text, IntWritable, Text>{
    
    public void map(LongWritable key, Text value, 
            OutputCollector<IntWritable, Text> output, Reporter reporter)
            throws IOException {
        double dist = 1000000;
        int num = -1;
        String str = value.toString();
        for (int i = 0; i < MTree.CountOfPivots; i++){
            Point a = new Point(str, -1);
            if(a.get_distance(MTree.pivots[i]) < dist){
                dist = a.get_distance(MTree.pivots[i]);
                num = MTree.pivots[i].get_number();
            }
        }
        output.collect(new IntWritable(num), new Text(str));
    }
}

   