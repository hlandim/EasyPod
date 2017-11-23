package com.hlandim.easypod.fragment.main

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.exoplayer2.DefaultLoadControl
import com.google.android.exoplayer2.DefaultRenderersFactory
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.hlandim.easypod.R
import kotlinx.android.synthetic.main.fragment_player.*


/**
 * Created by hugo.landim.santos on 21/11/2017.
 */
class PlayListFragment : MainTabLayoutFragment() {

    private lateinit var player: SimpleExoPlayer
    private var playbackPosition: Long = 0
    private var currentWindow: Int = 0
    private var playWhenReady: Boolean = true

    companion object {
        var BANDWIDTH_METER = DefaultBandwidthMeter()
    }


    override fun getTabName(): String = "PlayList"

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_player, container, false)


        return view
    }

    override fun onResume() {
        super.onResume()
        initializePlayer()
    }

    override fun onStop() {
        super.onStop()
        releasePlayer()
    }

    private fun initializePlayer() {
        player = ExoPlayerFactory.newSimpleInstance(
                DefaultRenderersFactory(activity),
                DefaultTrackSelector(), DefaultLoadControl())

        playerView.player = player

        player.playWhenReady = playWhenReady
        player.seekTo(currentWindow, playbackPosition)

        val uri = Uri.parse("http://feedproxy.google.com/~r/TheCombatJackShow/~5/s_9fWPxLDu0/188058705-thecombatjackshow-the-j-cole-episode.mp3")
        val mediaSource = buildMediaSource(uri)
        player.prepare(mediaSource, true, false)


    }

    private fun releasePlayer() {
        if (player != null) {
            playbackPosition = player.currentPosition
            currentWindow = player.currentWindowIndex
            playWhenReady = player.playWhenReady
            player.release()
        }
    }

    private fun buildMediaSource(uri: Uri): MediaSource {
        return ExtractorMediaSource(uri,
                DefaultHttpDataSourceFactory("ua"),
                DefaultExtractorsFactory(), null, null)
    }
}