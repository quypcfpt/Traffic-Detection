package com.spring2019.trafficJamDetectionSystem.controllerImpl;

import com.spring2019.trafficJamDetectionSystem.common.CoreConstant;
import com.spring2019.trafficJamDetectionSystem.controller.BookmarkController;
import com.spring2019.trafficJamDetectionSystem.entity.Bookmark;
import com.spring2019.trafficJamDetectionSystem.model.BookmarkModel;
import com.spring2019.trafficJamDetectionSystem.model.MultipleBookmarkModel;
import com.spring2019.trafficJamDetectionSystem.model.Response;
import com.spring2019.trafficJamDetectionSystem.service.BookmarkService;
import com.spring2019.trafficJamDetectionSystem.transformer.BookmarkTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
public class BookmarkControllerImpl extends AbstractController implements BookmarkController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountControllerImpl.class);
    @Autowired
     BookmarkService bookmarkService;
    @Autowired
    BookmarkTransformer bookmarkTransformer;

    @Override
    public String getBookMarkByAccountID(Integer accountID) {
        Response<MultipleBookmarkModel> response = new Response<MultipleBookmarkModel>(CoreConstant.STATUS_CODE_FAIL, CoreConstant.MESSAGE_FAIL);
        try {
            MultipleBookmarkModel data = new MultipleBookmarkModel();
            LOGGER.info("Start Book mark" + accountID);
            List<BookmarkModel> models = new ArrayList<BookmarkModel>();
            List<Bookmark> bookmarkList = bookmarkService.getBookMarkByAccountId(accountID);
            for(Bookmark item : bookmarkList){
            models.add(bookmarkTransformer.entityToModel(item));
            }
            data.setBookmarkModelList(models);
            response.setResponse(CoreConstant.STATUS_CODE_SUCCESS, CoreConstant.MESSAGE_SUCCESS, data);

            LOGGER.info("End Book Mark" + accountID);
        } catch (Exception e) {
            response.setResponse(CoreConstant.STATUS_CODE_SERVER_ERROR, CoreConstant.MESSAGE_SERVER_ERROR);
            LOGGER.error(e.getMessage());
        }
        return gson.toJson(response);
    }

    @Override
    public String deleteBookmarkById(Integer id) {
        Response<Boolean> response = new Response<Boolean>(CoreConstant.STATUS_CODE_FAIL, CoreConstant.MESSAGE_FAIL);
        try {
            LOGGER.info("Start Delete Bookmark" + id);
            List<BookmarkModel> models = new ArrayList<BookmarkModel>();
            bookmarkService.removeBookMarkById(id);
            response.setResponse(CoreConstant.STATUS_CODE_SUCCESS, CoreConstant.MESSAGE_SUCCESS);
            LOGGER.info("End Delete Bookmark" + id);
        } catch (Exception e) {
            response.setResponse(CoreConstant.STATUS_CODE_SERVER_ERROR, CoreConstant.MESSAGE_SERVER_ERROR);
            LOGGER.error(e.getMessage());
        }
        return gson.toJson(response);
    }
}
