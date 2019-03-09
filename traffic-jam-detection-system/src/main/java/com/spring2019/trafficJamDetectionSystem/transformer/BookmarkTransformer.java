package com.spring2019.trafficJamDetectionSystem.transformer;

import com.spring2019.trafficJamDetectionSystem.entity.Bookmark;
import com.spring2019.trafficJamDetectionSystem.model.BookmarkModel;
import org.springframework.stereotype.Service;

@Service
public interface BookmarkTransformer {
    public BookmarkModel entityToModel(Bookmark entity);

    public Bookmark modelToEntity(BookmarkModel model);
}
