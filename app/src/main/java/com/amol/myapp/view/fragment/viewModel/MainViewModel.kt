package com.amol.myapp.view.fragment.viewModel

import android.view.View
import androidx.lifecycle.ViewModel
import com.amol.myapp.network.AuthRepository

import androidx.lifecycle.MutableLiveData
import com.amol.myapp.dataclass.RepoOutputModel
import com.amol.myapp.interfaces.CallbackHandler
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

class MainViewModel : ViewModel() {
    lateinit var disposable: Disposable
     var authRepository: AuthRepository
     var repoList: MutableLiveData<RepoOutputModel>
     lateinit var mainFragmentCallback : CallBack
     lateinit var callback : CallbackHandler

    init {
        repoList = MutableLiveData()
        authRepository = AuthRepository();
    }

    fun getRepoListObserver(): MutableLiveData<RepoOutputModel> {
        return repoList
    }

    fun getRepoFromServer() {
        authRepository.getRepos().subscribe(getRepoObserverRx())
    }

    fun onClick(view : View){
        mainFragmentCallback.onClick(view)
    }

    private fun getRepoObserverRx(): Observer<RepoOutputModel> {
        return object : Observer<RepoOutputModel> {
            override fun onComplete() {
                callback.onDone()
            }

            override fun onError(e: Throwable) {
                repoList.postValue(null)
                callback.onFail(e)
            }

            override fun onNext(t: RepoOutputModel) {
                repoList.postValue(t)

            }

            override fun onSubscribe(d: Disposable) {
                disposable = d;
                callback.onStart()
            }
        }
    }

    fun onDestroy() {
        disposable.dispose()
    }

    public interface CallBack{

        fun onClick(view: View)

    }
}