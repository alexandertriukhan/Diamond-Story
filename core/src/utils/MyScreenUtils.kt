package utils

import com.badlogic.gdx.Gdx

class MyScreenUtils {

    // TODO: add similar calculations for width
    val initScreenWidth = Gdx.graphics.width.toFloat()
    val initScreenHeight = Gdx.graphics.height.toFloat()
    val initXOffset = 0f

    var screenWidthFullHD = Gdx.graphics.height.toFloat() * 0.5625f
    var xOffsetFullHD = ((Gdx.graphics.width.toFloat() - screenWidthFullHD) / 2f)

    init {
        if (Gdx.graphics.width.toFloat() < screenWidthFullHD) {
            screenWidthFullHD = Gdx.graphics.width.toFloat()
            xOffsetFullHD = 0f
        }
    }

}