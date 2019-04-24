package com.spring2019.trafficJamDetectionSystem.service;

import com.spring2019.trafficJamDetectionSystem.entity.Camera;
import com.spring2019.trafficJamDetectionSystem.entity.Report;
import com.spring2019.trafficJamDetectionSystem.model.DetectionModel;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.List;

@Service
public interface ReportService {

    public List<Report> getReportByCameraAndDate(int cameraId, String date) throws ParseException;

    public Report saveReport(Camera camera, DetectionModel detectionModel);

    public Report getLastReportByCamera(Camera camera);

    public List<Report> getUnfinishedReport();
}
