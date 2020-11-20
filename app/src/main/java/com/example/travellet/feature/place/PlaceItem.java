package com.example.travellet.feature.place;

public class PlaceItem {
    String placeListThumb, placeListTitle, placeListAddr;
    boolean likeState;

    public PlaceItem(String placeListThumb, String placeListTitle, String placeListAddr, boolean likeState) {
        this.placeListThumb = placeListThumb;
        this.placeListTitle = placeListTitle;
        this.placeListAddr = placeListAddr;
        this.likeState = likeState;
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

    public boolean getlikeState() {
        return likeState;
    }

    public void setlikeState(boolean likeState) {
        this.likeState = likeState;
    }


}
