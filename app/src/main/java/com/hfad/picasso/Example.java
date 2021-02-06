package com.hfad.picasso;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Example {

    @SerializedName("result")
    @Expose
    private Result[] result;

    public Result[] getResult() {
        return result;
    }

}