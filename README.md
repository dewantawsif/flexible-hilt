## Guide
### Compatibility
The library requires your app's `minSdk` to be 21+

### Add dependencies
- KSP (https://github.com/google/ksp)

```kotlin
plugins {
    alias(libs.plugins.ksp)
}

dependencies {
    implementation("me.tawsif.hilt:flexible-hilt-core:0.7.0")
    ksp("me.tawsif.hilt:flexible-hilt-compiler:0.7.0")
}
```

### Prepare class/interface
Make your base class/interface inherit `FlexibleHiltItem`
```kotlin
class Pet @Inject constructor(): FlexibleHiltItem {
    val type = "dog"
}

interface Human: FlexibleHiltItem {
    val profession: String
}
```

### Enjoy the flexibility!
```kotlin
fun printPetType(pet: Pet = FlexibleHilt.get()) {
    println(pet.type)
}
```

_Note: There's also `FlexibleHilt.getLazy()` which you can use to get a lazy instance of the class._
_Helpful in `Activity` classes where you can only use hilt classes after `super.onCreate()`_

## License

    Copyright (c) 2024 Dewan Tawsif

    This Source Code Form is subject to the terms of the Mozilla Public
    License, v. 2.0. If a copy of the MPL was not distributed with this
    file, You can obtain one at http://mozilla.org/MPL/2.0/.
