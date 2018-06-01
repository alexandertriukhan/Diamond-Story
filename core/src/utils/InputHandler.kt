package utils

import com.badlogic.gdx.input.GestureDetector.GestureListener
import com.badlogic.gdx.math.Vector2
import screens.GameScreen


class InputHandler(private val gs : GameScreen) : GestureListener {

    private val lastTouch = Vector2(0f,0f)

    override fun fling(velocityX: Float, velocityY: Float, button: Int): Boolean {
        val direction: String
        if (Math.abs(velocityX) > Math.abs(velocityY)) {
            if (velocityX > 0) {
                direction = "right"
            } else {
                direction = "left"
            }
        } else {
            if (velocityY > 0) {
                direction = "down"
            } else {
                direction = "up"
            }
        }
        gs.onSwipe(lastTouch,direction)
        return true
    }

    override fun zoom(initialDistance: Float, distance: Float): Boolean {
        return true
    }

    override fun pan(x: Float, y: Float, deltaX: Float, deltaY: Float): Boolean {
        return true
    }

    override fun pinchStop() {

    }

    override fun tap(x: Float, y: Float, count: Int, button: Int): Boolean {
        gs.onClick()
        return true
    }

    override fun panStop(x: Float, y: Float, pointer: Int, button: Int): Boolean {
        return true
    }

    override fun longPress(x: Float, y: Float): Boolean {
        return true
    }

    override fun touchDown(x: Float, y: Float, pointer: Int, button: Int): Boolean {
        lastTouch.set(x,y)
        return true
    }

    override fun pinch(initialPointer1: Vector2?, initialPointer2: Vector2?, pointer1: Vector2?, pointer2: Vector2?): Boolean {
        return true
    }


}