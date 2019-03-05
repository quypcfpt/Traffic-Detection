package com.spring2019.trafficJamDetectionSystem.transformer;

import com.spring2019.trafficJamDetectionSystem.entity.Image;
import com.spring2019.trafficJamDetectionSystem.model.ImageModel;
import org.springframework.stereotype.Service;

@Service
public interface ImageTransformer {
    public ImageModel entityToModel(Image entity);

    public Image modelToEntity(ImageModel model);
}
