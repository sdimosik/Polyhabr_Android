package ru.sdimosik.polyhabr

import android.app.Application
import com.hjq.toast.Toaster
import dagger.hilt.android.HiltAndroidApp
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import rxdogtag2.RxDogTag
import timber.log.Timber

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Toaster.init(this)
        RxDogTag.install()
        RxJavaPlugins.setErrorHandler(Timber::e)
    }
}
