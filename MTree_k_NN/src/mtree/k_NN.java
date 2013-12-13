package mtree;

import java.util.ArrayList;
/**
 *
 * @author vasilisa
 */
public class k_NN {
    
    public static ArrayList<elements> PR;
    public static ArrayList<Point> NN;
    public static ArrayList<Double> d;
    public static double inf = 100000.0;
    
    static public void k_NN_Search(Point in_point, int k){
        //init start
        PR = new ArrayList<elements>(0);
        elements root = new elements();
        root.level = Point.level;
        String str = ChoosingPivots.ReadFile(0, "tree/level-"+root.level+"/pivots");
        root.point = new Point(str);
        root.d_min = Math.max(in_point.get_distance(root.point) - root.point.get_radius(), 0);
        PR.add(root);
        
        NN = new ArrayList<Point>(0);
        d = new ArrayList<Double>(0);
        for(int i = 0; i<k; i++){
            NN.add(null);
            d.add(inf);
        }
        //init finished
        
        while (!PR.isEmpty()){
            elements NextNode = ChooseNode(PR);
            k_NN_NodeSearch(NextNode, in_point, k);
        }
        for(int i = 0; i< NN.size() ; i++){
            String flower;
            if(NN.get(i).get_number()<50){
                flower = "Iris-setosa";
            }
            else{
                if(NN.get(i).get_number()<100){
                    flower = "Iris-versicolor";
                }
                else{
                    flower = "Iris-virginica";
                    }
            }
            ChoosingPivots.WriteFile(flower +"\n", "out");
        }
    }
    
    static public elements ChooseNode(ArrayList<elements> PR){
        int num_min = 0;
        for(int i = 1; i < PR.size(); i++){
            if(PR.get(num_min).d_min > PR.get(i).d_min){
                num_min = i;
            }
        }
        elements el_min = new elements();
        el_min.d_min = PR.get(num_min).d_min;
        el_min.level = PR.get(num_min).level;
        el_min.point = PR.get(num_min).point;
        PR.remove(num_min);
        return el_min;
    }
    
    static public void k_NN_NodeSearch(elements Node, Point point, int k){
        //download all points, which are children of Node
        ArrayList<Point> children = new ArrayList<Point>(0);
        children = ChoosingPivots.Children(Node.level, Node.point.get_number());
        if (Node.level != 1){
            for(int i = 0; i < children.size(); i++){
                double d_Node_point = Node.point.get_distance(point);
                double d_Node_ichild = children.get(i).get_distance(Node.point);
                double d_k = d.get(k-1);
                double rad_ichild = children.get(i).get_radius();      
                if (Math.abs(d_Node_point - d_Node_ichild) <= (d_k + rad_ichild)){
                    elements newNode = new elements();
                    newNode.level = Node.level - 1;
                    newNode.point = children.get(i);
                    newNode.d_min = Math.max(point.get_distance(newNode.point) - newNode.point.get_radius(), 0);
                    if (newNode.d_min <= d_k){
                        PR.add(newNode);
                        double d_max =  point.get_distance(newNode.point) + rad_ichild;
                        if(d_max < d_k){
                            int j = 0;
                            while(j < PR.size()){
                                if(PR.get(j).d_min > d_k){
                                    PR.remove(j);
                                    j--;
                                }
                                j++;
                            }
                        }
                    }
                }
            }
        }
        else{
            for(int i = 0; i < children.size(); i++){
                double d_Node_point = Node.point.get_distance(point);
                double d_Node_ichild = children.get(i).get_distance(Node.point);
                double d_k = d.get(k-1);
                if (Math.abs(d_Node_point - d_Node_ichild) <= d_k){
                    if(children.get(i).get_distance(point) <= d_k){
                        double smth = 0;//NN_Update,
                        d.set(k-1, NN_update(children.get(i), point));
                        int j = 0;
                        while(j < PR.size()){
                            if(PR.get(j).d_min > d.get(k-1)){
                                PR.remove(j);
                                j--;
                            }
                            j++;
                        }
                    }
                }
            }
        }
    }
    
    static public double NN_update(Point child, Point point){
        for(int i = 0; i < NN.size(); i++){
            if(child.get_distance(point) < d.get(i)){
                NN.add(i, child);
                NN.remove(NN.size()-1);
                d.add(i, child.get_distance(point));
                d.remove(d.size()-1);
                return d.get(d.size()-1);
            }
        }
        return d.get(NN.size()-1);
    }
    
}
