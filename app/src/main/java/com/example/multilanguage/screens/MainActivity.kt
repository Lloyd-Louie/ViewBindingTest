package com.example.multilanguage.screens

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import com.example.multilanguage.databinding.ActivityMainBinding
import java.util.*

import com.example.multilanguage.utils.BaseActivity
import com.example.multilanguage.utils.LanguageManager
import com.example.multilanguage.LocaleType
import com.example.multilanguage.adapters.MemeAdapter
import com.example.multilanguage.databinding.ActivityRandomBinding
import com.example.multilanguage.utils.UserSessionManager


class MainActivity : BaseActivity<ActivityMainBinding>() {
    private lateinit var binding: ActivityMainBinding
    private val languageManager: LanguageManager = LanguageManager(this@MainActivity)

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        attachActions()
        Log.i("Hakdog", "I am Created")

    }


    private fun attachActions() {
        with(binding) {
            englishBTN.setOnClickListener {
                Log.i("Hakdog", "Hakdog ENGLISH")
                if(UserSessionManager.currentLanguage != LocaleType.ENGLISH.locale){
                    languageManager.updateResource(LocaleType.ENGLISH.locale)
                    UserSessionManager.currentLanguage = LocaleType.ENGLISH.locale
                    recreate()
                }
            }
            japaneseBTN.setOnClickListener {
                Log.i("Hakdog", "Hakdog JAPANESE")
                if(UserSessionManager.currentLanguage != LocaleType.JAPANESE.locale){
                    languageManager.updateResource(LocaleType.JAPANESE.locale)
                    UserSessionManager.currentLanguage = LocaleType.JAPANESE.locale
                    recreate()
                }
            }
            goBTN.setOnClickListener {
                navigate<RandomActivity>(finishCurrent = false){

                }
            }
        }
    }


    override fun onResume() {
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun setupViewBinding(inflater: LayoutInflater): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

}