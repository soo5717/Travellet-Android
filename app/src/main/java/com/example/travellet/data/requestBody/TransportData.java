package com.example.travellet.data.requestBody;

import com.google.gson.annotations.SerializedName;

public class TransportData {
    @SerializedName("sx")
    private Double sx;
    @SerializedName("sy")
    private Double sy;
    @SerializedName("ex")
    private Double ex;
    @SerializedName("ey")
    private Double ey;
    @SerializedName("pathType")
    private Integer pathType;
    @SerializedName("memo")
    private String memo;

    public TransportData(double sx, double sy, double ex, double ey, int pathType, String memo) {
        this.sx = sx;
        this.sy = sy;
        this.ex = ex;
        this.ey = ey;
        this.pathType = pathType;
        this.memo = memo;
    }
}
