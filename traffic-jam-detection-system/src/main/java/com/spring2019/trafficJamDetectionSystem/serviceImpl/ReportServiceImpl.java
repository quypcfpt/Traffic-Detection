package com.spring2019.trafficJamDetectionSystem.serviceImpl;

import com.spring2019.trafficJamDetectionSystem.entity.Camera;
import com.spring2019.trafficJamDetectionSystem.entity.Report;
import com.spring2019.trafficJamDetectionSystem.model.DetectionModel;
import com.spring2019.trafficJamDetectionSystem.repository.ReportRepostitory;
import com.spring2019.trafficJamDetectionSystem.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    ReportRepostitory reportRepostitory;

    @Override
    public List<Report> getReportByCameraAndDate(int cameraId, String dateStr) throws ParseException {
        Camera camera = new Camera();
        camera.setId(cameraId);

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

        Date date = new Date(formatter.parse(dateStr).getTime());

        System.out.println(date.toString());
        return reportRepostitory.findByCameraByCameraIdAndDate(camera, date);
    }

    @Override
    public Report saveReport(Camera camera, DetectionModel detectionModel) {

        // Update last report end time
        Report lastReport = reportRepostitory.findTopByCameraByCameraIdOrderByIdDesc(camera);
        lastReport.setEndTime(detectionModel.getTime());
        reportRepostitory.save(lastReport);

        // Save new report
        Report newReport = new Report();
        newReport.setCameraByCameraId(camera);
        newReport.setStatus(detectionModel.getStatusId());
        newReport.setImageUrl(detectionModel.getImageUrl());
        newReport.setStartTime(addOneSec(detectionModel.getTime()));

        Date date = new Date(detectionModel.getTime().getTime());
        newReport.setDate(date);

        return reportRepostitory.save(newReport);
    }


    @Override
    public Report getLastReportByCamera(Camera camera) {
        return reportRepostitory.findTopByCameraByCameraIdOrderByIdDesc(camera);
    }

    @Override
    public List<Report> getUnfinishedReport() {
        return reportRepostitory.findByEndTimeIsNull();
    }

    private Timestamp addOneSec(Timestamp timestamp) {

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timestamp.getTime());
        cal.add(Calendar.SECOND, 1);

        return new Timestamp(cal.getTime().getTime());
    }
}
