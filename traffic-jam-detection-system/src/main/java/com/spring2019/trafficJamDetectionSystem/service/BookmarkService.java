package com.spring2019.trafficJamDetectionSystem.service;

import com.spring2019.trafficJamDetectionSystem.entity.Account;
import com.spring2019.trafficJamDetectionSystem.entity.Bookmark;
import com.spring2019.trafficJamDetectionSystem.entity.BookmarkCamera;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookmarkService {
    public List<Bookmark> getBookMarkByAccountId(Integer accountID);

    public List<Bookmark> getAllBookmarks();

    public Integer removeBookMarkById(Integer id);

    public Bookmark createBookmark(Bookmark newBookmark);

    public List<String> getAccountByCameraId(int cameraId);

    public void removeBookMarkCameraWithBookMarkID(Bookmark bookMark);

    public BookmarkCamera saveBookmarkCamera(BookmarkCamera bookmarkCamera);
}
