package gameobjects

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.graphics.g2d.TextureRegion

class Cell(var isPlaying : Boolean, var jewel : Jewel, var tileTexture : TextureRegion) {

    fun draw(batch: Batch, x : Float, y : Float, size : Float, delta : Float) {
        if (isPlaying) {
            batch.draw(tileTexture, x, y, size, size)
            jewel.draw(batch, x, y, size, delta)
        }
    }

}