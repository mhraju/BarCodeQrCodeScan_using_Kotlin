package com.example.mhraju.barcodeqrcodescan

import android.content.ContentValues.TAG
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.vision.barcode.Barcode
import es.dmoral.toasty.Toasty
import info.androidhive.barcode.BarcodeReader


class BarCodeReadFragment : Fragment(), BarcodeReader.BarcodeReaderListener {

    private lateinit var barcodeReader: BarcodeReader

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_bar_code_read, container, false)

        barcodeReader = (childFragmentManager.findFragmentById(R.id.barcode_fragment) as BarcodeReader?)!!
        barcodeReader.setListener(this)

        return v
    }


    override fun onScannedMultiple(barcodes: List<Barcode>) {
        Log.e(TAG, "onScannedMultiple: " + barcodes.size)

        var codes = ""
        for (barcode in barcodes) {
            codes += barcode.displayValue + ", "
        }

        val finalCodes = codes
        activity!!.runOnUiThread { Toast.makeText(activity, "Barcodes: $finalCodes", Toast.LENGTH_SHORT).show() }
    }

    override fun onBitmapScanned(sparseArray: SparseArray<Barcode>) {

    }

    override fun onScanError(errorMessage: String) {
        Log.e(TAG, "onScanError: $errorMessage")
    }

    override fun onCameraPermissionDenied() {
        Toast.makeText(activity, "Camera permission denied!", Toast.LENGTH_LONG).show()
    }

    override fun onScanned(barcode: Barcode?) {
        barcodeReader.playBeep()

        activity!!.runOnUiThread {
            if (barcode != null) {

                Toasty.success(activity!!, "Barcode: " + barcode.displayValue, Toast.LENGTH_SHORT, true).show();


                Toast.makeText(activity, "Barcode: " + barcode.displayValue, Toast.LENGTH_SHORT).show()
            }
        }
    }

}
