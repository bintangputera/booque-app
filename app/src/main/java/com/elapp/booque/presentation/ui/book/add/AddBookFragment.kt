package com.elapp.booque.presentation.ui.book.add

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.elapp.booque.databinding.FragmentAddBookBinding
import com.elapp.booque.presentation.ui.book.listener.BookListener
import com.elapp.booque.utils.global.SessionManager
import com.elapp.booque.utils.global.factory.ViewModelFactory
import com.elapp.booque.utils.helper.FileHelper
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import timber.log.Timber

class AddBookFragment: Fragment(), BookListener {

    private var _fragmentAddBookBinding: FragmentAddBookBinding? = null
    private val binding get() = _fragmentAddBookBinding

    private val viewModel by lazy {
        val factory = ViewModelFactory.getInstance()
        ViewModelProvider(this, factory).get(AddBookViewModel::class.java)
    }

    private var bookImagePath: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentAddBookBinding = FragmentAddBookBinding.inflate(inflater, container, false)
        return _fragmentAddBookBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.imgBook?.setOnClickListener {
            chooseFoodImage.launch("image/*")
        }
        binding?.btnAddBook?.setOnClickListener {
            postBook()
        }
        initiatePermission(requireContext())
    }

    private fun postBook() {
        val bookName = binding?.edtNamaBuku?.text?.toString()
        val bookDescription = binding?.edtDeskripsi?.text?.toString()
        val bookAddress = binding?.edtAlamat?.text?.toString()
        val category = binding?.spinnerCategory?.selectedItem.toString()
        val bookAuthor = binding?.edtPenulis?.text?.toString()
        val bookYear = binding?.edtYear?.text?.toString()
        val bookRelease = binding?.edtPenerbit?.text?.toString()
        val userId = SessionManager(requireContext()).userId

        when {
            bookName.isNullOrEmpty() -> {
                binding?.edtNamaBuku?.error = "Nama buku tidak boleh kosong"
            }
            bookDescription.isNullOrEmpty() -> {
                binding?.edtDeskripsi?.error = "Deskripsi buku tidak boleh kosong"
            }
            bookAddress.isNullOrEmpty() -> {
                binding?.edtAlamat?.error = "Alamat tidak boleh kosong"
            }
            bookAuthor.isNullOrEmpty() -> {
                binding?.edtPenulis?.error = "Penulis tidak boleh kosong"
            }
            bookYear.isNullOrEmpty() -> {
                binding?.edtYear?.error = "Tahun buku tidak boleh kosong"
            }
            bookRelease.isNullOrEmpty() -> {
                binding?.edtPenerbit?.error = "Penerbit tidak boleh kosong"
            }
            else -> {
                viewModel.validateCreateBook(
                    requireContext(),
                    viewLifecycleOwner,
                    bookName,
                    userId,
                    bookDescription,
                    bookAddress,
                    1,
                    bookImagePath,
                    bookAuthor,
                    bookYear,
                    bookRelease,
                    1,
                    1
                )
            }
        }

    }

    private val chooseFoodImage = registerForActivityResult(ActivityResultContracts.GetContent()) {
        binding?.imgBook?.setImageURI(it)
        Timber.d("uri Check, $it")
        bookImagePath = FileHelper().convertUriToFilePath(requireContext(), it)
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

    private fun checkLoading(isLoading: Boolean) {
        if (!isLoading) {
            binding?.backgroundDim?.visibility = View.VISIBLE
            binding?.progressLogin?.visibility = View.VISIBLE
            binding?.imgBook?.isEnabled = false
            binding?.edtAlamat?.isEnabled = false
            binding?.edtDeskripsi?.isEnabled = false
            binding?.edtNamaBuku?.isEnabled = false
            binding?.edtPenerbit?.isEnabled = false
            binding?.edtPenulis?.isEnabled = false
            binding?.edtYear?.isEnabled = false
        } else {
            binding?.backgroundDim?.visibility = View.INVISIBLE
            binding?.progressLogin?.visibility = View.INVISIBLE
            binding?.imgBook?.isEnabled = true
            binding?.edtAlamat?.isEnabled = true
            binding?.edtDeskripsi?.isEnabled = true
            binding?.edtNamaBuku?.isEnabled = true
            binding?.edtPenerbit?.isEnabled = true
            binding?.edtPenulis?.isEnabled = true
            binding?.edtYear?.isEnabled = true
        }
    }

    override fun onInitiating() {
        checkLoading(true)
    }

    override fun onSuccess(message: String) {
        Toast.makeText(context?.applicationContext, "Message : $message", Toast.LENGTH_SHORT).show()
    }

    override fun onFailed(message: String) {
        Toast.makeText(context?.applicationContext, "Message : $message", Toast.LENGTH_SHORT).show()
    }

}