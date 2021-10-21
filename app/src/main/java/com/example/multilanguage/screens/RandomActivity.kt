package com.example.multilanguage.screens

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.recyclerview.widget.GridLayoutManager
import com.example.multilanguage.R
import com.example.multilanguage.adapters.MemeAdapter
import com.example.multilanguage.utils.BaseActivity

import com.example.multilanguage.databinding.ActivityRandomBinding
import com.example.multilanguage.databinding.DialogNoInternetBinding
import com.example.multilanguage.utils.GLOBAL_TAG
import com.example.multilanguage.utils.setSafeOnClickListener
import com.example.multilanguage.utils.show
import com.example.multilanguage.viewmodel.MemeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class RandomActivity : BaseActivity<ActivityRandomBinding>() {
    private lateinit var binding: ActivityRandomBinding
    private val memeViewModel: MemeViewModel by viewModel()
    private val memeAdapter: MemeAdapter = MemeAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityRandomBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        attachUI()
        loadAPI()
        initViewModel()

    }

    fun loadAPI() {
        Log.i("loadAPI","loadAPI yes")
        memeViewModel.getMemeImages()
    }

    fun initViewModel() {
        memeViewModel.memeResponse.observe(this, {
            Log.i("MemeImages", "Meme Response $it")
            memeAdapter.updateMemeList(it.memeList?: emptyList())
        })
        memeViewModel.showNetworkError.observe(this, {
            Log.i("showNetworkError","showNetworkError Meme")
            val noNetworkDialog = createCustomAlertDialog(true,R.layout.dialog_no_internet)
            val dialogNoInternetBinding = DialogNoInternetBinding.bind(noNetworkDialog.view)
            noNetworkDialog.alertDialog.show()
            with(dialogNoInternetBinding){
                okBTN.setSafeOnClickListener {
                    noNetworkDialog.alertDialog.dismiss()
                }
            }
        })
    }

    private fun attachUI() {
        with(binding) {
            memeRV.apply {
                adapter = memeAdapter
                layoutManager = GridLayoutManager(context, 2)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e(GLOBAL_TAG, "RandomActivity onCleared")
    }
    override fun onBackPressed() {
        finish()
        Log.e(GLOBAL_TAG, "RandomActivity onBackPressed")
    }

    override fun onResume() {
        super.onResume()
    }

    override fun setupViewBinding(inflater: LayoutInflater): ActivityRandomBinding {
        return ActivityRandomBinding.inflate(layoutInflater)
    }
}