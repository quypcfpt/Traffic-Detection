package com.spring2019.trafficJamDetectionSystem.repository;

import com.spring2019.trafficJamDetectionSystem.entity.Account;
import com.spring2019.trafficJamDetectionSystem.entity.Bookmark;
import com.spring2019.trafficJamDetectionSystem.entity.BookmarkCamera;
import com.spring2019.trafficJamDetectionSystem.entity.Camera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface BookmarkCameraRepository extends JpaRepository<BookmarkCamera, Integer> {

    @Query(value="SELECT DISTINCT username FROM Bookmark_Camera JOIN Bookmark B " +
            "ON Bookmark_Camera.bookmark_id = B.id JOIN Account A on B.account_id = A.id " +
            "JOIN Camera C ON Bookmark_Camera.camera_id = C.id " +
            "WHERE camera_id=?1 and isActive =?2",nativeQuery = true)
    List<String> findUsernameByCamera(int cameraId, boolean isActive);


    List<BookmarkCamera> findByCameraByCameraId(Camera camera);

    @Transactional
    Integer deleteBookmarkCamerasByBookmarkByBookmarkId(Bookmark bookMarkId);

    @Transactional
  //  @Query(value = "DELETE FROM BookmarkCamera WHERE cameraByCameraId=camera",nativeQuery = true)
    Integer deleteBookmarkCamerasByCameraByCameraId(Camera cameraId);

    List<BookmarkCamera> findBookmarkCamerasByBookmarkByBookmarkIdAndCameraByCameraIdIsActive(Bookmark bookmark, boolean isActive   );
}
