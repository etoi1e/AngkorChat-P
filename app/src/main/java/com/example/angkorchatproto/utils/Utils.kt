package com.example.angkorchatproto.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.res.Resources
import android.content.res.TypedArray
import android.graphics.Rect
import android.util.DisplayMetrics
import com.example.angkorchatproto.R
import kotlin.math.roundToInt


object Utils {
    fun pxToDp(resources: Resources, px: Int): Int {
        val metrics: DisplayMetrics = resources.displayMetrics
        return (px / (metrics.densityDpi / 160f)).roundToInt()
    }

    @SuppressLint("DiscouragedApi", "InternalInsetResource")
    fun getNavigationBarHeight(activity: Activity): Int {
        val resourceId = activity.resources.getIdentifier("navigation_bar_height", "dimen", "android")
        return if (resourceId > 0) {
            activity.resources.getDimensionPixelSize(resourceId)
        } else 0
    }

    @SuppressLint("InternalInsetResource", "DiscouragedApi")
    fun getStatusBarHeight(activity: Activity): Int {
        val resourceId = activity.resources.getIdentifier("status_bar_height", "dimen", "android")
        return if (resourceId > 0) {
            activity.resources.getDimensionPixelSize(resourceId)
        } else 0
    }

    fun getSoftKeyHeight(activity: Activity): Int {
        val rect = Rect()
        val rootView = activity.window.decorView.rootView
        rootView.getWindowVisibleDisplayFrame(rect)
        val screenHeight = rootView.height
        val visibleBottom = rect.bottom
        return screenHeight - visibleBottom
    }

    fun getSoftKeyAndNavigationHeight(activity: Activity): Int {
        return getSoftKeyHeight(activity) + getNavigationBarHeight(activity)
    }

    fun getSoftKeyAndStatusBarHeight(activity: Activity): Int {
        return getSoftKeyHeight(activity) + getStatusBarHeight(activity)
    }

    fun getSoftKeyAndNavigationAndStatusBarHeight(activity: Activity): Int {
        return getSoftKeyHeight(activity) + getNavigationBarHeight(activity) + getStatusBarHeight(activity)
    }

    fun typedArrayToArrayList(typedArray: TypedArray): ArrayList<Int> {
        val arrayList = arrayListOf<Int>()
        for (i in 0 until typedArray.length()) {
            val resourceId = typedArray.getResourceId(i, 0)
            arrayList.add(resourceId)
        }
        return arrayList
    }
}