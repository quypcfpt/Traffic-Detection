package com.spring2019.trafficJamDetectionSystem.transformerImpl;

import com.spring2019.trafficJamDetectionSystem.entity.Camera;
import com.spring2019.trafficJamDetectionSystem.entity.Report;
import com.spring2019.trafficJamDetectionSystem.model.ReportModel;
import com.spring2019.trafficJamDetectionSystem.transformer.CameraTransformer;
import com.spring2019.trafficJamDetectionSystem.transformer.ReportTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class ReportTransfromerImpl implements ReportTransformer {

    @Autowired
    CameraTransformer cameraTransformer;

    @Override
    public Report modelToEntity(ReportModel model) {
        Report report = new Report();

        report.setId(model.getId());

        report.setCameraByCameraId(cameraTransformer.modelToEntity(model.getCamera()));
        report.setEndTime(Timestamp.valueOf(model.getEndTime()));
        report.setStartTime(Timestamp.valueOf(model.getStartTime()));
        report.setDate(model.getDate());
        report.setImageUrl(model.getImgUrl());
        report.setStatus(model.getStatus());
        return report;
    }

    @Override
    public ReportModel entityToModel(Report entity) {
        ReportModel model = new ReportModel();

        model.setCamera(cameraTransformer.entityToModel(entity.getCameraByCameraId()));
        model.setImgUrl(entity.getImageUrl());
        model.setStartTime(entity.getStartTime().toString());
        if (entity.getEndTime() != null) {
            model.setEndTime(entity.getEndTime().toString());
        }
        model.setStatus(entity.getStatus());
        model.setDate(entity.getDate());
        return model;
    }
}
