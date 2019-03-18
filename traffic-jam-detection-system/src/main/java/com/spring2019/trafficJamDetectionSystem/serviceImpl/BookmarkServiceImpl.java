package com.spring2019.trafficJamDetectionSystem.serviceImpl;

import com.spring2019.trafficJamDetectionSystem.entity.Account;
import com.spring2019.trafficJamDetectionSystem.entity.Bookmark;
import com.spring2019.trafficJamDetectionSystem.entity.Camera;
import com.spring2019.trafficJamDetectionSystem.entity.CameraAccount;
import com.spring2019.trafficJamDetectionSystem.repository.AccountRepository;
import com.spring2019.trafficJamDetectionSystem.repository.BookmarkRepository;
import com.spring2019.trafficJamDetectionSystem.repository.CameraAccountRepository;
import com.spring2019.trafficJamDetectionSystem.service.AccountService;
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
    CameraAccountRepository cameraAccountRepository;

    @Override
    public List<Bookmark> getBookMarkByAccountId(Integer accountID) {
        List<Bookmark> bookmarkList = bookmarkRepository.findBookmarksByAccountId(accountID);
        return bookmarkList;
    }

    @Override
    public Integer removeBookMarkById(Integer id) {
        bookmarkRepository.deleteBookmarkById(id);
        Integer isDelete = bookmarkRepository.deleteBookmarkById(id);
        return isDelete;
    }

    @Override
    public List<Bookmark> getAllBookmarks() {
        return bookmarkRepository.findAll();
    }

    @Override
    public void createBookmark(Bookmark newBookmark) {
        bookmarkRepository.save(newBookmark);
    }

    @Override
    public List<Account> getAccountByCameraId(int cameraId) {
        List<Account> accountList = new ArrayList<>();

        Camera camera = new Camera();
        camera.setId(cameraId);

        List<CameraAccount> cameraAccounts = cameraAccountRepository.findByCameraByCameraId(camera);
        for (CameraAccount cameraAccount : cameraAccounts) {
            accountList.add(cameraAccount.getAccountByAccountId());
        }

        return accountList;
    }
}
