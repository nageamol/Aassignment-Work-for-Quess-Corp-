package com.amol.myapp.network

import com.amol.myapp.dataclass.RepoOutputModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*

interface ApiInterface {

    @GET("hep-tesst.json")
    fun getRepo() : Observable<RepoOutputModel>
}