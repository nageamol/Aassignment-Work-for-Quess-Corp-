package com.amol.myapp.network

import com.amol.myapp.dataclass.RepoOutputModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class AuthRepository {
   var retroInstance : ApiInterface
    init {
        retroInstance = RetrofitClient.getRetrofitClient().create(ApiInterface::class.java)
    }

    fun getRepos(): Observable<RepoOutputModel> {
        return retroInstance.getRepo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    companion object {
        private const val TAG = "AuthRepository"
    }
}