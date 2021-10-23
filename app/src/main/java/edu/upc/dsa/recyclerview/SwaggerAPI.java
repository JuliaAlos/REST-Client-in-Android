package edu.upc.dsa.recyclerview;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface SwaggerAPI {
    //GET the List of Tracks stored
    @GET("tracks")
    Call<List<Track>> getTracks();

    //GET a single Track given its ID
    @GET("tracks/{id}")
    Call<Track> getTrackbyID(@Path("id") String trackid);

    //POST (add) a Track
    @POST("tracks")
    Call<Track> addTrack(@Body Track track);

    //PUT (edit) a Track
    @PUT("tracks")
    Call<Void> editTrack(@Body Track track); //API only returns a code error

    //DELETE a Track
    @DELETE("tracks/{id}")
    Call<Void> deleteTrack(@Path("id") String trackid); //API only returns a code error
}
