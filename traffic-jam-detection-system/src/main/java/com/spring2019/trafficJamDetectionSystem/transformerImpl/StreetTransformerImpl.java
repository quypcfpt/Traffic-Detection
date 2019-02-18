package com.spring2019.trafficJamDetectionSystem.transformerImpl;

import com.spring2019.trafficJamDetectionSystem.entity.Street;
import com.spring2019.trafficJamDetectionSystem.model.StreetModel;
import com.spring2019.trafficJamDetectionSystem.transformer.StreetTransformer;
import org.springframework.stereotype.Service;

@Service
public class StreetTransformerImpl implements StreetTransformer {

    @Override
    public StreetModel entityToModel(Street entity) {
        StreetModel model = new StreetModel();

        model.setId(entity.getId());
        model.setName(entity.getName());
        model.setDistrict(entity.getDistrict());
        model.setCity(entity.getCity());

        return model;
    }

    @Override
    public Street modelToEntity(StreetModel model) {
        Street entity = new Street();

        entity.setId(model.getId());
        entity.setName(model.getName());
        entity.setDistrict(model.getDistrict());
        entity.setCity(model.getCity());

        return entity;
    }
}
