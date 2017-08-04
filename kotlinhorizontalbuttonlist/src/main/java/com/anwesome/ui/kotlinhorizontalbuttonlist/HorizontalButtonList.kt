package com.anwesome.ui.kotlinhorizontalbuttonlist

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Point
import android.hardware.display.DisplayManager
import android.view.View
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import android.widget.ScrollView

/**
 * Created by anweshmishra on 05/08/17.
 */
class HorizontalButtonList(context: Context):ViewGroup(context) {
    var w = 0
    var h = 0
    init {
        getDimension(context)
    }
    private fun getDimension(context: Context) {
        var manager = context.getSystemService(Context.DISPLAY_SERVICE) as DisplayManager
        var display = manager.getDisplay(0)
        var size = Point()
        display.getRealSize(size)
        w = size.x
        h = size.y
    }
    override fun onMeasure(wspec:Int,hspec:Int) {
        var totalW = w/20
        for(i in 0..childCount-1) {
            var child = getChildAt(i)
            measureChild(child,wspec,hspec)
            totalW += child.measuredWidth+w/20
        }
        setMeasuredDimension(totalW,h/4)
    }
    override fun onLayout(reloaded:Boolean,a:Int,b:Int,aw:Int,ah:Int) {
        var x = w/20
        for(i in 0..childCount-1) {
            var child = getChildAt(i)
            child.layout(x,0,x+child.measuredWidth,child.measuredHeight)
            x += child.measuredWidth + w/20
        }
    }
    fun addButton(bitmap: Bitmap,text:String) {
        var view = HorizontalButtonView(context,bitmap,text)
        addView(view, LayoutParams(w/4,w/4))
    }
    companion object {
        var list:HorizontalButtonList?=null
        var shown = false
        fun create(activity:Activity) {
            if(list == null) {
                list = HorizontalButtonList(activity)
            }
        }
        fun addButton(bitmap:Bitmap,text:String) {
            if(!shown) {
                list?.addButton(bitmap, text)
            }
        }
        fun show(activity: Activity) {
            if(!shown) {
                var scrollView = HorizontalScrollView(activity)
                scrollView.addView(list, LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT))
                scrollView.isHorizontalScrollBarEnabled = false
                activity.addContentView(scrollView,LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT))
                shown = true
            }
        }
    }
}