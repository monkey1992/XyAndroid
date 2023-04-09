package com.xy.android.sdk.media.player

import android.content.res.AssetFileDescriptor
import android.media.MediaDataSource
import android.view.Surface
import android.view.SurfaceHolder
import com.xy.android.sdk.media.controller.IMediaController
import java.io.FileDescriptor

/**
 * The interface for media player.
 */
interface IMediaPlayer {

    /**
     * Interface definition for a callback to be invoked when the media
     * source is ready for playback.
     */
    interface OnPreparedListener {

        /**
         * Called when the media file is ready for playback.
         *
         * @param mp the MediaPlayer that is ready for playback
         */
        fun onPrepared(mp: IMediaPlayer)
    }

    /**
     * Interface definition of a callback to be invoked to communicate some
     * info and/or warning about the media or its playback.
     */
    interface OnInfoListener {

        /**
         * Called to indicate an info or a warning.
         *
         * @param mp      the MediaPlayer the info pertains to.
         * @param what    the type of info or warning.
         *
         *  * [.MEDIA_INFO_UNKNOWN]
         *  * [.MEDIA_INFO_VIDEO_TRACK_LAGGING]
         *  * [.MEDIA_INFO_VIDEO_RENDERING_START]
         *  * [.MEDIA_INFO_BUFFERING_START]
         *  * [.MEDIA_INFO_BUFFERING_END]
         *  * `MEDIA_INFO_NETWORK_BANDWIDTH (703)` -
         * bandwidth information is available (as `extra` kbps)
         *  * [.MEDIA_INFO_BAD_INTERLEAVING]
         *  * [.MEDIA_INFO_NOT_SEEKABLE]
         *  * [.MEDIA_INFO_METADATA_UPDATE]
         *  * [.MEDIA_INFO_UNSUPPORTED_SUBTITLE]
         *  * [.MEDIA_INFO_SUBTITLE_TIMED_OUT]
         *
         * @param extra an extra code, specific to the info. Typically
         * implementation dependent.
         * @return True if the method handled the info, false if it didn't.
         * Returning false, or not having an OnInfoListener at all, will
         * cause the info to be discarded.
         */
        fun onInfo(mp: IMediaPlayer, what: Int, extra: Int): Boolean
    }

    /**
     * Interface definition of a callback to be invoked when there
     * has been an error during an asynchronous operation (other errors
     * will throw exceptions at method call time).
     */
    interface OnErrorListener {
        /**
         * Called to indicate an error.
         *
         * @param mp      the MediaPlayer the error pertains to
         * @param what    the type of error that has occurred:
         *
         *  * [.MEDIA_ERROR_UNKNOWN]
         *  * [.MEDIA_ERROR_SERVER_DIED]
         *
         * @param extra an extra code, specific to the error. Typically
         * implementation dependent.
         *
         *  * [.MEDIA_ERROR_IO]
         *  * [.MEDIA_ERROR_MALFORMED]
         *  * [.MEDIA_ERROR_UNSUPPORTED]
         *  * [.MEDIA_ERROR_TIMED_OUT]
         *  * `MEDIA_ERROR_SYSTEM (-2147483648)` - low-level system error.
         *
         * @return True if the method handled the error, false if it didn't.
         * Returning false, or not having an OnErrorListener at all, will
         * cause the OnCompletionListener to be called.
         */
        fun onError(mp: IMediaPlayer, what: Int, extra: Int): Boolean
    }

    /**
     * Interface definition for a callback to be invoked when playback of
     * a media source has completed.
     */
    interface OnCompletionListener {

        /**
         * Called when the end of a media source is reached during playback.
         *
         * @param mp the MediaPlayer that reached the end of the file
         */
        fun onCompletion(mp: IMediaPlayer)
    }

    /**
     * Interface definition of a callback to be invoked indicating
     * the completion of a seek operation.
     */
    interface OnSeekCompleteListener {

        /**
         * Called to indicate the completion of a seek operation.
         *
         * @param mp the MediaPlayer that issued the seek operation
         */
        fun onSeekComplete(mp: IMediaPlayer)
    }

    fun attachMediaController(mediaController: IMediaController)

    /**
     * Sets the [Surface] to be used as the sink for the video portion of the media.
     * @param surface The Surface to be used for the video portion of the media.
     */
    fun setSurface(surface: Surface?)

    /**
     * Sets the [SurfaceHolder] to use for displaying the video portion of the media.
     * @param sh the SurfaceHolder to use for video display.
     */
    fun setDisplay(sh: SurfaceHolder?)

    /**
     * Sets the data source (file-path or http/rtsp URL) to use.
     * @param path the path of the file, or the http/rtsp URL of the stream you want to play.
     */
    fun setDataSource(path: String?)

    /**
     * Sets the data source (file-path or http/rtsp URL) to use.
     * @param path the path of the file, or the http/rtsp URL of the stream you want to play
     * @param headers the headers associated with the http request for the stream you want to play
     */
    fun setDataSource(path: String?, headers: Map<String, String>?)

    /**
     * Sets the data source (AssetFileDescriptor) to use.
     * @param afd the AssetFileDescriptor for the file you want to play
     */
    fun setDataSource(afd: AssetFileDescriptor)

    /**
     * Sets the data source (FileDescriptor) to use.
     * @param fd the FileDescriptor for the file you want to play
     */
    fun setDataSource(fd: FileDescriptor)

    /**
     * Sets the data source (FileDescriptor) to use.
     * The FileDescriptor must be seekable (N.B. a LocalSocket is not seekable).
     * @param fd the FileDescriptor for the file you want to play
     * @param offset the offset into the file where the data to be played starts, in bytes
     * @param length the length in bytes of the data to be played
     */
    fun setDataSource(fd: FileDescriptor, offset: Long, length: Long)

    /**
     * Sets the data source (MediaDataSource) to use.
     * @param dataSource the MediaDataSource for the media you want to play
     */
    fun setDataSource(dataSource: MediaDataSource)

    /**
     * Prepares the player for playback, synchronously.
     * After setting the datasource and the display surface, you need to either call prepare() or prepareAsync().
     * For files, it is OK to call prepare(), which blocks until MediaPlayer is ready for playback.
     */
    fun prepare()

    /**
     * Prepares the player for playback, asynchronously.
     * After setting the datasource and the display surface, you need to either call prepare() or prepareAsync().
     * For streams, you should call prepareAsync(), which returns immediately, rather than blocking until enough data has been buffered.
     */
    fun prepareAsync()

    /**
     * Starts or resumes playback. If playback had previously been paused, playback will continue from where it was paused.
     * If playback had been stopped, or never started before, playback will start at the beginning.
     */
    fun start()

    /**
     * Pauses playback. Call start() to resume.
     */
    fun pause()

    /**
     * Stops playback after playback has been started or paused.
     */
    fun stop()

    /**
     * Gets the current playback position.
     * @return the current position in milliseconds
     */
    fun getCurrentPosition(): Int

    /**
     * Gets the duration of the file.
     * @return the duration in milliseconds, if no duration is available (for example, if streaming live content), -1 is returned.
     */
    fun getDuration(): Int

    /**
     * Seeks to specified time position. Same as seekTo(long, int) with mode = SEEK_PREVIOUS_SYNC.
     * @param msec the offset in milliseconds from the start to seek to
     */
    fun seekTo(msec: Int)

    /**
     * Moves the media to specified time position by considering the given mode.
     * @param msec the offset in milliseconds from the start to seek to.
     * @param mode the mode indicating where exactly to seek to.
     */
    fun seekTo(msec: Long, mode: Int)

    /**
     * Register a callback to be invoked when the media source is ready for playback.
     * @param listener the callback that will be run
     */
    fun setOnPreparedListener(listener: OnPreparedListener)

    /**
     * Register a callback to be invoked when an info/warning is available.
     * @Param listener the callback that will be run
     */
    fun setOnInfoListener(listener: OnInfoListener)

    /**
     * Register a callback to be invoked when an error has happened during an asynchronous operation.
     * @Param listener the callback that will be run
     */
    fun setOnErrorListener(listener: OnErrorListener)

    /**
     * Register a callback to be invoked when the end of a media source has been reached during playback.
     * @Param listener the callback that will be run
     */
    fun setOnCompletionListener(listener: OnCompletionListener)

    /**
     * Register a callback to be invoked when a seek operation has been completed.
     * @Param listener the callback that will be run
     */
    fun setOnSeekCompleteListener(listener: OnSeekCompleteListener)
}