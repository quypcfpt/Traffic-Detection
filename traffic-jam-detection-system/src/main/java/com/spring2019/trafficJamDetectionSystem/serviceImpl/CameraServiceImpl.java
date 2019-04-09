package com.spring2019.trafficJamDetectionSystem.serviceImpl;

import com.google.maps.model.LatLng;
import com.spring2019.trafficJamDetectionSystem.common.CoreConstant;
import com.spring2019.trafficJamDetectionSystem.common.DirectionsJSONParser;
import com.spring2019.trafficJamDetectionSystem.common.ParameterStringBuilder;
import com.spring2019.trafficJamDetectionSystem.entity.Bookmark;
import com.spring2019.trafficJamDetectionSystem.entity.Camera;
import com.spring2019.trafficJamDetectionSystem.entity.Street;
import com.spring2019.trafficJamDetectionSystem.repository.CameraRepository;
import com.spring2019.trafficJamDetectionSystem.service.CameraService;
import com.spring2019.trafficJamDetectionSystem.utils.PolyUtil;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.*;

@Service
public class CameraServiceImpl implements CameraService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CameraServiceImpl.class);

    @Autowired
    CameraRepository cameraRepository;

    @Override
    public Camera getCameraById(int id) {
        Optional<Camera> camera = cameraRepository.getByIdAndIsActive(id, true);
        return camera.orElse(null);
    }

    @Override
    public Page<Camera> getAllCameras(Pageable pageable) {
        return cameraRepository.findAllByIsActive(true, pageable);
    }

    @Override
    public List<Camera> getAllCameras() {
        return cameraRepository.findAll();
    }

    @Override
    public List<Camera> getCamerasByStreetAndIsActive(Integer streetId) {
        Street street = new Street();
        street.setId(streetId);
        return cameraRepository.findByStreetByStreetIdAndIsActive(street, true);
    }

    @Override
    public List<Camera> getCameraByStreetNameAndIsActive(String streetName) {
        return cameraRepository.findCameraByStreetName(streetName);
    }


    @Override
    public Camera createCamera(Camera camera) {
        camera.setIsActive(true);
        camera.setObservedStatus(CoreConstant.STATUS_CAMERA_CLEAR);
        return cameraRepository.save(camera);
    }

    @Override
    public void updateCamera(Camera camera) {
        cameraRepository.save(camera);
    }

    @Override
    public boolean checkCameraOnroute(Bookmark bookmark, Camera camera) {

        JSONObject response = null;

        // Adding params
        Map<String, String> parameters = new HashMap<>();
        parameters.put("origin", bookmark.getOrigin());
        parameters.put("destination", bookmark.getDestination());
        parameters.put("key", CoreConstant.GOOGLE_MAP_API_KEY);

        try {
            String param = ParameterStringBuilder.getParamsString(parameters);
            response = connectGoogleMap(param);
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }

        if (response != null) {
            List<List<HashMap<String, String>>> routes = null;
            try {
                DirectionsJSONParser parser = new DirectionsJSONParser();
                routes = parser.parse(response);
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
            }

            if (routes != null) {
                ArrayList<LatLng> points = new ArrayList<>();
                // Traversing through all the routes
                for (int i = 0; i < routes.size(); i++) {

                    // Fetching i-th route
                    List<HashMap<String, String>> path = routes.get(i);

                    // Fetching all the points in i-th route
                    for (int j = 0; j < path.size(); j++) {
                        HashMap<String, String> point = path.get(j);
                        double lat = Double.parseDouble(point.get("lat"));
                        double lng = Double.parseDouble(point.get("lng"));
                        LatLng position = new LatLng(lat, lng);
                        points.add(position);
                    }
                }

                if (!points.isEmpty()) {
                    String[] xPosArr = camera.getPosition().split(",");
                    LatLng xPos = new LatLng(Double.parseDouble(xPosArr[0]), Double.parseDouble(xPosArr[1]));
                    if (PolyUtil.isLocationOnPath(xPos, points, false, 10)) {
                        LOGGER.info("Camera is on " + bookmark.getOrigin() + "-" + bookmark.getDestination() + " route");
                        return true;
                    } else {
                        LOGGER.info("Camera is not on " + bookmark.getOrigin() + "-" + bookmark.getDestination() + " route");
                        return false;
                    }
                }
            } else {
                return false;
            }
        }
        return false;

    }

    private JSONObject connectGoogleMap(String param) throws IOException {
        URL url = new URL("https://maps.googleapis.com/maps/api/directions/json?" + param);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        con.setRequestProperty("Content-Type", "application/json");
        String contentType = con.getHeaderField("Content-Type");
        con.setConnectTimeout(10000);
        con.setReadTimeout(10000);
        StringBuilder content = new StringBuilder();
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()))) {
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
        }
        return new JSONObject(content.toString());
    }

}
