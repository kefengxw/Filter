package com.filter.android.model.remote

import com.filter.android.model.data.InternalDataConfiguration.VISIT_TOKEN
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.*

interface RemoteDataInfoService {

    //Search for an Item
    //Description	Search for an Item DOCS
    //Endpoint	https://api.spotify.com/v1/search
    //for example: https://api.spotify.com/v1/search?q=ABBA&type=artist&market=SE&limit=12&offset=18
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
        //"Authorization: Bearer $VISIT_TOKEN"
    )
    @GET("search")//@GET("search?q={name}&type={type}&market={market}&limit={limit}&offset={offset}")
    fun getRemoteInfo(
        @Header("Authorization") auth: String = "Bearer $VISIT_TOKEN",
        @Query("q") name: String,
        @Query("type") type: String,
        @Query("market") market: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): Single<Response<RemoteBean>>

    //Apply for the token
//    @Headers(
//        "Content-type:application/x-www-form-urlencoded",
//        "Authorization: Basic <base64 encoded eb80bf21fddb49c68fb66091f042a06a:0f88b9cda6084af483e9a2bfff1c80f7>"
//    )
//    @POST("https://accounts.spotify.com/api/token")
//    fun getRemoteToken(@Body requestBody: RequestBody): Single<Response<RemoteToken>>

//    @Headers(
//        "Content-type: application/x-www-form-urlencoded",
//        "Authorization: Basic eb80bf21fddb49c68fb66091f042a06a:0f88b9cda6084af483e9a2bfff1c80f7"
//    )
//    @POST("https://accounts.spotify.com/api/token")
//    fun getRemoteToken(@Query("grant_type") body: String = "client_credentials"): Single<Response<RemoteToken>>

    @Headers(
        "Content-type: application/x-www-form-urlencoded"
    )
    @FormUrlEncoded
    @POST(value = "https://accounts.spotify.com/api/token")
    fun getRemoteToken(
        @Field("grant_type") grantType: String,
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String
    ): Single<Response<RemoteToken>>
}