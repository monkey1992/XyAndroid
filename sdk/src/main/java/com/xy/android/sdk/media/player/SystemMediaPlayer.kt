package com.xy.android.sdk.media.player

import android.content.res.AssetFileDescriptor
import android.media.MediaDataSource
import android.media.MediaPlayer
import android.os.Build
import android.view.Surface
import android.view.SurfaceHolder
import java.io.FileDescriptor

/**
 * One implementation of [IMediaPlayer], is the wrapper of android system media player.
 */
class SystemMediaPlayer : AbsMediaPlayer() {

    private var mediaPlayer: MediaPlayer = MediaPlayer()

    override fun setSurface(surface: Surface?) {
        mediaPlayer.setSurface(surface)
    }

    override fun setDisplay(sh: SurfaceHolder?) {
        mediaPlayer.setDisplay(sh)
    }

    override fun setDataSource(path: String?) {
        mediaPlayer.setDataSource(path)
    }

    override fun setDataSource(path: String?, headers: Map<String, String>?) {
        mediaPlayer.setDataSource(path)
    }

    override fun setDataSource(afd: AssetFileDescriptor) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mediaPlayer.setDataSource(afd)
        }
    }

    override fun setDataSource(fd: FileDescriptor) {
        mediaPlayer.setDataSource(fd)
    }

    override fun setDataSource(fd: FileDescriptor, offset: Long, length: Long) {
        mediaPlayer.setDataSource(fd, offset, length)
    }

    override fun setDataSource(dataSource: MediaDataSource) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mediaPlayer.setDataSource(dataSource)
        }
    }

    override fun prepare() {
        mediaPlayer.prepare()
    }

    override fun prepareAsync() {
        mediaPlayer.prepareAsync()
    }

    override fun release() {
        mediaPlayer.release()
    }

    override fun reset() {
        mediaPlayer.reset()
    }

    override fun start() {
        mediaPlayer.start()
    }

    override fun pause() {
        mediaPlayer.pause()
    }

    override fun stop() {
        mediaPlayer.stop()
    }

    override fun isPlaying(): Boolean {
        return mediaPlayer.isPlaying
    }

    override fun getCurrentPosition(): Int {
        return mediaPlayer.currentPosition
    }

    override fun getDuration(): Int {
        return mediaPlayer.duration
    }

    override fun seekTo(msec: Int) {
        mediaPlayer.seekTo(msec)
    }

    override fun seekTo(msec: Long, mode: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mediaPlayer.seekTo(msec, mode)
            return
        }
        mediaPlayer.seekTo(
            when {
                msec > Int.MAX_VALUE -> Int.MAX_VALUE
                msec < Int.MIN_VALUE -> Int.MIN_VALUE
                else -> msec.toInt()
            }
        )
    }

    override fun setOnPreparedListener(listener: IMediaPlayer.OnPreparedListener?) {
        mediaPlayer.setOnPreparedListener { listener?.onPrepared(this@SystemMediaPlayer) }
    }

    override fun setOnInfoListener(listener: IMediaPlayer.OnInfoListener?) {
        mediaPlayer.setOnInfoListener { mp, what, extra ->
            listener?.onInfo(
                this@SystemMediaPlayer,
                what,
                extra
            ) ?: false
        }
    }

    override fun setOnErrorListener(listener: IMediaPlayer.OnErrorListener?) {
        mediaPlayer.setOnErrorListener { mp, what, extra ->
            listener?.onError(
                this@SystemMediaPlayer,
                what,
                extra
            ) ?: false
        }
    }

    override fun setOnCompletionListener(listener: IMediaPlayer.OnCompletionListener?) {
        mediaPlayer.setOnCompletionListener { listener?.onCompletion(this@SystemMediaPlayer) }
    }

    override fun setOnSeekCompleteListener(listener: IMediaPlayer.OnSeekCompleteListener?) {
        mediaPlayer.setOnSeekCompleteListener { listener?.onSeekComplete(this@SystemMediaPlayer) }
    }

    override fun setOnBufferingUpdateListener(listener: IMediaPlayer.OnBufferingUpdateListener?) {
        mediaPlayer.setOnBufferingUpdateListener { mp, percent ->
            listener?.onBufferingUpdate(
                this@SystemMediaPlayer,
                percent
            )
        }
    }
}