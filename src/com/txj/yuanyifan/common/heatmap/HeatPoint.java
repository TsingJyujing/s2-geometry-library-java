package com.txj.yuanyifan.common.heatmap;

import com.txj.yuanyifan.common.geohash.GeometryPoint;

/**
 * Created by Administrator on 2017/6/20.
 */
public class HeatPoint<T> extends GeometryPoint<T> implements java.io.Serializable{
    private long accuracy = 180;

    public long getAccuracy(){
        return accuracy;
    }

    public void reformatGPS(){
        longitude = reformatVariable(longitude,accuracy);
        latitude = reformatVariable(latitude,accuracy);
    }

    public static double reformatVariable(double variable,long accuracy){
        return ((long)Math.floor(variable/180*accuracy) + 0.5)*180.0/accuracy;
    }
    public HeatPoint(double longitudeIn, double latitudeIn, long acc) {
        super(longitudeIn, latitudeIn);
        accuracy = acc;
        reformatGPS();
    }

    public HeatPoint(double longitudeIn, double latitudeIn, T value, long acc) {
        super(longitudeIn, latitudeIn, value);
        accuracy = acc;
        reformatGPS();
    }

    public long hashCodeLong(){
        return geohashcode(accuracy);
    }

    @Override
    public int hashCode(){
        return ((Long)geohashcode(accuracy)).hashCode();
    }

    @Override
    public boolean equals(Object obj){
        if (this.getClass().equals(obj.getClass())){
            return geohashcode(accuracy)==((HeatPoint)obj).hashCodeLong();
        }else{
            return false;
        }
    }
    @Override
    public String toString() {
        return String.format("(%f, %f)",longitude,latitude);
    }
    
}
