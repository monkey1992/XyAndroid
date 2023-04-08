package com.xy.android.sdk.media.controller

import android.content.Context
import android.graphics.SurfaceTexture
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.Surface
import android.view.TextureView
import android.widget.Button
import android.widget.FrameLayout
import android.widget.SeekBar
import android.widget.TextView
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
    private lateinit var btnPlayPause: Button
    private lateinit var tvCurrentPosition: TextView
    private lateinit var tvDuration: TextView
    private lateinit var seekBar: SeekBar

    private var surfaceTexture: SurfaceTexture? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.video_controller, null)
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
        btnPlayPause = findViewById(R.id.btn_play_pause)
        btnPlayPause.setOnClickListener {

        }
        tvCurrentPosition = findViewById(R.id.tv_current_position)
        tvDuration = findViewById(R.id.tv_duration)
        seekBar = findViewById(R.id.seek_bar)
    }

    override fun attachMediaPlayer(mediaPlayer: IMediaPlayer) {
        this.mediaPlayer = mediaPlayer
        surfaceTexture?.apply {
            mediaPlayer.setSurface(Surface(this))
        }
    }

    companion object {

        private const val TAG = "XyMediaController"
    }
}