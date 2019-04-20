package com.spring2019.trafficJamDetectionSystem.service;

import com.spring2019.trafficJamDetectionSystem.entity.Report;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.List;

@Service
public interface ReportService {

    public List<Report> getReportByCameraAndDate(int cameraId, String date) throws ParseException;

    public Report saveReport(Report report);
}
