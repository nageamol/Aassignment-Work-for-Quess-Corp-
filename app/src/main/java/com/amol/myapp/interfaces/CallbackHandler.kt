package com.amol.myapp.interfaces

interface CallbackHandler {
    fun onDone()
    fun onStart();
    fun onFail(e: Throwable)
}