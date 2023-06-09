package ru.geekbrain.android.translator.view.descriptionscreen

import android.content.Context
import android.content.Intent
import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import coil.ImageLoader
import coil.request.LoadRequest
import coil.transform.CircleCropTransformation
import org.koin.android.scope.currentScope
import ru.geekbrain.android.utils.AlertDialogFragment
import ru.geekbrain.android.utils.online.OnlineLiveData
import ru.geekbrains.android.translator.R
import ru.geekbrains.android.translator.databinding.ActivityDescriptionBinding

class DescriptionActivity : AppCompatActivity() {
    companion object {
        private const val DIALOG_FRAGMENT_TAG = "8c7dff51-9769-4f6d-bbee-a3896085e76e"
        private const val WORD_EXTRA = "f76a288a-5dcc-43f1-ba89-7fe1d53f63b0"
        private const val DESCRIPTION_EXTRA = "0eeb92aa-520b-4fd1-bb4b-027fbf963d9a"
        private const val URL_EXTRA = "6e4b154d-e01f-4953-a404-639fb3bf7281"

        fun getIntent(
            context: Context,
            word: String,
            description: String,
            url: String?
        ): Intent = Intent(context, DescriptionActivity::class.java).apply {
            putExtra(WORD_EXTRA, word)
            putExtra(DESCRIPTION_EXTRA, description)
            putExtra(URL_EXTRA, url)
        }
    }

    private lateinit var binding: ActivityDescriptionBinding

    val imageLoader: ImageLoader by currentScope.inject()

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDescriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setActionHomeButtonAsUp()
        setData()

        binding.descriptionScreenSwipeRefreshLayout.setOnRefreshListener {
            startLoadingOrShowError()
        }
    }

    override fun onStop() {
        super.onStop()
        imageLoader.shutdown()
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun startLoadingOrShowError() {
        OnlineLiveData(this).observe(
            this
        ) {
            if (it)
                setData()
            else {
                AlertDialogFragment.newInstance(
                    getString(R.string.dialog_title_device_is_offline),
                    getString(R.string.dialog_message_device_is_offline),
                ).show(
                    supportFragmentManager,
                    DIALOG_FRAGMENT_TAG
                )
                stopRefreshAnimatorIfNeeded()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun setData() {
        val bundle = intent.extras
        binding.descriptionHeader.text = bundle?.getString(WORD_EXTRA)
        binding.descriptionTextview.text = bundle?.getString(DESCRIPTION_EXTRA)
        bundle?.getString(URL_EXTRA)?.let {
            if (it.isBlank()) {
                stopRefreshAnimatorIfNeeded()
            } else {
                //useGlideToLoadPhoto(binding.descriptionImageview, it)
                useCoilToLoadPhoto(binding.descriptionImageview , it)
            }

        }
    }

    private fun stopRefreshAnimatorIfNeeded() {
        if (binding.descriptionScreenSwipeRefreshLayout.isRefreshing) {
            binding.descriptionScreenSwipeRefreshLayout.isRefreshing = false
        }
    }

    private fun setActionHomeButtonAsUp() {
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun useCoilToLoadPhoto(imageView: ImageView, imageLink: String){
        val blurEffect = RenderEffect.createBlurEffect(16f, 16f, Shader.TileMode.MIRROR)
        val request = LoadRequest.Builder(this)
            .data("https:$imageLink")
            .target(
                onStart = {},
                onSuccess = {result ->
                    imageView.setRenderEffect(blurEffect)
                    imageView.setImageDrawable(result)
                },
                onError = {
                    imageView.setImageResource(R.drawable.ic_load_error_vector)
                }
            )
            .transformations(
                CircleCropTransformation()
            )
            .build()

        imageLoader.execute(request)
    }


}