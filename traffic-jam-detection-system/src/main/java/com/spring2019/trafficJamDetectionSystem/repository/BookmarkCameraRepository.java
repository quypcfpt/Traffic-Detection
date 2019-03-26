package com.spring2019.trafficJamDetectionSystem.repository;

import com.spring2019.trafficJamDetectionSystem.entity.Account;
import com.spring2019.trafficJamDetectionSystem.entity.BookmarkCamera;
import com.spring2019.trafficJamDetectionSystem.entity.Camera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookmarkCameraRepository extends JpaRepository<BookmarkCamera, Integer> {

    @Query(value="SELECT DISTINCT username FROM Bookmark_Camera JOIN Bookmark B " +
            "ON Bookmark_Camera.bookmark_id = B.id JOIN Account A on B.account_id = A.id",nativeQuery = true)
    List<String> findUsernameByCamera(int cameraId);


    List<BookmarkCamera> findByCameraByCameraId(Camera camera);
}
