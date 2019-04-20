package com.spring2019.trafficJamDetectionSystem.transformer;

import com.spring2019.trafficJamDetectionSystem.entity.Report;
import com.spring2019.trafficJamDetectionSystem.model.ReportModel;
import org.springframework.stereotype.Service;

@Service
public interface ReportTransformer {
    public Report modelToEntity(ReportModel model);

    public ReportModel entityToModel(Report entity);
}
