package com.test.axxess.is.ui.network;

import java.util.ArrayList;
import java.util.List;

public class ImageSearchResponse {
    private List<ImageData> data;
    private boolean success;
    private int status;

    public List<ImageData> getData() {
        return data != null ? data : new ArrayList<ImageData>(); // to avoid null pointer exception
    }

    public boolean isSuccess() {
        return success;
    }

    public int getStatus() {
        return status;
    }
}
