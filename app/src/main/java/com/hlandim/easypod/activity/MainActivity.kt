package com.hlandim.easypod.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.hlandim.easypod.R
import com.hlandim.easypod.fragment.PodCastListFragment
import com.hlandim.easypod.logic.web.audiosear.AudioSearApi
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initAudioSearApi()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({ result ->
                    if (result) {
                        supportFragmentManager
                                .beginTransaction()
                                .add(R.id.fragments_container, PodCastListFragment())
                                .commit()
                    } else {
                        Toast.makeText(this, "Não foi possivel obter o token da API AudioSear.ch", Toast.LENGTH_LONG).show()
                    }
                }, { error ->
                    error.printStackTrace()
                }, {

                })
    }

    private fun initAudioSearApi(): Observable<Boolean> {
        return AudioSearApi
                .instance
                .updateToken()
                .flatMap({ result ->
                    var response = false
                    if (!result.isNullOrEmpty()) {
                        response = true
                    }
                    Observable.just(response)
                })
    }
}
