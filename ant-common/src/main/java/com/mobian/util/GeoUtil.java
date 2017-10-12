package com.mobian.util;

import com.alibaba.fastjson.JSONObject;

import java.math.BigDecimal;

/**
 * Created by john on 17/10/11.
 */
public class GeoUtil {
    /**
     * 计算两经纬度点之间的距离（单位：米）
     * @param lng1  经度
     * @param lat1  纬度
     * @param lng2
     * @param lat2
     * @return
     */
    public static double getDistance(double lng1,double lat1,double lng2,double lat2){
        double radLat1 = Math.toRadians(lat1);
        double radLat2 = Math.toRadians(lat2);
        double a = radLat1 - radLat2;
        double b = Math.toRadians(lng1) - Math.toRadians(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1)
                * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        s = s * 6378137.0;// 取WGS84标准参考椭球中的地球长半径(单位:m)
        s = Math.round(s * 10000) / 10000;
        return s;
    }
    /**
     * 计算TP值
     * @param curPoint      当前点
     * @param relatedPoint  偏移点
     * @param isGeography   是否是地理坐标 false为2d坐标
     * @return              tp值
     *//*
    public static double getDirAngle(Point curPoint,Point relatedPoint,boolean isGeography){
        double result = 0;
        if(isGeography){
            double y2 = Math.toRadians(relatedPoint.getLat());
            double y1 = Math.toRadians(curPoint.getLat());
            double alpha = Math.atan2(relatedPoint.getLat() - curPoint.getLat(), (relatedPoint.getLng() - curPoint.getLng()) * Math.cos((y2 - y1) / 2));//纬度方向乘以cos(y2-y1/2)
            double delta =alpha<0?(2*Math.PI+alpha):alpha;
            result = Math.toDegrees(delta);
        }else {
            double alpha = Math.atan2(relatedPoint.getLat() - curPoint.getLat(), relatedPoint.getLng() - curPoint.getLng());
            double delta=alpha<0?(2*Math.PI+alpha):alpha;
            result = Math.toDegrees(delta);
        }
        return result;
    }*/
    public static BigDecimal[] getPosition(String address) {
        address = address.replaceAll(" ", "");
        String requestUrl = "http://api.map.baidu.com/geocoder/v2/?output=json&address=" + address + "&ak=yDOmoXl5HIFt6KZe3BMeL4NRHBGLmCHe";
        JSONObject jsonObject = JSONObject.parseObject(HttpUtil.httpRequest(requestUrl, "GET", null));
        if (jsonObject != null) {
            if (jsonObject.getInteger("status") == 0) {
                JSONObject result = (JSONObject) jsonObject.get("result");
                JSONObject location = (JSONObject) result.get("location");
                Object ln = location.get("lng");
                Object la = location.get("lat");


                BigDecimal[] point = new BigDecimal[2];
                point[0] = new BigDecimal(ln.toString());
                point[1] = new BigDecimal(la.toString());
                return point;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(getDistance(121.446014,31.215937,121.446028464238,31.2158502442799  ));
    }
}
