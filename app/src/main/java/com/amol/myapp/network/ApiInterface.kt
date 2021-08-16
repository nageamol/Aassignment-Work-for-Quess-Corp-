package com.amol.myapp.network

import com.amol.myapp.dataclass.RepoOutputModelI
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*

interface ApiInterface {

    @GET("cpOAeecWGa")
    fun getRepo(@Query("indent") query: String) : Observable<RepoOutputModelI>
}