package com.xy.android.sdk.media.controller

import com.xy.android.sdk.media.player.IMediaPlayer

/**
 * The interface for media controller.
 */
interface IMediaController {

    var mediaPlayer: IMediaPlayer?

    fun attachMediaPlayer(mediaPlayer: IMediaPlayer)
}