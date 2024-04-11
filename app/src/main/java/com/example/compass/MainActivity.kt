package com.example.compass

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.compass.databinding.ActivityMainBinding
import android.widget.ImageView

class MainActivity : AppCompatActivity(), SensorEventListener {

    //объявление ViewBinding
    //также в build.gradle.kts добавить строки
    lateinit var bindingClass: ActivityMainBinding

    var manager:SensorManager? = null
    var current_degree:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        //setContentView(R.layout.activity_main)

//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        manager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        //объявление ViewBinding, 2 строчки
        bindingClass = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)
    }

    override fun onResume() {
        super.onResume()
        manager?.registerListener(this, manager?.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SensorManager.SENSOR_DELAY_GAME)
    }

    override fun onPause() {
        super.onPause()
        manager?.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        val degree:Int = event?.values?.get(0)?.toInt()!!
        //применение ViewBinding
        bindingClass.tvDegree.text = degree.toString()

        val rotation = RotateAnimation(current_degree.toFloat(),(-degree).toFloat(),
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f)
        rotation.duration = 210
        rotation.fillAfter = true
        current_degree = -degree
        bindingClass.imDinamic.startAnimation(rotation)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }
}