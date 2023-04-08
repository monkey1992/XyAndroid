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

    private lateinit var mediaPlayerFactory: MediaPlayerFactory

    private lateinit var mediaPlayer: IMediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityVideoPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        initVideoPlayer()

        mediaPlayer.attachMediaController(binding.mediaController)
    }

    private fun initVideoPlayer() {
        mediaPlayerFactory = MediaPlayerFactory()
        mediaPlayer = mediaPlayerFactory.create()
        mediaPlayer.setOnPreparedListener(this)
        mediaPlayer.setOnInfoListener(this)
        mediaPlayer.setOnErrorListener(this)
        mediaPlayer.setOnCompletionListener(this)
        mediaPlayer.setOnSeekCompleteListener(this)
        mediaPlayer.setDataSource("")
        mediaPlayer.prepareAsync()
    }

    override fun onPrepared(mp: IMediaPlayer?) {
        mediaPlayer.start()
    }

    override fun onInfo(mp: IMediaPlayer?, what: Int, extra: Int): Boolean {
        Log.d(TAG, "onInfo $mp, $what, $extra")
        return true
    }

    override fun onError(mp: IMediaPlayer?, what: Int, extra: Int): Boolean {
        Log.d(TAG, "onError $mp, $what, $extra")
        return true
    }

    override fun onCompletion(mp: IMediaPlayer?) {
        Log.d(TAG, "onCompletion $mp")
    }

    override fun onSeekComplete(mp: IMediaPlayer?) {
        Log.d(TAG, "onSeekComplete $mp")
    }

    companion object {

        const val TAG = "VideoPlayerActivity"
    }
}