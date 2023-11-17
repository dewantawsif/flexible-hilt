package dev.dtuz.hilt.sample

import dagger.hilt.flexible.FlexibleHiltItem
import dagger.hilt.flexible.MakeFlexible
import javax.inject.Inject

@MakeFlexible
class Pet @Inject constructor(): FlexibleHiltItem {
    val type = "dog"
}
