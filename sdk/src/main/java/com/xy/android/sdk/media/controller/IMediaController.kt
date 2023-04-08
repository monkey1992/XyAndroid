package com.xy.android.sdk.media.controller

import com.xy.android.sdk.media.player.IMediaPlayer

/**
 * The interface for media controller.
 */
interface IMediaController {

    var mediaPlayer: IMediaPlayer?

    fun attachMediaPlayer(mediaPlayer: IMediaPlayer)

    fun start()

    fun pause()

    fun stop()

    fun onPrepared(mp: IMediaPlayer)

    fun onInfo(mp: IMediaPlayer, what: Int, extra: Int)

    fun onError(mp: IMediaPlayer, what: Int, extra: Int)

    fun onCompletion(mp: IMediaPlayer)

    fun onSeekComplete(mp: IMediaPlayer)
}