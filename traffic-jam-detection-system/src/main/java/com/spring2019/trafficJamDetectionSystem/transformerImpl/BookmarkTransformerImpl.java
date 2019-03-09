package com.spring2019.trafficJamDetectionSystem.transformerImpl;

import com.spring2019.trafficJamDetectionSystem.entity.Bookmark;
import com.spring2019.trafficJamDetectionSystem.model.BookmarkModel;
import com.spring2019.trafficJamDetectionSystem.transformer.BookmarkTransformer;
import org.springframework.stereotype.Service;

@Service
public class BookmarkTransformerImpl implements BookmarkTransformer {
    @Override
    public BookmarkModel entityToModel(Bookmark entity) {
        BookmarkModel model = new BookmarkModel();

        model.setId(entity.getId());
        model.setAccountId(entity.getAccountId());
        model.setOrigin(entity.getOrigin());
        model.setDestination(entity.getDestination());
        model.setOri_coordinate(entity.getOri_coordinate());
        model.setDes_coordinate(entity.getDes_coordinate());
        return model;
    }

    @Override
    public Bookmark modelToEntity(BookmarkModel model) {
        Bookmark entity = new Bookmark();

        entity.setId(model.getId());
        entity.setAccountId(model.getAccountId());
        entity.setOrigin(model.getOrigin());
        entity.setDestination(model.getDestination());
        entity.setOri_coordinate(model.getOri_coordinate());
        entity.setDes_coordinate(model.getDes_coordinate());
        return entity;
    }
}
