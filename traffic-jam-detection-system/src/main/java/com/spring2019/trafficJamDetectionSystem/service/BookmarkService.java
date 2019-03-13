package com.spring2019.trafficJamDetectionSystem.service;

import com.spring2019.trafficJamDetectionSystem.entity.Bookmark;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookmarkService {

    public List<Bookmark> getBookMarkByAccountId(Integer accountID);

    public Integer removeBookMarkById(Integer id);
}
