package com.elapp.booque.presentation.ui.book.edit

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.elapp.booque.R
import com.elapp.booque.databinding.ActivityEditBookBinding
import com.elapp.booque.presentation.ui.book.BookViewModel
import com.elapp.booque.utils.global.factory.ViewModelFactory
import com.elapp.booque.utils.helper.FileHelper
import com.elapp.booque.utils.network.NetworkState
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import timber.log.Timber

class EditBookActivity : AppCompatActivity() {

    private val viewModel by lazy {
        val factory = ViewModelFactory.getInstance()
        ViewModelProvider(this, factory).get(BookViewModel::class.java)
    }

    private var _activityEditBookBinding: ActivityEditBookBinding? = null
    private val binding get() = _activityEditBookBinding

    private var bookImagePath: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityEditBookBinding = ActivityEditBookBinding.inflate(layoutInflater)
        setContentView(_activityEditBookBinding?.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        initiatePermission(this)

        val id = intent.getIntExtra("book_id",0)

        binding?.imgBook?.setOnClickListener {
            chooseBookImage.launch("image/*")
        }

        loadBookDetail(id)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun loadBookDetail(bookId: Int) {
        viewModel.getDetailBuku(bookId).observe(this, Observer {
            binding?.edtNamaBuku?.setText(it.bookName)
            binding?.edtDeskripsi?.setText(it.description)
            binding?.edtAlamat?.setText(it.address)
            when (it.categoryName) {
                "Novel" -> binding?.spinnerCategory?.setSelection(0)
                "Komik" -> binding?.spinnerCategory?.setSelection(1)
                "Buku Pelajaran" -> binding?.spinnerCategory?.setSelection(2)
                "Sejarah" -> binding?.spinnerCategory?.setSelection(3)
                "Dongeng" -> binding?.spinnerCategory?.setSelection(4)
                "Kamus" -> binding?.spinnerCategory?.setSelection(5)
                "Cergam" -> binding?.spinnerCategory?.setSelection(6)
            }
            binding?.edtPenulis?.setText(it.author)
            binding?.edtYear?.setText("" + it.year)
            binding?.edtPenerbit?.setText(it.publisher)
        })
        viewModel.networkState.observe(this, Observer {
            when(it) {
                NetworkState.LOADING -> isLoading(true)
                NetworkState.LOADED -> isLoading(false)
            }
        })
    }

    private val chooseBookImage = registerForActivityResult(ActivityResultContracts.GetContent()) {
        binding?.imgBook?.setImageURI(it)
        Timber.d("uri Check, $it")
        bookImagePath = FileHelper().convertUriToFilePath(this, it)
        Timber.d("Check uri converted : $bookImagePath")
        Toast.makeText(applicationContext, "Uri : $bookImagePath", Toast.LENGTH_SHORT).show()
    }

    private fun initiatePermission(context: Context) {
        Dexter.withContext(context)
            .withPermissions(
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.CAMERA
            ).withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(p0: MultiplePermissionsReport?) {
                    if (p0?.areAllPermissionsGranted() == true) {
                        Timber.d("Granted!")
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: MutableList<PermissionRequest>?,
                    p1: PermissionToken?
                ) {
                    p1?.continuePermissionRequest()
                }
            }).withErrorListener {
                Toast.makeText(context.applicationContext, "Error", Toast.LENGTH_SHORT).show()
            }.onSameThread()
            .check()
    }

    private fun isLoading(status: Boolean) {
        if (!status) {
            binding?.backgroundDim?.visibility = View.INVISIBLE
            binding?.progressBar?.visibility = View.INVISIBLE
        } else {
            binding?.backgroundDim?.visibility = View.VISIBLE
            binding?.progressBar?.visibility = View.VISIBLE
        }
    }

}