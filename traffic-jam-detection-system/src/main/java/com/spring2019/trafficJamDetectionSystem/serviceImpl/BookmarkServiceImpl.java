package com.spring2019.trafficJamDetectionSystem.serviceImpl;

import com.spring2019.trafficJamDetectionSystem.entity.Bookmark;
import com.spring2019.trafficJamDetectionSystem.repository.BookmarkRepository;
import com.spring2019.trafficJamDetectionSystem.service.BookmarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookmarkServiceImpl implements BookmarkService {
    @Autowired
    BookmarkRepository bookmarkRepository;
    @Override
    public List<Bookmark> getBookMarkByAccountId(Integer accountID) {
        List<Bookmark> bookmarkList= bookmarkRepository.findBookmarksByAccountId(accountID);
        return bookmarkList;
    }

    @Override
    public void removeBookMarkById(Integer id) {
        bookmarkRepository.deleteBookmarkById(id);
        Integer isDelete=bookmarkRepository.deleteBookmarkById(id);
        return isDelete;
    }
}
