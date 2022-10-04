package com.santino.rpgandroidgame

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import com.santino.rpgandroidgame.databinding.ActivityMainBinding
import java.lang.Thread.sleep

class MainActivity : Activity() {

    private lateinit var binding: ActivityMainBinding
    private var x: Float = 100F
    private var y: Float = 20F
    private var vx: Float = 10F
    private var vy: Float = 0F
    private var fps = (1000/60).toLong()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initListeners()
        updateDrawing()

        Thread {
            kotlin.run {
                while (true) {
                    if (x >= 1500F || x <= 0F || y >= 850 || y <= 0) {
                        Handler(Looper.getMainLooper()).post {
                            showGameOver()
                        }
                        break
                    } else {
                        x += vx
                        y += vy
                    }
                    sleep(fps)
                    updateDrawing()
                }
            }
        }.start()

    }

    private fun updateDrawing() {
        val bitmap = Bitmap.createBitmap(
            1500,
            850,
            Bitmap.Config.ARGB_8888
        )

        val canvas = Canvas(bitmap).apply {
            drawColor(Color.parseColor("#A2A2D0"))
        }

        val paint = Paint().apply {
            isAntiAlias = true
            color = Color.parseColor("#333399")

            this.strokeWidth = 50F
            style = Paint.Style.STROKE
        }

        canvas.drawPoint(
            x,
            y,
            paint
        )
        binding.imgScenario.setImageBitmap(bitmap)
    }

    private fun showGameOver() {
        Toast.makeText(this, "GameOver!", Toast.LENGTH_LONG).show()
        binding.btnRestart.visibility = View.VISIBLE
    }

    private fun initListeners() {
        binding.btnUp.setOnClickListener { vy = -10F ; vx = 0F }
        binding.btnDown.setOnClickListener { vy = 10F ; vx = 0F }
        binding.btnLeft.setOnClickListener { vx = -10F ; vy = 0F }
        binding.btnRight.setOnClickListener { vx = 10F ; vy = 0F }

        binding.btnRestart.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}