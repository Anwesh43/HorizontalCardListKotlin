package com.anwesome.ui.kotlinhorizontalbuttonlist

import android.content.Context
import android.graphics.*
import android.view.MotionEvent
import android.view.View

/**
 * Created by anweshmishra on 05/08/17.
 */
class HorizontalButtonView(ctx:Context,var bitmap: Bitmap,var text:String):View(ctx) {
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    override fun onDraw(canvas:Canvas) {
    }
    override fun onTouchEvent(event:MotionEvent):Boolean {
        when(event.action) {
            MotionEvent.ACTION_DOWN -> {

            }
        }
        return true
    }
    data class HorizontalButton(var bitmap: Bitmap,var text:String,var w:Float,var h:Float) {
        fun draw(canvas:Canvas,paint:Paint,scale:Float) {
            var bw = bitmap.width.toFloat()
            var bh = bitmap.height.toFloat()
            canvas.save()
            canvas.translate(w/2,2*h/5)
            canvas.drawBitmap(bitmap,-bw/2,-bh/2,paint)
            canvas.save()
            canvas.scale(scale,scale)
            paint.color = Color.argb(100,0,255,0)
            canvas.drawRect(RectF(-bw/2,-bh/2,bw/2,bh/2),paint)
            canvas.restore()
            canvas.restore()
            paint.textSize = h/10
            paint.color = Color.BLACK
            canvas.save()
            canvas.translate(w/2,9*h/10)
            canvas.drawText(text,-paint.measureText(text)/2,-h/20,paint)
            canvas.restore()
        }
    }
}