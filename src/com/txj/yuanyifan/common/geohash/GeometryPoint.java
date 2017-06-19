package com.txj.yuanyifan.common.geohash;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yuanyifan
 * @Mail Address  yuanyifan@deewinfl.com
 * @Telephone 185-0290-5886
 * @Company Shaanxi Skywin Internet of Vehicles Information Technology Co.,Ltd
 */

public class GeometryPoint<T> implements java.io.Serializable{
    public T userInfo = null;
    public double longitude = 0.0D;
    public double latitude = 0.0D;
    
    /**
     * Radius of earth: km
     */
    public static final double EARTH_RADIUS = 6378.5;

    /**
     * Multiplier transfer degree to rad
     */
    public static final double DEG2RAD = Math.PI/180;

    /**
     * Multiplier transfer rad to degree
     */
    public static final double RAD2DEG = 180/Math.PI;
    
    /**
     * 2^30
     */
    public static final long POW2E30 = 0x40000000L;

    /**
     * 2^31
     */
    public static final long POW2E31 = 0x80000000L;
    
    /**
     *
     * @param lng longitude
     * @param lat latitude
     */
    public GeometryPoint(double lng, double lat){
         longitude = lng;
         latitude = lat;
    }
    
    /**
     * 
     * @param lng longitude
     * @param lat latitude
     * @param userInfoInput Input tag
     */
    public GeometryPoint(double lng, double lat, T userInfoInput){
         longitude = lng;
         latitude = lat;
         userInfo = userInfoInput;
    }
    
    /**
     *
     * @param accuracy
     * @return HashCode
     */
    public long geohashcode(long accuracy){
        accuracy = Math.min(accuracy, POW2E30);
        long lngCode = (long)Math.floor((longitude +180.0D)/180*accuracy);
        long latCode = (long)Math.floor((latitude +90.00D)/180*accuracy);
        return lngCode*POW2E31 + latCode;
    }
    
    /**
     * Get the boundary of given Geometry Hash Block (given by Long) and point
     * @param blockHash given block hash
     * @param accuracy accuracy of hash block
     * @param centerPoint search point opp to this block
     * @return List of the points seems to nearest or farthest
     */
    public List<GeometryPoint<T>> geometryHashBlockBoundary(long blockHash, long accuracy, GeometryPoint<T> centerPoint) {
        List<GeometryPoint<T>> returnPoints = new ArrayList<GeometryPoint<T>>();
        long latCode = blockHash % POW2E31;
        long lngCode = (blockHash-latCode)/POW2E31;
        double lngMin = (180.0D*lngCode-180.0D*accuracy)/((double)accuracy);
        double latMin = (180.0D*latCode-90.00D*accuracy)/((double)accuracy);
        double lngMax = (180.0D*(lngCode+1)-180.0D*accuracy)/((double)accuracy);
        double latMax = (180.0D*(latCode+1)-90.00D*accuracy)/((double)accuracy);
        returnPoints.add(new GeometryPoint<T>(lngMin,latMin));
        returnPoints.add(new GeometryPoint<T>(lngMin,latMax));
        returnPoints.add(new GeometryPoint<T>(lngMax,latMin));
        returnPoints.add(new GeometryPoint<T>(lngMax,latMax));
        
        if (lngMin<centerPoint.longitude && lngMax>centerPoint.longitude){
            returnPoints.add(new GeometryPoint<T>(centerPoint.longitude,latMin));
            returnPoints.add(new GeometryPoint<T>(centerPoint.longitude,latMax));
        }
        
        double [] hashBoundary = new double[2];
        hashBoundary[0] = Math.cos((lngMin - centerPoint.longitude)*DEG2RAD);
        hashBoundary[1] = Math.cos((lngMax - centerPoint.longitude)*DEG2RAD);
        double upBoundary = Math.max(hashBoundary[0],hashBoundary[1]);
        double downBoundary = Math.min(hashBoundary[0],hashBoundary[1]);
        double partialDiffVariable = Math.tan(centerPoint.latitude *DEG2RAD);
        double judgeMinLatitude = partialDiffVariable/Math.tan(latMin*DEG2RAD);
        double judgeMaxLatitude = partialDiffVariable/Math.tan(latMax*DEG2RAD);
        if (judgeMinLatitude<upBoundary && judgeMinLatitude>downBoundary){
            returnPoints.add(
                    new GeometryPoint<T>(
                            centerPoint.longitude +Math.acos(judgeMinLatitude)*RAD2DEG,
                            judgeMinLatitude));
        }
        if (judgeMaxLatitude<upBoundary && judgeMaxLatitude>downBoundary){
            returnPoints.add(
                    new GeometryPoint<T>(
                            centerPoint.longitude +Math.acos(judgeMaxLatitude)*RAD2DEG,
                            judgeMinLatitude));
        }
        return returnPoints;
    }
    
    /**
     *
     * @param accuracy
     * @return the bound point of this hash
     */
    public List<GeometryPoint<T>> geometryHashBlockBoundary(long accuracy) {
        return geometryHashBlockBoundary(geohashcode(accuracy),accuracy,this);
    }
    
    /**
     *
     * @param pointList
     * @param centerPoint
     * @return
     */
    public double minDistance(Iterable<GeometryPoint<T>> pointList, GeometryPoint<T> centerPoint){
        double minValue = EARTH_RADIUS*Math.PI;
        for (GeometryPoint<T> point : pointList){
            double dist = centerPoint.distance(point);
            if (minValue>dist){
                minValue = dist;
            }
        }
        return minValue;
    }
    
    /**
     *
     * @param pointList
     * @return
     */
    public double minDistance(List<GeometryPoint<T>> pointList){
        return minDistance(pointList,this);
    }

    /**
     *
     * @param pointList
     * @param centerpoint
     * @return
     */
    public double maxDistance(List<GeometryPoint<T>> pointList, GeometryPoint<T> centerpoint){
        double maxValue = 0.0D;
        for (GeometryPoint<T> point:pointList){
            double dist = point.distance(centerpoint);
            if (maxValue<dist){
                maxValue = dist;
            }
        }
        return maxValue;
    }
    
    /**
     *
     * @param pointList
     * @return
     */
    public double maxDistance(List<GeometryPoint<T>> pointList){
        return maxDistance(pointList,this);
    }
    
    /**
     *
     * @param point1
     * @return distance(km) to this point
     */
    public double distance(GeometryPoint<T> point1){
    	return geodesicDistance(point1,this);
    }
    
    /**
     *
     * @param point1
     * @param point2
     * @return return distance between point1 and point2
     */
    public double distance(GeometryPoint<T> point1, GeometryPoint<T> point2){
    	return geodesicDistance(point1,point2);
    }

    private double geodesicDistance(GeometryPoint<T> point1, GeometryPoint<T> point2){
        double alpha = getInnerProduct(point1,point2);
        if (alpha>=1.0D){
            return 0.0D;
        }else if (alpha<=-1.0D){
            return Math.PI*EARTH_RADIUS;
        }else{
            return Math.acos(alpha)*EARTH_RADIUS;
        }
    }
    
    /**
     *
     * @param point1
     * @param point2
     * @return 3D-vector inner product of two normlized vectors 
     */
    public double getInnerProduct(GeometryPoint<T> point1, GeometryPoint<T> point2){
        return Math.sin(point1.latitude *DEG2RAD)*Math.sin(point2.latitude *DEG2RAD)+
                Math.cos(point1.latitude *DEG2RAD)*Math.cos(point2.latitude *DEG2RAD)*
                Math.cos(point1.longitude *DEG2RAD-point2.longitude *DEG2RAD);
    }
    
    /**
     *
     * @param point1
     * @return 3D-vector inner product of normlized vector and this vector  
     */
    public double getInnerProduct(GeometryPoint<T> point1){
        return getInnerProduct(point1,this);
    }
    
    /**
     *
     * @param point1
     * @param point2 
     * @return another distance valuation
     */
    public double getInnerProductDistance(GeometryPoint<T> point1, GeometryPoint<T> point2){
        return Math.PI - getInnerProduct(point1,point2);
    }
    
    /**
     *
     * @param point1
     * @return another distance valuation
     */
    public double getInnerProductDistance(GeometryPoint<T> point1){
        return Math.PI - getInnerProduct(point1);
    }
    
    /**
     *
     * @param pointList
     * @param centerPoint
     * @return get min inner productor between list and center
     */
    public double minInnerProduct(List<GeometryPoint<T>> pointList, GeometryPoint<T> centerPoint){
        double minip = 1.0D;
        double ip;
        for (GeometryPoint<T> point: pointList){
            ip = point.getInnerProduct(centerPoint);
            if (minip>ip){
                minip = ip;
            }
        }
        return minip;
    }
    
    /**
     *
     * @param pointList
     * @return get min inner productor between list and this
     */
    public double minInnerProduct(List<GeometryPoint<T>> pointList){
        return GeometryPoint.this.minInnerProduct(pointList,this);
    }
    
    /**
     *
     * @param pointList
     * @param centerPoint
     * @return get max inner productor between list and center
     */
    public double maxInnerProduct(List<GeometryPoint<T>> pointList, GeometryPoint<T> centerPoint){
        double maxip = 0.0D;
        for (int i = 0; i< pointList.size() ; ++i){
            double ip = pointList.get(i).getInnerProduct(centerPoint);
            if (maxip<ip){
                maxip = ip;
            }
        }
        return maxip;
    }
    
    /**
     *
     * @param pointList
     * @return get max inner productor between list and this
     */
    public double maxInnerProduct(List<GeometryPoint<T>> pointList){
        return GeometryPoint.this.maxInnerProduct(pointList,this);
    }
    
    /**
     *
     * @param pointList
     * @param centerPoint
     * @return find nearest point
     */
    public GeometryPoint<T> nearestPoint(List<GeometryPoint<T>> pointList, GeometryPoint<T> centerPoint){
        int return_value = 0;
        double max_ip = centerPoint.getInnerProduct(pointList.get(0));
        for(int i = 1; i<pointList.size(); ++i){
            double ip = centerPoint.getInnerProduct(pointList.get(i));
            if (ip>max_ip){
                max_ip = ip;
                return_value = i;
            }
        }
        return pointList.get(return_value);
    }
    
    /**
     *
     * @param point_list
     * @return find nearest point
     */
    public GeometryPoint<T> nearestPoint(List<GeometryPoint<T>> point_list){
        return nearestPoint(point_list,this);
    }
    
    /**
     *
     * @param point_list
     * @param centerpoint
     * @return find nearest point index
     */
    public int nearestPointIndex(List<GeometryPoint<T>> point_list, GeometryPoint<T> centerpoint){
        int return_value = 0;
        double max_ip = centerpoint.getInnerProduct(point_list.get(0));
        for(int i = 1; i<point_list.size(); ++i){
            double ip = centerpoint.getInnerProduct(point_list.get(i));
            if (ip>max_ip){
                max_ip = ip;
                return_value = i;
            }
        }
        return return_value;
    }
    
    /**
     *
     * @param point_list
     * @return find nearest point index
     */
    public int nearestPointIndex(List<GeometryPoint<T>> point_list){
        return nearestPointIndex(point_list,this);
    }
}
