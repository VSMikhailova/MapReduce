package mtree;

import org.apache.hadoop.io.*;
/**
 *
 * @author vasilisa
 */
public class PointArrayWritable extends ArrayWritable {
    public PointArrayWritable(){
        super(Point.class);
    }
}
//public class MTreeMapper extends MapReduceBase 
//  implements Mapper <PointArrayWritable, Point, IntWritable, Text>{
//    
//    public void map(PointArrayWritable key, Point value, 
//            OutputCollector<IntWritable, Text> output, Reporter reporter)
//            throws IOException {
//        double dist = 1000000;
//        int CountOfPivots = 100;
//        int num = -1;
//        
//        for (int i = 0; i < CountOfPivots; i++){
//            point a = key[i];
//            if(value.get_distance(key[i]) < dist){
//                dist = a.get_distance(pivots[i]);
//                num = pivots[i].get_number();
//            }
//        }
//        output.collect(new IntWritable(num), new Text(line));
//    }
//}
