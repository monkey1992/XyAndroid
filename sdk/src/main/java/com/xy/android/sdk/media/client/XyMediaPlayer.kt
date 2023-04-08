package com.xy.android.sdk.media.client

/**
 * XyMediaPlayer
 */
object XyMediaPlayer {

    /**
     * The [Config] instance.
     */
    private var config: Config = Config.default()

    /**
     * Set the [Config] instance
     * @param config The [Config] instance
     * @return [XyMediaPlayer]
     */
    fun setConfig(config: Config): XyMediaPlayer {
        XyMediaPlayer.config = config
        return this
    }

    /**
     * Get the [Config] instance
     * @return [config] The [Config] instance
     */
    fun getConfig(): Config {
        return config
    }
}