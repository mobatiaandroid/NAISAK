package com.nas.naisak.constants

import android.graphics.Point
import android.view.View

class MyDragShadowBuilder (view: View?) : View.DragShadowBuilder(view) {
    override fun onProvideShadowMetrics(outShadowSize: Point?, outShadowTouchPoint: Point?) {
        outShadowSize!!.set(1, 1)
        outShadowTouchPoint!!.set(0, 0)
        super.onProvideShadowMetrics(outShadowSize, outShadowTouchPoint)
    }
}