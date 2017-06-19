package com.txj.yuanyifan.common.tests;

import com.txj.yuanyifan.common.frechet.GeometryFrechet;
import com.txj.yuanyifan.util.file.text.TextFileLineReader;
import com.txj.yuanyifan.common.frechet.SearchResult;
import com.txj.yuanyifan.common.geohash.GeometryPoint;

import java.util.ArrayList;
import java.util.List;

public class FrechetTestOfTXJData {
     private static String major_dir = "E:\\Code\\JavaWorkspace\\geohash\\test_data\\";
    
    public static void main(String[] argv){
        //车辆路径读取
        List<GeometryPoint<Double>> route = import_data(major_dir + "TxjVehicleRoute.csv");
        //用户定义路线读取
        List<GeometryPoint<Double>> user_defined = import_data(major_dir + "UserdefinedRoute.csv");
        
        GeometryFrechet comparer = new GeometryFrechet();
        comparer.addRoute(user_defined, 0.2, 2.0);

        System.out.println("Normal--Normal:");
        print_result_list(comparer.frechetGeofetch(
                route, 0.15, 2.0, 1 , Math.PI/2, 0.0D));

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
    
    private static List<GeometryPoint<Double>> import_data(String FN){
        List<GeometryPoint<Double>> returnlist= new ArrayList<GeometryPoint<Double>>();
        TextFileLineReader read_obj = new TextFileLineReader(FN);
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
