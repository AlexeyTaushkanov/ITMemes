package com.hfad.picasso.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {
    @SerializedName("description")
    @Expose
    private String _description;
    @SerializedName("gifURL")
    @Expose
    private String _gifURL;

    public String getDescription() {
        return _description;
    }
    public String getGifURL() {
        return _gifURL;
    }
}
