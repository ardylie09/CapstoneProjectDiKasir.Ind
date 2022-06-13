package com.dicoding.dikasirind

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.dicoding.dikasirind.ml.FoodModelOptimize
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.nio.ByteBuffer

class StockActivity : AppCompatActivity() {
    lateinit var prediksi : FloatingActionButton
    lateinit var img_view : ImageView
    lateinit var text_view : TextView
    lateinit var bitmap: Bitmap
    lateinit var camerabtn : FloatingActionButton

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 100){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "Camera permission granted", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stock)
        prediksi = findViewById(R.id.btn_OK)
        text_view = findViewById(R.id.textPrediksi)
        camerabtn = findViewById(R.id.btn_Update)

        val labels = application.assets.open("food.txt").bufferedReader()
            .use{ it.readText() }.split("\n")

        prediksi.setOnClickListener(View.OnClickListener {
            val model = FoodModelOptimize.newInstance(this)

            var resized = Bitmap.createScaledBitmap(bitmap, 224, 224, true)

            val buffer = ByteBuffer.allocate(448 * 448 * 3 )
            buffer.rewind()
            resized.copyPixelsToBuffer(buffer)

            val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.FLOAT32)
            inputFeature0.loadBuffer(buffer)

            val outputs = model.process(inputFeature0)
            val outputFeature0 = outputs.outputFeature0AsTensorBuffer

            Log.d("shape", buffer.toString())
            Log.d("shape", inputFeature0.buffer.toString())

            var max = getMax(outputFeature0.floatArray)

            text_view.setText(labels[max])

            model.close()
            prediksi.isEnabled = false
            prediksi.alpha = 0.0f
            camerabtn.isEnabled = true
            camerabtn.alpha = 1.0f
        })

        camerabtn.setOnClickListener(View.OnClickListener {
            prediksi.isEnabled = true
            prediksi.alpha = 1.0f
            camerabtn.isEnabled = false
            camerabtn.alpha = 0.0f
            var camera : Intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(camera, 200)
        })


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 250){
            img_view.setImageURI(data?.data)

            var uri : Uri?= data?.data
            bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, uri)
        }
        else if(requestCode == 200 && resultCode == Activity.RESULT_OK){
            bitmap = data?.extras?.get("data") as Bitmap
            img_view.setImageBitmap(bitmap)
        }

    }

    fun getMax(arr:FloatArray) : Int{
        var ind = 0;
        var min = 0.0f;

        for(i in 0..1000)
        {
            if(arr[i] > min)
            {
                min = arr[i]
                ind = i;
            }
        }
        return ind
    }
}