package com.anwesome.ui.kotlinhorizontalbuttonlist

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.view.MotionEvent
import android.view.View

/**
 * Created by anweshmishra on 05/08/17.
 */
class HorizontalButtonView(ctx:Context,var bitmap: Bitmap,var text:String):View(ctx) {
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    val renderer = Renderer(this)
    override fun onDraw(canvas:Canvas) {
        renderer.render(canvas,paint)
    }
    override fun onTouchEvent(event:MotionEvent):Boolean {
        when(event.action) {
            MotionEvent.ACTION_DOWN -> {
                renderer.handleTap()
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
    class Renderer(var v:HorizontalButtonView) {
        var time = 0
        var scale:Float = 0.0f
        var controller = AnimController(this)
        var horizontalButton:HorizontalButton?=null
        fun render(canvas:Canvas,paint:Paint) {
            if(time == 0) {
                var w = canvas.width.toFloat()
                var h = canvas.height.toFloat()
                var bitmap = Bitmap.createScaledBitmap(v.bitmap,4*canvas.width/5,3*canvas.height/5,true)
                horizontalButton = HorizontalButton(bitmap,v.text,w,h)
            }
            horizontalButton?.draw(canvas,paint,scale)
            time++
        }
        fun handleTap() {
            controller.start()
        }
        fun update(factor:Float) {
            scale = factor
            v.postInvalidate()
        }
    }
    class AnimController(var renderer:Renderer):AnimatorListenerAdapter(),ValueAnimator.AnimatorUpdateListener {
        val startAnim  = ValueAnimator.ofFloat(0.0f,1.0f)
        val endAnim = ValueAnimator.ofFloat(1.0f,0.0f)
        var animated = false
        var dir = 0
        init {
            startAnim.addUpdateListener(this)
            endAnim.addUpdateListener(this)
            startAnim.addListener(this)
            endAnim.addListener(this)
            startAnim.duration = 200
            endAnim.duration = 200
        }
        override fun onAnimationUpdate(vf:ValueAnimator) {
            renderer.update(vf.animatedValue as Float)
        }
        override fun onAnimationEnd(animator:Animator) {
            if(animated) {
                when(dir) {
                    0 -> {
                        endAnim.start()
                    }
                    1 -> {
                        animated = false
                    }
                }
                dir = (dir+1)%2
            }
        }
        fun start() {
            if(!animated && dir == 0) {
                startAnim.start()
                animated = true
            }
        }
    }
}
