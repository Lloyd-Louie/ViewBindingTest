package com.example.multilanguage.utils

import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import android.content.res.Resources
import java.util.*

class LanguageManager (var context: Context) {


    fun updateResource(localeString: String){

        val locale: Locale = Locale(localeString)
        val resources: Resources = context.resources
        val configuration: Configuration = resources.configuration
        configuration.setLocale(locale)
        //MyContextWrapper.wrap(context,localeString)
        resources.updateConfiguration(configuration,resources.displayMetrics)
    }


}