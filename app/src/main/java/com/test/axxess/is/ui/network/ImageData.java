package com.test.axxess.is.ui.network;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ImageData {
    private String id;
    private String title;
    private String link;
    private List<ChildImage> images;
    @Expose
    @SerializedName("is_album")
    private boolean isAlbum;
    @Expose
    @SerializedName("cover")
    private String cover;
    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public List<ChildImage> getImages() {
        return images;
    }

    public boolean isAlbum() {
        return isAlbum;
    }

    public String getCover() {
        return cover;
    }
}
