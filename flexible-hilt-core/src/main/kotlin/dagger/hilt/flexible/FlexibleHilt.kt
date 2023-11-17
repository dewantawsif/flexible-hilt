package dagger.hilt.flexible

import android.content.Context
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Provider

object FlexibleHilt {
    private var graph: FlexibleHiltGraphEntryPoint? = null

    fun init(context: Context) {
        graph = EntryPointAccessors.fromApplication<FlexibleHiltGraphEntryPoint>(context)
    }

    fun getItems(): Map<Class<out FlexibleHiltItem>, Provider<FlexibleHiltItem>> {
        return requireNotNull(graph) { "FlexibleHilt not initialized" }.items()
    }
}
