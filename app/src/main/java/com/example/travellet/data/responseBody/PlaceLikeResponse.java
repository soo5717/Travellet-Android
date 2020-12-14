package com.example.travellet.data.responseBody;

import android.provider.ContactsContract;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PlaceLikeResponse {
    @SerializedName("status")
    public Integer status;
    @SerializedName("success")
    public Boolean success;
    @SerializedName("data")
    private Data data;

    public Integer getStatus() {
        return status;
    }

    public Boolean getSuccess() {
        return success;
    }

    public Data getData() {
        return data;
    }

    public static class Data {
        @SerializedName("count")
        public int count;
        @SerializedName("rows")
        public ArrayList<ContentId> rows;

        public int getCount() {
            return count;
        }

        public ArrayList<ContentId> getRow() {
            return rows;
        }
    }

    public static class ContentId {
        @SerializedName("contentId")
        public int contentId;

        public int getContentId() {
            return contentId;
        }
    }

}
