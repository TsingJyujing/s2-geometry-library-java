package tests;

import com.txj.yuanyifan.common.frechet.GeometryFrechet;
import com.txj.yuanyifan.util.file.text.GPSFileIO;
import com.txj.yuanyifan.common.frechet.SearchResult;
import com.txj.yuanyifan.common.geohash.GeometryPoint;

import java.util.List;

public class FrechetTestOfTXJData {
    private static String major_dir = "E:\\Code\\JavaWorkspace\\geohash\\test_data\\";

    public static void main(String[] argv) {
        //车辆路径读取
        List<GeometryPoint<Double>> route = GPSFileIO.importFileDouble(major_dir + "TxjVehicleRoute.csv");
        //用户定义路线读取
        List<GeometryPoint<Double>> user_defined = GPSFileIO.importFileDouble(major_dir + "UserdefinedRoute.csv");

        GeometryFrechet comparer = new GeometryFrechet();
        comparer.addRoute(user_defined, 0.2, 2.0);

        System.out.println("Normal--Normal:");
        printResultList(comparer.frechetGeofetch(
                route, 0.15, 2.0, 1, Math.PI / 2, 0.0D));

    }

    private static void printResultList(List<SearchResult> input) {
        if (!input.isEmpty()) {
            System.out.println(SearchResult.toCSVHead("\t"));
            for (SearchResult result : input) {
                System.out.println(result.toCSV("\t"));
            }
        } else {
            System.out.println("No result in list.");
        }
    }
}
