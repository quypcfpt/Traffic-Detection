package com.spring2019.trafficJamDetectionSystem.serviceImpl;

import com.spring2019.trafficJamDetectionSystem.entity.Bookmark;
import com.spring2019.trafficJamDetectionSystem.entity.BookmarkCamera;
import com.spring2019.trafficJamDetectionSystem.entity.Camera;
import com.spring2019.trafficJamDetectionSystem.repository.BookmarkRepository;
import com.spring2019.trafficJamDetectionSystem.repository.BookmarkCameraRepository;
import com.spring2019.trafficJamDetectionSystem.service.BookmarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookmarkServiceImpl implements BookmarkService {

    @Autowired
    BookmarkRepository bookmarkRepository;

    @Autowired
    BookmarkCameraRepository bookmarkCameraRepository;

    @Override
    public List<Bookmark> getBookMarkByAccountId(Integer accountID) {
        List<Bookmark> bookmarkList = bookmarkRepository.findBookmarksByAccountId(accountID);
        return bookmarkList;
    }

    @Override
    public List<Bookmark> getAllBookmarks() {
        return bookmarkRepository.findAll();
    }

    @Override
    public Integer removeBookMarkById(Integer id) {
        Integer isDelete = bookmarkRepository.deleteBookmarkById(id);
        return isDelete;
    }

    @Override
    public Bookmark createBookmark(Bookmark newBookmark) {
        return bookmarkRepository.save(newBookmark);
    }

    @Override
    public List<String> getAccountByCameraId(int cameraId) {
        List<String> accountList = new ArrayList<>();

        Camera camera = new Camera();
        camera.setId(cameraId);

        accountList = bookmarkCameraRepository.findUsernameByCamera(cameraId);

        return accountList;
    }

    @Override
    public void removeBookMarkCameraWithBookMarkID(Bookmark bookMarkId) {
        bookmarkCameraRepository.deleteBookmarkCamerasByBookmarkByBookmarkId(bookMarkId);
    }

    @Override
    public BookmarkCamera saveBookmarkCamera(BookmarkCamera bookmarkCamera) {
        return bookmarkCameraRepository.save(bookmarkCamera);
    }

    public List<BookmarkCamera> getCameraInBookmark(int id){
        Bookmark bookmark = new Bookmark();
        bookmark.setId(id);
        return bookmarkCameraRepository.findBookmarkCamerasByBookmarkByBookmarkId(bookmark);
    }

    @Override
    public void deleteBookmarkByCamera(Camera camera) {
        bookmarkCameraRepository.deleteBookmarkCamerasByCameraByCameraId(camera);
    }
}
