package com.txj.yuanyifan.common.frechet;

import com.txj.yuanyifan.common.geohash.GeometryPoint;

/**
 * @author yuanyifan
 * @Mail Address  yuanyifan@deewinfl.com
 * @Telephone 185-0290-5886
 * @Company Shaanxi Skywin Internet of Vehicles Information Technology Co.,Ltd
 */
public class MapPointInfo {
    public double ds;
    public double dt;
    
    public Long userDefinedIndex = 0L;
    public double tick = 0.0D;
    
    public double getVelocity(){
        return ds/dt;
    }
    
    private double x;
    private double y;
    
    public static double getInnerProduct(MapPointInfo a, MapPointInfo b){
        return a.getX()*b.getX() + a.getY()*b.getY();
    }
    
    public static double getAngle(MapPointInfo a, MapPointInfo b){
        return Math.acos(getInnerProduct(a,b));
    }
    
    public double getInnerProduct(MapPointInfo a){
        return getInnerProduct(this,a);
    }
    
    public double getAngle(MapPointInfo a){
        return getAngle(this,a);
    }
    
    public void writeDirection(double ix, double iy){
        double norm = Math.sqrt(ix*ix+iy*iy);
        x = ix/norm;
        y = iy/norm;
    }
    
    public double getX(){
        return x;
    }
    
    public double getY(){
        return y;
    }
    
    public void writeDirection(GeometryPoint a, GeometryPoint b){
        double d_east = (b.longitude - a.longitude)*Math.cos((a.latitude + b.latitude)/2* GeometryPoint.DEG2RAD);
        double d_north = b.latitude - a.latitude;
        writeDirection(d_east, d_north);
    }
}