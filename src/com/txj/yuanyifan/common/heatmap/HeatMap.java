package com.txj.yuanyifan.common.heatmap;


import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by YuanYifan on 2017/6/20.
 */
public class HeatMap implements java.io.Serializable {
    long accuracy;

    public long getAccuracy() {
        return accuracy;
    }

    public HeatMap(long acc) {
        accuracy = acc;
    }

    public HeatMap(double[][] gpsInfo, long acc) {
        accuracy = acc;
        append(gpsInfo);
    }

    HashMap<HeatPoint<Integer>, Double> hMap = new HashMap<>();

    public HashMap<HeatPoint<Integer>, Double> getHeatMap() {
        return hMap;
    }

    public void append(double[][] gpsInfo) {
        for (double[] pointInfo : gpsInfo) {
            assert pointInfo.length == 3;
            HeatPoint<Integer> gp = new HeatPoint<>(pointInfo[0], pointInfo[1], 0, accuracy);
            if (!hMap.containsKey(gp)) {
                hMap.put(gp, pointInfo[2]);
            } else {
                hMap.put(gp, hMap.get(gp) + pointInfo[2]);
            }
        }
    }

    public double apply(double longitudeSearch, double latitudeSearch) {
        HeatPoint<Integer> heatPointSearch
                = new HeatPoint<Integer>(longitudeSearch, latitudeSearch, accuracy);
        if (getHeatMap().containsKey(heatPointSearch)) {
            return getHeatMap().get(heatPointSearch);
        } else {
            return 0.0f;
        }
    }

    public double apply(HeatPoint heatPointInput) {
        HeatPoint<Integer> heatPointSearch
                = new HeatPoint<Integer>(
                heatPointInput.longitude,
                heatPointInput.latitude,
                accuracy
        );
        if (getHeatMap().containsKey(heatPointSearch)) {
            return getHeatMap().get(heatPointSearch);
        } else {
            return 0.0f;
        }
    }


    public int unionSize(HeatMap hm) {
        Set<HeatPoint<Integer>> objSet = hm.getHeatMap().keySet();
        objSet.retainAll(getHeatMap().keySet());
        return objSet.size();
    }

    public double innerProduct(HeatMap hm) {
        Set<HeatPoint<Integer>> objSet = hm.getHeatMap().keySet();
        objSet.retainAll(getHeatMap().keySet());
        double sumIP = 0;
        for (HeatPoint<Integer> gp : objSet) {
            sumIP += getHeatMap().get(gp) * hm.getHeatMap().get(gp);
        }
        return sumIP;
    }

    public double modulus() {
        double sum = 0;
        for (double val : getHeatMap().values()) {
            sum += val * val;
        }
        return sum;
    }

    public double modulus(int n) {
        double sum = 0;
        for (double val : getHeatMap().values()) {
            sum += Math.abs(Math.pow(val, n));
        }
        return sum;
    }

    public double[][] exportMatrix() {
        double[][] sparse_coo = new double[hMap.size()][3];
        Iterator iter = hMap.entrySet().iterator();
        int i = 0;
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            HeatPoint<Integer> key = (HeatPoint<Integer>) entry.getKey();
            Double val = (Double) entry.getValue();
            sparse_coo[i][0] = key.longitude;
            sparse_coo[i][1] = key.latitude;
            sparse_coo[i][2] = val;
            i++;
        }
        return sparse_coo;
    }

    public void display() {
        for (HeatPoint<Integer> gp : getHeatMap().keySet()) {
            System.out.printf("%s --> %s\n", gp.toString(), getHeatMap().get(gp).toString());
        }
    }
}
