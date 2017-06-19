package tests;

import com.txj.yuanyifan.common.geohash.GeometryHashConnectionLayer;
import com.txj.yuanyifan.common.geohash.GeometryHashFinalLayer;
import com.txj.yuanyifan.common.geohash.GeometryPoint;
import com.txj.yuanyifan.util.file.text.GPSFileIO;

import java.util.List;

/**
 * @author yuanyifan
 * @Mail Address  yuanyifan@deewinfl.com
 * @Telephone 185-0290-5886
 * @Company Shaanxi Skywin Internet of Vehicles Information Technology Co.,Ltd
 */

public class GeometryHashTest {
    public static int first_layer_para = 256;
    public static int final_layer_para = first_layer_para*128;
    private static String major_dir = "E:\\Code\\JavaWorkspace\\geohash\\test_data\\";
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        //testPoint();
        testMap();
        speedTesting(10000);
        //speedTestingFlip(10000);
    }
    
    public static void testPoint(){
        GeometryPoint<Long> TestPoint = new GeometryPoint<Long>(105.476927,35.064192,Long.valueOf(1));
        long acc = 8192;
        System.out.println(TestPoint.geohashcode(acc));
        System.out.println(Long.toHexString(TestPoint.geohashcode(acc)));
        List<GeometryPoint<Long>> goro = TestPoint.geometryHashBlockBoundary(acc);
        for (GeometryPoint<Long> point:goro){
            System.out.printf("%3.6f,%3.6f\n",point.longitude,point.latitude);
        }
    }
    
    public static void testMap() throws Exception {
        List<GeometryPoint<Long>> rand = GPSFileIO.importFileLong(major_dir + "rand_data.csv");
        List<GeometryPoint<Long>> comp = GPSFileIO.importFileLong(major_dir + "compare_data.csv");
        GeometryHashConnectionLayer<Long> Map_raw = new GeometryHashConnectionLayer<Long>(first_layer_para,final_layer_para);
        Map_raw.insert(GPSFileIO.importFileLong(major_dir + "raw_data.csv"));
        assert(rand.size()==comp.size());
        for(int i = 0; i<rand.size() ; ++i){
            GeometryPoint<Long> pn_get = Map_raw.searchNearestPoint(rand.get(i));
            Long index_get = pn_get.userInfo;
            double distance = pn_get.distance(rand.get(i))-comp.get(i).distance(rand.get(i));
            if ( !comp.get(i).userInfo.equals(index_get) ){
                Map_raw.searchNearestPoint(rand.get(i));
                System.out.println("Error RandID: "+ Integer.toString(i));
                System.out.printf("Error distance:%10.2f\n",distance*1000);
                System.out.println("Real Distance: " + Double.toString(comp.get(i).distance(rand.get(i))));
            }
        }
        
    }
    
    public static void speedTesting(int times) throws Exception{
        List<GeometryPoint<Long>> rand = GPSFileIO.importFileLong(major_dir + "rand_data.csv");
        List<GeometryPoint<Long>> comp = GPSFileIO.importFileLong(major_dir + "compare_data.csv");
        GeometryHashConnectionLayer<Long> Map_raw = new GeometryHashConnectionLayer<Long>(first_layer_para,final_layer_para);
        Map_raw.insert(GPSFileIO.importFileLong(major_dir + "raw_data.csv"));
        assert(rand.size()==comp.size());
        for(int i = 0; i<times ; ++i){
            int index = i % rand.size();
            Map_raw.searchNearestDistance(rand.get(index),2);
        }
    }
    
    public static void speedTestingFlip(int times) throws Exception{
        List<GeometryPoint<Long>> rand = GPSFileIO.importFileLong(major_dir + "rand_data.csv");
        List<GeometryPoint<Long>> comp = GPSFileIO.importFileLong(major_dir + "compare_data.csv");
        GeometryHashFinalLayer<Long> Map_raw = new GeometryHashFinalLayer<Long>(final_layer_para);
        Map_raw.insert(GPSFileIO.importFileLong(major_dir + "raw_data.csv"));
        assert(rand.size()==comp.size());
        for(int i = 0; i<times ; ++i){
            int index = i % rand.size();
            Map_raw.searchNearestDistance(rand.get(index),2);
        }
    }
    

}
