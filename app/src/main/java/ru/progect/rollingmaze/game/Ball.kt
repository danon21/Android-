package ru.progect.rollingmaze.game

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager

class Ball(context: Context,
           private val r: Float,
           pos: PointF
): SensorEventListener {
    private val sensorManager: SensorManager = context.getSystemService(Context.SENSOR_SERVICE)  as SensorManager
    init {
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL)
    }

    var acceleration: PointF    = PointF(0f, 0f)
    private var prevPos: PointF = pos
    private var currPos: PointF = pos

    var position: PointF
        get() = currPos
        set(value) {
          currPos = value
          prevPos = value
        }

    fun update(dt: Float) {
        val aCoef = 2000

        var nextPosX = 2*currPos.x - prevPos.x
        var nextPosY = 2*currPos.y - prevPos.y
        if (acceleration.length() > 0.2) {
            nextPosX += 0.5f*acceleration.x*dt*dt*aCoef
            nextPosY += 0.5f*acceleration.y*dt*dt*aCoef
        }
        prevPos = currPos
        currPos = PointF(nextPosX, nextPosY)
    }

    fun draw(canvas: Canvas, color: Int) {
        val paint = Paint()
        paint.color = color
        canvas.drawCircle(currPos.x, currPos.y, r, paint)
    }

    fun inverseVelX() {
        val temp = currPos.x
        currPos.x = prevPos.x
        prevPos.x = temp
    }

    fun inverseVelY() {
        val temp = currPos.y
        currPos.y = prevPos.y
        prevPos.y = temp
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == Sensor.TYPE_ACCELEROMETER){
            acceleration = PointF(-event.values[0], event.values[1])
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {}
}