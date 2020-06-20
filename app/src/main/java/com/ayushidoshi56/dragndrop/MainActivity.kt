package com.ayushidoshi56.dragndrop

import android.content.ClipData
import android.content.ClipDescription
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.DragEvent
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity: AppCompatActivity(), View.OnTouchListener, View.OnDragListener {

    var mainscreenid=0
    var c=0
    private val TAG = MainActivity::class.java.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        createLayout()
//        btnadd.setOnClickListener {
//            addTV()
//            c++
//        }
        setListeners()
    }

    private fun addTV()
    {
        val tv=TextView(this)
        tv.text="hi"
        var tvid = resources.getIdentifier("tv${c}", "id", packageName)
        val mainscreen=findViewById<LinearLayout>(mainscreenid)
        mainscreen.addView(tv)
        tv.setOnTouchListener(this)
        ll_pinklayout.setOnDragListener(this)
    }
    private fun createLayout()
    {
        var screenid = resources.getIdentifier("screen", "id", packageName)
        val screen=findViewById<LinearLayout>(screenid)

        val mainscreen = RelativeLayout(this)
        mainscreen.layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
         mainscreenid = resources.getIdentifier("mainscreen", "id", packageName)
        mainscreen.id=mainscreenid
        screen.addView(mainscreen)
    }

    private fun setListeners() {
        tv_dropdrop.setOnTouchListener(this)
        //ll_pinklayout.setOnDragListener(this)
        tv_drop.setOnTouchListener(this)

        ll_pinklayout.setOnDragListener(this)
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
                return true
            }
            DragEvent.ACTION_DROP -> {
                Log.d(TAG, "onDrag: ACTION_DROP")
                val tvState = dragEvent.localState as View
                Log.d(TAG, "onDrag:viewX" + dragEvent.x + "viewY" + dragEvent.y)
                Log.d(TAG, "onDrag: Owner->" + tvState.parent)
                val tvParent = tvState.parent as ViewGroup
                tvParent.removeView(tvState)
                val container = view as RelativeLayout
                container.addView(tvState)
                tvParent.removeView(tvState)
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
        return if (motionEvent.action === MotionEvent.ACTION_DOWN) {
            val dragShadowBuilder = View.DragShadowBuilder(view)
            view.startDrag(null, dragShadowBuilder, view, 0)
            true
        } else {
            false
        }
    }
}
