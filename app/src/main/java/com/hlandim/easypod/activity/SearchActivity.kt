package com.hlandim.easypod.activity

import android.app.SearchManager
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.Menu
import android.widget.EditText
import com.hlandim.easypod.R
import com.hlandim.easypod.domain.PodCast
import com.hlandim.easypod.logic.PodCastListViewModel
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.toolbar.*

class SearchActivity : AppCompatActivity() {

    var podCastListViewModel: PodCastListViewModel? = null
    val adapter: SearchListAdapter = SearchListAdapter(mutableListOf(), this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setSupportActionBar(my_toolbar)


        search_result_list.adapter = adapter
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        search_result_list.layoutManager = layoutManager
        podCastListViewModel = ViewModelProviders.of(this).get(PodCastListViewModel::class.java)
        podCastListViewModel?.getPodCastList()?.observe(this, Observer<MutableList<PodCast>> { list ->
            if (list != null) {
                adapter.updateList(list)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_activity_menu, menu)
        configureSearchView(menu)
        return true
    }

    private fun configureSearchView(menu: Menu?) {
        val searchManager: SearchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView: SearchView = menu?.findItem(R.id.action_search_podcast)?.actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(ComponentName(applicationContext, SearchActivity::class.java)))
        searchView.setIconifiedByDefault(false)
        val txtSearch = searchView.findViewById<EditText>(android.support.v7.appcompat.R.id.search_src_text) as EditText
        txtSearch.hint = "Digite o nome do PodCast"
        txtSearch.setHintTextColor(Color.GRAY)
        searchView.requestFocus()

        searchView.onQueryText({ query ->
            podCastListViewModel?.search(query)
            searchView.clearFocus()
            true
        })
    }

    fun SearchView.onQueryText(submit: (String) -> Boolean = { false }, textChange: (String) -> Boolean = { false }) {

        this.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean = submit(query)

            override fun onQueryTextChange(newText: String): Boolean = textChange(newText)

        })
    }

    override fun onSearchRequested(): Boolean {
        return super.onSearchRequested()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
    }
}
