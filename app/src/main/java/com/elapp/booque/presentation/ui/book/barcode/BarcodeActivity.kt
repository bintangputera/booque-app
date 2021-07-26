package com.elapp.booque.presentation.ui.book.barcode

import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.elapp.booque.databinding.ActivityBarcodeScanBinding
import com.elapp.booque.databinding.ActivityCityBinding
import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView

class BarcodeActivity: AppCompatActivity(), ZXingScannerView.ResultHandler {

    private var zXingScannerView: ZXingScannerView? = null

    private var _activityBarcodeBinding: ActivityBarcodeScanBinding? = null
    private val binding get() = _activityBarcodeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityBarcodeBinding = ActivityBarcodeScanBinding.inflate(layoutInflater)
        setContentView(_activityBarcodeBinding?.root)

        initScannerView()
    }

    private fun initScannerView() {
        zXingScannerView = ZXingScannerView(applicationContext)
        zXingScannerView!!.setAutoFocus(true)
        zXingScannerView!!.setResultHandler(this)
        binding?.cameraFrame?.addView(zXingScannerView)
    }

    private fun onRequestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (applicationContext?.checkSelfPermission(android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ) {
                requestPermissions(arrayOf(android.Manifest.permission.CAMERA), 100)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode) {
            100 -> initScannerView()
            else -> {

            }
        }
    }

    override fun onStart() {
        zXingScannerView?.startCamera()
        onRequestPermission()
        super.onStart()
    }

    override fun handleResult(result: Result?) {
        intent.putExtra("buyer_id", result?.text)
        setResult(Activity.RESULT_OK, intent)
        Toast.makeText(this, "Barcode : ${result?.text.toString()}", Toast.LENGTH_SHORT).show()
        finish()
    }



}