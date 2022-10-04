package com.santino.rpgandroidgame

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.santino.rpgandroidgame.databinding.ActivityMainBinding
import java.lang.Thread.sleep

class MainActivity : Activity() {

    private lateinit var binding: ActivityMainBinding
    private var x: Float = 100F
    private var y: Float = 20F
    private var vx: Float = 10F
    private var vy: Float = 0F

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initListeners()
        // show initial drawing on image view
        updateDrawing()

        Thread {
            kotlin.run {
                while (true) {
                    if (x >= 1500F || x <= 0F || y >= 850 || y <= 0) {
                        println("GAMEOVER")
                        break
                    } else {
                        x += vx
                        y += vy
                    }
                    sleep(50L)
                    updateDrawing()
                }
            }
        }.start()

    }

    // function to update drawing
    private fun updateDrawing() {
        val bitmap = Bitmap.createBitmap(
            1500,
            850,
            Bitmap.Config.ARGB_8888
        )

        val canvas = Canvas(bitmap).apply {
            drawColor(Color.parseColor("#A2A2D0"))
        }

        // paint to draw point / dot on canvas
        val paint = Paint().apply {
            isAntiAlias = true
            color = Color.parseColor("#333399")

            // point / dot width
            this.strokeWidth = 50F
            style = Paint.Style.STROKE
        }

        // finally, draw point / dot on canvas
        canvas.drawPoint(
            x,
            y,
            paint
        )
        binding.imgScenario.setImageBitmap(bitmap)
    }

    private fun showGameOver() {
        Toast.makeText(this, "GameOver!", Toast.LENGTH_LONG).show()
    }

    private fun initListeners() {
        binding.btnUp.setOnClickListener { vy = -10F ; vx = 0F }
        binding.btnDown.setOnClickListener { vy = 10F ; vx = 0F }
        binding.btnLeft.setOnClickListener { vx = -10F ; vy = 0F }
        binding.btnRight.setOnClickListener { vx = 10F ; vy = 0F }
    }
}