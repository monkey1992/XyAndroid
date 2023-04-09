package com.xy.android.sdk.media.controller

import android.content.Context
import android.graphics.SurfaceTexture
import android.text.format.DateUtils
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.Surface
import android.view.TextureView
import android.view.View
import android.widget.*
import android.widget.SeekBar.OnSeekBarChangeListener
import com.xy.android.sdk.R
import com.xy.android.sdk.media.player.IMediaPlayer

/**
 * The default implementation of [IMediaController].
 */
class XyMediaController @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs), IMediaController {

    override var mediaPlayer: IMediaPlayer? = null

    private lateinit var textureView: TextureView
    private lateinit var progressBar: ProgressBar
    private lateinit var btnPlayPause: Button
    private lateinit var tvError: TextView
    private lateinit var tvCurrentPosition: TextView
    private lateinit var tvDuration: TextView
    private lateinit var seekBar: SeekBar

    private var surfaceTexture: SurfaceTexture? = null

    private var updateCurrentPositionRunnable: Runnable? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.video_controller, this)
        initViews()
    }

    private fun initViews() {
        textureView = findViewById(R.id.texture_view)
        textureView.surfaceTextureListener = object : TextureView.SurfaceTextureListener {
            override fun onSurfaceTextureAvailable(
                surface: SurfaceTexture,
                width: Int,
                height: Int
            ) {
                Log.d(TAG, "onSurfaceTextureAvailable")
                if (mediaPlayer == null) {
                    surfaceTexture = surface
                } else {
                    mediaPlayer?.setSurface(Surface(surface))
                }
            }

            override fun onSurfaceTextureSizeChanged(
                surface: SurfaceTexture,
                width: Int,
                height: Int
            ) {
                Log.d(TAG, "onSurfaceTextureSizeChanged")
            }

            override fun onSurfaceTextureDestroyed(surface: SurfaceTexture): Boolean {
                Log.d(TAG, "onSurfaceTextureDestroyed")
                return true
            }

            override fun onSurfaceTextureUpdated(surface: SurfaceTexture) {
                Log.d(TAG, "onSurfaceTextureUpdated")
            }
        }
        progressBar = findViewById(R.id.progress_bar)
        btnPlayPause = findViewById(R.id.btn_play_pause)
        btnPlayPause.setOnClickListener {
            mediaPlayer?.apply {
                if (isPlaying()) {
                    pause()
                    onPause()
                } else {
                    start()
                    onStart()
                }
            }
        }
        tvError = findViewById(R.id.tv_error)
        tvCurrentPosition = findViewById(R.id.tv_current_position)
        tvDuration = findViewById(R.id.tv_duration)
        seekBar = findViewById(R.id.seek_bar)
        seekBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    mediaPlayer?.seekTo(progress)
                }
                checkShowLoading()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
    }

    private fun setCurrentPosition() {
        mediaPlayer?.apply {
            val progress = getCurrentPosition()
            tvCurrentPosition.text =
                DateUtils.formatElapsedTime(progress.toLong() / 1000)
            seekBar.progress = progress
        }
    }

    private fun startToUpdateCurrentPosition() {
        if (updateCurrentPositionRunnable == null) {
            updateCurrentPositionRunnable = Runnable {
                setCurrentPosition()
                postDelayed(updateCurrentPositionRunnable, 100)
            }
        }
        post(updateCurrentPositionRunnable)
    }

    private fun stopUpdatingCurrentPosition() {
        removeCallbacks(updateCurrentPositionRunnable)
    }

    private fun setPlayOrPauseBtnBackground(isPlaying: Boolean) {
        btnPlayPause.setBackgroundResource(if (isPlaying) android.R.drawable.ic_media_pause else android.R.drawable.ic_media_play)
    }

    private fun showLoading() {
        progressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        progressBar.visibility = View.GONE
    }

    private fun showError(error: String) {
        tvError.text = error
        tvError.visibility = View.VISIBLE
    }

    private fun hideError() {
        tvError.visibility = View.GONE
    }

    private fun checkShowLoading() {
        if (seekBar.progress >= seekBar.secondaryProgress) {
            showLoading()
        } else {
            hideLoading()
        }
    }

    override fun attachMediaPlayer(mediaPlayer: IMediaPlayer) {
        this.mediaPlayer = mediaPlayer
        surfaceTexture?.apply {
            mediaPlayer.setSurface(Surface(this))
        }
    }

    override fun onStart() {
        startToUpdateCurrentPosition()
        setPlayOrPauseBtnBackground(true)
    }

    override fun onPause() {
        stopUpdatingCurrentPosition()
        setPlayOrPauseBtnBackground(false)
    }

    override fun onStop() {
        stopUpdatingCurrentPosition()
        setPlayOrPauseBtnBackground(false)
    }

    override fun onPrepared(mp: IMediaPlayer) {
        setCurrentPosition()
        val duration = mp.getDuration()
        tvDuration.text = DateUtils.formatElapsedTime(duration.toLong() / 1000)
        seekBar.max = duration
        hideLoading()
        hideError()
    }

    override fun onInfo(mp: IMediaPlayer, what: Int, extra: Int) {
    }

    override fun onError(mp: IMediaPlayer, what: Int, extra: Int) {
        showError("Error ( $what, $extra )")
        stopUpdatingCurrentPosition()
    }

    override fun onCompletion(mp: IMediaPlayer) {
        stopUpdatingCurrentPosition()
        setPlayOrPauseBtnBackground(false)
    }

    override fun onSeekComplete(mp: IMediaPlayer) {
    }

    override fun onBufferingUpdate(mp: IMediaPlayer, percent: Int) {
        seekBar.secondaryProgress = seekBar.max * percent / 100
        checkShowLoading()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        stopUpdatingCurrentPosition()
    }

    companion object {

        private const val TAG = "XyMediaController"
    }
}