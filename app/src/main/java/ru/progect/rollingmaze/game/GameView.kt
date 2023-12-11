package ru.progect.rollingmaze.game

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import android.view.SurfaceView
import kotlin.math.abs


class GameView(context: Context,
               private val size: Point):
    SurfaceView(context), Runnable, SensorEventListener {
    // Class variables
    private var gameThread: Thread? = null
    private var canvas = Canvas()
    private var paint: Paint = Paint()
    private var sensorManager: SensorManager = context.getSystemService(Context.SENSOR_SERVICE)  as SensorManager

    init {
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL)
    }

    private var isRunning: Boolean = false

    private var deltaTime: Float = 0.0f

    private var ax: Float = 0.0f
    private var ay: Float = 0.0f

    private var vx: Float = 0.0f
    private var vy: Float = 0.0f
    private var xx: Float = size.x / 2.0f
    private var yy: Float = size.y / 2.0f

    
    // Class methods
    override fun run() {
        var startFrameTime = System.currentTimeMillis()

        while (isRunning) {
            val nextFrameTime = System.currentTimeMillis()
            this.deltaTime = (nextFrameTime - startFrameTime)*1e-3f

            this.update()
            this.draw()

            startFrameTime = nextFrameTime
        }
    }

    private fun update() {
        val aCoef = 500
        val r = 0.6f

        if (abs(ax) > 0.8) {
            vx += ax*deltaTime*aCoef
        }
        if (abs(ay) > 0.8) {
            vy += ay*deltaTime*aCoef
        }

        xx += vx*deltaTime
        yy += vy*deltaTime

        if (xx > size.x - 50) {
            xx = size.x.toFloat() - 50.0f
            vx = -vx*r
        } else if (xx < 50) {
            xx = 50.0f
            vx = -vx*r
        }
        if (yy > size.y - 50) {
            yy = size.y.toFloat() - 50.0f
            vy = -vy*r
        } else if (yy < 50) {
            yy = 50.0f
            vy = -vy*r
        }
    }

    private fun draw() {
        if (holder.surface.isValid) {
            canvas = holder.lockCanvas()

            canvas.drawColor(Color.argb(255, 255, 255, 255))

            paint.color = Color.argb(255, 255, 0, 0)
            canvas.drawCircle(xx, yy, 50f, paint)

            paint.color = Color.argb(255, 255, 0, 255)
            paint.textSize = 70f
            canvas.drawText("FPS: " + (1.0 / deltaTime).toString(), 20f, 80f, paint)
            canvas.drawText("Dt: $deltaTime", 20f, 150f, paint)
            canvas.drawText("Ax: $ax", 20f, 220f, paint)
            canvas.drawText("Ay: $ay", 20f, 290f, paint)
            canvas.drawText("Vx: $vx", 20f, 360f, paint)
            canvas.drawText("Vy: $vy", 20f, 430f, paint)
            canvas.drawText("X: $xx", 20f, 500f, paint)
            canvas.drawText("Y: $yy", 20f, 570f, paint)

            holder.unlockCanvasAndPost(canvas)

        }
    }

    fun start() {
        isRunning = true
        gameThread = Thread(this)
        gameThread?.start()
    }

    fun pause() {
        isRunning = false
        try {
            gameThread?.join()
        } catch (e: InterruptedException) {
            Log.e("Error:", "joining thread")
        }
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == Sensor.TYPE_ACCELEROMETER){
            ax = -event.values[0];
            ay = event.values[1];
        }
    }

    override fun onAccuracyChanged(event: Sensor?, accuracy: Int) {}
}