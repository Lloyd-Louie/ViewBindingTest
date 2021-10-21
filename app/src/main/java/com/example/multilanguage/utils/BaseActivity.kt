package com.example.multilanguage.utils

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import java.util.*

abstract class BaseActivity <viewBinding : ViewBinding>: BaseActivityUtils() {
    var _binding: ViewBinding? = null
    private val binding: viewBinding get() = _binding as viewBinding
    abstract fun setupViewBinding(inflater: LayoutInflater): viewBinding
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        _binding = setupViewBinding(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()

        Log.i("Hakdog","Hakdog BaseAct ${UserSessionManager.currentLanguage}")
        if(baseContext.resources.configuration.locale.toString().substring(0,2)!= UserSessionManager.currentLanguage && UserSessionManager.currentLanguage !=""){
            val languageManager = LanguageManager(this@BaseActivity)
            languageManager.updateResource(UserSessionManager.currentLanguage)
            recreate()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding =  null
    }
    /**
     * This destroys activity after successful add load
     */


    fun getCurrentLocale(context: Context): Locale? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            context.resources.configuration.locales[0]
        } else {
            context.resources.configuration.locale
        }
    }
    fun geCurrentShortenLocale():String{
        val currentDeviceLocale =getCurrentLocale(this)
        val myLocale = currentDeviceLocale.toString().substring(0,2)
        return myLocale
    }
}