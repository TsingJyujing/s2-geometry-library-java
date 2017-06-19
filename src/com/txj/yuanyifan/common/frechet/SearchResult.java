package com.txj.yuanyifan.common.frechet;

/**
 * @author yuanyifan
 * @Mail Address  yuanyifan@deewinfl.com
 * @Telephone 185-0290-5886
 * @Company Shaanxi Skywin Internet of Vehicles Information Technology Co.,Ltd
 */
public class SearchResult {
    public int inIndex;
    public double inTime;
    public int outIndex;
    public double outTime;
    @Override
    public String toString(){
        return String.format("In time:%f\t In index:%d\t Out time:%f\t Out index:%d\t",
                inTime, inIndex, outTime, outIndex);
    }
    public String toCSV(String dec){
        return String.format("%f%s%d%s%f%s%d\t",
                inTime, dec, inIndex, dec, outTime, dec, outIndex);
    }
    public static String toCSVHead(String dec){
        return String.format("Time(in)%sIndex(in)%sTime(out)%sIndex(out)\t",
                dec, dec, dec);
    }
}