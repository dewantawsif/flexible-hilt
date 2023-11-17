package dagger.hilt.flexible

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Provider

@EntryPoint
@InstallIn(SingletonComponent::class)
internal interface FlexibleHiltGraphEntryPoint {
    fun items(): Map<Class<out FlexibleHiltItem>, @JvmSuppressWildcards Provider<FlexibleHiltItem>>
}
