package mtree;
import java.io.IOException;
import java.util.Iterator;
import java.util.ArrayList;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;
/**
 *
 * @author vasilisa
 */
public class MTreeReducer extends MapReduceBase
    implements Reducer <IntWritable, Text, IntWritable, Text>{
    
    public void reduce(IntWritable key, Iterator<Text> value,
            OutputCollector<IntWritable, Text> output, Reporter reporter)
            throws IOException{
        
        ArrayList<Point> points = new ArrayList<Point>(0);
        Point center = new Point("0 0 0 0 0 0", 0);
        while (value.hasNext()){
            Point point = new Point(value.next().toString());
            points.add(point);
            for(int i = 0; i < Point.dem; i++){
                center.add_coord(i, points.get(points.size() - 1).get_coord()[i]);
            }
        }
        for(int i = 0; i< Point.dem; i++){
            double x = center.get_coord()[i] / points.size();
            center.set_coord(i, x);
        }
        for(int i = 0; i < points.size(); i++){
            double dist = points.get(i).get_distance(center) + points.get(i).get_radius();
            center.set_radius(Math.max(center.get_radius(), dist));
        }
        output.collect(key, new Text("" + points.size()));
        for(int i = 0; i < points.size(); i++){
            output.collect(null, new Text(points.get(i).toString() + " " + points.get(i).get_number()));
        } 
        ChoosingPivots.WriteFile(center.toString()  + " " + key, "tree/level-" + Point.level + "/pivots");
    }  
}
