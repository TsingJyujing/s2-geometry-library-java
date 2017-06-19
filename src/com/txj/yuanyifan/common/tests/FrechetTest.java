package com.txj.yuanyifan.common.tests;
import com.txj.yuanyifan.common.frechet.GeometryFrechet;
import com.txj.yuanyifan.common.frechet.SearchResult;
import com.txj.yuanyifan.common.geohash.GeometryPoint;
import com.txj.yuanyifan.util.file.text.TextFileLineReader;
import java.util.ArrayList;
import java.util.List;

public class FrechetTest {
    
    private static String major_dir = "E:\\Code\\JavaWorkspace\\geohash\\test_data\\";
    
    public static void main(String[] argv){
        //车辆路径读取
        List<GeometryPoint<Double>> route = import_data(major_dir + "route_user.csv");
        List<GeometryPoint<Double>> route_flip = import_data(major_dir + "route_user_flip.csv");
        List<GeometryPoint<Double>> route_realempty = import_data(major_dir + "route_real.csv");
        //用户定义路线读取
        List<GeometryPoint<Double>> user_defined = import_data(major_dir + "route_define.csv");
        List<GeometryPoint<Double>> user_defined_flip = import_data(major_dir + "route_define_flip.csv");
        
        GeometryFrechet comparer = new GeometryFrechet();
        comparer.addRoute(user_defined, 0.2, 2.0);
        GeometryFrechet comparer_flip = new GeometryFrechet();
        comparer_flip.addRoute(user_defined_flip, 0.2, 2.0);

        System.out.println("Normal--ReadEmpty:");
        print_result_list(comparer.frechetGeofetch(
                route_realempty, 0.2, 2.0, 1.0, Math.PI/2, 0.0D));
        
        System.out.println("Normal--Normal:");
        print_result_list(comparer.frechetGeofetch(
                route, 0.2, 2.0, 1.0, Math.PI/2, 0.0D));
        
        System.out.println("Normal--Flip:");
        print_result_list(comparer.frechetGeofetch(
                route_flip, 0.2, 2.0, 1.0, Math.PI/2, 0.0D));
        
        System.out.println("Flip--Normal:");
        print_result_list(comparer_flip.frechetGeofetch(
                route, 0.2, 2.0, 1.0, Math.PI/2, 0.0D));
        
        System.out.println("Flip--Flip:");
        print_result_list(comparer_flip.frechetGeofetch(
                route_flip, 0.2, 2.0, 1.0, Math.PI/2, 0.0D));
    }
    
    private static void print_result(SearchResult input){
        System.out.printf("In time:%f\t In index:%d\t Out time:%f\t Out index:%d\t\n", 
                    input.inTime,
                    input.inIndex,
                    input.outTime,
                    input.outIndex);
    }
    
    private static void print_result_list(List<SearchResult> input){
        if (!input.isEmpty()){
            for (SearchResult result : input) {
                print_result(result);
            }
        }else{
            System.out.println("No result in list.");
        }
    }
    
    private static List<GeometryPoint<Double>> import_data(String filename){
        List<GeometryPoint<Double>> returnlist= new ArrayList<GeometryPoint<Double>>();
        TextFileLineReader read_obj = new TextFileLineReader(filename);
        while(true){
            String line_read = read_obj.line_read();
            if (line_read==null){
                break;
            }
            String[] line_split = line_read.split(",");

            returnlist.add(new GeometryPoint<Double>(
                    Double.valueOf(line_split[0]),
                    Double.valueOf(line_split[1]),
                    Double.valueOf(line_split[2])
            ));
        }
        return returnlist;
    }
}
