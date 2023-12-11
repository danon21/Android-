package ru.progect.rollingmaze

import android.graphics.Point
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.progect.rollingmaze.game.GameView
import ru.progect.rollingmaze.ui.theme.RollingMazeTheme

class MainActivity : ComponentActivity() {
    private var gameView: GameView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            RollingMazeTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    StartButton { startGame() }
                }
            }
        }
    }

    fun startGame() {
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        this.gameView = GameView(this, size)
        setContentView(this.gameView)
        this.gameView?.start()
    }

    override fun onResume() {
        super.onResume()
        this.gameView?.start()
    }

    override fun onPause() {
        super.onPause()
        this.gameView?.pause()
    }
}

@Composable
fun StartButton(onClick: () -> Unit) {
    Button(onClick = {onClick()}) {
        Text(text = "Start")
    }
}