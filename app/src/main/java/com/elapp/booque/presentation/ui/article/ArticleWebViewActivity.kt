package com.elapp.booque.presentation.ui.article

import android.os.Bundle
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.elapp.booque.databinding.ActivityWebviewBinding

class ArticleWebViewActivity: AppCompatActivity() {

    private var _activityWebViewBinding: ActivityWebviewBinding? = null
    private val binding get() = _activityWebViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityWebViewBinding = ActivityWebviewBinding.inflate(layoutInflater)
        setContentView(_activityWebViewBinding?.root)

        val url = intent.getStringExtra("selectedUrl")
        binding?.wvArticle?.apply {
            webViewClient = WebViewClient()
            if (url != null) {
                loadUrl("https://blog.bibit.id/blog-1/promo-april-2021-minggu-pertama-ramadan-tetap-ada-cuan")
            }
        }
    }

}