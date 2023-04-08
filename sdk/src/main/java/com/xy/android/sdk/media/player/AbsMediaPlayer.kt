package com.xy.android.sdk.media.player

import android.content.res.AssetFileDescriptor
import android.media.MediaDataSource
import com.xy.android.sdk.media.controller.IMediaController
import java.io.FileDescriptor

abstract class AbsMediaPlayer : IMediaPlayer {

    var mediaController: IMediaController? = null

    override fun attachMediaController(mediaController: IMediaController) {
        this.mediaController = mediaController
        mediaController.attachMediaPlayer(this)
    }

    override fun setDataSource(afd: AssetFileDescriptor) {}

    override fun setDataSource(fd: FileDescriptor) {}

    override fun setDataSource(fd: FileDescriptor, offset: Long, length: Long) {}

    override fun setDataSource(dataSource: MediaDataSource) {}
}