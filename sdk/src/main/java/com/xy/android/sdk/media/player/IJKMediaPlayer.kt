package com.xy.android.sdk.media.player

import android.view.Surface
import android.view.SurfaceHolder

/**
 * One implementation of [IMediaPlayer], is the wrapper of IJK media player.
 */
class IJKMediaPlayer : AbsMediaPlayer() {

    override fun setSurface(surface: Surface?) {
        TODO("Not yet implemented")
    }

    override fun setDisplay(sh: SurfaceHolder?) {
        TODO("Not yet implemented")
    }

    override fun setDataSource(path: String?) {
        TODO("Not yet implemented")
    }

    override fun setDataSource(path: String?, headers: Map<String, String>?) {
        TODO("Not yet implemented")
    }

    override fun prepare() {
        TODO("Not yet implemented")
    }

    override fun prepareAsync() {
        TODO("Not yet implemented")
    }

    override fun start() {
        TODO("Not yet implemented")
    }

    override fun pause() {
        TODO("Not yet implemented")
    }

    override fun stop() {
        TODO("Not yet implemented")
    }

    override fun isPlaying(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getCurrentPosition(): Int {
        TODO("Not yet implemented")
    }

    override fun getDuration(): Int {
        TODO("Not yet implemented")
    }

    override fun seekTo(msec: Int) {
        TODO("Not yet implemented")
    }

    override fun seekTo(msec: Long, mode: Int) {
        TODO("Not yet implemented")
    }

    override fun setOnPreparedListener(listener: IMediaPlayer.OnPreparedListener) {
        TODO("Not yet implemented")
    }

    override fun setOnInfoListener(listener: IMediaPlayer.OnInfoListener) {
        TODO("Not yet implemented")
    }

    override fun setOnErrorListener(listener: IMediaPlayer.OnErrorListener) {
        TODO("Not yet implemented")
    }

    override fun setOnCompletionListener(listener: IMediaPlayer.OnCompletionListener) {
        TODO("Not yet implemented")
    }

    override fun setOnSeekCompleteListener(listener: IMediaPlayer.OnSeekCompleteListener) {
        TODO("Not yet implemented")
    }
}