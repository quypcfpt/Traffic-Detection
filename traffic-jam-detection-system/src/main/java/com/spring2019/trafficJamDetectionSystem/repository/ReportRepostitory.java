package com.spring2019.trafficJamDetectionSystem.repository;

import com.spring2019.trafficJamDetectionSystem.entity.Camera;
import com.spring2019.trafficJamDetectionSystem.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@Repository
public interface ReportRepostitory  extends JpaRepository<Report,Integer> {

    List<Report> findByCameraByCameraIdAndDate(Camera camera, Date date);

    Report findTopByCameraByCameraIdOrderByIdDesc(Camera camera);
}
