package com.spring2019.trafficJamDetectionSystem.transformer;

import com.spring2019.trafficJamDetectionSystem.entity.Camera;
import com.spring2019.trafficJamDetectionSystem.model.CameraModel;
import org.springframework.stereotype.Service;

@Service
public interface CameraTransformer {

    public CameraModel entityToModel(Camera entity);

    public Camera modelToEntity(CameraModel model);
}
