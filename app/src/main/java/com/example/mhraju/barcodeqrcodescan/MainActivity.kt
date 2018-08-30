package com.example.mhraju.barcodeqrcodescan

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val ft = supportFragmentManager.beginTransaction()
        val bf = BarCodeReadFragment()
        ft.add(R.id.container, bf)
        ft.commit()
    }
}
