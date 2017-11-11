package com.hlandim.easypod.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.hlandim.easypod.R
import com.hlandim.easypod.fragment.PodCastListFragment
import com.hlandim.easypod.logic.web.audiosear.AudioSearApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        AudioSearApi
                .instance
                .updateToken()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ result ->
                    println(result)
                }, { error ->
                    error.printStackTrace()
                }, {
                    supportFragmentManager
                            .beginTransaction()
                            .add(R.id.fragments_container, PodCastListFragment())
                            .commit()
                })
    }
}
