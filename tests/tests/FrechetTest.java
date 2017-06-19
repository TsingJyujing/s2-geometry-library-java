package tests;
import com.txj.yuanyifan.common.frechet.GeometryFrechet;
import com.txj.yuanyifan.common.frechet.SearchResult;
import com.txj.yuanyifan.common.geohash.GeometryPoint;
import com.txj.yuanyifan.util.file.selector.CommonDialogs;
import com.txj.yuanyifan.util.file.text.GPSFileIO;
import java.util.List;

public class FrechetTest {
    
    private static String major_dir = CommonDialogs.getFolder("E:\\Code\\JavaWorkspace\\geohash\\test_data\\");
    
    public static void main(String[] argv){
        //车辆路径读取
        List<GeometryPoint<Double>> route = GPSFileIO.importFileDouble(major_dir + "route_user.csv");
        List<GeometryPoint<Double>> route_flip = GPSFileIO.importFileDouble(major_dir + "route_user_flip.csv");
        List<GeometryPoint<Double>> route_realempty = GPSFileIO.importFileDouble(major_dir + "route_real.csv");
        //用户定义路线读取
        List<GeometryPoint<Double>> user_defined = GPSFileIO.importFileDouble(major_dir + "route_define.csv");
        List<GeometryPoint<Double>> user_defined_flip = GPSFileIO.importFileDouble(major_dir + "route_define_flip.csv");
        
        GeometryFrechet comparer = new GeometryFrechet();
        comparer.addRoute(user_defined, 0.2, 2.0);
        GeometryFrechet comparer_flip = new GeometryFrechet();
        comparer_flip.addRoute(user_defined_flip, 0.2, 2.0);

        System.out.println("Normal--ReadEmpty:");
        printResultList(comparer.frechetGeofetch(
                route_realempty, 0.2, 2.0, 1.0, Math.PI/2, 0.0D));
        
        System.out.println("Normal--Normal:");
        printResultList(comparer.frechetGeofetch(
                route, 0.2, 2.0, 1.0, Math.PI/2, 0.0D));
        
        System.out.println("Normal--Flip:");
        printResultList(comparer.frechetGeofetch(
                route_flip, 0.2, 2.0, 1.0, Math.PI/2, 0.0D));
        
        System.out.println("Flip--Normal:");
        printResultList(comparer_flip.frechetGeofetch(
                route, 0.2, 2.0, 1.0, Math.PI/2, 0.0D));
        
        System.out.println("Flip--Flip:");
        printResultList(comparer_flip.frechetGeofetch(
                route_flip, 0.2, 2.0, 1.0, Math.PI/2, 0.0D));
    }
    
    private static void printResultList(List<SearchResult> input){
        if (!input.isEmpty()){
            System.out.println(SearchResult.toCSVHead("\t"));
            for (SearchResult result : input) {
                System.out.println(result.toCSV("\t"));
            }
        }else{
            System.out.println("No result in list.");
        }
    }
}
