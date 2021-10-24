package com.example.multilanguage

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.LifecycleObserver
import android.os.Build
import com.example.multilanguage.utils.UserSessionManager
import com.example.multilanguage.utils.appModules
import com.facebook.stetho.Stetho
import dagger.hilt.android.HiltAndroidApp
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import java.util.*

@HiltAndroidApp
class MultiLanguageApplication:Application(), LifecycleObserver {
    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
        UserSessionManager.init(this)
        val currentDeviceLocale =getCurrentLocale(this)
        if(UserSessionManager.currentLanguage.isEmpty() || UserSessionManager.currentLanguage == "" ){
            val myLocale = currentDeviceLocale.toString().substring(0,2)

            UserSessionManager.currentLanguage = LocaleType.ENGLISH.locale
            if(myLocale == LocaleType.ENGLISH.locale || myLocale == LocaleType.JAPANESE.locale){
                UserSessionManager.currentLanguage = myLocale
            }

            Log.i("Hakdog","Hakdog MultiApplication $currentDeviceLocale ${UserSessionManager.currentLanguage}")
        }
        // Logs
        Stetho.initializeWithDefaults(this)
        // Dependency injection
//        startKoin {
//            androidLogger()
//            androidContext(this@MultiLanguageApplication)
//            modules(appModules)
//        }

    }

    companion object {

        private var instance: MultiLanguageApplication? = null

        fun applicationContext(): Context {
            return instance!!.applicationContext
        }

        fun getInstance(): MultiLanguageApplication? {
            return instance
        }
    }

    fun getCurrentLocale(context: Context): Locale? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            context.resources.configuration.locales[0]
        } else {
            context.resources.configuration.locale
        }
    }


}