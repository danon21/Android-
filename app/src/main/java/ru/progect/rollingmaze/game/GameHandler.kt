package ru.progect.rollingmaze.game

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.os.SystemClock
import android.util.Log
import android.view.SurfaceView
import java.sql.Time
import java.time.Instant


class GameHandler(
    context: Context,
    private val view: SurfaceView): Runnable {

    // Class variables
    private var gameThread: Thread? = null


    // Properties
    private var isRunning: Boolean = false
    private var deltaTime: Float = 0f

    // Settings
    private val ballRadius = 70f

    // Game Objects
    private var ball: Ball = Ball(context, ballRadius, PointF(view.width / 2f+100, view.height / 2f + 100))
    
    // Class methods
    override fun run() {
        var timePrev = SystemClock.elapsedRealtimeNanos()
        while (isRunning) {
            val timeCurr = SystemClock.elapsedRealtimeNanos()
            deltaTime = (timeCurr - timePrev)*1e-9f
            timePrev = timeCurr

//            if(deltaTime > 0.01)
//                Log.wtf("time", deltaTime.toString())

            this.update()
            this.draw()
        }
    }

    private fun update() {
        ball.update(deltaTime)

        if (ball.position.x > view.width - ballRadius) {
            ball.position.x = view.width - ballRadius
            ball.inverseVelX()
        } else if (ball.position.x < ballRadius) {
            ball.position.x = ballRadius
            ball.inverseVelX()
        }
        if (ball.position.y > view.height - ballRadius) {
            ball.position.y = view.height - ballRadius
            ball.inverseVelY()
        } else if (ball.position.y < ballRadius) {
            ball.position.y = ballRadius
            ball.inverseVelY()
        }
    }

    private fun draw() {
        if (view.holder.surface.isValid) {
            val canvas = view.holder.lockCanvas()
            val paint = Paint()

            canvas.drawColor(Color.argb(255, 255, 255, 255))

            ball.draw(canvas, Color.argb(255, 255, 128, 80))

            view.holder.unlockCanvasAndPost(canvas)
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
}