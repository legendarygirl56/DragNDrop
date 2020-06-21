package com.ayushidoshi56.dragndrop

import android.content.Context
import android.graphics.PointF
import android.os.Bundle
import android.os.Vibrator
import android.util.Log
import android.view.DragEvent
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity: AppCompatActivity(), View.OnTouchListener, View.OnDragListener {
    var pointA=PointF(10f,100f)
    var pointB=PointF(500f,400f)

    var c=0
    var state=0
    var clickstate=0
    private val TAG = "LINER"
    //val x = parent.getSystemService(Context.VIBRATOR_SERVICE)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnadd.setOnClickListener {
            addTV()
            c++
        }
        btnline.setOnClickListener {
            if(clickstate==0){
                clickstate=1

            }
            else
                clickstate=0
        }
    }

    private fun addTV()
    {
        val tv=TextView(this)
        tv.text=""
        tv.setBackground(ContextCompat.getDrawable(this, R.drawable.circle));
        ll_pinklayout.addView(tv)
        tv.setOnTouchListener(this)

        ll_pinklayout.setOnDragListener(this)
        tv.setOnLongClickListener {
            //x.vibrate(2000)
            if(state==0){
                pointA=PointF(tv.x+(tv.width/2),tv.y+(tv.height/2))
                state=1
            }
            else{
                pointB= PointF(tv.x+(tv.width/2),tv.y+(tv.height/2))
                var lvLine=LineView(this)
                ll_pinklayout.addView(lvLine)
                lateinit var mLineView: LineView
                mLineView=lvLine
                mLineView.pointA=pointA
                mLineView.pointB=pointB
                mLineView.draw()
                state=0
            }
            true
        }

    }

    override fun onDrag(view:View, dragEvent: DragEvent):Boolean {
        Log.d(TAG, "onDrag: view->$view\n DragEvent$dragEvent")
        when (dragEvent.action) {
            DragEvent.ACTION_DRAG_ENDED -> {
                Log.d(TAG, "onDrag: ACTION_DRAG_ENDED ")
                return true
            }
            DragEvent.ACTION_DRAG_EXITED -> {
                Log.d(TAG, "onDrag: ACTION_DRAG_EXITED")
                return true
            }
            DragEvent.ACTION_DRAG_ENTERED -> {
                Log.d(TAG, "onDrag: ACTION_DRAG_ENTERED")
                return true
            }
            DragEvent.ACTION_DRAG_STARTED -> {
                Log.d(TAG, "onDrag: ACTION_DRAG_STARTED")
                val tvState = dragEvent.localState as View
                val tvParent = tvState.parent as ViewGroup
                tvParent.removeView(tvState)
                return true
            }
            DragEvent.ACTION_DROP -> {
                Log.d(TAG, "onDrag: ACTION_DROP")
                val tvState = dragEvent.localState as View
                Log.d(TAG, "onDrag:viewX" + dragEvent.x + "viewY" + dragEvent.y)
                Log.d(TAG, "onDrag: Owner->" + tvState.parent)
                //val tvParent = tvState.parent as ViewGroup
                val container = view as RelativeLayout
                //container.addView(tvState)
               // tvParent.removeView(tvState)
                tvState.x = dragEvent.x
                tvState.y = dragEvent.y
                view.addView(tvState)
                view.setVisibility(View.VISIBLE)
                return true
            }
            DragEvent.ACTION_DRAG_LOCATION -> {
                Log.d(TAG, "onDrag: ACTION_DRAG_LOCATION")
                return true
            }
            else -> return false
        }
    }
    override fun onTouch(view:View, motionEvent: MotionEvent):Boolean {
        Log.d(TAG, "onTouch: view->view$view\n MotionEvent$motionEvent")
        if (clickstate==1){
            return false
        }
        return if (motionEvent.action === MotionEvent.ACTION_DOWN) {
            val dragShadowBuilder = View.DragShadowBuilder(view)
            view.startDrag(null, dragShadowBuilder, view, 0)
            true
        } else {
            false
        }
    }
}
