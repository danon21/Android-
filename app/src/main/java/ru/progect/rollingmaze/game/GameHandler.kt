package ru.progect.rollingmaze.game

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import android.graphics.PointF
import android.graphics.Rect
import android.util.Log
import android.util.Size
import android.view.MotionEvent
import android.view.SurfaceView
import android.view.View.OnTouchListener


class GameHandler(
    context: Context,
    private val resources: Resources,
    private val view: SurfaceView,
    private val displaySize: Size): Runnable {

    // Class variables
    private var gameThread: Thread? = null


    // Properties
    var isRunning: Boolean = false
    private var deltaTime: Float = 1 / 60f


    // Game Objects
    private var mazeMap: MazeMap   = MazeMap(resources, displaySize)
    private var ball: Ball?         = null

    @SuppressLint("ClickableViewAccessibility")
    private val handleTouch = OnTouchListener { _, event ->
        val x = event.x.toInt()
        val y = event.y.toInt()
        if (event.action == MotionEvent.ACTION_DOWN) {
            Log.i("TAG", "Touched: ($x, $y)")
            mazeMap.generate(PointF(x.toFloat(), y.toFloat()))
            if (!mazeMap.isGenerated) {
                ball = Ball(context, mazeMap.cellSize / 2 - 7, PointF(mazeMap.startPoint))
            }
        }
        true
    }
    
    // Class methods
    override fun run() {
        view.setOnTouchListener(handleTouch)

//        var timePrev = System.nanoTime()
        while (isRunning) {
//            val timeCurr = System.nanoTime()
//            deltaTime = (timeCurr - timePrev).toFloat()*1e-9f
//            timePrev = timeCurr
            this.update()
            this.draw()
        }
    }

    private fun update() {
//        ball?.update(deltaTime)
        if (mazeMap.isGenerated)
            resolveCollision()

//        ball?.let {
//            if (it.position.x > displaySize.width - ballRadius) {
//                it.position.x = (displaySize.width - ballRadius).toInt()
//                it.inverseVelX()
//            } else if (it.position.x < ballRadius) {
//                it.position.x = ballRadius.toInt()
//                it.inverseVelX()
//            }
//            if (it.position.y > displaySize.height - ballRadius) {
//                it.position.y = (displaySize.height - ballRadius).toInt()
//                it.inverseVelY()
//            } else if (it.position.y < ballRadius) {
//                it.position.y = ballRadius.toInt()
//                it.inverseVelY()
//            }
//        }
    }

    private fun resolveCollision() {
        ball?.let {
            it.moveX(deltaTime)
            var rect = Rect((it.position.x - it.r).toInt(),
                (it.position.y - it.r).toInt(), (it.position.x + it.r).toInt(), (it.position.y + it.r).toInt()
            )
            var collideRects = mazeMap.collisionCheck(rect)
            for (otherRect in collideRects) {
                if (it.velocity.x > 0) {
                    it.position.x = (otherRect.left - it.r).toFloat()
                } else if (it.velocity.x < 0) {
                    it.position.x = (otherRect.right + it.r).toFloat()
                }
            }
            if (collideRects.isNotEmpty()) {
                it.inverseVelX()
            }
            it.moveY(deltaTime)
            rect = Rect((it.position.x - it.r).toInt(),
                (it.position.y - it.r).toInt(), (it.position.x + it.r).toInt(), (it.position.y + it.r).toInt()
            )
            collideRects = mazeMap.collisionCheck(rect)
            for (otherRect in collideRects) {
                if (it.velocity.y > 0) {
                    it.position.y = (otherRect.top - it.r).toFloat()
                } else if (it.velocity.y < 0) {
                    it.position.y = (otherRect.bottom + it.r).toFloat()
                }
            }
            if (collideRects.isNotEmpty()) {
                it.inverseVelY()
            }
        }
    }

    private fun draw() {
        if (view.holder.surface.isValid) {
            val canvas = view.holder.lockCanvas()
            val paint = Paint()

            canvas.drawColor(Color.argb(255, 255, 255, 255))

            mazeMap.draw(canvas,
                Color.argb(255, 10, 110, 230),
                Color.argb(255, 30, 20, 190))

            if (mazeMap.isGenerated) {
                ball?.draw(canvas, Color.argb(255, 255, 128, 80))
            }

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