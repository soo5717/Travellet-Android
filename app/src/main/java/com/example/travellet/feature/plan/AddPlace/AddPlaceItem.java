package com.example.travellet.feature.plan.AddPlace;

public class AddPlaceItem {
    String title, type, addr;

    public AddPlaceItem(String title, String type, String addr) {
        this.title = title;
        this.type = type;
        this.addr = addr;
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
}
