package com.hlandim.easypod

import android.arch.lifecycle.ViewModelProviders
import com.hlandim.easypod.activity.MainActivity
import com.hlandim.easypod.logic.PodCastListViewModel
import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class PodCastTest {

    @Test
    fun list() {
        val viewModel = getViewModel()
        viewModel.search("Jovem nerd")
        Thread.sleep(5000)
        assertNotNull(viewModel.getPodCastList())

//        assertTrue(viewModel.getPodCastList().value!!.size > 0)
    }

    fun getViewModel(): PodCastListViewModel {
        val activity = Robolectric.setupActivity(MainActivity::class.java)
        return ViewModelProviders.of(activity).get(PodCastListViewModel::class.java)
    }
}
