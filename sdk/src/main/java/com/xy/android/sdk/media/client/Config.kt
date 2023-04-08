package com.xy.android.sdk.media.client

/**
 * Config information
 */
class Config {

    private var playerType: PlayerType = PlayerType.SYSTEM

    fun setPlayerType(playerType: PlayerType): Config {
        this.playerType = playerType
        return this
    }

    fun getPlayerType(): PlayerType {
        return playerType
    }

    companion object {

        fun default(): Config {
            return Config().setPlayerType(PlayerType.SYSTEM)
        }
    }
}

/**
 * Media player type.
 */
enum class PlayerType {

    /**
     * Android system media player
     */
    SYSTEM,

    /**
     * IJK Player
     */
    IJK,

    /**
     * Exo Player
     */
    EXO,

    /**
     * VLC Player
     */
    VLC,
}