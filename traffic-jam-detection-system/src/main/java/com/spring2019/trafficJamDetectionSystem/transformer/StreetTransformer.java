package com.spring2019.trafficJamDetectionSystem.transformer;

import com.spring2019.trafficJamDetectionSystem.entity.Street;
import com.spring2019.trafficJamDetectionSystem.model.StreetModel;
import org.springframework.stereotype.Service;

@Service
public interface StreetTransformer {

    StreetModel entityToModel(Street entity);

    Street modelToEntity(StreetModel model);
}
