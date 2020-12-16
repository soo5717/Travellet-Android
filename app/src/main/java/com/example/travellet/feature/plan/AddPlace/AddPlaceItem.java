package com.example.travellet.feature.plan.AddPlace;

public class AddPlaceItem {
    String title, type, addr;
    double mapx, mapy;

    public AddPlaceItem(String title, String type, String addr, double mapx, double mapy) {
        this.title = title;
        this.type = type;
        this.addr = addr;
        this.mapx = mapx;
        this.mapy = mapy;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public double getMapx() {
        return mapx;
    }

    public void setMapx(double mapx) {
        this.mapx = mapx;
    }

    public double getMapy() {
        return mapy;
    }

    public void setMapy(double mapy) {
        this.mapy = mapy;
    }
}
