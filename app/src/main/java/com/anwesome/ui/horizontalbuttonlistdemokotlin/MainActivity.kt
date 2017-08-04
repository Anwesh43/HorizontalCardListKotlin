package com.anwesome.ui.horizontalbuttonlistdemokotlin

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.anwesome.ui.kotlinhorizontalbuttonlist.HorizontalButtonList

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var bitmap = BitmapFactory.decodeResource(resources,R.drawable.mustafi)
        HorizontalButtonList.create(this)
        for(i in 1..10) {
            HorizontalButtonList.addButton(bitmap,"Mustafi $i")
        }
        HorizontalButtonList.show(this)
    }
}
