package com.example.notey.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.notey.R
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException

 fun Context.loadImageFromStorage(path:String) : Bitmap {
    try
    {
        val f = File(path, "profile.jpg")
        val b = BitmapFactory.decodeStream(FileInputStream(f))
        return  b
    }
    catch (e: FileNotFoundException) {
        e.printStackTrace()
        return BitmapFactory.decodeResource(this.resources, R.drawable.user);
    }
}