package com.spring2019.trafficJamDetectionSystem.controllerImpl;

import com.spring2019.trafficJamDetectionSystem.common.CoreConstant;
import com.spring2019.trafficJamDetectionSystem.controller.ReportController;
import com.spring2019.trafficJamDetectionSystem.entity.Report;
import com.spring2019.trafficJamDetectionSystem.model.ReportModel;
import com.spring2019.trafficJamDetectionSystem.model.Response;
import com.spring2019.trafficJamDetectionSystem.service.ReportService;
import com.spring2019.trafficJamDetectionSystem.transformer.ReportTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
public class ReportControllerImpl extends AbstractController implements ReportController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReportController.class);

    @Autowired
    ReportService reportService;

    @Autowired
    ReportTransformer reportTransformer;

    @Override
    public String loadReportByCameraAndDate(Integer id, String date) {
        Response response = new Response<>(CoreConstant.STATUS_CODE_FAIL, CoreConstant.MESSAGE_FAIL);

        try {
            LOGGER.info("Start load report by Camera " + id);

            List<ReportModel> data = new ArrayList<>();
            List<Report> reports = reportService.getReportByCameraAndDate(id, date);

            for (Report item : reports) {
                data.add(reportTransformer.entityToModel(item));
            }

            response.setResponse(CoreConstant.STATUS_CODE_SUCCESS, CoreConstant.MESSAGE_SUCCESS, data);
            LOGGER.info("End load report by Camera " + id);
        } catch (Exception e) {
            response.setResponse(CoreConstant.STATUS_CODE_SERVER_ERROR, CoreConstant.MESSAGE_SERVER_ERROR);
            LOGGER.error(e.getMessage());
        }
        return gson.toJson(response);
    }
}
