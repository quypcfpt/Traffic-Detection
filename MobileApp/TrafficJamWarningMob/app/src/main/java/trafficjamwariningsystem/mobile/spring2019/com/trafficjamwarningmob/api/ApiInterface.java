package trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.model.AccountModel;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.model.BookmarkModel;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.model.CameraModel;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.model.ImageModel;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.model.MultiBookmarkCameraModel;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.model.MultiCameraModel;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.model.MultiStreetModel;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.model.MultipleBookmarkModel;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.model.Response;

public interface ApiInterface {
    
    @GET("api/street")
    Call<Response<MultiStreetModel>> getAllStreets(@Query("sortBy") String sortBy, @Query("size") int size);

    @GET("api/street")
    Call<Response<MultiStreetModel>> getAllStreets(@Query("sortBy") String sortBy, @Query("page") int page, @Query("size") int size);

    @GET("api/street/search/{search}")
    Call<Response<MultiStreetModel>> getStreetsBySearchNameLike(@Path("search") String search, @Query("sortBy") String sortBy, @Query("page") int page);

    //Camera
    @GET("api/camera/{id}")
    Call<Response<CameraModel>> loadCameraById(@Path("id") Integer id);

    @GET("api/camera")
    Call<Response<MultiCameraModel>> loadAllCameras(@Query("sortBy") String sortBy);

    @GET("api/camera/streetId/{streetId}")
    Call<Response<MultiCameraModel>> loadCamerasByStreet(@Path("streetId") int streetId);

    @GET("api/camera/streetName/{streetName}")
    Call<Response<List<CameraModel>>> loadCamerasByStreetName(@Path("streetName") String streetName);

    //Image
    @GET("api/camera/image/{id}")
    Call<Response<ImageModel>> loadImageByCameraId(@Path("id") Integer id);

    //Account
    @POST("api/account/checkLogin")
    Call<Response<AccountModel>> checkUserLogin(@Query("accountModel") String accountModel);

    @POST("api/account/createNewAccount")
    Call<Response<String>> createNewAccount(@Query("accountModel") String accountModel);

    //Bookmark
    @GET("api/bookmark/{id}")
    Call<Response<List<BookmarkModel>>> getBookMarkByAccountId(@Path("id") int accountId);

    @DELETE("api/bookmark/{id}")
    Call<Response<String>> deleteBookmarkWithId(@Path("id") int bookmarkId);

    @POST("api/bookmark")
    Call<Response<String>> createBookmark(@Body MultiBookmarkCameraModel multiBookmarkCameraModel);

    @GET("api/bookmark/{id}/camera")
    Call<Response<List<CameraModel>>> getCameraInBookmark(@Path("id") int bookmarkId);
}
