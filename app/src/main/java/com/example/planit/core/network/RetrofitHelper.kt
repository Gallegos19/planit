package com.example.planit.core.network

import com.example.planit.components.left_bar.data.datasource.LeftBarService
import com.example.planit.core.navigation.CreateIndividualActivities
import com.example.planit.utils.save_token.data.datasource.SaveTokenService
import com.example.planit.views.add_team.data.datasource.AddTeamService
import com.example.planit.views.create_individual_activities.data.datasource.CreateIndividualActivitiesService
import com.example.planit.views.general_team.data.datasource.GroupService
import com.example.planit.views.individual_activity.data.datasource.IndividualActivityService
import com.example.planit.views.login.data.datasource.LoginService
import com.example.planit.views.register.data.datasource.RegisterService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object RetrofitHelper{
    private const val BASE_URL = "http://44.218.103.187:3000/api/"

    val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .addConverterFactory(ScalarsConverterFactory.create())
        .build()


    fun getRetrofitLogin(): LoginService {
        return retrofit.create(LoginService::class.java)
    }

    fun getRetrogitRegister() : RegisterService {
        return retrofit.create(RegisterService::class.java)
    }

    fun getRetrofitLeftBar() : LeftBarService {
        return retrofit.create(LeftBarService::class.java)
    }

    fun getRetrofitIndividualActivity() : IndividualActivityService {
        return retrofit.create(IndividualActivityService::class.java)
    }

    fun getRetrofitCreateIndividualActivity() : CreateIndividualActivitiesService{
        return retrofit.create(CreateIndividualActivitiesService::class.java)
    }

    fun getRetrofitToken() : SaveTokenService{
        return retrofit.create(SaveTokenService::class.java)
    }

    fun getRetrofitAddTeam() : AddTeamService{
        return retrofit.create(AddTeamService::class.java)
    }

    fun getRetrofitGroup(): GroupService {
        return retrofit.create(GroupService::class.java)
    }

}