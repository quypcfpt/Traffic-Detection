package trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.api;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.model.AccountModel;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.model.BookmarkModel;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.model.CameraModel;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.model.ImageModel;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.model.MultiCameraModel;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.model.MultiStreetModel;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.model.MultipleBookmarkModel;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.model.Response;

public interface  ApiInterface {
    //Street
    @GET("api/street/{district}")
    Call<Response<MultiStreetModel>> getStreets(@Path("district") String district);

    @GET("api/street")
    Call<Response<MultiStreetModel>> getAllStreets(@Query("sortBy") String sortBy);

    @GET("api/street")
    Call<Response<MultiStreetModel>> getAllStreets(@Query("sortBy") String sortBy, @Query("page") int page);
    @GET("api/street/search/{search}")
    Call<Response<MultiStreetModel>> getStreetsBySearchNameLike (@Path("search") String search ,@Query("sortBy") String sortBy, @Query("page") int page);

    //Camera
    @GET("api/camera/{id}")
    Call<Response<CameraModel>> loadCameraById(@Path("id") Integer id);

    @GET("api/camera")
    Call<Response<MultiCameraModel>> loadAllCameras(@Query("sortBy") String sortBy);

    @GET("api/camera/streetId/{streetId}")
    Call<Response<MultiCameraModel>> loadCamerasByStreet(@Path("streetId") int streetId);

    @GET("api/camera/streetId/{streetId}")
    Call<Response<MultiCameraModel>> loadCamerasByStreet2(@Path("streetId") int streetId, @Query("sortBy") String sortBy);

    //Image
    @GET("api/camera/image/{id}")
    Call<Response<ImageModel>> loadImageByCameraId(@Path("id") Integer id);

    //Account
    @POST("api/account/checkLogin")
    Call<Response<AccountModel>> checkUserLogin(@Query("accountModel")String accountModel);
    @POST("api/account/createNewAccount")
    Call<Response<String>> createNewAccount(@Query("accountModel")String accountModel);

    //Bookmark
    @GET("api/bookmark/{id}")
    Call<Response<MultipleBookmarkModel>> getBookMakByAccountId(@Path("id") int accountId);

    @DELETE("api/bookmark/{id}")
    Call<Response<String>> deleteBookmarkWithId(@Path("id") int accountId);

    @GET("api/bookmark")
    Call<Response<MultipleBookmarkModel>> getAllBookmarks();

    @POST("api/bookmark")
    Call<BookmarkModel> createBookmark(@Body BookmarkModel newBookmarkModel);
}
