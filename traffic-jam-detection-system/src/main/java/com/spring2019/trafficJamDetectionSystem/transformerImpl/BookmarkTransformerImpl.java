package com.spring2019.trafficJamDetectionSystem.transformerImpl;

import com.spring2019.trafficJamDetectionSystem.entity.Bookmark;
import com.spring2019.trafficJamDetectionSystem.entity.BookmarkCamera;
import com.spring2019.trafficJamDetectionSystem.entity.Camera;
import com.spring2019.trafficJamDetectionSystem.model.BookmarkCameraModel;
import com.spring2019.trafficJamDetectionSystem.model.BookmarkModel;
import com.spring2019.trafficJamDetectionSystem.transformer.BookmarkTransformer;
import com.spring2019.trafficJamDetectionSystem.transformer.CameraTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookmarkTransformerImpl implements BookmarkTransformer {

    @Autowired
    CameraTransformer cameraTransformer;

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

    @Override
    public BookmarkCamera bookmarkCameraModeltoEntity(BookmarkCameraModel bookmarkCameraModel) {
        Camera camera = cameraTransformer.modelToEntity(bookmarkCameraModel.getCamerakModel());
        Bookmark bookmark = modelToEntity(bookmarkCameraModel.getBookmarkModel());
        BookmarkCamera bookmarkCamera = new BookmarkCamera();
        bookmarkCamera.setCameraByCameraId(camera);
        bookmarkCamera.setBookmarkByBookmarkId(bookmark);
        return bookmarkCamera;
    }
}
