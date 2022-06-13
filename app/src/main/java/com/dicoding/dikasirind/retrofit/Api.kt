package com.dicoding.dikasirind.retrofit

import com.dicoding.dikasirind.model.UserData
import retrofit2.Call
import retrofit2.http.FormUrlEncoded
import retrofit2.http.*

interface Api {
    @FormUrlEncoded
    @POST("menus")
    fun postmenu(
        @Field("nama") nama: String,
        @Field("stock") stock: String
    ): Call<UserData>

    @GET("menus")
    fun getmenu():Call<ArrayList<UserData>>

    @DELETE("menus/{id}")
    fun deletemenu(@Path("id")id:String): Call<Void>
}