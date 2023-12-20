package ru.progect.rollingmaze.game

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import android.graphics.PointF
import android.graphics.RectF
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.core.graphics.toPoint
import androidx.core.graphics.toPointF
import kotlin.math.abs
import kotlin.math.sign

class Ball(context: Context,
           val r: Int,
           pos: PointF
): SensorEventListener {
    private val sensorManager: SensorManager = context.getSystemService(Context.SENSOR_SERVICE)  as SensorManager
    init {
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL)
    }

    var acceleration: PointF    = PointF(0f, 0f)
    var velocity: PointF = PointF(0f, 0f)
    var position: PointF = pos

    val maxVelocity: Float = 300f

    var accelerationFactor: Float= 500f
    private val accelerationThreshold = 0.2

    fun update(dt: Float) {
        velocity.x += acceleration.x*dt*accelerationFactor
        if (abs(velocity.x) > maxVelocity)
            velocity.x = sign(velocity.x) * maxVelocity
        velocity.y += acceleration.y*dt*accelerationFactor
        if (abs(velocity.y) > maxVelocity)
            velocity.y = sign(velocity.y) * maxVelocity
        position.x += velocity.x*dt
        position.y += velocity.y*dt
    }

    fun moveX(dt: Float) {
        velocity.x += acceleration.x*dt*accelerationFactor
        if (abs(velocity.x) > maxVelocity)
            velocity.x = sign(velocity.x) * maxVelocity
        position.x += velocity.x*dt
    }

    fun moveY(dt: Float) {
        velocity.y += acceleration.y*dt*accelerationFactor
        if (abs(velocity.y) > maxVelocity)
            velocity.y = sign(velocity.y) * maxVelocity
        position.y += velocity.y*dt
    }

    fun draw(canvas: Canvas, color: Int) {
        val paint = Paint()
        paint.color = Color.argb(200, 125, 128, 128)
        canvas.drawOval(RectF(position.x-r, position.y + r / 4, position.x+r, position.y + r), paint)
        paint.color = color
        canvas.drawCircle(position.x, position.y, r.toFloat(), paint)
    }

    fun inverseVelX(loss: Float = 0.5f) {
        velocity.x *= -loss
    }

    fun inverseVelY(loss: Float = 0.5f) {
        velocity.y *= -loss
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == Sensor.TYPE_ACCELEROMETER){
            acceleration = PointF(-event.values[0], event.values[1])
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {}
}