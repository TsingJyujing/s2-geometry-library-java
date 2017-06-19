package com.txj.yuanyifan.common.timeseries;


import com.txj.yuanyifan.common.geohash.GeometryPoint;

/**
 * @author yuanyifan
 * @Mail Address  yuanyifan@deewinfl.com
 * @Telephone 185-0290-5886
 * @Company Shaanxi Skywin Internet of Vehicles Information Technology Co.,Ltd
 */
public class GPSTimeSeries<T> extends ATimerSeries<GeometryPoint<T>> {
    
    /**
     *
     * @return velocity of GPS time series
     */
    public DoubleTimeSeries differential(){
        if (!uniqued){
            this.unique();
        }
        DoubleTimeSeries res = new DoubleTimeSeries();
        Unit<GeometryPoint<T>> last_unit = ts_list.get(0);
        Unit<GeometryPoint<T>> this_unit;
        for (int i = 1;i<ts_list.size();++i){
            this_unit = ts_list.get(i);
            double ds = this_unit.value.distance(last_unit.value);
            double dt = (this_unit.time-last_unit.time);
            res.append(
                new Unit(
                    (this_unit.time+last_unit.time)/2.0D,
                    ds/dt
                )
            );
            last_unit = this_unit;
        }
        return res;
    }
    
    /**
     *
     * @return δs of GPS time series
     */
    public DoubleTimeSeries difference(){
        if (!uniqued){
            this.unique();
        }
        DoubleTimeSeries res = new DoubleTimeSeries();
        Unit<GeometryPoint<T>> last_unit = ts_list.get(0);
        Unit<GeometryPoint<T>> this_unit;
        for (int i = 1;i<ts_list.size();++i){
            this_unit = ts_list.get(i);
            res.append(new Unit(
                    (this_unit.time+last_unit.time)/2.0D,
                    this_unit.value.distance(last_unit.value)
            ));
            last_unit = this_unit;
        }
        return res;
    }
    
    /**
     *
     * @return calculate the len of the curve on earth
     */
    public double curve_integral(){
        if (!uniqued){
            this.unique();
        }
        double res = 0.0D;
        Unit<GeometryPoint<T>> last_unit = ts_list.get(0);
        Unit<GeometryPoint<T>> this_unit;
        for (int i = 1;i<ts_list.size();++i){
            this_unit = ts_list.get(i);
            res += this_unit.value.distance(last_unit.value);
            last_unit = this_unit;
        }
        return res;
    }
    
    /**
     *
     * @param time a double
     * @param lng longitude
     * @param lat latitude
     */
    public void append(double time, double lng, double lat){
        ts_list.add(new Unit(time,new GeometryPoint<T>(lng,lat)));
        uniqued = false;
        sorted = 0;
    }
    
    /**
     * display the time series
     */
    public void display(){
        System.out.println("Time\tLongitude\tLatitude");
        for(Unit<GeometryPoint<T>> tmp: ts_list){
            System.out.printf("%f\t%f\t%f\n",
                    tmp.time,tmp.value.longitude,tmp.value.latitude);
        }
    }
    
    
}
