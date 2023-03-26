package com.xy.android.plugin.host

import android.content.Context
import dalvik.system.DexClassLoader
import java.io.File
import java.io.InputStream

/**
 * 插件管理接口
 */
object PluginsManager : IPluginsManager {

    const val PLUGIN_NAME_PLUGIN = "plugin"

    private const val PLUGINS_DIRECTORY_NAME = "plugins"

    private const val PLUGIN_OPTIMIZED_DIRECTORY = "optimized"

    private const val PLUGIN_LIBRARY_SEARCH_DIRECTORY = "library"

    private lateinit var pluginsDirectory: File

    private val plugins: HashMap<String, Plugin> by lazy { HashMap() }

    override fun init(context: Context) {
        pluginsDirectory = File(context.filesDir, PLUGINS_DIRECTORY_NAME)
        if (!pluginsDirectory.exists() || !pluginsDirectory.isDirectory) {
            pluginsDirectory.mkdirs()
        }
    }

    override fun installPlugin(name: String, inputStream: InputStream) {
        val pluginDirectory = File(pluginsDirectory, name)
        pluginDirectory.mkdirs()
        val optimizedDirectory = File(pluginDirectory, PLUGIN_OPTIMIZED_DIRECTORY)
        optimizedDirectory.mkdirs()
        val librarySearchDirectory = File(pluginDirectory, PLUGIN_LIBRARY_SEARCH_DIRECTORY)
        librarySearchDirectory.mkdirs()
        val pluginFile = File(pluginDirectory, "$name.apk")
        pluginFile.writeBytes(inputStream.readBytes())
        val classLoader = DexClassLoader(
            pluginFile.absolutePath,
            optimizedDirectory.absolutePath,
            librarySearchDirectory.absolutePath,
            ClassLoader.getSystemClassLoader()
        )
        plugins[name] = Plugin(name, pluginFile, classLoader)
    }

    override fun uninstallPlugin(name: String) {
        val pluginDirectory = File(pluginsDirectory, name)
        if (pluginDirectory.exists()) {
            pluginsDirectory.delete()
        }
        plugins.remove(name)
    }

    override fun getPlugin(name: String): Plugin? {
        return plugins[name]
    }
}