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
        model.setCode(entity.getCode());
        model.setLatitude(entity.getLatitude());
        model.setLongtitude(entity.getLongtitude());
        model.setOrder(entity.getCamOrder());
        model.setWidth(entity.getWidth());
        model.setImage(entity.getImage());
        model.setStatus(entity.getStatus());

      //  model.setStreet(streetTransformer.entityToModel(entity.getStreetByStreetId()));
        return model;
    }

    @Override
    public Camera modelToEntity(CameraModel model) {
        Camera entity=new Camera();

        entity.setId(model.getId());
        entity.setCode(model.getCode());
        entity.setLatitude(model.getLatitude());
        entity.setLongtitude(model.getLongtitude());
        entity.setCamOrder(model.getOrder());
        entity.setWidth(entity.getWidth());
        entity.setStatus(entity.getStatus());
        entity.setImage(entity.getImage());
        Street street=new Street();
        street.setId(model.getStreet().getId());


        return entity;
    }
}
