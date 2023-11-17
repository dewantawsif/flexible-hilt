## Guide

### Add dependencies
```kotlin
plugiin {
    id("com.google.devtools.ksp") version "1.9.20-1.0.14"
}

dependencies {
    implementation("dev.dtuz.hilt:flexible-hilt-core:1.0.0")
    ksp("dev.dtuz.hilt:flexible-hilt-compiler:1.0.0")
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

    Copyright 2023 Dewan Tawsif

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
