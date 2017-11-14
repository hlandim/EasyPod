package com.hlandim.easypod.activity

import android.app.SearchManager
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import com.hlandim.easypod.R
import com.hlandim.easypod.fragment.PodCastListFragment
import com.hlandim.easypod.logic.web.audiosear.AudioSearApi
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.toolbar.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(my_toolbar)

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

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.action_add_podcast -> {
//                val intent = Intent(this, SearchActivity::class.java)
//                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_activity_menu, menu)
        val searchManager: SearchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView: SearchView = menu?.findItem(R.id.action_add_podcast)?.actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.setIconifiedByDefault(true) // iconify the widget; not expand it by default
        // Set hint and the text colors


        val txtSearch = searchView.findViewById<EditText>(android.support.v7.appcompat.R.id.search_src_text) as EditText
        txtSearch.hint = "..."
        txtSearch.setHintTextColor(Color.GRAY)
        return true
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
