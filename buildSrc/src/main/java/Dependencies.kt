import org.gradle.api.JavaVersion

object Config {
    const val application_id = "ru.geekbrains.android.translator"
    const val compile_sdk = 33
    const val min_sdk = 23
    const val target_sdk = 33
    val java_version = JavaVersion.VERSION_1_8
}

object Releases {
    const val version_code = 1
    const val version_name = "1.0"
}

object Modules {
    const val app = ":app"
    const val core = ":core"
    const val model = ":model"
    const val repository = ":repository"
    const val utils = ":utils"

    //Features
    const val historyScreen = ":historyscreen"
}

object Versions{
    //Design
    const val appcompat = "1.6.1"
    const val material = "1.8.0"

    //Kotlin
    const val core = "1.7.0"
    const val stdlib = "1.7.10"
    const val coroutinesCore = "1.5.1"
    const val coroutinesAndroid = "1.6.4"

    //Retrofit
    const val retrofit = "2.9.0"
    const val converterGson = "2.9.0"
    const val interceptor = "4.9.2"
    const val adapterCoroutines = "0.9.2"

    //Koin
    const val koinAndroid = "2.1.6"
    const val koinViewModel = "2.1.6"

    //Coil

    const val coil = "0.11.0"

    //Room
    const val roomKtx = "2.5.1"
    const val runtime = "2.5.1"
    const val roomCompiler = "2.5.1"

    //Test
    const val jUnit = "4.13.2"
    const val runner = "1.1.5"
    const val espressoCore = "3.5.1"

    //SwipeRefreshLayout
    const val swiperefreshlayout = "1.1.0"

    //Picasso
    const  val picasso = "2.71828"

    //Glide
    const val glide = "4.11.0"

}

object Design{
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val material = "com.google.android.material:material:${Versions.material}"
}

object Kotlin {
    const val core = "androidx.core:core-ktx:${Versions.core}"
    const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.stdlib}"
    const val coroutines_core =
        "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutinesCore}"
    const val coroutines_android =
        "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutinesAndroid}"
}

object Retrofit {
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val converter_gson = "com.squareup.retrofit2:converter-gson:${Versions.converterGson}"
    const val adapter_coroutines =
        "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:${Versions.adapterCoroutines}"
    const val logging_interceptor =
        "com.squareup.okhttp3:logging-interceptor:${Versions.interceptor}"
}

object Koin {
    const val koin_android = "io.insert-koin:koin-android:${Versions.koinAndroid}"
    const val koin_view_model = "io.insert-koin:koin-android-viewmodel:${Versions.koinViewModel}"
}

object Coil {
    const val coil = "io.coil-kt:coil:${Versions.coil}"
}

object Room {
    const val runtime = "androidx.room:room-runtime:${Versions.runtime}"
    const val compiler = "androidx.room:room-compiler:${Versions.roomCompiler}"
    const val room_ktx = "androidx.room:room-ktx:${Versions.roomKtx}"
}

object Swipe{
    const val swiperefreshlayout = "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swiperefreshlayout}"
}

object Picasso {
    const val picasso = "com.squareup.picasso:picasso:${Versions.picasso}"
}

object Glide {
    const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    const val glideKapt = "com.github.bumptech.glide:compiler:${Versions.glide}"
}

object TestImpl {
    const val junit = "junit:junit:${Versions.jUnit}"
    const val runner = "androidx.test:runner:${Versions.runner}"
    const val espresso = "androidx.test.espresso:espresso-core:${Versions.espressoCore}"
}