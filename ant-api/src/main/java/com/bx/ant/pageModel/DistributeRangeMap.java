package com.bx.ant.pageModel;


/**
 * Created by 黄晓渝 on 2018/1/4.
 */
public class DistributeRangeMap implements java.io.Serializable {

    private static final long serialVersionUID = 5454155825314635342L;
    private Double lng;
    private Double lat;

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }
}
