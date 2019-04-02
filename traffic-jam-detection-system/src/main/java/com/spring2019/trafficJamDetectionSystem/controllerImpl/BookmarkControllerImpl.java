package com.spring2019.trafficJamDetectionSystem.controllerImpl;

import com.spring2019.trafficJamDetectionSystem.common.CoreConstant;
import com.spring2019.trafficJamDetectionSystem.controller.BookmarkController;
import com.spring2019.trafficJamDetectionSystem.entity.Bookmark;
import com.spring2019.trafficJamDetectionSystem.model.*;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(BookmarkControllerImpl.class);
    @Autowired
     BookmarkService bookmarkService;
    @Autowired
    BookmarkTransformer bookmarkTransformer;

    @Override
    public String getAllBookmarks() {
        Response<MultipleBookmarkModel> response = new Response<>(CoreConstant.STATUS_CODE_FAIL, CoreConstant.MESSAGE_FAIL);
        try {
            LOGGER.info("Getting all bookmarks...");
            List<Bookmark> bookmarkList = bookmarkService.getAllBookmarks();
            MultipleBookmarkModel data = new MultipleBookmarkModel();
            List<BookmarkModel> bookmarkModelList = new ArrayList<>();
            for (Bookmark x : bookmarkList) {
                bookmarkModelList.add(bookmarkTransformer.entityToModel(x));
            }
            List<BookmarkModel> testArr = bookmarkModelList;
            if(testArr.isEmpty()){
                data.setBookmarkModelList(new ArrayList<BookmarkModel>());
            }else
                data.setBookmarkModelList(testArr);
            response.setResponse(CoreConstant.STATUS_CODE_SUCCESS, CoreConstant.MESSAGE_SUCCESS, data);
            LOGGER.info("All bookmarks are loaded!");
        }catch (Exception e){
            response.setResponse(CoreConstant.STATUS_CODE_SERVER_ERROR, CoreConstant.MESSAGE_SERVER_ERROR);
            LOGGER.error(e.getMessage());

        }
        return gson.toJson(response);
    }

    @Override
    public String createBookmark(BookmarkModel newBookmarkModel) {
        Response response = new Response<>(CoreConstant.STATUS_CODE_FAIL, CoreConstant.MESSAGE_FAIL);
        try {
            LOGGER.info("Creating new bookmark...");
            Bookmark newBookmarkEntity = bookmarkTransformer.modelToEntity(newBookmarkModel);
            newBookmarkEntity = bookmarkService.createBookmark(newBookmarkEntity);
            BookmarkModel resBookmark = new BookmarkModel();
            resBookmark.setId(newBookmarkEntity.getId());
            response.setResponse(CoreConstant.STATUS_CODE_SUCCESS, CoreConstant.MESSAGE_SUCCESS, resBookmark);
            LOGGER.info("A new bookmark is created: " + newBookmarkEntity.getId());
        }catch (Exception e){
            response.setResponse(CoreConstant.STATUS_CODE_SERVER_ERROR, CoreConstant.MESSAGE_SERVER_ERROR);
            LOGGER.error(e.getMessage());

        }
        return gson.toJson(response);

    }

    @Override
    public String getBookMarkByAccountID(Integer accountID) {
        Response<List<BookmarkModel>> response = new Response<List<BookmarkModel>>(CoreConstant.STATUS_CODE_FAIL, CoreConstant.MESSAGE_FAIL);
        try {
            LOGGER.info("Get bookmark of user ID: " + accountID);
            List<BookmarkModel> models = new ArrayList<BookmarkModel>();
            List<Bookmark> bookmarkList = bookmarkService.getBookMarkByAccountId(accountID);
            for(Bookmark item : bookmarkList){
            models.add(bookmarkTransformer.entityToModel(item));
            }
            response.setResponse(CoreConstant.STATUS_CODE_SUCCESS, CoreConstant.MESSAGE_SUCCESS, models);

            LOGGER.info("End get bookmark of user ID: " + accountID);
        } catch (Exception e) {
            response.setResponse(CoreConstant.STATUS_CODE_SERVER_ERROR, CoreConstant.MESSAGE_SERVER_ERROR);
            LOGGER.error(e.getMessage());
        }
        return gson.toJson(response);
    }

    public String deleteBookmarkById(Integer id) {
        Response<String> response = new Response<String>(CoreConstant.STATUS_CODE_FAIL, CoreConstant.MESSAGE_FAIL);
        try {
            LOGGER.info("Start Delete Bookmark" + id);
            Bookmark bookmark = new Bookmark();
            bookmark.setId(id);
             bookmarkService.removeBookMarkCameraWithBookMarkID(bookmark);
            String check = bookmarkService.removeBookMarkById(id) + "";

            response.setResponse(CoreConstant.STATUS_CODE_SUCCESS, CoreConstant.MESSAGE_SUCCESS,check);
            LOGGER.info("End Delete Bookmark" + id);
        } catch (Exception e) {
            response.setResponse(CoreConstant.STATUS_CODE_SERVER_ERROR, CoreConstant.MESSAGE_SERVER_ERROR);
            LOGGER.error(e.getMessage());
        }
        return gson.toJson(response);

    }

    @Override
    public String saveBookmarkCamera(MultiBookmarkCameraModel multiBookmarkCameraModel) {
        Response response = new Response<>(CoreConstant.STATUS_CODE_FAIL, CoreConstant.MESSAGE_FAIL);
        try {
            LOGGER.info("Start save Bookmark Camera");
            BookmarkModel bookmarkModel = new BookmarkModel();
            bookmarkModel.setId(multiBookmarkCameraModel.getBookmarkId());
            for (CameraModel x : multiBookmarkCameraModel.getCameraList()) {
                BookmarkCameraModel bookmarkCameraModel = new BookmarkCameraModel();
                bookmarkCameraModel.setBookmarkModel(bookmarkModel);
                bookmarkCameraModel.setCameraModel(x);
                bookmarkService.saveBookmarkCamera(bookmarkTransformer.bookmarkCameraModeltoEntity(bookmarkCameraModel));
            }
            response.setResponse(CoreConstant.STATUS_CODE_SUCCESS, CoreConstant.MESSAGE_SUCCESS, true);
            LOGGER.info("End save Bookmark Camera: "+ bookmarkModel.getId());
        }catch (Exception e){
            response.setResponse(CoreConstant.STATUS_CODE_SERVER_ERROR, CoreConstant.MESSAGE_SERVER_ERROR);
            LOGGER.error(e.getMessage());
        }
        return gson.toJson(response);
    }
}
