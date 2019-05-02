package com.spring2019.trafficJamDetectionSystem.repository;

import com.spring2019.trafficJamDetectionSystem.entity.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Integer> {

    List<Bookmark> findBookmarksByAccountId(Integer accountID);

    @Transactional
    Integer deleteBookmarkById(Integer id);

    @Query(value = "SELECT DISTINCT B.id,B.route_points FROM Bookmark B " +
            "WHERE b.id NOT IN (SELECT DISTINCT B.id FROM Bookmark_Camera " +
            "JOIN Bookmark B ON Bookmark_Camera.bookmark_id = B.id " +
            "JOIN Account A on B.account_id = A.id " +
            "JOIN Camera C ON Bookmark_Camera.camera_id = C.id " +
            "WHERE camera_id =?1", nativeQuery = true)
    List<Bookmark> getBookmarksNotHaveCamera(int cameraId);
}
