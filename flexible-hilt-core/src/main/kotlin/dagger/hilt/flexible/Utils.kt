package dagger.hilt.flexible

inline fun <reified T : FlexibleHiltItem> getFromFlexibleHilt(): T {
    return FlexibleHilt.getItems()[T::class.java]?.get() as? T
        ?: error(
            "Couldn't find ${T::class.java} in 'FlexibleHiltGraph'. Make sure you have annotated " +
                "the base class/interface with [IncludeInFlexibleHiltGraph] and it inherits [FlexibleHiltItem]",
        )
}

inline fun <reified T : FlexibleHiltItem> lazyFromFlexibleHilt(): Lazy<T> {
    return lazy { getFromFlexibleHilt() }
}
