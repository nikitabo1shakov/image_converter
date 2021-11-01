package com.nikitabolshakov.image_converter

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.nikitabolshakov.image_converter.databinding.ActivityMainBinding
import java.io.ByteArrayOutputStream
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bitmap = assetsToBitmap("dog.jpg")

        bitmap?.let {
            binding.bitmapImage.setImageBitmap(bitmap)
        }

        binding.convertButton.setOnClickListener {
            if (bitmap != null) {

                val compressedBitmap = bitmap.compress(Bitmap.CompressFormat.PNG)

                binding.compressedImage.setImageBitmap(compressedBitmap)
                binding.text.text = getString(R.string.text_convert_success)

            } else {
                Log.e("666", "Error")
            }
        }
    }

    private fun assetsToBitmap(fileName: String): Bitmap? {
        return try {
            val stream = assets.open(fileName)
            BitmapFactory.decodeStream(stream)
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }
}

fun Bitmap.compress(
    format: Bitmap.CompressFormat = Bitmap.CompressFormat.JPEG,
    quality: Int = 100
): Bitmap {

    val stream = ByteArrayOutputStream()

    this.compress(format, quality, stream)

    val byteArray = stream.toByteArray()

    return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
}