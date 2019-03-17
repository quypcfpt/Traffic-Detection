package com.spring2019.trafficJamDetectionSystem.repository;

import com.spring2019.trafficJamDetectionSystem.entity.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark,Integer> {
   List<Bookmark> findBookmarksByAccountId(Integer accountID);
    @Transactional
    Integer deleteBookmarkById(Integer id);
}
