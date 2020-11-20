package com.example.travellet.feature.place;

public class PlaceItem {
    String placeListThumb, placeListTitle, placeListAddr;
    int placeLike;

    public PlaceItem(String placeListThumb, String placeListTitle, String placeListAddr) {
        this.placeListThumb = placeListThumb;
        this.placeListTitle = placeListTitle;
        this.placeListAddr = placeListAddr;
        this.placeLike = 0;
    }


    public String getPlaceListThumb() {
        return placeListThumb;
    }

    public void setPlaceListThumb(String placeListThumb) {
        this.placeListThumb = placeListThumb;
    }

    public String getPlaceListTitle() {
        return placeListTitle;
    }

    public void setPlaceListTitle(String placeListTitle) {
        this.placeListTitle = placeListTitle;
    }

    public String getPlaceListAddr() {
        return placeListAddr;
    }

    public void setPlaceListAddr(String placeListAddr) {
        this.placeListAddr = placeListAddr;
    }

    public int getPlaceLike() {
        return placeLike;
    }

    public void setPlaceLike(int placeLike) {
        this.placeLike = placeLike;
    }


}
