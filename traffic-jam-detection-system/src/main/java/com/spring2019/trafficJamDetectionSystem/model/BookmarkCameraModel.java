package com.spring2019.trafficJamDetectionSystem.model;

import com.google.gson.annotations.Expose;


public class BookmarkCameraModel {

    @Expose
    private int id;
    private BookmarkModel bookmarkModel;
    private CameraModel camerakModel;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BookmarkModel getBookmarkModel() {
        return bookmarkModel;
    }

    public void setBookmarkModel(BookmarkModel bookmarkModel) {
        this.bookmarkModel = bookmarkModel;
    }

    public CameraModel getCamerakModel() {
        return camerakModel;
    }

    public void setCameraModel(CameraModel camerakModel) {
        this.camerakModel = camerakModel;
    }
}