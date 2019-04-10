package com.spring2019.trafficJamDetectionSystem.serviceImpl;

import com.google.gson.JsonObject;
import com.google.maps.DirectionsApi;
import com.google.maps.model.LatLng;
import com.spring2019.trafficJamDetectionSystem.common.ParameterStringBuilder;
import com.spring2019.trafficJamDetectionSystem.entity.Account;
import com.spring2019.trafficJamDetectionSystem.entity.Bookmark;
import com.spring2019.trafficJamDetectionSystem.entity.BookmarkCamera;
import com.spring2019.trafficJamDetectionSystem.entity.Camera;
import com.spring2019.trafficJamDetectionSystem.repository.BookmarkRepository;
import com.spring2019.trafficJamDetectionSystem.repository.BookmarkCameraRepository;
import com.spring2019.trafficJamDetectionSystem.service.BookmarkService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpUtils;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
}
