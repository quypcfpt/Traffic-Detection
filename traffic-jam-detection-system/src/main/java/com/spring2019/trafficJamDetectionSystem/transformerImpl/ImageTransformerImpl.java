package com.spring2019.trafficJamDetectionSystem.transformerImpl;

import com.spring2019.trafficJamDetectionSystem.entity.Image;
import com.spring2019.trafficJamDetectionSystem.model.ImageModel;
import com.spring2019.trafficJamDetectionSystem.transformer.ImageTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class ImageTransformerImpl implements ImageTransformer {
    @Autowired
    ImageTransformer imageTransformer;
    @Override
    public ImageModel entityToModel(Image entity) {
        ImageModel models =new ImageModel();

        models.setId(entity.getId());
        models.setCameraId(entity.getCameraId());
        models.setLink(entity.getLink());
        models.setTime(entity.getTime()+"");

        return models;
    }

    @Override
    public Image modelToEntity(ImageModel model) {
        Image entity = new Image();

        entity.setId(model.getId());
        entity.setCameraId(model.getCameraId());
        entity.setLink(model.getLink());
        entity.setTime(Timestamp.valueOf(model.getTime()));

        return entity;
    }
}
