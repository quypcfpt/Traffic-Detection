package com.spring2019.trafficJamDetectionSystem.repository;

import com.spring2019.trafficJamDetectionSystem.entity.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark,Integer> {
   List<Bookmark> findBookmarksByAccountId(Integer accountID);
}
