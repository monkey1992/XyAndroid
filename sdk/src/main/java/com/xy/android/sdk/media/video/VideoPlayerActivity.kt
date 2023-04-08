package com.xy.android.sdk.media.video

import android.os.Bundle
import android.util.Log
import com.xy.android.sdk.BackActivity
import com.xy.android.sdk.databinding.ActivityVideoPlayerBinding
import com.xy.android.sdk.media.factory.MediaPlayerFactory
import com.xy.android.sdk.media.player.IMediaPlayer

/**
 * Video player activity.
 */
class VideoPlayerActivity : BackActivity(), IMediaPlayer.OnPreparedListener,
    IMediaPlayer.OnInfoListener, IMediaPlayer.OnErrorListener, IMediaPlayer.OnCompletionListener,
    IMediaPlayer.OnSeekCompleteListener {

    private lateinit var binding: ActivityVideoPlayerBinding

    private val mediaController
        get() = binding.mediaController

    private lateinit var mediaPlayerFactory: MediaPlayerFactory

    private lateinit var mediaPlayer: IMediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityVideoPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        initVideoPlayer()

        mediaPlayer.attachMediaController(mediaController)
    }

    private fun initVideoPlayer() {
        mediaPlayerFactory = MediaPlayerFactory()
        mediaPlayer = mediaPlayerFactory.create()
        mediaPlayer.setOnPreparedListener(this)
        mediaPlayer.setOnInfoListener(this)
        mediaPlayer.setOnErrorListener(this)
        mediaPlayer.setOnCompletionListener(this)
        mediaPlayer.setOnSeekCompleteListener(this)
        mediaPlayer.setDataSource("https://mazwai.com/videvo_files/video/free/2020-04/small_watermarked/200401_Fizzy%20Drinks%206_04_preview.webm")
        mediaPlayer.prepareAsync()
    }

    override fun onPrepared(mp: IMediaPlayer) {
        Log.d(TAG, "onPrepared $mp")
        mediaPlayer.start()
        mediaController.onPrepared(mp)
        mediaController.start()
    }

    override fun onInfo(mp: IMediaPlayer, what: Int, extra: Int): Boolean {
        Log.d(TAG, "onInfo $mp, $what, $extra")
        mediaController.onInfo(mp, what, extra)
        return true
    }

    override fun onError(mp: IMediaPlayer, what: Int, extra: Int): Boolean {
        Log.d(TAG, "onError $mp, $what, $extra")
        mediaController.onError(mp, what, extra)
        return true
    }

    override fun onCompletion(mp: IMediaPlayer) {
        Log.d(TAG, "onCompletion $mp")
        mediaController.onCompletion(mp)
    }

    override fun onSeekComplete(mp: IMediaPlayer) {
        Log.d(TAG, "onSeekComplete $mp")
        mediaController.onSeekComplete(mp)
    }

    companion object {

        const val TAG = "VideoPlayerActivity"
    }
}