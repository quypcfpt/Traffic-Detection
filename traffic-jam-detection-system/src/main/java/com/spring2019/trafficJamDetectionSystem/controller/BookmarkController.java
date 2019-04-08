package com.spring2019.trafficJamDetectionSystem.controller;

import com.spring2019.trafficJamDetectionSystem.common.CoreConstant;
import com.spring2019.trafficJamDetectionSystem.model.BookmarkModel;
import com.spring2019.trafficJamDetectionSystem.model.MultiBookmarkCameraModel;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public interface BookmarkController {

    // Create new bookmark
    @PostMapping(CoreConstant.API_BOOKMARK)
    public String createBookmark(@RequestBody MultiBookmarkCameraModel multiBookmarkCameraModel);

    // Get bookmark by Id
    @GetMapping(CoreConstant.API_BOOKMARK + "/{id}")
    public String getBookMarkByAccountID(@PathVariable("id") Integer id);

    // Delete bookmark
    @DeleteMapping(CoreConstant.API_BOOKMARK + "/{id}")
    public String deleteBookmarkById(@PathVariable("id") Integer id);

}
