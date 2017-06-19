package com.txj.yuanyifan.util.file.text;

import com.txj.yuanyifan.common.geohash.GeometryPoint;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/19.
 */
public class GPSFileIO {
    public static List<GeometryPoint<Long>> importFileLong(String filename){
        List<GeometryPoint<Long>> returnlist= new ArrayList<GeometryPoint<Long>>();
        TextFileLineReader read_obj = new TextFileLineReader(filename);
        while(true){
            String line_read = read_obj.lineRead();
            if (line_read==null){
                break;
            }
            String[] line_split = line_read.split(",");

            returnlist.add(new GeometryPoint<Long>(
                    Double.valueOf(line_split[0]),
                    Double.valueOf(line_split[1]),
                    (Long)Math.round(Double.valueOf(line_split[2]))
            ));
        }
        return returnlist;
    }

    public static List<GeometryPoint<Double>> importFileDouble(String filename){
        List<GeometryPoint<Double>> returnlist= new ArrayList<GeometryPoint<Double>>();
        TextFileLineReader read_obj = new TextFileLineReader(filename);
        while(true){
            String line_read = read_obj.lineRead();
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

    public static List<GeometryPoint<String>> importFileString(String filename){
        List<GeometryPoint<String>> returnlist= new ArrayList<GeometryPoint<String>>();
        TextFileLineReader read_obj = new TextFileLineReader(filename);
        while(true){
            String line_read = read_obj.lineRead();
            if (line_read==null){
                break;
            }
            String[] line_split = line_read.split(",");

            returnlist.add(new GeometryPoint<String>(
                    Double.valueOf(line_split[0]),
                    Double.valueOf(line_split[1]),
                    line_split[2]
            ));
        }
        return returnlist;
    }

    public static void exportToFile(Iterable<GeometryPoint> geoPoints,String filename){
        TextFileLineWriter fileWriter = new TextFileLineWriter(filename);
        for (GeometryPoint geoPoint: geoPoints){
            fileWriter.writeln(String.format("%f,%f,%s",
                    geoPoint.longitude,geoPoint.latitude,
                    geoPoint.userInfo.toString()));
        }
        fileWriter.close();
    }
}
