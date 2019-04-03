package trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.model;

import com.google.gson.annotations.Expose;

import java.util.List;

public class MultiBookmarkCameraModel {

    @Expose
    private BookmarkModel bookmark;
    @Expose
    private List<CameraModel> cameraList;

    public BookmarkModel getBookmark() {
        return bookmark;
    }

    public void setBookmark(BookmarkModel bookmark) {
        this.bookmark = bookmark;
    }

    public List<CameraModel> getCameraList() {
        return cameraList;
    }

    public void setCameraList(List<CameraModel> cameraList) {
        this.cameraList = cameraList;
    }
}