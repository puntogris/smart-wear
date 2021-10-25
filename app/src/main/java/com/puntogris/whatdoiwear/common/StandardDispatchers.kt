package com.puntogris.whatdoiwear.common

import com.puntogris.whatdoiwear.domain.repository.DispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class StandardDispatchers: DispatcherProvider {

    override val main: CoroutineDispatcher
        get() = Dispatchers.Main

    override val io: CoroutineDispatcher
        get() = Dispatchers.IO

    override val default: CoroutineDispatcher
        get() = Dispatchers.Default
}