package com.elapp.booque.presentation.ui.profile.barcode

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Point
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidmads.library.qrgenearator.QRGContents
import androidmads.library.qrgenearator.QRGEncoder
import com.elapp.booque.databinding.FragmentBarcodeDialogBinding
import com.elapp.booque.utils.global.SessionManager

class BarcodeDialogFragment : androidx.fragment.app.DialogFragment() {

    private var _fragmentBarcodeDialogBinding: FragmentBarcodeDialogBinding? = null
    private val binding get() = _fragmentBarcodeDialogBinding

    private lateinit var userId: String

    private lateinit var qrgEncoder: QRGEncoder
    private lateinit var bitmap: Bitmap

    companion object {
        fun newInstance(): BarcodeDialogFragment {
            return BarcodeDialogFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentBarcodeDialogBinding = FragmentBarcodeDialogBinding.inflate(layoutInflater)
        return _fragmentBarcodeDialogBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userId = requireArguments().getString("user_id", null)

        val userId2 = SessionManager(requireContext()).userId.toString()
        Toast.makeText(context?.applicationContext, userId2, Toast.LENGTH_SHORT).show()

        if (userId2.isNotEmpty()) {
            val manager: WindowManager = activity?.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val display = manager.defaultDisplay
            val point = Point()
            display.getSize(point)
            val width = point.x
            val height = point.y
            var smallerDimension = if (width < height) width else height
            smallerDimension = smallerDimension * 3 / 4

            qrgEncoder = QRGEncoder(
                userId, null,
                QRGContents.Type.TEXT,
                smallerDimension
            )
            qrgEncoder.colorBlack = Color.BLACK
            qrgEncoder.colorWhite = Color.WHITE
            try {
                bitmap = qrgEncoder.bitmap
                binding?.imgQrcode?.setImageBitmap(bitmap)
            } catch (e: Exception) {
                Toast.makeText(context, "Error occured ${e.message}", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(context, "Tidak ada Code", Toast.LENGTH_SHORT).show()
        }
    }

}