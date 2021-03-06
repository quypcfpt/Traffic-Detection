package com.spring2019.trafficJamDetectionSystem.transformerImpl;

import com.spring2019.trafficJamDetectionSystem.entity.Camera;
import com.spring2019.trafficJamDetectionSystem.entity.Street;
import com.spring2019.trafficJamDetectionSystem.model.CameraModel;
import com.spring2019.trafficJamDetectionSystem.transformer.CameraTransformer;
import com.spring2019.trafficJamDetectionSystem.transformer.StreetTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

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
        model.setResource(entity.getResource());
        model.setObserverStatus(entity.getObservedStatus());
        model.setStreet(streetTransformer.entityToModel(entity.getStreetByStreetId()));
        model.setActive(entity.getIsActive());
        model.setImgUrl(entity.getImageUrl());
        if (entity.getTime() != null) {
            model.setTime(entity.getTime().toString());
        }
        return model;
    }

    @Override
    public Camera modelToEntity(CameraModel model) {
        Camera entity = new Camera();

        entity.setId(model.getId());
        if (model.getDescription() != null) {
            entity.setDescription(model.getDescription());
        }

        if (model.getPosition() != null) {
            entity.setPosition(model.getPosition());
        }

        if (model.getObserverStatus() != null) {
            entity.setObservedStatus(model.getObserverStatus());
        }

        if (model.getResource() != null) {
            entity.setResource(model.getResource());
        }

        if (model.getOrder() != null) {
            entity.setCamOrder(model.getOrder());
        }

        if (model.getStreet() != null) {
            Street street = new Street();
            street.setId(model.getStreet().getId());
            entity.setStreetByStreetId(street);
        }
        if (model.getImgUrl() != null) {
            entity.setImageUrl(model.getImgUrl());
        }

        if (model.getTime() != null) {
            entity.setTime(Timestamp.valueOf(model.getTime()));
        }

        entity.setIsActive(model.isActive());
        return entity;


    }
}
