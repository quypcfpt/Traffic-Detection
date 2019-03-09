package trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.model;

import com.google.gson.annotations.Expose;

import java.util.List;

public class MultipleBookmarkModel {
    @Expose
    List<BookmarkModel> bookmarkModelList;


    public MultipleBookmarkModel() {
    }

    public List<BookmarkModel> getBookmarkModelList() {
        return bookmarkModelList;
    }

    public void setBookmarkModelList(List<BookmarkModel> bookmarkModelList) {
        this.bookmarkModelList = bookmarkModelList;
    }
}
