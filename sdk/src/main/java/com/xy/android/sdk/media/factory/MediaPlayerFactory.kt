package com.xy.android.sdk.media.factory

import com.xy.android.sdk.media.client.PlayerType
import com.xy.android.sdk.media.client.XyMediaPlayer
import com.xy.android.sdk.media.player.IJKMediaPlayer
import com.xy.android.sdk.media.player.IMediaPlayer
import com.xy.android.sdk.media.player.SystemMediaPlayer

class MediaPlayerFactory {

    fun create(): IMediaPlayer {
        return when (XyMediaPlayer.getConfig().getPlayerType()) {
            PlayerType.IJK -> tryCreateIJKMediaPlayer()
            PlayerType.EXO -> tryCreateExoMediaPlayer()
            else -> SystemMediaPlayer()
        }
    }

    private fun tryCreateIJKMediaPlayer(): IMediaPlayer {
        if (isIJKPlayerIntegrated()) {
            return IJKMediaPlayer()
        }
        return SystemMediaPlayer()
    }

    private fun isIJKPlayerIntegrated(): Boolean {
        return false
    }

    private fun tryCreateExoMediaPlayer(): IMediaPlayer {
        if (isExoPlayerIntegrated()) {
            return IJKMediaPlayer()
        }
        return SystemMediaPlayer()
    }

    private fun isExoPlayerIntegrated(): Boolean {
        return false
    }
}