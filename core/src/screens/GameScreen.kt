package screens

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Screen
import enums.GridType
import gameobjects.JewelGrid

class GameScreen : Screen {

    private val screenWidth = Gdx.graphics.width.toFloat()
    private val screenHeight = Gdx.graphics.height.toFloat()
    private val gameGrid = JewelGrid(10, GridType.SQUARE)

    override fun hide() {

    }

    override fun show() {

    }

    override fun render(delta: Float) {

    }

    override fun pause() {

    }

    override fun resume() {

    }

    override fun resize(width: Int, height: Int) {

    }

    override fun dispose() {

    }
}