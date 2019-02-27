package com.stameni.com.movieapp.api

import com.stameni.com.movieapp.models.movieList.ServerResponse
import com.stameni.com.movieapp.models.actorDetails.SingleActorResponse
import com.stameni.com.movieapp.models.actorMovies.ActorMoviesResponse
import com.stameni.com.movieapp.models.singleMovie.SingleMovieResponse
import com.stameni.com.movieapp.models.movieActors.MovieActorsResponse
import com.stameni.com.movieapp.models.movieClips.ClipListResponse
import com.stameni.com.movieapp.models.movieImages.MovieImagesResponse
import com.stameni.com.movieapp.models.movieReviews.MovieReviewResponse
import com.stameni.com.movieapp.models.movieSearch.MovieSearchResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val BASE_URL = "https://api.themoviedb.org/"

interface TMDBApi {
    @GET("/3/movie/popular")
    fun getPopularMovies(
        @Query("page") page: Int = 1,
        @Query("api_key") key: String = "5e35bda1500b7f696342a3ab91d79e52"
    ): Observable<ServerResponse>

    @GET("/3/movie/top_rated")
    fun getTopRatedMovies(
        @Query("page") page: Int = 1,
        @Query("api_key") key: String = "5e35bda1500b7f696342a3ab91d79e52"
    ): Observable<ServerResponse>

    @GET("/3/movie/upcoming")
    fun getUpcomingMovies(
        @Query("page") page: Int = 1,
        @Query("api_key") key: String = "5e35bda1500b7f696342a3ab91d79e52"
    ): Observable<ServerResponse>

    @GET("/3/movie/{movie_id}")
    fun getSingleMovie(
        @Path("movie_id") id: Int,
        @Query("api_key") key: String = "5e35bda1500b7f696342a3ab91d79e52"
    ): Observable<SingleMovieResponse>

    @GET("/3/movie/{movie_id}/videos")
    fun getMovieClips(
        @Path("movie_id") id: Int,
        @Query("api_key") key: String = "5e35bda1500b7f696342a3ab91d79e52"
    ): Observable<ClipListResponse>

    @GET("/3/movie/{movie_id}/credits")
    fun getMovieActors(
        @Path("movie_id") id: Int,
        @Query("api_key") key: String = "5e35bda1500b7f696342a3ab91d79e52"
    ): Observable<MovieActorsResponse>

    @GET("/3/person/{person_id}")
    fun getSingleActor(
        @Path("person_id") id: Int,
        @Query("api_key") key: String = "5e35bda1500b7f696342a3ab91d79e52"
    ): Observable<SingleActorResponse>

    @GET("/3/person/{person_id}/movie_credits")
    fun getActorMovies(
        @Path("person_id") id: Int,
        @Query("api_key") key: String = "5e35bda1500b7f696342a3ab91d79e52"
    ): Observable<ActorMoviesResponse>

    @GET("/3/search/movie")
    fun getMovieSearch(
        @Query("query") name: String,
        @Query("page") page: Int = 1,
        @Query("api_key") key: String = "5e35bda1500b7f696342a3ab91d79e52"
    ): Observable<ServerResponse>


    @GET("/3/movie/{movie_id}/reviews")
    fun getMovieReviews(
        @Path("movie_id") id: Int,
        @Query("page") page: Int = 1,
        @Query("api_key") key: String = "5e35bda1500b7f696342a3ab91d79e52"
    ): Observable<MovieReviewResponse>


    @GET("/3/movie/{movie_id}/images")
    fun getMovieImages(
        @Path("movie_id") id: Int,
        @Query("page") page: Int = 1,
        @Query("api_key") key: String = "5e35bda1500b7f696342a3ab91d79e52"
    ): Observable<MovieImagesResponse>
}