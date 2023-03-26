package com.xy.android.plugin.host

import java.io.File

/**
 * 插件
 * @param name 插件名称
 * @param path 插件文件
 * @param classLoader 插件[ClassLoader]
 */
data class Plugin(
    val name: String,
    val path: File,
    val classLoader: ClassLoader
)