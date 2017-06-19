package com.txj.yuanyifan.common.timeseries;

/**
 * @author yuanyifan
 * @Mail Address  yuanyifan@deewinfl.com
 * @Telephone 185-0290-5886
 * @Company Shaanxi Skywin Internet of Vehicles Information Technology Co.,Ltd
 */
public class Unit<T> implements Comparable<Unit>{
    
    public T value;
    public double time = 0.0D;
    public boolean remove = false;
    
    /**
     * Initial by no parameters
     */
    public Unit(){}

    /**
     *
     * @param time_in initial time
     * @param value_in initial value
     */
    public Unit(double time_in, T value_in){
        time = time_in;
        value = value_in;
    }

    @Override
    public int compareTo(Unit o){
        if (o.time<this.time){
            return 1;
        } else if (o.time>this.time){
            return -1;
        } else {
            return 0;
        }
    }
}
