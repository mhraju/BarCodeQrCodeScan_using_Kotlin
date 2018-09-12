package com.example.mhraju.barcodeqrcodescan

import android.content.ContentValues.TAG
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat.getSystemService
import android.util.Log
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.vision.barcode.Barcode
import es.dmoral.toasty.Toasty
import info.androidhive.barcode.BarcodeReader


class BarCodeReadFragment : Fragment(), BarcodeReader.BarcodeReaderListener {

    private lateinit var barcodeReader: BarcodeReader

    private lateinit var mySensorManager: SensorManager
    private lateinit var myProximitySensor: Sensor

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_bar_code_read, container, false)

        barcodeReader = (childFragmentManager.findFragmentById(R.id.barcode_fragment) as BarcodeReader?)!!
        barcodeReader.setListener(this)


        mySensorManager =  activity!!.getSystemService(
                Context.SENSOR_SERVICE) as SensorManager
        myProximitySensor = mySensorManager.getDefaultSensor(
                Sensor.TYPE_PROXIMITY)

        mySensorManager.registerListener(proximitySensorEventListener,
                myProximitySensor,
                SensorManager.SENSOR_DELAY_NORMAL);


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


               // Toast.makeText(activity, "Barcode: " + barcode.displayValue, Toast.LENGTH_SHORT).show()
            }
        }
    }


    var proximitySensorEventListener: SensorEventListener = object : SensorEventListener {
        override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
            // TODO Auto-generated method stub
        }

        override fun onSensorChanged(event: SensorEvent) {
            // TODO Auto-generated method stub
            if (event.sensor.type == Sensor.TYPE_PROXIMITY) {

                var  params : WindowManager.LayoutParams = activity!!.getWindow().getAttributes()

                if(event.values[0]==0f){

                    params.flags != WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                    params.screenBrightness = 0f
                    activity!!.getWindow().setAttributes(params)
                }
                else{
                    params.flags != WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                    params.screenBrightness = -1f
                    activity!!.getWindow().setAttributes(params)
                }

            }
        }
    }

}
