package com.hnam.recyclerview

/**
 * Created by Huu Hoang on 4/19/19.
 */

import android.content.Context
import android.content.res.Resources

fun Context.drawableByName(resIdName: String?): Int {
    resIdName?.let {
        return resources.getIdentifier(it, "drawable", packageName)
    }
    throw Resources.NotFoundException()
}