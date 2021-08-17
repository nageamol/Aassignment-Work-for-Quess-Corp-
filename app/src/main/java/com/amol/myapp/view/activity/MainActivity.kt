package com.amol.myapp.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.amol.myapp.R
import com.amol.myapp.view.fragment.MainFragment

class MainActivity : AppCompatActivity()  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, MainFragment.newInstance())
            .commitAllowingStateLoss()
    }


}