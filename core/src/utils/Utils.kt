package utils

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.Button
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener

fun Button.setOnClickListener(callback: () -> Unit) {
    addListener(object : ClickListener() {
        override fun clicked(event: InputEvent, x: Float, y: Float) {
            callback()
        }
    })
}