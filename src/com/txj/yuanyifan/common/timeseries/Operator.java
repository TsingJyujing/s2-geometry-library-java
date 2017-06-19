package com.txj.yuanyifan.common.timeseries;

/**
 * @author yuanyifan
 * @Mail Address  yuanyifan@deewinfl.com
 * @Telephone 185-0290-5886
 * @Company Shaanxi Skywin Internet of Vehicles Information Technology Co.,Ltd
 */
public abstract class Operator<Type> {
    /**
     * 
     * @param value_in value in to process
     * @return process result
     * @example 
       new Operator<Double></>(){
            @Override
            public Double op(Double value_in) {
                return value_in*value_in;
            }
     * This Class defined a pow(x,2) operation
     */
    abstract public Type op(Type value_in);
}
