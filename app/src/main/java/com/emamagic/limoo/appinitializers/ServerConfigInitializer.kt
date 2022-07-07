package com.emamagic.limoo.appinitializers

import android.app.Application
import com.emamagic.common_ui.appinitializer.AppInitializer
import com.emamagic.core.AppCoroutineDispatchers
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
            getServerConfig(GetServerConfig.Params("https://test.limonadapp.ir", true))
        }

    }

}