package com.spring2019.trafficJamDetectionSystem.controller;

import com.spring2019.trafficJamDetectionSystem.common.CoreConstant;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public interface BookmarkController {
    @GetMapping(CoreConstant.API_BOOKMARK + "/{id}")
    public String getBookMarkByAccountID(@PathVariable("id") Integer id);


    @DeleteMapping(CoreConstant.API_BOOKMARK + "/{id}")
    public String deleteBookmarkById(@PathVariable("id") Integer id);
}
