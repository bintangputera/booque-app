package com.elapp.booque.presentation.ui.book.konfirmasi

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.elapp.booque.databinding.ActivityKonfirmasiBinding
import com.elapp.booque.presentation.ui.book.barcode.BarcodeActivity
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

class KonfirmasiActivity : AppCompatActivity(), BookListener {

    private val viewModel by lazy {
        val factory = ViewModelFactory.getInstance()
        ViewModelProvider(this, factory).get(KonfirmasiViewModel::class.java)
    }

    private var _activityKonfirmasiBinding: ActivityKonfirmasiBinding? = null
    private val binding get() = _activityKonfirmasiBinding

    private var buktiImagePath: String? = null

    private lateinit var buyerId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityKonfirmasiBinding = ActivityKonfirmasiBinding.inflate(layoutInflater)
        setContentView(_activityKonfirmasiBinding?.root)
        title = "Konfirmasi"

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        initiatePermission(this)

        viewModel.listener = this

        binding?.imgBook?.setOnClickListener {
            chooseFoodImage.launch("image/*")
        }

        binding?.tvBarcode?.setOnClickListener {
            barcodeLauncher.launch(Intent(this, BarcodeActivity::class.java))
        }

        binding?.btnAddKonfirmasi?.setOnClickListener {
            openLogoutDialog()
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun openLogoutDialog() {
        val alertDialog = this.let { AlertDialog.Builder(it) }
        alertDialog.setTitle("Kamu yakin ingin melepaskan buku ini ?")
            ?.setPositiveButton(
                "Ya"
            ) { _, _ -> postKonfirmasi() }
            ?.setNegativeButton("Cancel", null)
        val alert = alertDialog.create()
        alert.show()
    }

    private val barcodeLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val intent = result.data
            buyerId = intent?.getStringExtra("buyer_id")!!
            Toast.makeText(this, "Province id : $buyerId", Toast.LENGTH_SHORT).show()
        }
    }

    private fun postKonfirmasi() {
        val bookId = intent.getIntExtra("book_id", 0)
        val userId = SessionManager(this).userId

        val idBuyer = Integer.parseInt(buyerId)
        viewModel.validateCreateKonfirmasi(this, this, userId, bookId, buktiImagePath, idBuyer)
    }

    private val chooseFoodImage = registerForActivityResult(ActivityResultContracts.GetContent()) {
        binding?.imgBook?.setImageURI(it)
        Timber.d("uri Check, $it")
        buktiImagePath = FileHelper().convertUriToFilePath(this, it)
        Timber.d("Check uri converted : $buktiImagePath")
        Toast.makeText(applicationContext, "Uri : $buktiImagePath", Toast.LENGTH_SHORT).show()
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
            binding?.bgDim?.visibility = View.VISIBLE
            binding?.progressBar?.visibility = View.VISIBLE
        } else {
            binding?.bgDim?.visibility = View.INVISIBLE
            binding?.progressBar?.visibility = View.INVISIBLE
        }
    }

    override fun onInitiating() {
        checkLoading(true)
    }

    override fun onSuccess(message: String) {
        checkLoading(false)
        Toast.makeText(applicationContext, "Message : $message", Toast.LENGTH_SHORT).show()
    }

    override fun onFailed(message: String) {
        Toast.makeText(applicationContext, "Message : $message", Toast.LENGTH_SHORT).show()
    }

}