package com.spring2019.trafficJamDetectionSystem.transformerImpl;

import com.spring2019.trafficJamDetectionSystem.entity.Camera;
import com.spring2019.trafficJamDetectionSystem.entity.Street;
import com.spring2019.trafficJamDetectionSystem.model.CameraModel;
import com.spring2019.trafficJamDetectionSystem.transformer.CameraTransformer;
import com.spring2019.trafficJamDetectionSystem.transformer.StreetTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CameraTransformerImpl implements CameraTransformer {

    @Autowired
    StreetTransformer streetTransformer;

    @Override
    public CameraModel entityToModel(Camera entity) {
        CameraModel model = new CameraModel();

        model.setId(entity.getId());
        model.setDescription(entity.getDescription());
        model.setPosition(entity.getPosition());
        model.setOrder(entity.getCamOrder());
        model.setObserverStatus(entity.getObservedStatus());
        model.setStreet(streetTransformer.entityToModel(entity.getStreetByStreetId()));
        model.setActive(entity.getIsActive());
        return model;
    }

    @Override
    public Camera modelToEntity(CameraModel model) {
        Camera entity=new Camera();

        entity.setId(model.getId());
        entity.setDescription(model.getDescription());
        entity.setPosition(model.getPosition());
        entity.setObservedStatus(model.getObserverStatus());
        entity.setCamOrder(model.getOrder());
        entity.setObservedStatus(model.getObserverStatus());

        Street street=new Street();
        street.setId(model.getStreet().getId());
        entity.setStreetByStreetId(street);


        return entity;
    }
}
