package com.spring2019.trafficJamDetectionSystem.serviceImpl;

import com.spring2019.trafficJamDetectionSystem.entity.Camera;
import com.spring2019.trafficJamDetectionSystem.entity.Report;
import com.spring2019.trafficJamDetectionSystem.repository.ReportRepostitory;
import com.spring2019.trafficJamDetectionSystem.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

        Date date= new Date(formatter.parse(dateStr).getTime());

        System.out.println(date.toString());
        return reportRepostitory.findByCameraByCameraIdAndDate(camera, date);
    }

    @Override
    public Report saveReport(Report report) {
        return reportRepostitory.save(report);
    }
}
