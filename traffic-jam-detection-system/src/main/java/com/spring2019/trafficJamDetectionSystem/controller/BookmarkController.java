package com.spring2019.trafficJamDetectionSystem.controller;

import com.spring2019.trafficJamDetectionSystem.common.CoreConstant;
import com.spring2019.trafficJamDetectionSystem.model.BookmarkModel;
import com.spring2019.trafficJamDetectionSystem.model.MultiBookmarkCameraModel;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public interface BookmarkController {
    @GetMapping(CoreConstant.API_BOOKMARK)
    public String getAllBookmarks();

    @PostMapping(CoreConstant.API_BOOKMARK)
    public String createBookmark(@RequestBody BookmarkModel newBookmarkModel);

    @GetMapping(CoreConstant.API_BOOKMARK + "/{id}")
    public String getBookMarkByAccountID(@PathVariable("id") Integer id);

    @DeleteMapping(CoreConstant.API_BOOKMARK + "/{id}")
    public String deleteBookmarkById(@PathVariable("id") Integer id);

    @PostMapping(CoreConstant.API_BOOKMARK + "/camera")
    public String saveBookmarkCamera(@RequestBody MultiBookmarkCameraModel multiBookmarkCameraModel);
}
