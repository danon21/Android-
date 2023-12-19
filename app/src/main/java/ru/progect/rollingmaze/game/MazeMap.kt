package ru.progect.rollingmaze.game

import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Point
import android.graphics.PointF
import android.graphics.Rect
import android.util.Log
import android.util.Size
import java.lang.Float.min
import kotlin.concurrent.thread
import kotlin.random.Random

class MazeMap(resources: Resources,
              displaySize: Size) {
    private val minCellSizeInch: Float = 0.16f
    private val minPadding: Int = 5
    private val sTimeMillis: Long = 15

    var cellSize: Int = 0
        private set
    var nRows: Int = 0
        private set
    var nCols: Int = 0
        private set
    var padding: Point = Point(0, 0)
        private set
    var wallHeight: Int = 0
        private set
    var startCell: Point = Point(0, 0)
        private set
    var startPoint: Point = Point(0, 0)
        private set
    var isGenerated: Boolean = false
        private set

    private var passages: HashSet<Point> = hashSetOf()

    init {
        nCols = ((displaySize.width - 2*minPadding) / resources.displayMetrics.xdpi / minCellSizeInch).toInt()
        if (nCols % 2 == 0) {
            nCols -= 1
        }
        nRows = ((displaySize.height - 2*minPadding) / resources.displayMetrics.ydpi / minCellSizeInch).toInt()
        if (nRows % 2 == 0) {
            nRows -= 1
        }

        cellSize = min((displaySize.width - 2*minPadding) / nCols.toFloat(), (displaySize.height - 2*minPadding) / nRows.toFloat()).toInt()
        wallHeight = (cellSize / 4f).toInt()

        padding.x = (displaySize.width - cellSize*nCols) / 2
        padding.y = (displaySize.height - cellSize*nRows) / 2

        Log.wtf("---", "${displaySize.width}   $nCols")
        Log.wtf("---", "${displaySize.height}   $nRows")
    }

    fun draw(canvas: Canvas, colorCell: Int, colorWall: Int) {
        val paintCell = Paint()
        val paintWall = Paint()
        paintCell.color = colorCell
        paintWall.color = colorWall
//        paint.style = Paint.Style.STROKE;
//        paint.strokeWidth = 10f;

        for (col in 0 until nCols) {
            for (row in 0 until nRows) {
                val left = padding.x + col*cellSize.toFloat()
                val top = padding.y + row*cellSize.toFloat()
                if (!passages.contains(Point(col, row))) {
                    canvas.drawRect(left, top, left+cellSize, top+cellSize, paintCell)
                } else if (!passages.contains(Point(col, row-1))){
                    canvas.drawRect(left, top, left+cellSize, top+wallHeight, paintWall)
                }
            }
        }
    }

    fun generate(touchPoint: PointF) {
        val col = ((touchPoint.x - padding.x) / cellSize).toInt()
        val row = ((touchPoint.y - padding.y) / cellSize).toInt()
        startCell = Point(col, row)
        startPoint = Point(padding.x + ((col + 0.5)*cellSize).toInt(), padding.y + ((row + 0.5)*cellSize).toInt())

        thread {
            if (!isGenerated && col > 0 && col < nCols-1 && row > 0 && row < nRows-1 && col % 2 == 1 && row % 2 == 1) {
                val visitCells: HashSet<Point> = hashSetOf(Point(col, row))

                while (visitCells.isNotEmpty()) {
                    val randIdx = Random.nextInt(visitCells.size)
                    val cell = visitCells.elementAt(randIdx)
                    visitCells.remove(cell)

                    passages.add(cell)

                    connect(cell)
                    addVisitCells(cell, visitCells)
                    Thread.sleep(sTimeMillis)
                }
                isGenerated = true
            }
        }
    }

    fun collisionCheck(rect: Rect): MutableList<Rect> {
        val col = (rect.centerX() - padding.x) / cellSize
        val row = (rect.centerY() - padding.y) / cellSize

        var otherRect: Rect
        val collisionList: MutableList<Rect> = mutableListOf()
        for (otherCol in col-1..col+1) {
            for (otherRow in row - 1..row + 1) {
                otherRect = Rect(
                    otherCol * cellSize + padding.x,
                    otherRow * cellSize + padding.y,
                    otherCol * cellSize + cellSize + padding.x,
                    otherRow * cellSize + cellSize + padding.y
                )
                if (!passages.contains(Point(otherCol, otherRow)) && Rect.intersects(rect, otherRect)) {
                    collisionList.add(otherRect)
                }
            }
        }
        return collisionList
    }

    private fun connect(cell: Point) {
        val dirs = listOf(Point(1, 0), Point(-1, 0), Point(0, 1), Point(0, -1))

        for (dir in dirs.shuffled()) {
            val neighbor = Point(cell.x + 2*dir.x, cell.y + 2*dir.y)
            if (isRoad(neighbor)) {
                passages.add(Point(cell.x + dir.x, cell.y + dir.y))
                return
            }
        }
    }

    private fun addVisitCells(cell: Point, visitCells: HashSet<Point>) {
        val dirs = listOf(Point(1, 0), Point(-1, 0), Point(0, 1), Point(0, -1))

        for (dir in dirs) {
            val neighbor = Point(cell.x + 2*dir.x, cell.y + 2*dir.y)
            if (isInsideMaze(neighbor) && !isRoad(neighbor)) {
                visitCells.add(neighbor)
            }
        }
    }

    private fun isRoad(cell: Point): Boolean {
        return isInsideMaze(cell) && passages.contains(cell)
    }

    private fun isInsideMaze(cell: Point): Boolean {
        return cell.x > 0 && cell.x < nCols-1 && cell.y > 0 && cell.y < nRows-1
    }
}