package com.spring2019.trafficJamDetectionSystem.controller;

import com.spring2019.trafficJamDetectionSystem.common.CoreConstant;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public interface BookmarkController {
    @GetMapping(CoreConstant.API_BOOKMARK + "/{id}")
    public String getBookMarkByAccountID(@PathVariable("id") Integer id);

    @GetMapping(CoreConstant.API_BOOKMARK + "/delete/{id}")
    public String deleteBookmarkById(@PathVariable("id") Integer id);
}
