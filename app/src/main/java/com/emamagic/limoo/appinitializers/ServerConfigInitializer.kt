package com.emamagic.limoo.appinitializers

import android.app.Application
import android.util.Log
import com.emamagic.cache.cache.CacheFactory
import com.emamagic.cache.cache.get
import com.emamagic.cache.cache.pref
import com.emamagic.common_ui.appinitializer.AppInitializer
import com.emamagic.core.AppCoroutineDispatchers
import com.emamagic.core.PrefKeys
import com.emamagic.domain.interactors.GetServerConfig
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class ServerConfigInitializer @Inject constructor(
    private val getServerConfig: GetServerConfig,
    private val dispatchers: AppCoroutineDispatchers
): AppInitializer {
    override fun init(application: Application) {
        @OptIn(DelicateCoroutinesApi::class)
        GlobalScope.launch(dispatchers.io) {
            while (true) { // wait until CacheInitializer initialize the pref
                if (CacheFactory.isCacheInitialized()) {
                    val host = pref[PrefKeys.HOST, "https://test.limonadapp.ir"]
                    getServerConfig(GetServerConfig.Params(host, true))
                    return@launch
                }
            }
        }

    }

}