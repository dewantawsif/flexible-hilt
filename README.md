## Guide
### Compatibility
The library requires your app's `minSdk` to be 21+

### Add dependencies
```kotlin
plugiin {
    id("com.google.devtools.ksp") version "1.9.20-1.0.14"
}

dependencies {
    implementation("me.tawsif.hilt:flexible-hilt-core:0.2.1")
    ksp("me.tawsif.hilt:flexible-hilt-compiler:0.2.1")
}
```

### Prepare class/interface
Make your base class/interface inherit `FlexibleHiltItem ` and annotate them with `MakeFlexible`
```kotlin
@MakeFlexible
class Pet @Inject constructor(): FlexibleHiltItem {
    val type = "dog"
}

@MakeFlexible
interface Human: FlexibleHiltItem {
    val profession: String
}
```

### Initialize
```kotlin
@HiltAndroidApp
class App: Application() {
    override fun onCreate() {
        super.onCreate()
        FlexibleHilt.init(this)
    }
}
```

### Enjoy the flexibility!
```kotlin
fun printPetType(pet: Pet = getFromFlexibleHilt()) {
    println(pet.type)
}
```

_Note: There's also `lazyFromFlexibleHilt()` which you can use to get a lazy instance of the class._
_Helpful in `Activity` classes where you can only use hilt classes after `super.onCreate()`_

## License

    Copyright (c) 2024 Dewan Tawsif

    This Source Code Form is subject to the terms of the Mozilla Public
    License, v. 2.0. If a copy of the MPL was not distributed with this
    file, You can obtain one at http://mozilla.org/MPL/2.0/.
